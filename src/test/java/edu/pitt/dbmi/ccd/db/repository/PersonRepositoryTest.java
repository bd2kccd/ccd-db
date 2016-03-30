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

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Person;

/**
 *
 * Jul 23, 2015 1:45:36 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void findByEmail() {
        final Optional<Person> person = personRepository.findByEmail("alan@example.com");
        assertTrue(person.isPresent());
    }

    @Test
    public void testCrudOperations() {
        System.out.println("Person Repository CRUD Operations");

        // create
        String firstName = "Alan";
        String lastName = "Turing";
        String email = "aturing@cs.pitt.edu";
        String workspaceDirectory = "/home/aturing/workspace";
        Person person = new Person(firstName, lastName, email, workspaceDirectory);
        person = personRepository.save(person);
        Assert.assertNotNull(person.getId());

        // read
        Long id = person.getId();
        person = personRepository.findOne(id);
        Assert.assertNotNull(person);

        // update
        email = "alanturing@cs.pitt.edu";
        person.setEmail(email);
        person = personRepository.save(person);
        Assert.assertEquals(email, person.getEmail());

        // delete
        id = person.getId();
        personRepository.delete(person);
        person = personRepository.findOne(id);
        Assert.assertNull(person);
    }

}
