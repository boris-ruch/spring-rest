package com.boo.rest.controller;

import com.boo.rest.model.Person;
import com.boo.rest.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @RequestMapping(method = GET, path = "/all")
    public Set<Person> getAll() {
        return service.getAll();
    }

    @RequestMapping(method = GET, path = "/{id}")
    public ResponseEntity<Person> getById(@PathVariable long id) {
        Optional<Person> person = service.getPersonById(id);
        if (person.isPresent()) {
            return ResponseEntity.ok(person.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        boolean success = service.addPerson(person);
        if (success) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .buildAndExpand(person.getId()).toUri();
            return ResponseEntity.created(location).body(person);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
