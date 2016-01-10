package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by dave on 23.10.15.
 */
public interface AppRepository extends CrudRepository<App, String> {

    @Nonnull
    Iterable<App> findAllByUser(@Param("user") User user);

    @Nullable
    App findOneByApiKey(@Param("apiKey") String apiKey);
}
