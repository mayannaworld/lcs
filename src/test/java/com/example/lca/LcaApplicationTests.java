package com.example.lca;

import com.example.lca.models.ExceptionResponse;
import com.example.lca.models.LcsRequest;
import com.example.lca.models.LcsResponse;
import com.example.lca.models.StringData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LcsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LcaApplicationTests {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void test1() throws JSONException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        // convert JSON string to Book object
        LcsRequest lcsRequest = mapper.readValue("{\n" +
                "\"setOfStrings\": [\n" +
                "{\"value\": \"comcast\"},\n" +
                "{\"value\": \"comcastic\"},\n" +
                "{\"value\": \"broadcaster\"}\n" +
                "]\n" +
                "}", LcsRequest.class);

        HttpEntity<LcsRequest> entity = new HttpEntity<>(lcsRequest);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/lcs"),
                HttpMethod.POST, entity, String.class);

        String expected = "{\"lcs\":[{\"value\":\"cast\"}]}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void test2() throws JSONException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        // convert JSON string to Book object
        LcsRequest lcsRequest = mapper.readValue("{\n" +
                "\"setOfStrings\": [\n" +
                "{\"value\": \"crustomcast\"},\n" +
                "{\"value\": \"comcasticrust\"},\n" +
                "{\"value\": \"broarustdcaster\"}\n" +
                "]\n" +
                "}", LcsRequest.class);

        HttpEntity<LcsRequest> entity = new HttpEntity<>(lcsRequest);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/lcs"),
                HttpMethod.POST, entity, String.class);

        String expected = "{\"lcs\":[{\"value\":\"cast\"},{\"value\":\"rust\"}]}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void test3() throws JSONException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        // convert JSON string to Book object
        LcsRequest lcsRequest = mapper.readValue("{\n" +
                "\"setOfStrings\": [\n" +
                "{\"value\": \"abcdcrustomcast\"},\n" +
                "{\"value\": \"coabcdmcasticrust\"},\n" +
                "{\"value\": \"brabcdoarustdcaster\"}\n" +
                "]\n" +
                "}", LcsRequest.class);

        HttpEntity<LcsRequest> entity = new HttpEntity<>(lcsRequest);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/lcs"),
                HttpMethod.POST, entity, String.class);

        String expected = "{\"lcs\":[{\"value\":\"abcd\"},{\"value\":\"cast\"},{\"value\":\"rust\"}]}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void test4() throws JSONException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        // convert JSON string to Book object
        LcsRequest lcsRequest = mapper.readValue("{\n" +
                "\"setOfStrings\": [\n" +
                "{\"value\": \"comcast\"},\n" +
                "{\"value\": \"comcast\"},\n" +
                "{\"value\": \"comcast\"}\n" +
                "]\n" +
                "}", LcsRequest.class);

        HttpEntity<LcsRequest> entity = new HttpEntity<>(lcsRequest);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/lcs"),
                HttpMethod.POST, entity, String.class);

        String expected = "{\"message\":\"The strings supplied must be unique\",\"httpStatus\":\"BAD_REQUEST\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
        assertEquals(response.getStatusCode(), response.getStatusCode());
    }


    @Test
    public void test5() throws JSONException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        // convert JSON string to Book object
        LcsRequest lcsRequest = mapper.readValue("{\n" +
                "  \"setOfStrings\": [\n" +
                "  ]\n" +
                "}", LcsRequest.class);

        HttpEntity<LcsRequest> entity = new HttpEntity<>(lcsRequest);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/lcs"),
                HttpMethod.POST, entity, String.class);

        String expected = "{\"message\":\"Request doesnt contain the required strings\",\"httpStatus\":\"BAD_REQUEST\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
        assertEquals(response.getStatusCode(), response.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}