package ch.bfh.projekt1.vatra.rest.open;


import ch.bfh.projekt1.vatra.model.*;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@RequestMapping("/rest/open")
@RestController
public class OpenWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private AlgorithmRequestResultRepository algorithmRequestResultRepository;

    @Autowired
    private RequestRepository requestRepository;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> createRequest(@RequestParam(value = "jsonParams") String jsonParams,
                                                 HttpServletRequest requestInfos) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(jsonParams);

            App app = appRepository.findOneByApiKey((String) json.get(VaTraKey.VATRA_API_KEY.getId()));
            if (app == null) {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }

            Map<String, String> header = new HashMap<>();
            Enumeration<String> headerNames = requestInfos.getHeaderNames();
        	while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                String value = requestInfos.getHeader(key);
        		header.put(key, value);
        	}
            System.out.println(header.toString());
            Request request = new Request((String) json.get(VaTraKey.VATRA_IDENTIFICATION_NUMBER.getId()), app, header.toString());
            fillVatraRequestObject(json, request);
            final Request savedRequest = requestRepository.save(request);

            AtomicBoolean valid = new AtomicBoolean(true);

            app.getAlgorithms().forEach(algorithm -> {
            	if (valid.get()) {
            		valid.set(checkWithAlgorithm(app, savedRequest, algorithm));
            	}
            });

            if (valid.get()) {
                savedRequest.setValid(true);
                requestRepository.save(savedRequest);
            }

            return new ResponseEntity<>(valid.get(), HttpStatus.OK);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    private boolean checkWithAlgorithm(App app, Request savedRequest, Algorithm algorithm) {
        int value = 0;
        try {
            value = ((ch.bfh.projekt1.vatra.algorithm.Algorithm) algorithm.getType().getAlgorithmClass().newInstance()).check(app, savedRequest, requestRepository);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return createAlgorithmRequestResult(app, savedRequest, algorithm, value);
    }

    private boolean createAlgorithmRequestResult(App app, Request savedRequest, Algorithm algorithm, int value) {
        AlgorithmRequestResult algorithmRequestResult = new AlgorithmRequestResult();
        algorithmRequestResult.setAlgorithm(algorithm);
        algorithmRequestResult.setRequest(savedRequest);
        if (value < app.getToleranz()) {
            algorithmRequestResult.setResult(true);
            algorithmRequestResultRepository.save(algorithmRequestResult);
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
	private void fillVatraRequestObject(JSONObject json, Request request) {
    	Map<VaTraKey, String> vatraFields = new HashMap<>();
        json.keySet().forEach(key -> {
            VaTraKey vaTraKey = VaTraKey.getWithId((String) key);
            if (!Objects.isNull(vaTraKey)) {
            	vatraFields.put(vaTraKey, (String) json.get(key));
            }
        });
        request.setVatraFields(vatraFields);
    }
}
