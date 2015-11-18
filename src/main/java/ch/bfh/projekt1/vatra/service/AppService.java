package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.App;

/**
 * Created by dave on 11.11.15.
 */
public interface AppService {

    /**
     * @return
     */
    Iterable<App> findAll();


    /**
     * @param app
     * @return
     */
    App create(App app);
}
