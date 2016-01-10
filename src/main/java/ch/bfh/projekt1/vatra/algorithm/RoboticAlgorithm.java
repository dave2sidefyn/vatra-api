package ch.bfh.projekt1.vatra.algorithm;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import ch.bfh.projekt1.vatra.model.VaTraKey;
import ch.bfh.projekt1.vatra.service.RequestRepository;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by dave on 05.01.15.
 * <p/>
 * Überprüft, ob die Request von einer Maschine stammen (sehr kurze Ausführzeiten)
 * Benötigte Felder:
 * <p/>
 * GOOGLE_API_KEY
 * VATRA_IDENTIFICATION_NUMBER
 */
public class RoboticAlgorithm implements Algorithm {

    @Override
    public List<VaTraKey> neededKeys() {
        return Arrays.asList(VaTraKey.VATRA_API_KEY, VaTraKey.VATRA_IDENTIFICATION_NUMBER);
    }

    @Override
    public int check(@Nonnull App app, @Nonnull Request request, @Nonnull CrudRepository... crudRepositories) {
        Optional<Request> lastRequest = findLastRequest(app, request, crudRepositories);

        if (lastRequest.isPresent()) {
            long delta = request.getCreatedDate().getTime() - lastRequest.get().getCreatedDate().getTime();

            if (delta > 0) {
                long weight = delta / 100;
                if (weight < MAX_WEIGHT) {
                    return (int) weight;
                } else {
                    return MAX_WEIGHT;
                }
            }
        }

        return MIN_WEIGHT;
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
}
