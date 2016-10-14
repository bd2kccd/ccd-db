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

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import edu.pitt.dbmi.ccd.db.entity.Group;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
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
        Group found = groupService.findById(group.getId());
        assertNull(found);
    }

    @Test
    public void findById() {
        Group group = groupService.findById(1L);
        assertNotNull(group);
    }

    @Test
    public void findByName() {
        Group group = groupService.findByName("Scientists");
        assertNotNull(group);
    }

    @Test
    @Ignore
    public void findByMember() {
//        // is member
//        UserAccount newton = userAccountService.findById(1L).get();
//        Page<Group> groups = groupService.findByMember(newton, pageable);
//        assertEquals(1, groups.getTotalElements());
//
//        // is not member
//        UserAccount turing = userAccountService.findById(2L).get();
//        groups = groupService.findByMember(turing, pageable);
//        assertEquals(0, groups.getTotalElements());
    }

    @Test
    @Ignore
    public void findByModerator() {
//        // is moderator
//        UserAccount newton = userAccountService.findById(1L).get();
//        Page<Group> groups = groupService.findByModerator(newton, pageable);
//        assertEquals(1, groups.getTotalElements());
//
//        // is not moderator
//        UserAccount turing = userAccountService.findById(2L).get();
//        groups = groupService.findByModerator(turing, pageable);
//        assertEquals(0, groups.getTotalElements());
    }

    @Test
    @Ignore
    public void findByRequester() {
//        // is requester
//        UserAccount turing = userAccountService.findById(2L).get();
//        Page<Group> groups = groupService.findByRequester(turing, pageable);
//        assertEquals(1, groups.getTotalElements());
//
//        // is not requester
//        UserAccount newton = userAccountService.findById(1L).get();
//        groups = groupService.findByRequester(newton, pageable);
//        assertEquals(0, groups.getTotalElements());
    }

    @Test
    public void findAll() {
        Page<Group> groups = groupService.findAll(pageable);
        assertEquals(1, groups.getTotalElements());
    }
}
