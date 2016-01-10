package ch.bfh.projekt1.vatra.rest.secure;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.User;
import ch.bfh.projekt1.vatra.model.Whitelabel;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.UserRepository;
import ch.bfh.projekt1.vatra.service.WhitelabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.Objects;

@RequestMapping("/rest/secure/app/{id}/whitelabel")
@RestController
public class WhitelabelWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private WhitelabelRepository whitelabelRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterator<Whitelabel>> getWhitelabels(@PathVariable("id") String id) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App app = appRepository.findOne(id);
        if (Objects.isNull(app) || !app.getUser().equals(user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(whitelabelRepository.findAllByApp(app), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<App> create(@PathVariable("id") String id, @RequestBody Iterable<Whitelabel> whitelabels) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App currentApp = appRepository.findOne(id);
        if (Objects.isNull(currentApp) || !currentApp.getUser().equals(user)) {
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
