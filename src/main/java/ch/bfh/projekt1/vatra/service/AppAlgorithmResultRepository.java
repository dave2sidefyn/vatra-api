package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.Algorithm;
import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.AppAlgorithmResult;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by dave on 23.10.15.
 */
public interface AppAlgorithmResultRepository extends CrudRepository<AppAlgorithmResult, String> {
	AppAlgorithmResult findByAppAndAlgorithm(@Param("app") App app, @Param("algorithm") Algorithm algorithm);
}
