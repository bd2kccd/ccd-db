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
import edu.pitt.dbmi.ccd.db.entity.UserLogin;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * Feb 21, 2016 9:04:36 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class UserLoginRepositoryTest {

    @Autowired
    private UserLoginRepository userLoginRepository;

    public UserLoginRepositoryTest() {
    }

    /**
     * Test of getOne method, of class UserLoginRepository.
     */
    @Test
    public void testGetOne() {
        System.out.println("getOne");

        Long id = 1L;
        UserLogin userLogin = userLoginRepository.findOne(id);
        Assert.assertNotNull(userLogin);
    }

    @Test
    public void testFindAll() {
        System.out.println("findAll");

        List<UserLogin> userLogins = userLoginRepository.findAll();
        Assert.assertNotNull(userLogins);
        Assert.assertTrue(!userLogins.isEmpty());
    }

}
