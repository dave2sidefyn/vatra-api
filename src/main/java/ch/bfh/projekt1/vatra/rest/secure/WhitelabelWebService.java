package ch.bfh.projekt1.vatra.rest.secure;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Benutzer;
import ch.bfh.projekt1.vatra.model.Whitelabel;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.BenutzerRepository;
import ch.bfh.projekt1.vatra.service.WhitelabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Hier können Whitlabels gelesen, und updated werden
 */
@RequestMapping("/rest/secure/app/{id}/whitelabel")
@RestController
public class WhitelabelWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private WhitelabelRepository whitelabelRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Whitelabel>> getWhitelabels(@PathVariable("id") String id) {
        Benutzer benutzer = benutzerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App app = appRepository.findOne(id);
        if (Objects.isNull(app) || !app.getBenutzer().equals(benutzer)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(whitelabelRepository.findAllByApp(app), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<App> update(@PathVariable("id") String id, @RequestBody Iterable<Whitelabel> whitelabels) {
        Benutzer benutzer = benutzerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App currentApp = appRepository.findOne(id);
        if (Objects.isNull(currentApp) || !currentApp.getBenutzer().equals(benutzer)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        whitelabelRepository.deleteAllByApp(currentApp);

        whitelabels.forEach(whitelabel -> {
            if (!whitelabel.getName().isEmpty()) {
                Whitelabel newWhitelabel = new Whitelabel(whitelabel.getName(), currentApp);
                whitelabelRepository.save(newWhitelabel);
            }
        });

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
