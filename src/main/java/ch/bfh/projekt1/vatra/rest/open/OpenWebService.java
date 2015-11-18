package ch.bfh.projekt1.vatra.rest.open;


import ch.bfh.projekt1.vatra.rest.Greetings;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/open")
@RestController
public class OpenWebService {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greetings> getLogin() {
        Greetings greetings = new Greetings("Hello, open REST!");
        return new ResponseEntity<>(greetings, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postGreetings(@RequestBody Greetings greetings) {
        System.out.println("Greetings have been OPENLY posted. They say: " + greetings.getGreetings());
        return new ResponseEntity(HttpStatus.OK);
    }
}
