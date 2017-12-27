package com.boo.rest.client;

import com.boo.rest.model.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomPersonCreator {


    private final CounterService counterService;

    @Autowired
    public RandomPersonCreator(CounterService counterService){
        this.counterService=counterService;
    }

    @Scheduled(fixedRate = 10_000)
    public void createPerson() {
        ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<Person> request = new HttpEntity<>(Person.builder().name(UUID.randomUUID().toString()).id(ThreadLocalRandom.current().nextInt(1000,8000)).birthday(LocalDate.now()).build());
        Person person = restTemplate.postForObject("http://localhost:9000/person/", request, Person.class);
        counterService.increment("boo_person_counter");
    }

    private static ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }
}
