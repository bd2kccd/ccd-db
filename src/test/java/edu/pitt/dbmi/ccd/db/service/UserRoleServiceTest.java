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

import edu.pitt.dbmi.ccd.db.entity.UserRole;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * Jan 29, 2018 3:11:36 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleServiceTest {

    @Autowired
    private UserRoleService userRoleService;

    public UserRoleServiceTest() {
    }

    @Test
    public void testGetRegularRole() {
        UserRole userRole = userRoleService.getRegularRole();
        Assert.assertNotNull(userRole);
        Assert.assertTrue("regular".equals(userRole.getShortName()));
    }

    @Test
    public void testGetAdminRole() {
        UserRole userRole = userRoleService.getAdminRole();
        Assert.assertNotNull(userRole);
        Assert.assertTrue("admin".equals(userRole.getShortName()));
    }

    @Test
    public void testFindByShortName() {
        Assert.assertNotNull(userRoleService.findByShortName("admin"));
    }

    @Test
    public void testFindAll() {
        Assert.assertTrue(userRoleService.findAll().size() > 0);
    }

    @Test
    public void testGetRepository() {
        Assert.assertNotNull(userRoleService.getRepository());
    }

}
