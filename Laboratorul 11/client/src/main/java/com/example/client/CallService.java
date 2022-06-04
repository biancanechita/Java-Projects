package com.example.client;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class CallService {
    static final Logger log = (Logger) LoggerFactory.getLogger(CallService.class);

    public static List<PersonPojo> getPeople() {
        String uri = "http://localhost:8081/people";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<PersonPojo>> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        List<PersonPojo> result = response.getBody();
        assert result != null;
        result.forEach(p -> log.info(p.toString()));
        return result;
    }

    public static List<PersonPojo> getFriends(int id) {
        String uri = String.format("http://localhost:8081/people/%d/relationship", id);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<PersonPojo>> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        List<PersonPojo> result = response.getBody();
        assert result != null;
        result.forEach(p -> log.info(p.toString()));
        return result;
    }

}
