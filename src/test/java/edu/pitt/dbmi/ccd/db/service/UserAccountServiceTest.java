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

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.error.NotFoundException;
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
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * Aug 13, 2015 10:42:19 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class UserAccountServiceTest {

    @Autowired
    private UserAccountService userAccountService;

    public UserAccountServiceTest() { }

    private static final String firstName = "Isaac";
    private static final String lastName = "Newton";
    private static final String email = "test@example.com";
    private static final String workspace = "~/.test_workspace";
    private static final String username = "test";
    private static final String password = "test";
    private static final boolean active = true;
    private static final Date created = new Date();

    private static final String none = "No Such Username";

    @Test
    public void testCrud() {
        // depencencies
        final Person person = new Person(firstName, lastName, email, workspace);

        // create
        final UserAccount user = userAccountService.create(new UserAccount(person, username, password, active, created));
        assertNotNull(user.getId());

        // findByUsername
        try {
            final UserAccount found = userAccountService.findByUsername(username);
            assertEquals(user.getId(), found.getId());
        } catch (NotFoundException ex) {
            userAccountService.delete(user);
            fail(ex.getMessage());
        }

        // delete
        userAccountService.delete(user);
    }

    @Test(expected=NotFoundException.class)
    public void testNotFound() {
        userAccountService.findByUsername(none);
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
                UserAccount userAccount = new UserAccount(person, username, password, true, createdDate);
                userAccount.setLastLoginDate(lastLoginDate);
                userAccountService.saveUserAccount(userAccount);
            }
        } catch (IOException exception) {
            exception.printStackTrace(System.err);
        }
    }

}
