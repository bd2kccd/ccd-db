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

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
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

    public UserAccountServiceTest() {
    }

    @Ignore
    @Test
    public void testMigration() throws ParseException {
        System.out.println("testMigration");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Path dataFile = Paths.get("data", "data.txt");
        try (BufferedReader reader = Files.newBufferedReader(dataFile, Charset.defaultCharset())) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                line = line.trim();
                String[] fields = line.split("\\|");
                Date createdDate = df.parse(fields[1].trim());
                String password = fields[2].trim();
                String username = fields[3].trim();
                String email = fields[4].trim();
                String firstName = fields[5].trim();
                String lastName = fields[6].trim();
                String workspace = fields[7].trim();

                Person person = new Person(firstName, lastName, email, workspace);
                UserAccount userAccount = new UserAccount(person, username, password, true, createdDate);
                userAccount.setLastLoginDate(createdDate);
                userAccountService.saveUserAccount(userAccount);
            }
        } catch (IOException exception) {
            exception.printStackTrace(System.err);
        }
    }

}
