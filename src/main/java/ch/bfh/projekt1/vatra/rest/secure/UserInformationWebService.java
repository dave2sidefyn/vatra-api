package ch.bfh.projekt1.vatra.rest.secure;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.User;
import ch.bfh.projekt1.vatra.model.UserInformationDTO;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/secure/userinformation")
@RestController
public class UserInformationWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserInformationDTO>> getUserInformation() {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Iterable<App> apps = appRepository.findAllByUser(user);

        UserInformationDTO userInformationDTO = new UserInformationDTO();
        userInformationDTO.setEmail(user.getEmail());
        apps.forEach(app ->
                userInformationDTO.addAppInformation(app.getId(), app.getName())
        );
        List<UserInformationDTO> list = new ArrayList<>();
        list.add(userInformationDTO);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
