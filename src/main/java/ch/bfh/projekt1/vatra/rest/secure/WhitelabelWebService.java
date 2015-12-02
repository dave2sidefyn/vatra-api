package ch.bfh.projekt1.vatra.rest.secure;

import ch.bfh.projekt1.vatra.model.AlgorithmDTO;
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

import java.util.HashSet;
import java.util.Set;

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
    public ResponseEntity<Iterable<Whitelabel>> getWhitelabels(@PathVariable("id") String id) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App app = appRepository.findOne(id);
        if (!app.getUser().equals(user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Set<AlgorithmDTO> algorithms = new HashSet<>();
        app.getAlgorithmResults().forEach(algorithmResult -> {
            AlgorithmDTO algorithmDTO = new AlgorithmDTO(
                    algorithmResult.getId(),
                    algorithmResult.getId(),
                    algorithmResult.getName(),
                    true
            );
            algorithms.add(algorithmDTO);
        });

        return new ResponseEntity<>(whitelabelRepository.findAllByApp(app), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<App> create(@RequestBody Whitelabel whitelabel) {
        if (whitelabel.getName() == null || whitelabel.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //TODO

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
