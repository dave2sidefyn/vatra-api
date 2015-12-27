package ch.bfh.projekt1.vatra.algorithm;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import ch.bfh.projekt1.vatra.model.VaTraKey;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * DEFAULT ALGORITHM to provide NullPointerExceptions
 * Created by dave on 02.12.15.
 */
public class DefaultAlgorithm implements Algorithm {


    @Override
    public List<VaTraKey> neededKeys() {
        return new ArrayList<>();
    }

    @Override
    public int check(@Nonnull App app, @Nonnull Request request, @Nonnull CrudRepository... crudRepositories) {

        return MIN_WEIGHT;
    }
}
