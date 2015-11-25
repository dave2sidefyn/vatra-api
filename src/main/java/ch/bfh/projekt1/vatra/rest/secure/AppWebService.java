package ch.bfh.projekt1.vatra.rest.secure;


import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.User;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/rest/secure/app")
@RestController
public class AppWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<App>> getApps() {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Iterable<App> all = appRepository.findAllByUser(user);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public App create(@RequestBody App app) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        app.setUser(user);
        Date now = new Date();
        app.setValidFrom(now);
        app.setValidTo(new Date(now.getTime() + (1000 * 60 * 60 * 24 * 3)));
        return appRepository.save(app);
    }
}
