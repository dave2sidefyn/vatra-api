package ch.bfh.projekt1.vatra.algorithm;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import ch.bfh.projekt1.vatra.model.VaTraKey;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by dave on 02.12.15.
 */
public interface Algorithm {
    int MAX_WEIGHT = 10;
    int MIN_WEIGHT = 0;

    /**
     * Gibt die Benötigten VatraKeys zurück
     *
     * @return List<VaTraKey>
     */
    List<VaTraKey> neededKeys();

    /**
     * Das vatraRequestObject wird mitgegeben und ein Wert zwischen 1-10 muss zurückgegeben werden
     *
     * @param app
     * @param request
     * @param crudRepositories
     * @return int
     */
    int check(@Nonnull App app, @Nonnull Request request, @Nonnull CrudRepository... crudRepositories);
}
