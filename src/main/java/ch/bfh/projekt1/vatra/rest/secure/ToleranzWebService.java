package ch.bfh.projekt1.vatra.rest.secure;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Benutzer;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Hier wird die Toleranz für jede App gelesen und upgedated
 */
@RequestMapping("/rest/secure/app/{id}/toleranz")
@RestController
public class ToleranzWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<int[]> getToleranz(@PathVariable("id") String id) {
        Benutzer benutzer = benutzerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App app = appRepository.findOne(id);
        if (!app.getBenutzer().equals(benutzer)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        int[] toleranz = {app.getToleranz()};

        return new ResponseEntity<>(toleranz, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<App> updateToleranz(@PathVariable("id") String id, @RequestBody App app) {

        Benutzer benutzer = benutzerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App currentApp = appRepository.findOne(id);
        if (!currentApp.getBenutzer().equals(benutzer)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        currentApp.setToleranz(app.getToleranz());

        App savedApp = appRepository.save(currentApp);

        return new ResponseEntity<>(savedApp, HttpStatus.OK);
    }
}
