package ch.bfh.projekt1.vatra.rest.open;


import ch.bfh.projekt1.vatra.model.*;
import ch.bfh.projekt1.vatra.service.AlgorithmRequestResultRepository;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.RequestRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hier sind alle Webservices definiert, welche "von aussen", das heisst: ohne Login und Zugriffsberechtigung erreichbar sind.
 */
@RequestMapping("/rest/open")
@RestController
public class OpenWebService {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private AlgorithmRequestResultRepository algorithmRequestResultRepository;

    @Autowired
    private RequestRepository requestRepository;

    private static final Logger log = LoggerFactory.getLogger(OpenWebService.class);

    /**
     * Verarbeitet einen eingegangenen Request.
     *
     * @param jsonParams   dieses json wird vom Formular übermittelt.
     * @param requestInfos werden durch den ServletRequest automatisch übertragen (SpringBoot)
     * @return true wenn alles in Ordnung ist, false wenn Error oder Algorithmen den Request als nicht valid einstufen.
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> createRequest(@RequestParam(value = "jsonParams") String jsonParams,
                                                 HttpServletRequest requestInfos) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(jsonParams);

            String apiKey = (String) json.get(VaTraKey.VATRA_API_KEY.getId());
            App app = appRepository.findOneByApiKey(apiKey);
            if (Objects.isNull(app)) {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }


            String identify = (String) json.get(VaTraKey.VATRA_IDENTIFICATION.getId());
            if (Objects.isNull(identify)) {
                log.error("Keine Identifikation übermittelt!");
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
            Request request = new Request(identify, app, getClientInformations(requestInfos).toString());

            AtomicBoolean isValid = validateAndFillVatraRequestObject((JSONObject) new JSONParser().parse(app.getScheme()), json, request);

            request = requestRepository.save(request);
            if (!isValid.get()) {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }

            checkWidthAlgorithms(app, request, isValid);

            ifValidUpdateRequest(request, isValid);

            return new ResponseEntity<>(isValid.get(), HttpStatus.OK);
        } catch (ParseException e) {
            log.error("ParseException", e);
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    private void checkWidthAlgorithms(@Nonnull App app, @Nonnull Request request, @Nonnull AtomicBoolean isValid) {
        app.getAlgorithms().forEach(algorithm -> {
            if (isValid.get()) {
                isValid.set(checkWithAlgorithm(app, request, algorithm));
            }
        });
    }

    private void ifValidUpdateRequest(@Nonnull Request request, @Nonnull AtomicBoolean valid) {
        if (valid.get()) {
            request.setValid(true);
            requestRepository.save(request);
        }
    }

    @Nonnull
    private Map<String, String> getClientInformations(@Nonnull HttpServletRequest requestInfos) {
        Map<String, String> header = new HashMap<>();
        Enumeration<String> headerNames = requestInfos.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = requestInfos.getHeader(key);
            header.put(key, value);
        }
        log.info("Header: " + header.toString());
        return header;
    }

    private boolean checkWithAlgorithm(@Nonnull App app, @Nonnull Request savedRequest, @Nonnull Algorithm algorithm) {
        int value = 0;
        try {
            value = ((ch.bfh.projekt1.vatra.algorithm.Algorithm) algorithm.getType().getAlgorithmClass().newInstance()).check(app, savedRequest, requestRepository);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("InstantiationException | IllegalAccessException", e);
        }

        return createAlgorithmRequestResult(app, savedRequest, algorithm, value);
    }

    private boolean createAlgorithmRequestResult(@Nonnull App app, @Nonnull Request savedRequest, @Nonnull Algorithm algorithm, int value) {
        AlgorithmRequestResult algorithmRequestResult = new AlgorithmRequestResult();
        algorithmRequestResult.setAlgorithm(algorithm);
        algorithmRequestResult.setRequest(savedRequest);
        if (value < app.getToleranz()) {
            algorithmRequestResult.setValid(true);
            algorithmRequestResultRepository.save(algorithmRequestResult);
            return true;
        }
        return false;
    }

    @Nonnull
    public AtomicBoolean validateAndFillVatraRequestObject(@Nonnull JSONObject applicationSchema, @Nonnull JSONObject json, @Nonnull Request request) {
        Map<VaTraKey, String> vatraFields = new HashMap<>();
        Map<VaTraValidationKey, String> vatraValidationFields = new HashMap<>();
        Map<String, String> manualFields = new HashMap<>();

        final AtomicBoolean isValid = new AtomicBoolean(true);
        json.forEach((key, value) -> {
            final String schemaValue = getSchemaValue(applicationSchema, key);
            if (Objects.nonNull(schemaValue)) {
                VaTraKey vaTraKey = VaTraKey.getWithId(schemaValue);
                if (Objects.nonNull(vaTraKey)) {
                    vatraFields.put(vaTraKey, String.valueOf(value));
                } else {
                    VaTraValidationKey vaTraValidationKey = VaTraValidationKey.getWithId(schemaValue);
                    if (Objects.nonNull(vaTraValidationKey)) {
                        Pattern p = Pattern.compile(vaTraValidationKey.getRegex());
                        Matcher m = p.matcher(String.valueOf(value));
                        if (!m.matches()) {
                            isValid.set(false);
                        } else {
                            vatraValidationFields.put(vaTraValidationKey, String.valueOf(value));
                        }
                        //wir können bisher 2 Varianten an weiteren Eigabefeldern unterscheiden: Number und String, Wenn also Number gesetzt wird versuchen wird diese Eingabe also validator zuerst noch zu parsen. Dies dient als weitere sicherheits überprüfung
                    } else if (schemaValue.equals("number")) {
                        try {
                            value = Double.parseDouble(String.valueOf(value));
                        } catch (NumberFormatException nfe) {
                            log.error("number konnte nicht geparst werden:" + value);
                            isValid.set(false);

                        }
                        manualFields.put(String.valueOf(key), String.valueOf(value));
                    }
                }
            }
        });
        request.setVatraFields(vatraFields);
        request.setVatraValidationFields(vatraValidationFields);
        request.setManualFields(manualFields);

        return isValid;
    }

    @Nullable
    private String getSchemaValue(@Nonnull JSONObject applicationSchema, @Nonnull Object key) {
        List<String> keysWithShemaValue = new ArrayList<>();
        applicationSchema.forEach((o, o2) -> {
            if (o.equals(key)) {
                keysWithShemaValue.add((String) o2);
            }
        });
        if (keysWithShemaValue.isEmpty()) {
            return null;
        }
        return keysWithShemaValue.get(0);
    }
}
