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
import edu.pitt.dbmi.ccd.db.entity.Group;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import java.util.Optional;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserAccountService userAccountService;

    private final Pageable pageable = new PageRequest(0, 10);

    @Test
    public void saveAndDelete() {
        // save
        Group group = new Group("TEST", "Test group");
        group = groupService.save(group);
        assertNotNull(group.getId());

        // delete
        groupService.delete(group);
        Optional<Group> found = groupService.findById(group.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<Group> group = groupService.findById(1L);
        assertTrue(group.isPresent());
    }

    @Test
    public void findByName() {
        Optional<Group> group = groupService.findByName("Scientists");
        assertTrue(group.isPresent());
    }

    @Test
    public void findByMember() {
        // is member
        UserAccount newton = userAccountService.findById(1L).get();
        Page<Group> groups = groupService.findByMember(newton, pageable);
        assertEquals(1, groups.getTotalElements());

        // is not member
        UserAccount turing = userAccountService.findById(2L).get();
        groups = groupService.findByMember(turing, pageable);
        assertEquals(0, groups.getTotalElements());
    }

    @Test
    public void findByModerator() {
        // is moderator
        UserAccount newton = userAccountService.findById(1L).get();
        Page<Group> groups = groupService.findByModerator(newton, pageable);
        assertEquals(1, groups.getTotalElements());

        // is not moderator
        UserAccount turing = userAccountService.findById(2L).get();
        groups = groupService.findByModerator(turing, pageable);
        assertEquals(0, groups.getTotalElements());
    }

    @Test
    public void findByRequester() {
        // is requester
        UserAccount turing = userAccountService.findById(2L).get();
        Page<Group> groups = groupService.findByRequester(turing, pageable);
        assertEquals(1, groups.getTotalElements());

        // is not requester
        UserAccount newton = userAccountService.findById(1L).get();
        groups = groupService.findByRequester(newton, pageable);
        assertEquals(0, groups.getTotalElements());
    }

    @Test
    public void findAll() {
        Page<Group> groups = groupService.findAll(pageable);
        assertEquals(1, groups.getTotalElements());
    }
}
