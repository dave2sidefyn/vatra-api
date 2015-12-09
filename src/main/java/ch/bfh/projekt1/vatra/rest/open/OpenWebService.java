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

import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Boolean> createRequest(@RequestParam(value="jsonParams", required=true) String jsonParams,
    		HttpServletRequest requestInfos) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(jsonParams);

            Request request = new Request();
            App app = appRepository.findOneByApiKey((String) json.get("apiKey"));
            if (app == null) {
            	return new ResponseEntity<>(false, HttpStatus.OK);
            }
            request.setApp(app);
            request.setIdentify(requestInfos.getRemoteAddr());

            final Request savedRequest = requestRepository.save(request);

            AtomicBoolean valid = new AtomicBoolean(true);

            algorithmRepository.findAllByApps(app).forEach(algorithm -> {
                int value = 0;
                try {
                    Algorithm algorithmInterface = (Algorithm) AlgorithmEnum.GEO_ALGORITHM.getAlgorithmClass().newInstance();
                    value = algorithmInterface.check(json);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                AlgorithmRequestResult algorithmRequestResult = new AlgorithmRequestResult();
                algorithmRequestResult.setAlgorithm(algorithm);
                algorithmRequestResult.setRequest(savedRequest);
                if (value < app.getToleranz()) {
                	algorithmRequestResult.setResult(true);
                } else {
                	valid.set(false);
                }

                algorithmRequestResultRepository.save(algorithmRequestResult);
            });

            if (valid.get()) {
                savedRequest.setValid(true);
                requestRepository.save(savedRequest);
            }
            
            return new ResponseEntity<>(valid.get(), HttpStatus.OK);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
