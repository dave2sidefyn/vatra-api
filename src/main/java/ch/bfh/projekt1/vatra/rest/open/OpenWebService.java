package ch.bfh.projekt1.vatra.rest.open;


import ch.bfh.projekt1.vatra.model.App;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashSet;

@RequestMapping("/rest/open")
@RestController
public class OpenWebService {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<App> getApp() {
        App greetings = new App("Hello, open REST!", "", null, new Date(), new Date(), new HashSet<>());
        return new ResponseEntity<>(greetings, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postGreetings(@RequestBody App app) {
        System.out.println("Greetings have been OPENLY posted. They say: " + app.getName());
        return new ResponseEntity(HttpStatus.OK);
    }
}
