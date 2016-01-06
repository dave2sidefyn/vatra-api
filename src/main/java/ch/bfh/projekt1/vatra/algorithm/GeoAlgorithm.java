package ch.bfh.projekt1.vatra.algorithm;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import ch.bfh.projekt1.vatra.model.VaTraKey;
import ch.bfh.projekt1.vatra.service.RequestRepository;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.LatLng;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by dave on 02.12.15.
 * <p/>
 * Überprüft, ob es möglich ist, seit dem Letzten Request die neue Ortsangabe in der Zeit zu erreichen.
 * Benötigte Felder:
 * <p/>
 * GOOGLE_API_KEY
 * VATRA_IDENTIFICATION_NUMBER
 * VATRA_GEOLOCATION_LONGITUDE
 * VATRA_GEOLOCATION_LATTITUDE
 */
public class GeoAlgorithm implements Algorithm {


    private static final int FIVE_MIN_IN_SECONDS = 300;
    private static final String GOOGLE_API_KEY = "AIzaSyCmUq4uasZW_bjGU1nIlt-2BRqOntWt9Ho";

    @Override
    public List<VaTraKey> neededKeys() {
        return Arrays.asList(VaTraKey.VATRA_API_KEY, VaTraKey.VATRA_IDENTIFICATION_NUMBER, VaTraKey.VATRA_GEOLOCATION_LONGITUDE, VaTraKey.VATRA_GEOLOCATION_LATTITUDE);
    }

    @Override
    public int check(@Nonnull App app, @Nonnull Request request, @Nonnull CrudRepository... crudRepositories) {
        Optional<Request> lastRequest = findLastRequest(app, request, crudRepositories);
        if (lastRequest.isPresent()) {
            GeoApiContext context = new GeoApiContext().setApiKey(GOOGLE_API_KEY);

            LatLng originLatLng = getLatLngFromRequest(lastRequest.get());
            LatLng destinationLatLng = getLatLngFromRequest(request);
            DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context).origins(originLatLng).destinations(destinationLatLng);

            try {
                Optional<Long> shortestDelta = getShortestDelta(lastRequest, req.await());
                return calcWeight(shortestDelta);
            } catch (Exception e) {
                // Handle error
            }

            req.awaitIgnoreError(); // No checked exception.
        }

        return MIN_WEIGHT;
    }

    private int calcWeight(@Nonnull Optional<Long> shortestDelta) {
        if (!shortestDelta.isPresent() || shortestDelta.get() <= 0) {
            return MIN_WEIGHT;
        }

        //Pro 5 Minuten wir es +1 WEIGHT sein
        long weight = shortestDelta.get() / FIVE_MIN_IN_SECONDS;
        if (weight < MAX_WEIGHT) {
            return (int) weight;
        } else {
            return MAX_WEIGHT;
        }
    }

    @Nonnull
    private Optional<Request> findLastRequest(@Nonnull App app, @Nonnull Request request, @Nonnull CrudRepository[] crudRepositories) {
        for (CrudRepository crudRepository : crudRepositories) {
            if (crudRepository instanceof RequestRepository) {
                List<Request> allByApp = ((RequestRepository) crudRepository).findAllByApp(app);
                return allByApp.stream()
                        .filter(lastRequest -> request.getVatraFields().get(VaTraKey.VATRA_IDENTIFICATION_NUMBER).equals(lastRequest.getVatraFields().get(VaTraKey.VATRA_IDENTIFICATION_NUMBER)))
                        .sorted((o1, o2) -> o1.getCreatedDate().compareTo(o2.getCreatedDate()))
                        .findFirst();
            }
        }
        return Optional.empty();
    }

    @Nonnull
    private Optional<Long> getShortestDelta(@Nonnull Optional<Request> lastRequest, @Nonnull DistanceMatrix results) {
        Optional<Long> shortestDelta = Optional.empty();

        for (DistanceMatrixRow distanceMatrixRow : results.rows) {
            for (DistanceMatrixElement distanceMatrixElement : distanceMatrixRow.elements) {
                long delta = lastRequest.get().getCreatedDate().toInstant().getEpochSecond() - (((new Date()).toInstant().getEpochSecond() - distanceMatrixElement.duration.inSeconds));
                if (!shortestDelta.isPresent() || delta < shortestDelta.get()) {
                    shortestDelta = Optional.of(delta);
                }
            }
        }
        return shortestDelta;
    }

    @Nonnull
    private LatLng getLatLngFromRequest(@Nonnull Request request) {
        Double lng = Double.valueOf(request.getVatraFields().get(VaTraKey.VATRA_GEOLOCATION_LONGITUDE));
        Double lat = Double.valueOf(request.getVatraFields().get(VaTraKey.VATRA_GEOLOCATION_LATTITUDE));
        return new LatLng(lat, lng);
    }
}
