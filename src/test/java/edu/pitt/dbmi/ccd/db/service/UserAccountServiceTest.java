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
package edu.pitt.dbmi.ccd.db.service;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.UserLogin;
import edu.pitt.dbmi.ccd.db.entity.UserLoginAttempt;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * Feb 22, 2016 1:13:35 PM
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

    /**
     * Test of findByUsername method, of class UserAccountService.
     */
    @Test
    public void testFindByUsername() {
        System.out.println("findByUsername");

        String username = "bspears";
        UserAccount userAccount = userAccountService.findByUsername(username);
        Assert.assertNotNull(userAccount);
    }

    /**
     * Test of findByAccount method, of class UserAccountService.
     */
    @Test
    public void testFindByAccount() {
        System.out.println("findByAccount");

        String account = "56755429-351c-4fc5-888f-94679818d061";
        UserAccount userAccount = userAccountService.findByAccount(account);
        Assert.assertNotNull(userAccount);
    }

    /**
     * Test of findByEmail method, of class UserAccountService.
     */
    @Test
    public void testFindByEmail() {
        System.out.println("findByEmail");

        String email = "bspears@localhost";
        UserAccount userAccount = userAccountService.findByEmail(email);
        Assert.assertNotNull(userAccount);
    }

    /**
     * Test of saveUserAccount method, of class UserAccountService.
     */
    @Test
    public void testSaveUserAccount() {
        System.out.println("saveUserAccount");

        UserLogin userLogin = new UserLogin();
        userLogin.setLastLoginDate(new Date(System.currentTimeMillis()));
        userLogin.setLastLoginLocation(2130706433L);
        userLogin.setLoginDate(new Date(System.currentTimeMillis()));
        userLogin.setLoginLocation(2130706433L);

        UserLoginAttempt userLoginAttempt = new UserLoginAttempt();

        Person person = new Person();
        person.setEmail("aturing@cs.pitt.edu");
        person.setFirstName("Alan");
        person.setMiddleName("");
        person.setLastName("Turing");
        person.setWorkspace("/home/aturing/workspace");

        UserAccount user = new UserAccount();
        user.setAccount("f78c68cf-f485-40b5-888b-24b702e2bae8");
        user.setActive(true);
        user.setDisabled(false);
        user.setPassword("$shiro1$SHA-256$500000$x+VrejAj+iuxOrykFM9WRw==$FP1HOPWIpJuUUR1nR2niYi5pO3FokaiX1CTLZ9t0jMk=");
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        user.setRegistrationLocation(Long.MAX_VALUE);
        user.setUsername("aturing");
        user.setUserLogin(userLogin);
        user.setUserLoginAttempt(userLoginAttempt);
        user.setPerson(person);

        UserAccount userAccount = userAccountService.saveUserAccount(user);
        Assert.assertNotNull(userAccount);
    }

}
