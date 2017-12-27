package com.boo.rest;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {


    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testRetrieveStudentCourse() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);


        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/person/all"),
                HttpMethod.GET, entity, String.class);

        String expected = "[" +
                "{\"id\":6,\"name\":\"Mia\"}," +
                "{\"id\":2,\"name\":\"Sue\"}," +
                "{\"id\":3,\"name\":\"Lea\"}," +
                "{\"id\":1,\"name\":\"Joe\"}," +
                "{\"id\":8,\"name\":\"Leo\"}," +
                "{\"id\":4,\"name\":\"Kay\"}," +
                "{\"id\":7,\"name\":\"Bob\"}," +
                "{\"id\":5,\"name\":\"Jack\"}" +
                "]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}