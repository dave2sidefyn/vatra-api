package ch.bfh.projekt1.vatra.rest.open;

import ch.bfh.projekt1.vatra.model.Algorithm;
import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.Request;
import ch.bfh.projekt1.vatra.model.User;
import ch.bfh.projekt1.vatra.service.AlgorithmRequestResultRepository;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.RequestRepository;
import ch.bfh.projekt1.vatra.service.UserRepository;
import junit.framework.Assert;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testcase
 * <p>
 * Created by dave on 18.01.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class OpenWebServiceTest {

    @Autowired
    WebApplicationContext getWebApplicationContext;

    @InjectMocks
    private OpenWebService openWebService;


    @Resource
    private WebApplicationContext webApplicationContext;

    @Mock
    private AppRepository appRepository;

    @Mock
    private AlgorithmRequestResultRepository algorithmRequestResultRepository;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private UserRepository userRepository;


    private MockMvc mockMvc;

    @Before
    public void setup() {

        // Process mock annotations
        MockitoAnnotations.initMocks(this);

        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(openWebService).build();

    }

    @Test
    public void testVaTraApiKeyFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/open")
                .param("jsonParams", "{\"currency\":\"CHF\"," +
                        "\"amount\":\"10\"," +
                        "\"latitude\":\"46.9481307\"," +
                        "\"longitude\":\"7.4444333\"," +
                        "\"creditCardNumber\":\"1234\"," +
                        "\"creditCardHolder\":\"1234\"," +
                        "\"creditCardExpMonth\":\"12\"," +
                        "\"creditCardExpYear\":\"12\"," +
                        "\"creditCardCvc\":\"123\"," +
                        "\"VaTra.ApiKey\":\"d33i7sn7gj62t4mdptsfe1pclt\"," +
                        "\"VaTra.Identification\":\"ABC123456\",}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("false")));
        User userDave = new User("Dave Wiedmer", "david.wiedmer@gmail.com", "test1234");
        when(userRepository.save(userDave)).thenReturn(userDave);
        App app = new App("App Dave", 10, userDave, new Date(), new Date(), new HashSet<Algorithm>(), "d33i7sn7gj62t4mdptsfe1pclt");
        when(appRepository.save(app)).thenReturn(app);

        Request entity = Mockito.mock(Request.class);
        when(requestRepository.save((Request) anyObject())).thenReturn(entity);
        when(appRepository.findOneByApiKey(app.getApiKey())).thenReturn(app);
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/open")
                .param("jsonParams", "{\"currency\":\"CHF\"," +
                        "\"amount\":\"10\"," +
                        "\"latitude\":\"46.9481307\"," +
                        "\"longitude\":\"7.4444333\"," +
                        "\"creditCardNumber\":\"1234\"," +
                        "\"creditCardHolder\":\"1234\"," +
                        "\"creditCardExpMonth\":\"12\"," +
                        "\"creditCardExpYear\":\"12\"," +
                        "\"creditCardCvc\":\"123\"," +
                        "\"VaTra.ApiKey\":\"d33i7sn7gj62t4mdptsfe1pclt\"," +
                        "\"VaTra.Identification\":\"ABC123456\",}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("true")));

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/open")
                .param("jsonParams", "{\"currency\":\"CHF\"," +
                        "\"amount\":\"10\"," +
                        "\"latitude\":\"46.9481307\"," +
                        "\"longitude\":\"7.4444333\"," +
                        "\"creditCardNumber\":\"1234\"," +
                        "\"creditCardHolder\":\"1234\"," +
                        "\"creditCardExpMonth\":\"12\"," +
                        "\"creditCardExpYear\":\"12\"," +
                        "\"creditCardCvc\":\"123\"," +
                        "\"VaTra.ApiKey\":\"d33i7sn7gj62t4mdtsfe1pclt\"," +
                        "\"VaTra.Identification\":\"ABC123456\",}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("false")));
    }


    @Test
    public void testValidationJustSomeValues() throws Exception {

        OpenWebService openWebService = new OpenWebService();
        JSONObject jsonObject = new JSONObject();

        JSONObject schema = (JSONObject) new JSONParser().parse(App.DEFAULT_SCHEME);
        AtomicBoolean atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());


        //Add just some ignorableValues
        jsonObject.put("test", "test");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());

        jsonObject.put("VaTra.ApiKey", "VaTra.ApiKey");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());

        jsonObject.put("VaTra.ApiKey", "String");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());

        jsonObject.put("VaTra.ApiKey", "01");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());

    }

    @Test
    public void testValidationStringAcceptsEverything() throws Exception {

        OpenWebService openWebService = new OpenWebService();
        JSONObject jsonObject = new JSONObject();

        JSONObject schema = (JSONObject) new JSONParser().parse(App.DEFAULT_SCHEME);
        schema.put("name", "String");
        AtomicBoolean atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());

        jsonObject.put("name", "asdf");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());

        jsonObject.put("name", "");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());

        jsonObject.put("name", null);
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());
    }

    @Test
    public void testValidationNumber() throws Exception {

        OpenWebService openWebService = new OpenWebService();
        JSONObject jsonObject = new JSONObject();

        JSONObject schema = (JSONObject) new JSONParser().parse(App.DEFAULT_SCHEME);
        schema.put("tel", "number");
        AtomicBoolean atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());

        jsonObject.put("tel", "asdf");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertFalse(atomicBoolean.get());

        jsonObject.put("tel", "");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertFalse(atomicBoolean.get());

        jsonObject.put("tel", null);
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertFalse(atomicBoolean.get());

        jsonObject.put("tel", "1234019137");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());
    }

    @Test
    public void testValidationEmailRegex() throws Exception {

        OpenWebService openWebService = new OpenWebService();
        JSONObject jsonObject = new JSONObject();

        JSONObject schema = (JSONObject) new JSONParser().parse(App.DEFAULT_SCHEME);
        schema.put("email", "VaTra.Regex.email");
        AtomicBoolean atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());

        jsonObject.put("email", "asdf");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertFalse(atomicBoolean.get());

        jsonObject.put("email", "david.wiedmer@gmail.com");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());
    }

    @Test
    public void testValidationUrlRegex() throws Exception {

        OpenWebService openWebService = new OpenWebService();
        JSONObject jsonObject = new JSONObject();

        JSONObject schema = (JSONObject) new JSONParser().parse(App.DEFAULT_SCHEME);
        schema.put("url", "VaTra.Regex.URL");
        AtomicBoolean atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());

        jsonObject.put("url", "asdf");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertFalse(atomicBoolean.get());

        jsonObject.put("url", "sidefyn.ch");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertFalse(atomicBoolean.get());

        jsonObject.put("url", "www.sidefyn.ch");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertFalse(atomicBoolean.get());


        //Nur diese Typen sind erlaubt:
        jsonObject.put("url", "http://www.sidefyn.ch");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());

        jsonObject.put("url", "http://sidefyn.ch");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());

        jsonObject.put("url", "ftp://sidefyn.ch");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());

        jsonObject.put("url", "file://sidefyn.ch");
        atomicBoolean = openWebService.validateAndFillVatraRequestObject(schema, jsonObject, new Request());
        Assert.assertTrue(atomicBoolean.get());
    }

}
