package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Whitelabel;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by dave on 23.10.15.
 */
public interface WhitelabelRepository extends CrudRepository<Whitelabel, String> {

    Set<Whitelabel> findAllByApp(@Param("app") App app);
    
    Whitelabel findByName(@Param("name") String name);
}
