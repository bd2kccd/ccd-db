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
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * May 13, 2015 1:09:47 PM
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

    public UserAccountRepositoryTest() {
    }

    @Test
    public void crudOperations() {
        System.out.println("CRUD Operations");

        // create person first
        Person person = new Person("Jill", "Hill", "jhill@localhost");
        personRepository.save(person);
        Assert.assertNotNull(person.getId());

        // create new user account
        String username = "jhill";
        String password = "hello";
        boolean active = true;
        Date createdDate = new Date(System.currentTimeMillis());
        UserAccount userAccount = new UserAccount(username, password, active, createdDate, person);
        userAccountRepository.save(userAccount);
        Assert.assertNotNull(userAccount.getId());
        TestUtility.printUserAccount(userAccount, "Create New UserAccount");

        // update user account
        username = "j2hill";
        userAccount.setUsername(username);
        userAccount = userAccountRepository.save(userAccount);
        Assert.assertEquals(username, userAccount.getUsername());
        TestUtility.printUserAccount(userAccount, "Update UserAccount");

        // read user account by id
        Long id = userAccount.getId();
        userAccount = userAccountRepository.findOne(id);
        Assert.assertNotNull(userAccount);
        TestUtility.printUserAccount(userAccount, "Read By Id");

        // delete user account
        userAccountRepository.delete(userAccount);
        userAccount = userAccountRepository.findOne(id);
        Assert.assertNull(userAccount);
        TestUtility.printUserAccount(userAccount, "Delete UserAccount");
    }

    /**
     * Test of findByUsername method, of class UserAccountRepository.
     */
    @Test
    public void testFindByUsername() {
        System.out.println("findByUsername");

        // create person first
        Person person = new Person("Jack", "O'Larry", "jacklarry@localhost");
        personRepository.save(person);
        Assert.assertNotNull(person.getId());

        String username = "jacklarry";
        String password = "hello";
        boolean active = true;
        Date createdDate = new Date(System.currentTimeMillis());
        UserAccount userAccount = new UserAccount(username, password, active, createdDate, person);
        userAccountRepository.save(userAccount);
        Assert.assertNotNull(userAccount.getId());

        userAccount = userAccountRepository.findByUsername(username);
        Assert.assertNotNull(userAccount);
        TestUtility.printUserAccount(userAccount, "Find By Username");
    }

}
