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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
    public ResponseEntity<Iterable<AlgorithmDTO>> getAlgorithms(@PathVariable("id") long id) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App app = appRepository.findOne(id);
        if (!app.getUser().equals(user)) {
        	return new ResponseEntity<Iterable<AlgorithmDTO>>(HttpStatus.UNAUTHORIZED);
        }
        
        Set<AlgorithmDTO> algorithms = new HashSet<>();
        app.getAlgorithmResults().forEach(algorithmResult -> {
        	AlgorithmDTO algorithmDTO = new AlgorithmDTO(
        		algorithmResult.getAlgorithm().getId(),
        		algorithmResult.getId(),
        		algorithmResult.getAlgorithm().getName(),
        		true
        	);
        	algorithms.add(algorithmDTO);
        });
        
        algorithmRepository.findAll().forEach(algorithm -> {
        	if (true) {
        		AlgorithmDTO algorithmDTO = new AlgorithmDTO(
        			algorithm.getId(),
            		"",
            		algorithm.getName(),
            		false
            	);
        		
        		algorithms.add(algorithmDTO);
        	}
        });;
        
        
        return new ResponseEntity<Iterable<AlgorithmDTO>>(algorithms, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<App> create(@RequestBody App app) {
    	if (app.getName() == null || app.getName().isEmpty()) {
    		return new ResponseEntity<App>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        app.setUser(user);
        Date now = new Date();
        app.setValidFrom(now);
        app.setValidTo(new Date(now.getTime() + (1000 * 60 * 60 * 24 * 3)));
        appRepository.save(app);
        
        return new ResponseEntity<App>(HttpStatus.OK);
    }
}
