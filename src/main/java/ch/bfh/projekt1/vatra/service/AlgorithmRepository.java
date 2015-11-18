package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.Algorithm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.annotation.security.RolesAllowed;

/**
 * Created by dave on 23.10.15.
 */
@RepositoryRestResource(collectionResourceRel = "algorithm", path = "algorithm")
@RolesAllowed("USER")
public interface AlgorithmRepository extends CrudRepository<Algorithm, Long> {
}
