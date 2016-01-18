package ch.bfh.projekt1.vatra.rest.secure;

import ch.bfh.projekt1.vatra.model.Algorithm;
import ch.bfh.projekt1.vatra.model.AlgorithmDTO;
import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.User;
import ch.bfh.projekt1.vatra.service.AlgorithmRepository;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Bietet die RestServices für die Algorithmen pro App zu lesen, einzuschalten oder auszuschalten
 */
@RequestMapping("/rest/secure/app/{id}/algorithm")
@RestController
public class AlgorithmWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private AlgorithmRepository algorithmRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(AlgorithmWebService.class);


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<AlgorithmDTO>> getAlgorithms(@PathVariable("id") @Nonnull String id) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App app = appRepository.findOne(id);
        if (!app.getUser().equals(user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<AlgorithmDTO> algorithms = new ArrayList<>();

        Set<Algorithm> appAlgorithms = app.getAlgorithms();
        appAlgorithms.forEach(algo -> {
                    AlgorithmDTO algorithmDTO = getAlgorithmDTO(algo, true);
            if (Objects.nonNull(algorithmDTO)) {
                        algorithms.add(algorithmDTO);
                    }
                }
        );

        Iterable<Algorithm> allAlgorithms = algorithmRepository.findAll();
        allAlgorithms.forEach(algo -> {
            List<String> contain = new ArrayList<>();
            algorithms.forEach(innerAlgo -> {
                if (innerAlgo.getAlgorithmId().equals(algo.getId())) {
                    contain.add(algo.getId());
                }
            });

            if (!contain.contains(algo.getId())) {
                AlgorithmDTO algorithmDTO = getAlgorithmDTO(algo, false);
                if (Objects.nonNull(algorithmDTO)) {
                    algorithms.add(algorithmDTO);
                }
            }
        });

        return new ResponseEntity<>(algorithms, HttpStatus.OK);
    }

    @Nullable
    private AlgorithmDTO getAlgorithmDTO(@Nonnull Algorithm algo, boolean enabled) {
        AlgorithmDTO algorithmDTO = null;
        try {
            algorithmDTO = new AlgorithmDTO(
                    algo.getId(),
                    algo.getName(),
                    ((ch.bfh.projekt1.vatra.algorithm.Algorithm) algo.getType().getAlgorithmClass().newInstance()).neededKeys(),
                    enabled
            );
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("InstantiationException | IllegalAccessException", e);
        }
        return algorithmDTO;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<App> updateAppAlgorithms(@PathVariable("id") @Nonnull String id, @RequestBody @Nonnull Iterable<Algorithm> algorithms) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        App currentApp = appRepository.findOne(id);
        if (!currentApp.getUser().equals(user)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        currentApp.setAlgorithms(new HashSet<>());
        appRepository.save(currentApp);

        Set<Algorithm> newAppAlgorithms = new HashSet<>();
        algorithms.forEach(algorithm -> {
            if (!algorithm.getId().isEmpty()) {
                Algorithm appAlgorithm = algorithmRepository.findOne(algorithm.getId());
                if (appAlgorithm != null) {
                    newAppAlgorithms.add(appAlgorithm);
                }
            }
        });
        currentApp.setAlgorithms(newAppAlgorithms);
        appRepository.save(currentApp);

        return new ResponseEntity<>(currentApp, HttpStatus.OK);
    }
}
