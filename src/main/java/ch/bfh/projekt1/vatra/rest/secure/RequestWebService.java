package ch.bfh.projekt1.vatra.rest.secure;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import ch.bfh.projekt1.vatra.model.User;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.RequestRepository;
import ch.bfh.projekt1.vatra.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Hier können die Requests für jede App gelesen werden.
 */
@RequestMapping("/rest/secure/app/{id}/request")
@RestController
public class RequestWebService {

	@Autowired
    private UserRepository userRepository;
	
    @Autowired
    private AppRepository appRepository;

    @Autowired
    private RequestRepository requestRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Request>> getRequests(@PathVariable("id") String id) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App app = appRepository.findOne(id);
        if (!app.getUser().equals(user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(requestRepository.findAllByApp(app), HttpStatus.OK);
    }
}
