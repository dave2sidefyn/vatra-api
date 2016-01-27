package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Benutzer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Repository für Apps
 * <p>
 * Created by dave on 23.10.15.
 */
public interface AppRepository extends CrudRepository<App, String> {

    @Nonnull
    List<App> findAllByBenutzer(@Param("user") Benutzer benutzer);

    @Nullable
    App findOneByApiKey(@Param("apiKey") String apiKey);
}
