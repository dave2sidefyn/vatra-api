package ch.bfh.projekt1.vatra.algorithm;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import ch.bfh.projekt1.vatra.model.VaTraKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * VATRA_IDENTIFICATION
 */
public class RoboticAlgorithm implements Algorithm {

    private static final Logger log = LoggerFactory.getLogger(RoboticAlgorithm.class);

    @Override
    @Nonnull
    public List<VaTraKey> neededKeys() {
        return Arrays.asList(VaTraKey.VATRA_API_KEY, VaTraKey.VATRA_IDENTIFICATION);
    }

    @Override
    public int check(@Nonnull App app, @Nonnull Request request, @Nonnull CrudRepository... crudRepositories) {
        String identify = request.getVatraFields().get(VaTraKey.VATRA_IDENTIFICATION);
        Optional<Request> lastRequest = app.findLastValidRequest(identify, crudRepositories);

        if (lastRequest.isPresent()) {
            long delta = request.getCreatedDate().getTime() - lastRequest.get().getCreatedDate().getTime();

            if (delta > 0) {
                // Difference in seconds
                int diff = (int) delta / 1000;

                // If difference lower than 10 secoonds
                if (diff < 10) {
                    log.debug("Weight: " + (10 - diff));
                    return 10 - diff;
                } else {
                    log.debug("Weight: " + MIN_WEIGHT);
                    return MIN_WEIGHT;
                }
            }
        }

        log.debug("Weight: " + MIN_WEIGHT);
        return MIN_WEIGHT;
    }
}
