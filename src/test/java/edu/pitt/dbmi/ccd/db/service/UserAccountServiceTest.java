/*
 * Copyright (C) 2018 University of Pittsburgh.
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

import edu.pitt.dbmi.ccd.db.domain.account.UserAccountRegistration;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * Jan 29, 2018 4:07:07 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAccountServiceTest {

    @Autowired
    private UserAccountService userAccountService;

    public UserAccountServiceTest() {
    }

    @Test
    public void testRegisterRegularUser() {
        String email = "sgomez@selenagomez.com";
        String password = "gomez123";
        String firstName = "Selena";
        String lastName = "Gomez";
        String ipAddress = "127.0.0.1";

        UserAccountRegistration registration = new UserAccountRegistration(email, password);
        registration.setFirstName(firstName);
        registration.setLastName(lastName);
        registration.setIpAddress(ipAddress);

        UserAccount userAccount = userAccountService.registerRegularUser(registration);
        Assert.assertNotNull(userAccount);
    }

    @Test
    public void testGetRepository() {
        Assert.assertNotNull(userAccountService.getRepository());
    }

}
