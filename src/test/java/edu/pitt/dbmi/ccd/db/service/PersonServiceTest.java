package edu.pitt.dbmi.ccd.db.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.pitt.dbmi.ccd.db.entity.Person;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    public void findById() {
        Person person = personService.findById(1L);
        assertNotNull(person);
    }

    @Test
    public void findPersonByEmail() {
        Person person = personService.findPersonByEmail("isaac@example.com");
        assertNotNull(person);
    }
}
