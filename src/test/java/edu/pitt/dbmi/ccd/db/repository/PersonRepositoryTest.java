/*
 * Copyright (C) 2015 University of Pittsburgh.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package edu.pitt.dbmi.ccd.db.repository;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.TestUtility;
import edu.pitt.dbmi.ccd.db.entity.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * May 13, 2015 11:30:04 AM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    public PersonRepositoryTest() {
    }

    @Test
    public void crudOperations() {
        System.out.println("CRUD Operations");

        String firstName = "John";
        String lastName = "Doe";
        String email = "jdoe@localhost";

        // create
        Person person = new Person(firstName, lastName, email);
        person = personRepository.save(person);
        Assert.assertNotNull(person.getId());
        TestUtility.printPerson(person, "Create New Person");

        // update
        firstName = "Joe";
        person.setFirstName(firstName);
        person = personRepository.save(person);
        Assert.assertEquals(firstName, person.getFirstName());
        TestUtility.printPerson(person, "Update Person");

        // read
        Long id = person.getId();
        person = personRepository.findOne(id);
        Assert.assertNotNull(person);
        TestUtility.printPerson(person, "Find By ID");

        // delete
        id = person.getId();
        personRepository.delete(person);
        person = personRepository.findOne(id);
        Assert.assertNull(person);
        TestUtility.printPerson(person, "Delete Person");
    }

    /**
     * Test of findByEmail method, of class PersonRepository.
     */
    @Test
    public void testFindByEmail() {
        System.out.println("findByEmail");

        String firstName = "Sally";
        String lastName = "Wally";
        String email = "swally@localhost";

        // create
        Person person = new Person(firstName, lastName, email);
        personRepository.save(person);

        person = personRepository.findByEmail(email);
        Assert.assertNotNull(person);
        TestUtility.printPerson(person, "Find By Email");
    }

}
