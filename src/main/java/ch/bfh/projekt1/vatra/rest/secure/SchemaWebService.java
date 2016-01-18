package ch.bfh.projekt1.vatra.rest.secure;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.User;
import ch.bfh.projekt1.vatra.model.VaTraKey;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.UserRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Hier kann das Schema pro App gelesen und gespeichert werden.
 * Dieses wird beim Speichern zusätzlich mit einer Validierung überprüft
 */
@RequestMapping("/rest/secure/app/{id}/schema")
@RestController
public class SchemaWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(SchemaWebService.class);

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getScheme(@PathVariable("id") String id) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App app = appRepository.findOne(id);
        if (!app.getUser().equals(user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<String> list = new ArrayList<>();
        list.add(app.getScheme());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<App> updateScheme(@PathVariable("id") String id, @RequestBody String scheme) {
        if (scheme == null || scheme.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            JSONObject json = (JSONObject) new JSONParser().parse(scheme);

            final List<String> keyList = new ArrayList<>();
            json.forEach((key, value) -> {
                if (value.equals(VaTraKey.VATRA_API_KEY.getId())) {
                    keyList.add((String) key);
                }
            });
            if (keyList.isEmpty()) {
                log.info("ApiKeyField is missing");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (ParseException e) {
            log.error("ParseException", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App app = appRepository.findOne(id);
        if (!app.getUser().equals(user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        app.setScheme(scheme);

        App savedApp = appRepository.save(app);

        return new ResponseEntity<>(savedApp, HttpStatus.OK);
    }
}
