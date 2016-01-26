package ch.bfh.projekt1.vatra.algorithm;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import ch.bfh.projekt1.vatra.model.VaTraKey;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ZeroResultsException;
import com.google.maps.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by dave on 02.12.15.
 * <p/>
 * Überprüft, ob es möglich ist, seit dem Letzten Request die neue Ortsangabe in der Zeit zu erreichen.
 * Benötigte Felder:
 * <p/>
 * GOOGLE_API_KEY
 * VATRA_IDENTIFICATION
 * VATRA_GEOLOCATION_LONGITUDE
 * VATRA_GEOLOCATION_LATTITUDE
 */
public class GeoAlgorithm implements Algorithm {

    private static final Logger log = LoggerFactory.getLogger(GeoAlgorithm.class);

    private static final int FIVE_MIN_IN_SECONDS = 300;
    private static final String GOOGLE_API_KEY = "AIzaSyCmUq4uasZW_bjGU1nIlt-2BRqOntWt9Ho";

    @Override
    @Nonnull
    public List<VaTraKey> neededKeys() {
        return Arrays.asList(VaTraKey.VATRA_API_KEY, VaTraKey.VATRA_IDENTIFICATION, VaTraKey.VATRA_GEOLOCATION_LONGITUDE, VaTraKey.VATRA_GEOLOCATION_LATTITUDE);
    }

    @Override
    public int check(@Nonnull App app, @Nonnull Request request, @Nonnull CrudRepository... crudRepositories) {
        String identify = request.getVatraFields().get(VaTraKey.VATRA_IDENTIFICATION);
        Optional<Request> lastRequest = app.findLastValidRequest(identify, crudRepositories);

        if (lastRequest.isPresent()) {
            GeoApiContext context = new GeoApiContext().setApiKey(GOOGLE_API_KEY);

            LatLng originLatLng = getLatLngFromRequest(lastRequest.get());
            LatLng destinationLatLng = getLatLngFromRequest(request);
            DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context).origins(originLatLng).destinations(destinationLatLng);

            DistanceMatrix result;
            try {
                result = req.await();
            } catch (Exception e1) {
                log.debug("GeoAlgorithm weight: " + 10);
                return MAX_WEIGHT;
            }

            Optional<Long> shortestDelta;
            try {
                shortestDelta = getShortestDelta(lastRequest.get(), result);
            } catch (Exception e) {
                log.debug("GeoAlgorithm weight: " + 10);
                return MAX_WEIGHT;
            }
            int weight = calcWeight(shortestDelta);

            log.debug("GeoAlgorithm weight: " + weight);
            return weight;
        }

        log.debug("GeoAlgorithm weight: 0");
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
    public Optional<Long> getShortestDelta(@Nonnull Request lastRequest, @Nonnull DistanceMatrix results) throws Exception {
        Optional<Long> shortestDelta = Optional.empty();

        for (DistanceMatrixRow distanceMatrixRow : results.rows) {
            if (Objects.nonNull(distanceMatrixRow) && Objects.nonNull(distanceMatrixRow.elements)) {
                for (DistanceMatrixElement distanceMatrixElement : distanceMatrixRow.elements) {
                    if (Objects.nonNull(distanceMatrixElement)) {
                        if (distanceMatrixElement.status == DistanceMatrixElementStatus.ZERO_RESULTS) {
                            log.debug("Zero Results in distanceMatrix: " + distanceMatrixElement.toString());
                            throw new ZeroResultsException("Zero results");
                        }

                        long delta = lastRequest.getCreatedDate().toInstant().getEpochSecond() - (((new Date()).toInstant().getEpochSecond() - distanceMatrixElement.duration.inSeconds));
                        if (!shortestDelta.isPresent() || delta < shortestDelta.get()) {
                            shortestDelta = Optional.of(delta);
                        }
                    }
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
