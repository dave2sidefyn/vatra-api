package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.Algorithm;
import ch.bfh.projekt1.vatra.model.App;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by dave on 23.10.15.
 */
public interface AlgorithmRepository extends CrudRepository<Algorithm, String> {

    Iterable<Algorithm> findAllByApps(@Param("apps") App app);
}
