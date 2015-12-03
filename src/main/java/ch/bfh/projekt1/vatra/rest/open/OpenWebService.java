package ch.bfh.projekt1.vatra.rest.open;


import ch.bfh.projekt1.vatra.algorithm.Algorithm;
import ch.bfh.projekt1.vatra.algorithm.AlgorithmEnum;
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

@RequestMapping("/rest/open")
@RestController
public class OpenWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private AlgorithmRepository algorithmRepository;

    @Autowired
    private AlgorithmRequestResultRepository algorithmRequestResultRepository;

    @Autowired
    private RequestRepository requestRepository;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createRequest(@RequestBody String string) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(string);

            Request request = new Request();
            App app = appRepository.findOne((String) json.get("appId"));
            request.setApp(app);

            final Request savedRequest = requestRepository.save(request);

            final int[] i = {0};

            algorithmRepository.findAllByApps(app).forEach(algorithm -> {
                int value = 0;
                try {
                    Algorithm algorithmInterface = (Algorithm) AlgorithmEnum.GEO_ALGORITHM.getAlgorithm().newInstance();
                    value = algorithmInterface.check(json);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                AlgorithmRequestResult algorithmRequestResult = new AlgorithmRequestResult();
                algorithmRequestResult.setAlgorithm(algorithm);
                algorithmRequestResult.setRequest(savedRequest);
                algorithmRequestResult.setResult(value < 10);    //TODO WEDA
                i[0] = i[0] + value;
                algorithmRequestResultRepository.save(algorithmRequestResult);
            });


            if (i[0] < 10) {
                savedRequest.setValid(true);
                requestRepository.save(savedRequest);
            }


        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        System.out.println("Greetings have been OPENLY posted. They say: ");
        return new ResponseEntity(HttpStatus.OK);
    }
}
