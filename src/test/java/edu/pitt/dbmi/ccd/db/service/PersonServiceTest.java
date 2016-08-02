package edu.pitt.dbmi.ccd.db.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Person;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CCDDatabaseApplication.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    public void saveAndDelete() {
        // save
        Person person = new Person("Albert", null, "Einstein", "einstein@example.com", "~/ccd_workspace", "Physicist");
        person = personService.save(person);
        assertNotNull(person.getId());

        // delete
        personService.delete(person);
        Optional<Person> found = personService.findById(person.getId());
        assertFalse(found.isPresent());
    }

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