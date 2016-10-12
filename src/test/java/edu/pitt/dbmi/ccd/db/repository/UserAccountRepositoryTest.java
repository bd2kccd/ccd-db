/*
 * Copyright (C) 2016 University of Pittsburgh.
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
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 *
 * May 18, 2016 3:41:02 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;
    private final Pageable pageable = new PageRequest(0, 10);

    public UserAccountRepositoryTest() {
    }

//    @Test
//    public void saveAndDelete() {
//        // save
//        Person person = new Person("Albert", "Einstein", "einstein@example.com", "~/ccd_workspace/");
//        UserAccount user = new UserAccount(person, "einstein", "$2a$10$mTPRrCa1THQJyk60QrhIQOgvQAnSpDkcm1QK5zwKc6m9xBu87hKqG", UUID.randomUUID().toString(), true, new Date());
//        user = userAccountRepository.save(user);
//        assertNotNull(user.getId());
//
//        // delete
//        userAccountRepository.delete(user);
//        Optional<UserAccount> found = userAccountRepository.findById(user.getId());
//        assertFalse(found.isPresent());
//    }

    @Test
    public void findById() {
        Optional<UserAccount> user = userAccountRepository.findById(1L);
        assertTrue(user.isPresent());
    }

    @Test
    public void findByGroupMembership() {
        Page<UserAccount> users = userAccountRepository.findByGroupMembership("Scientists", pageable);
        assertTrue(users.getTotalElements() == 1);
        assertEquals(users.iterator().next().getUsername(), "isaac");
    }

    @Test
    public void findByGroupModeration() {
        Page<UserAccount> users = userAccountRepository.findByGroupModeration("Scientists", pageable);
        assertTrue(users.getTotalElements() == 1);
        assertEquals(users.iterator().next().getUsername(), "isaac");
    }

    @Test
    public void findByGroupRequests() {
        Page<UserAccount> users = userAccountRepository.findByGroupRequests("Scientists", pageable);
        assertTrue(users.getTotalElements() == 1);
        assertEquals(users.iterator().next().getUsername(), "alan");
    }

    @Test
    public void findAll() {
        Page<UserAccount> users = userAccountRepository.findAll(pageable);
        assertEquals(users.getTotalElements(), 3);
    }

    /**
     * Test of findByUsername method, of class UserAccountRepository.
     */
    @Test
    public void testFindByUsername() {
        System.out.println("findByUsername");

        String username = "bspears";
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        Assert.notNull(userAccount);

    }

    /**
     * Test of findByAccountId method, of class UserAccountRepository.
     */
    @Test
    public void testFindByAccountId() {
        System.out.println("findByAccountId");
        String accountId = "123456789";
        UserAccount userAccount = userAccountRepository.findByAccountId(accountId);
        Assert.notNull(userAccount);
    }

    /**
     * Test of findByEmail method, of class UserAccountRepository.
     */
    @Test
    public void testFindByEmail() {
        System.out.println("findByEmail");
        String email = "bspears@localhost";
        UserAccount userAccount = userAccountRepository.findByEmail(email);
        Assert.notNull(userAccount);
    }

}
