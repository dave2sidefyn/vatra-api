package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Whitelistlabel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Repository für Whitelist
 * <p>
 * Created by dave on 23.10.15.
 */
public interface WhitelistLabelRepository extends CrudRepository<Whitelistlabel, String> {

    @Nonnull
    List<Whitelistlabel> findAllByApp(@Param("app") App app);

    void deleteAllByApp(@Param("app") App app);
}
