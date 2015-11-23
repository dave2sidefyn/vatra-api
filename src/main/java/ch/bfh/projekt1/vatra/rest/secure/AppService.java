package ch.bfh.projekt1.vatra.rest.secure;


import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.rest.Greetings;
import ch.bfh.projekt1.vatra.service.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RequestMapping("/rest/secure/app")
@RestController
public class AppService {

    @Autowired
    private AppRepository appRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<App>> getApps() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String name = authentication.getName();


        Iterable<App> all = appRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postGreetings(@RequestBody Greetings greetings) {
        System.out.println("Greetings have been SECURELY posted. They say: " + greetings.getGreetings());
        return new ResponseEntity(HttpStatus.OK);
    }
}
