package ch.bfh.projekt1.vatra.rest.secure;

import ch.bfh.projekt1.vatra.model.AlgorithmDTO;
import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.User;
import ch.bfh.projekt1.vatra.service.AlgorithmRepository;
import ch.bfh.projekt1.vatra.service.AppRepository;
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

import java.util.HashSet;
import java.util.Set;

@RequestMapping("/rest/secure/app/{id}/algorithm")
@RestController
public class AppAlgorithmWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private AlgorithmRepository algorithmRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<AlgorithmDTO>> getAlgorithms(@PathVariable("id") String id) {
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

        algorithmRepository.findAll().forEach(algorithm -> {
            //TODO ???
            if (true) {
                AlgorithmDTO algorithmDTO = new AlgorithmDTO(
                        algorithm.getId(),
                        "",
                        algorithm.getName(),
                        false
                );

                algorithms.add(algorithmDTO);
            }
        });


        return new ResponseEntity<>(algorithms, HttpStatus.OK);
    }
}
