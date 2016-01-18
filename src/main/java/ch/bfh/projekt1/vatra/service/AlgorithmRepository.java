package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.Algorithm;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository für Algorithmen
 * <p>
 * Created by dave on 23.10.15.
 */
public interface AlgorithmRepository extends CrudRepository<Algorithm, String> {

}
