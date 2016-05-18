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
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
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

    public UserAccountRepositoryTest() {
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
