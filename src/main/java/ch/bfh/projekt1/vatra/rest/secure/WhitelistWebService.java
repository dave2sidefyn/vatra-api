package ch.bfh.projekt1.vatra.rest.secure;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Benutzer;
import ch.bfh.projekt1.vatra.model.Whitelistlabel;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.BenutzerRepository;
import ch.bfh.projekt1.vatra.service.WhitelistLabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Hier können Whitelist gelesen, und updated werden
 */
@RequestMapping("/rest/secure/app/{id}/whitelistlabel")
@RestController
public class WhitelistWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private WhitelistLabelRepository whitelistLabelRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Whitelistlabel>> getWhitelistLabels(@PathVariable("id") String id) {
        Benutzer benutzer = benutzerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App app = appRepository.findOne(id);
        if (Objects.isNull(app) || !app.getBenutzer().equals(benutzer)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(whitelistLabelRepository.findAllByApp(app), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<App> update(@PathVariable("id") String id, @RequestBody Iterable<Whitelistlabel> whitelist) {
        Benutzer benutzer = benutzerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App currentApp = appRepository.findOne(id);
        if (Objects.isNull(currentApp) || !currentApp.getBenutzer().equals(benutzer)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        whitelistLabelRepository.deleteAllByApp(currentApp);

        whitelist.forEach(whitelistlabel -> {
            if (!whitelistlabel.getName().isEmpty()) {
                Whitelistlabel newWhitelistlabel = new Whitelistlabel(whitelistlabel.getName(), currentApp);
                whitelistLabelRepository.save(newWhitelistlabel);
            }
        });

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
