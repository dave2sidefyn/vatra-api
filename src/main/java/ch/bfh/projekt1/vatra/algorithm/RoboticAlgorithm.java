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
        String identify = request.getVatraFields().get(VaTraKey.VATRA_IDENTIFICATION_NUMBER);
    	Request lastRequest = findLastRequest(app, identify, crudRepositories);

        if (lastRequest != null) {
            long delta = request.getCreatedDate().getTime() - lastRequest.getCreatedDate().getTime();

            if (delta > 0) {
            	// Difference in seconds
                int diff = (int) delta / 1000;

                // If difference lower than 10 secoonds
                if (diff < 10) {
                	System.out.println("RoboticAlgorithm weight: " + (10 - diff));
                    return (int) 10 - diff;
                } else {
                	System.out.println("RoboticAlgorithm weight: 0");
                    return MIN_WEIGHT;
                }
            }
        }

        System.out.println("RoboticAlgorithm weight: 0");
        return MIN_WEIGHT;
    }

    @Nonnull
    private Request findLastRequest(@Nonnull App app, @Nonnull String identify, @Nonnull CrudRepository[] crudRepositories) {
        for (CrudRepository crudRepository : crudRepositories) {
            if (crudRepository instanceof RequestRepository) {
                List<Request> allByApp = ((RequestRepository) crudRepository).findAllByAppAndIdentify(app, identify);
                if (allByApp.size() > 1) {
                	return allByApp.get(allByApp.size() - 2);
                }
            }
        }
        return null;
    }
}
