package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Whitelabel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by dave on 23.10.15.
 */
public interface WhitelabelRepository extends CrudRepository<Whitelabel, Long> {

    Iterable<Whitelabel> findAllByApp(@Param("app") App app);
}
