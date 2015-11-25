package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.Request;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by dave on 23.10.15.
 */
public interface RequestRepository extends CrudRepository<Request, Long> {
}
