package com.boo.rest.service;

import com.boo.rest.datastore.PersonRepo;
import com.boo.rest.model.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class PersonService {

    private final PersonRepo repo;

    @Autowired
    public PersonService(PersonRepo repo) {
        this.repo = repo;
    }

    public boolean addPerson(Person person) {
        return repo.addPerson(person);
    }

    public Optional<Person> getPersonById(long id) {
        return repo.getPersonById(id);
    }

    public Set<Person> getAll() {
        return repo.allPersons();
    }
}
