package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dave on 11.11.15.
 */
@Service
public class AppServiceBean implements AppService {

    @Autowired
    private AppRepository appRepository;

    @Override
    public Iterable<App> findAll() {
        return appRepository.findAll();
    }

    @Override
    public App create(App app) {
        return appRepository.save(app);
    }
}
