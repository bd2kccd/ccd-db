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
package edu.pitt.dbmi.ccd.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Group;
import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.UserLogin;
import edu.pitt.dbmi.ccd.db.entity.UserLoginAttempt;
import edu.pitt.dbmi.ccd.db.entity.UserRole;

/**
 *
 * Aug 13, 2015 10:42:19 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CCDDatabaseApplication.class)
public class UserAccountServiceTest {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private GroupService groupService;

    final Long registrationLocation = new Long(323223552);
    private final Pageable pageable = new PageRequest(0, 10);

    @Test
    public void saveAndDelete() {
        final UserRole userRole = userRoleService.findByName("ADMIN").get();

        // save
        Person person = new Person("Albert", null, "Einstein", "einstein@example.com", "~/ccd_workspace", "Physicist");
        UserAccount userAccount = new UserAccount(person, new UserLogin(), new UserLoginAttempt(), userRole, "einstein", "$2a$10$MSi.zsnU.TAHocBApb5BN.G.3Cyp/t0WLd6/76u87Lp8qIINkUy0i", true, false, new Date(), registrationLocation, UUID.randomUUID().toString(), "abcde");
        userAccount = userAccountService.saveUserAccount(userAccount);
        assertNotNull(userAccount.getId());

        // delete
        userAccountService.delete(userAccount);
        Optional<UserAccount> found = userAccountService.findById(userAccount.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<UserAccount> userAccount = userAccountService.findById(1L);
        assertTrue(userAccount.isPresent());
    }

    @Test
    public void findByUsername() {
        Optional<UserAccount> userAccount = userAccountService.findByUsername("alan");
        assertTrue(userAccount.isPresent());
    }

    @Test
    public void findByEmail() {
        Optional<UserAccount> userAccount = userAccountService.findByEmail("alan@example.com");
        assertTrue(userAccount.isPresent());
    }

    @Test
    public void findByGroupMembership() {
        Group group = groupService.findById(1L).get();
        Page<UserAccount> users = userAccountService.findByGroupMembership(group, pageable);
        assertEquals(1, users.getTotalElements());
        UserAccount user = users.iterator().next();
        assertEquals("isaac", user.getUsername());
    }

    @Test
    public void findByGroupModeration() {
        Group group = groupService.findById(1L).get();
        Page<UserAccount> users = userAccountService.findByGroupModeration(group, pageable);
        assertEquals(1, users.getTotalElements());
        UserAccount user = users.iterator().next();
        assertEquals("isaac", user.getUsername());
    }

    @Test
    public void findByGroupRequests() {
        Group group = groupService.findById(1L).get();
        Page<UserAccount> users = userAccountService.findByGroupRequests(group, pageable);
        assertEquals(1, users.getTotalElements());
        UserAccount user = users.iterator().next();
        assertEquals("alan", user.getUsername());
    }

    @Test
    public void findAll() {
        Page<UserAccount> users = userAccountService.findAll(pageable);
        assertEquals(2, users.getTotalElements());
    }
}
