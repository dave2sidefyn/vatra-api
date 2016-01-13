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
import java.util.Objects;

/**
 * Honeypot Algorithm
 * Created by raess on 10.01.16.
 */
public class HoneypotAlgorithm implements Algorithm {

    private static final Logger log = LoggerFactory.getLogger(HoneypotAlgorithm.class);

    @Override
    @Nonnull
    public List<VaTraKey> neededKeys() {
        return Arrays.asList(VaTraKey.VATRA_API_KEY, VaTraKey.VATRA_HONEYPOT);
    }

    @Override
    public int check(@Nonnull App app, @Nonnull Request request, @Nonnull CrudRepository... crudRepositories) {
        if (Objects.nonNull(request.getVatraFields().get(VaTraKey.VATRA_HONEYPOT))
        		&& request.getVatraFields().get(VaTraKey.VATRA_HONEYPOT).isEmpty()) {
            log.info("HoneypotAlgorithm weight: 0");
            return MIN_WEIGHT;
        } else {
            log.info("HoneypotAlgorithm weight: 10");
            return MAX_WEIGHT;
        }
    }
}
