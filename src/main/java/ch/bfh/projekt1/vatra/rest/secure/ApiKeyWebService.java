package ch.bfh.projekt1.vatra.rest.secure;


import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.service.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RequestMapping("/rest/secure/app/{id}/apiKey")
@RestController
public class ApiKeyWebService {

    @Autowired
    private AppRepository appRepository;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<App> generateApiKey(@PathVariable("id") String id) {

        App app = appRepository.findOne(id);
        if (Objects.isNull(app) || !app.getUser().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        app.setApiKey(UUID.randomUUID().toString());
        app = appRepository.save(app);

        return new ResponseEntity<>(app, HttpStatus.OK);
    }
}
