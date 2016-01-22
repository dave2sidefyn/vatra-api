package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Whitelabel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;

/**
 * Repository für Whitelabels
 * <p>
 * Created by dave on 23.10.15.
 */
public interface WhitelabelRepository extends CrudRepository<Whitelabel, String> {

    @Nonnull
    Whitelabel findAllByApp(@Param("app") App app);

    void deleteAllByApp(@Param("app") App app);
}
