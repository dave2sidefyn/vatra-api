package ch.bfh.projekt1.vatra.rest.secure;


import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.AppDTO;
import ch.bfh.projekt1.vatra.model.Benutzer;
import ch.bfh.projekt1.vatra.model.Request;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.BenutzerRepository;
import ch.bfh.projekt1.vatra.service.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Bietet die RestService um die App den eingeloggten Benutzers zu lesen und neue zu erstellen
 */
@RequestMapping("/rest/secure/app")
@RestController
public class AppWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Autowired
    private RequestRepository requestRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppDTO>> getApps() {
        Benutzer benutzer = benutzerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Iterable<App> all = appRepository.findAllByBenutzer(benutzer);

        List<AppDTO> appDTOList = new ArrayList<>();
        all.forEach(app -> {
            AppDTO appDTO = new AppDTO();
            appDTO.setId(app.getId());
            appDTO.setName(app.getName());
            appDTO.setApiKey(app.getApiKey());

            List<Request> requestByApp = requestRepository.findAllByApp(app);
            appDTO.setRequest((long) requestByApp.size());
            appDTO.setValid(requestByApp.stream().filter(Request::isValid).count());
            appDTO.setInvalid(requestByApp.stream().filter(Request::isInValid).count());
            if (!requestByApp.isEmpty()) {
                Request request = requestByApp.get(0);
                appDTO.setLastRequest(request.getCreatedDate());
            }
            appDTOList.add(appDTO);
        });


        return new ResponseEntity<>(appDTOList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<App> create(@RequestBody App app) {
        if (app.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Benutzer benutzer = benutzerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (Objects.isNull(benutzer)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        app.setBenutzer(benutzer);
        Date now = new Date();
        app.setValidFrom(now);
        app.setValidTo(new Date(now.getTime() + (1000 * 60 * 60 * 24 * 3)));
        app.setToleranz(10);
        appRepository.save(app);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
