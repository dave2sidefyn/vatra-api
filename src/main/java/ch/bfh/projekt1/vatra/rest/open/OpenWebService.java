package ch.bfh.projekt1.vatra.rest.open;


import ch.bfh.projekt1.vatra.model.AlgorithmRequestResult;
import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import ch.bfh.projekt1.vatra.service.AlgorithmRepository;
import ch.bfh.projekt1.vatra.service.AlgorithmRequestResultRepository;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.RequestRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private AlgorithmRepository algorithmRepository;

    @Autowired
    private AlgorithmRequestResultRepository algorithmRequestResultRepository;

    private RequestRepository requestRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<App> getApp() {
        App greetings = new App("Hello, open REST!", "", null, new Date(), new Date(), new HashSet<>());
        return new ResponseEntity<>(greetings, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createRequest(@RequestBody String string) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(string);

            Request request = new Request();
            App app = appRepository.findOne((String) json.get("appId"));
            request.setApp(app);


            Request savedRequest = requestRepository.save(request);

            algorithmRepository.findAllByApps(app).forEach(algorithm -> {


                AlgorithmRequestResult algorithmRequestResult = new AlgorithmRequestResult();
                algorithmRequestResult.setAlgorithm(algorithm);
                algorithmRequestResult.setRequest(savedRequest);
                algorithmRequestResult.setResult(true);
                algorithmRequestResultRepository.save(algorithmRequestResult);
            });


        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Greetings have been OPENLY posted. They say: ");
        return new ResponseEntity(HttpStatus.OK);
    }
}
