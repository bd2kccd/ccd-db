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

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Iterator;
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
    private static final String nameUpdated = "TEST_GROUP_UPDATED";
    private static final String descriptionUpdated = "Test description updated";
    private static final String none = "Does Not Exist";
    private static final Set<String> searchTerms = new HashSet<>(0);

    @Test
    public void testCrud() {
        // create
        final Group group = groupService.create(new Group(name, description));
        assertNotNull(group.getId());
        System.out.println("*****\nCREATED\n"+group.getName()+"\n*****");

        // findByName
        try {
            final Group found = groupService.findByName(name);
            assertEquals(group.getId(), found.getId());
        } catch (NotFoundException ex) {
            groupService.delete(group);
            fail(ex.getMessage());
        }

        // update
        final Group updated = groupService.update(group, new Group(nameUpdated, description));
        assertEquals(nameUpdated, updated.getName());
        assertEquals(group.getId(), updated.getId());

        System.out.println("*****\nUPDATED\n"+updated.getName()+"\n*****");

        // patch
        final Group patched = groupService.patch(group, null, descriptionUpdated);
        assertEquals(descriptionUpdated, group.getDescription());
        System.out.println("*****\nPATCHED\n"+patched.getName()+"\n*****");

        // delete
        groupService.delete(group.getId());
    }

    @Test
    public void testSearch() {
        final Group group = groupService.create(new Group(name, description));
        searchTerms.addAll(Arrays.asList(name.split("_")));
        final Iterable<Group> page = groupService.search(searchTerms, null, null);
        final Iterator<Group> iter = page.iterator();
        // assertTrue(iter.hasNext());
        // assertEquals(iter.next().getId(), group.getId());
        System.out.println("*****\n" + iter.next().getName() + "\n*****");
        groupService.delete(group);
    }

    /* Exception tests */

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
