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

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.junit.runner.RunWith;
import org.junit.Test;
import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Group;
import edu.pitt.dbmi.ccd.db.repository.GroupRepository;
import edu.pitt.dbmi.ccd.db.service.GroupService;
import edu.pitt.dbmi.ccd.db.error.NotFoundException;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class GroupServiceTest {

    @Autowired(required=true)
    private GroupService groupService;

    private static final String name = "TEST_GROUP";
    private static final String description = "Test description";
    private static final String none = "Does Not Exist";

    @Test
    public void testCrud() {
        // create
        final Group group = groupService.create(new Group(name, description));
        assertNotNull(group.getId());

        // findByName
        try {
            final Group found = groupService.findByName(name);
            assertEquals(group.getId(), found.getId());
        } catch (NotFoundException ex) {
            groupService.delete(group);
            fail(ex.getMessage());
        }

        // delete
        groupService.delete(group);
    }

    @Test(expected=NotFoundException.class)
    public void testNotFound() {
        groupService.findByName(none);
    }

    @Test(expected=DuplicateKeyException.class)
    public void testDuplicateName() {
        final Group group = new Group(name, description);
        groupService.create(group);     // should create group
        groupService.create(group);     // should throw DuplicateKeyException
        groupService.delete(group);        
    }
}
