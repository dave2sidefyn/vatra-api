package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.Whitelabel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by dave on 23.10.15.
 */
@RepositoryRestResource(collectionResourceRel = "whitelabel", path = "whitelabel")
public interface WhitelabelRepository extends CrudRepository<Whitelabel, Long> {
}
