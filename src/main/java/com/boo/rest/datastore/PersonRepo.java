package com.boo.rest.datastore;

import com.boo.rest.model.Person;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class PersonRepo {

    private Set<Person> repo;


    @PostConstruct
    public void init() {
        repo = new HashSet<>(Arrays.asList(
                Person.builder().id(1).name("Joe").birthday(LocalDate.of(1959, Month.JULY, 19)).build(),
                Person.builder().id(2).name("Sue").birthday(LocalDate.of(1978, Month.SEPTEMBER, 1)).build(),
                Person.builder().id(3).name("Lea").birthday(LocalDate.of(1974, Month.JANUARY, 6)).build(),
                Person.builder().id(4).name("Kay").birthday(LocalDate.of(1984, Month.DECEMBER, 6)).build(),
                Person.builder().id(5).name("Jack").birthday(LocalDate.of(1974, Month.FEBRUARY, 28)).build(),
                Person.builder().id(6).name("Mia").birthday(LocalDate.of(1990, Month.AUGUST, 23)).build(),
                Person.builder().id(7).name("Bob").birthday(LocalDate.of(1994, Month.JANUARY, 12)).build(),
                Person.builder().id(8).name("Leo").birthday(LocalDate.of(1976, Month.MAY, 6)).build()
        ));
    }


    public Set<Person> allPersons() {
        return repo;
    }

    public boolean addPerson(Person person) {
        return repo.add(person);
    }

    public Optional<Person> getPersonById(long id) {
        return repo.stream().filter(person -> person.getId() == id).findFirst();
    }
}
