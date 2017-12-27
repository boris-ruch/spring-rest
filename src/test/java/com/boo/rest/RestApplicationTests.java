package com.boo.rest;

import com.boo.rest.datastore.PersonRepo;
import com.boo.rest.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestApplication.class)
@WebAppConfiguration
public class RestApplicationTests {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PersonRepo repo;


    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void getAllPersons() throws Exception {
        Person mia = Person.builder().id(6).name("Mia").birthday(LocalDate.of(1990, Month.AUGUST, 23)).build();
        mockMvc.perform(get("/person/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
        //       .andExpect(jsonPath("$", contains(asJsonString(mia))));
        // .andExpect(jsonPath("$[0].id", is(repo.allPersons()..getId())))
        // .andExpect(jsonPath("$[0].name", is(repo.allPersons().get(0).getName())));

    }


    @Test
    public void getSue_Exists() throws Exception {
        mockMvc.perform(get("/person/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Sue")));

    }

    @Test
    public void getPerson_NotExists() throws Exception {
        mockMvc.perform(get("/person/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postNewPerson() throws Exception {

        Person person = Person.builder().name("Boris").build();
        mockMvc.perform(post("/person/")
                .contentType(contentType)
                .content(asJsonString(person)))
                .andExpect(status().isCreated());

    }

    @Test
    public void postExistingPerson() throws Exception {

        Person person = Person.builder().id(6).name("Mia").birthday(LocalDate.of(1990, Month.AUGUST, 23)).build();
        mockMvc.perform(post("/person/")
                .contentType(contentType)
                .content(asJsonString(person)))
                .andExpect(status().isConflict());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}