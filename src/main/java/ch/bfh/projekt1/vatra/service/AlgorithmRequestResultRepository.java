package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.AlgorithmRequestResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by dave on 23.10.15.
 */
@RepositoryRestResource(collectionResourceRel = "algorithmRequestResult", path = "algorithmRequestResult")
public interface AlgorithmRequestResultRepository extends CrudRepository<AlgorithmRequestResult, Long> {
}
