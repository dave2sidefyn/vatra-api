package ch.bfh.projekt1.vatra.rest.secure;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Benutzer;
import ch.bfh.projekt1.vatra.model.UserInformationDTO;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Gibt die Userinformationen (also den User mit all seinen Apps) zurück
 */
@RequestMapping("/rest/secure/userinformation")
@RestController
public class UserInformationWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserInformationDTO>> getUserInformation() {
        Benutzer benutzer = benutzerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (Objects.isNull(benutzer)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Iterable<App> apps = appRepository.findAllByBenutzer(benutzer);

        UserInformationDTO userInformationDTO = new UserInformationDTO();
        userInformationDTO.setEmail(benutzer.getEmail());
        apps.forEach(app ->
                userInformationDTO.addAppInformation(app.getId(), app.getName())
        );
        List<UserInformationDTO> list = new ArrayList<>();
        list.add(userInformationDTO);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
