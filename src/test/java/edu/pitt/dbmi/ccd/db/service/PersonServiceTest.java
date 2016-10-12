package edu.pitt.dbmi.ccd.db.service;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Person;
import java.util.Optional;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    public void findById() {
        Optional<Person> person = personService.findById(1L);
        assertTrue(person.isPresent());
    }

    @Test
    public void findPersonByEmail() {
        Optional<Person> person = personService.findPersonByEmail("isaac@example.com");
        assertTrue(person.isPresent());
    }
}
