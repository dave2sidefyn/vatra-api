package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.Algorithm;
import ch.bfh.projekt1.vatra.model.App;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by dave on 23.10.15.
 */
public interface AlgorithmRepository extends CrudRepository<Algorithm, String> {

    Set<Algorithm> findAllByApps(@Param("apps") App app);
}
