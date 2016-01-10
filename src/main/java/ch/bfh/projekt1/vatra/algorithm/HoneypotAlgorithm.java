package ch.bfh.projekt1.vatra.algorithm;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import ch.bfh.projekt1.vatra.model.VaTraKey;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nonnull;

import java.util.Arrays;
import java.util.List;

/**
 * Honeypot Algorithm
 * Created by raess on 10.01.16.
 */
public class HoneypotAlgorithm implements Algorithm {
    
    public List<VaTraKey> neededKeys() {
        return Arrays.asList(VaTraKey.VATRA_API_KEY, VaTraKey.VATRA_HONEYPOT);
    }

    @Override
    public int check(@Nonnull App app, @Nonnull Request request, @Nonnull CrudRepository... crudRepositories) {
    	if (request.getVatraFields().get(VaTraKey.VATRA_HONEYPOT) != null) {
    		System.out.println("HoneypotAlgorithm weight: 0");
    		return MIN_WEIGHT;
    	} else {
    		System.out.println("HoneypotAlgorithm weight: 10");
    		return MAX_WEIGHT;
    	}
    }
}
