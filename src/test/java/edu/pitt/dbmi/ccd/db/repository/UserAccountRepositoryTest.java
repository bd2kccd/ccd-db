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
import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import java.time.Instant;
import java.util.Date;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * Jul 20, 2015 12:36:52 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PersonRepository personRepository;

    private Person person;

    public UserAccountRepositoryTest() {
    }

    @Before
    public void setUp() {
        person = new Person("Ada", "Lovelace", "alovelace@cs.pitt.edu", "");
        person = personRepository.save(person);
    }

    @After
    public void tearDown() {
        personRepository.delete(person.getId());
    }

    @Test
    public void testCrudOperations() {
        System.out.println("UserAccountRepository CRUD Operations");

        // create
        String username = "alovelace";
        String password = "hello";
        boolean active = true;
        Date createdDate = Date.from(Instant.now());
        UserAccount userAccount = new UserAccount(username, password, active, createdDate, person);
        userAccountRepository.save(userAccount);
        Assert.assertNotNull(userAccount.getId());

        // read
        Long id = userAccount.getId();
        userAccount = userAccountRepository.findOne(id);
        Assert.assertNotNull(userAccount);

        // update
        username = "allace";
        userAccount.setUsername(username);
        userAccount = userAccountRepository.save(userAccount);
        Assert.assertEquals(username, userAccount.getUsername());

        // delete
        id = userAccount.getId();
        userAccountRepository.delete(userAccount);
        userAccount = userAccountRepository.findOne(id);
        Assert.assertNull(userAccount);
    }

    /**
     * Test of findByUsername method, of class UserAccountRepository.
     */
    @Ignore
    @Test
    public void testFindByUsername() {
    }

}
