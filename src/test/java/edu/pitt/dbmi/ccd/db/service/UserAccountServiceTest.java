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

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 *
 * Aug 13, 2015 10:42:19 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAccountServiceTest {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private GroupService groupService;

    private final Pageable pageable = new PageRequest(0, 10);

    @Test
    public void saveAndDelete() {
        // save
        Person person = new Person("Albert", "Einstein", "einstein@example.com", "~/ccd_workspace");
        UserAccount userAccount = new UserAccount(person, "einstein", "$2a$10$MSi.zsnU.TAHocBApb5BN.G.3Cyp/t0WLd6/76u87Lp8qIINkUy0i", true, "2ad00112-9d3a-11e6-8433-38c9860967a0", new Date(), new Date());
        userAccount = userAccountService.save(userAccount);
        assertNotNull(userAccount.getId());

        // delete
        userAccountService.delete(userAccount);
        UserAccount found = userAccountService.findById(userAccount.getId());
        assertNull(found);
    }

    @Test
    public void findById() {
        UserAccount userAccount = userAccountService.findById(1L);
        assertNotNull(userAccount);
    }

    @Test
    public void findByUsername() {
        UserAccount userAccount = userAccountService.findByUsername("alan");
        assertNotNull(userAccount);
    }

    @Test
    public void findByEmail() {
        UserAccount userAccount = userAccountService.findByEmail("alan@example.com");
        assertNotNull(userAccount);
    }

    @Test
    @Ignore
    public void findByGroupMembership() {
//        Group group = groupService.findById(1L)
//        Page<UserAccount> users = userAccountService.findByGroupMembership(group, pageable);
//        assertEquals(1, users.getTotalElements());
//        UserAccount user = users.iterator().next();
//        assertEquals("isaac", user.getUsername());
    }

    @Test
    @Ignore
    public void findByGroupModeration() {
//        Group group = groupService.findById(1L).get();
//        Page<UserAccount> users = userAccountService.findByGroupModeration(group, pageable);
//        assertEquals(1, users.getTotalElements());
//        UserAccount user = users.iterator().next();
//        assertEquals("isaac", user.getUsername());
    }

    @Test
    @Ignore
    public void findByGroupRequests() {
//        Group group = groupService.findById(1L).get();
//        Page<UserAccount> users = userAccountService.findByGroupRequests(group, pageable);
//        assertEquals(1, users.getTotalElements());
//        UserAccount user = users.iterator().next();
//        assertEquals("alan", user.getUsername());
    }

    @Test
    public void findAll() {
        Page<UserAccount> users = userAccountService.findAll(pageable);
        assertEquals(3, users.getTotalElements());
    }

    @Ignore
    @Test
    public void testMigration() throws ParseException {
        System.out.println("testMigration");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Path dataFile = Paths.get("data", "data.txt");
        try (BufferedReader reader = Files.newBufferedReader(dataFile, Charset.defaultCharset())) {
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                line = line.trim();
                String[] fields = line.split(";");
                Date createdDate = df.parse(fields[0].trim());
                Date lastLoginDate = df.parse(fields[1].trim());
                String password = fields[2].trim();
                String username = fields[3].trim();
                String email = fields[4].trim();
                String firstName = fields[5].trim();
                String lastName = fields[6].trim();
                String workspace = fields[7].trim();

                Person person = new Person(firstName, lastName, email, workspace);
                UserAccount userAccount = new UserAccount(person, username, password, true, "61b2b10c-9d3a-11e6-8c7a-38c9860967a0", createdDate, lastLoginDate);
                userAccountService.save(userAccount);
            }
        } catch (IOException exception) {
            exception.printStackTrace(System.err);
        }
    }

}
