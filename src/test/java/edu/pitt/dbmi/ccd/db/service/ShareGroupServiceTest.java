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

import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import edu.pitt.dbmi.ccd.db.entity.ShareGroup;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShareGroupServiceTest {

    @Autowired
    private ShareGroupService shareGroupService;

    @Autowired
    private UserAccountService userAccountService;

    private final Pageable pageable = new PageRequest(0, 10);

    @Test
    @Ignore
    public void saveAndDelete() {
        // save
        UserAccount owner = userAccountService.findById(1L).get();
        ShareGroup shareGroup = new ShareGroup(owner, "TEST", "Test shareGroup");
        shareGroup = shareGroupService.save(shareGroup);
        assertNotNull(shareGroup.getId());

        // delete
        shareGroupService.delete(shareGroup);
        Optional<ShareGroup> found = shareGroupService.findById(shareGroup.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<ShareGroup> shareGroup = shareGroupService.findById(1L);
        assertTrue(shareGroup.isPresent());
    }

    @Test
    public void findByName() {
        Optional<ShareGroup> shareGroup = shareGroupService.findByName("Scientists");
        assertTrue(shareGroup.isPresent());
    }

//    @Test
//    public void findByMember() {
//        // is member
//        UserAccount newton = userAccountService.findById(1L).get();
//        Page<ShareGroup> shareGroups = shareGroupService.findByMember(newton, pageable);
//        assertEquals(1, shareGroups.getTotalElements());
//
//        // is not member
//        UserAccount turing = userAccountService.findById(2L).get();
//        shareGroups = shareGroupService.findByMember(turing, pageable);
//        assertEquals(0, shareGroups.getTotalElements());
//    }
//
//    @Test
//    public void findByRequester() {
//        // is requester
//        UserAccount turing = userAccountService.findById(2L).get();
//        Page<ShareGroup> shareGroups = shareGroupService.findByRequester(turing, pageable);
//        assertEquals(1, shareGroups.getTotalElements());
//
//        // is not requester
//        UserAccount newton = userAccountService.findById(1L).get();
//        shareGroups = shareGroupService.findByRequester(newton, pageable);
//        assertEquals(0, shareGroups.getTotalElements());
//    }

    @Test
    public void findAll() {
        Page<ShareGroup> shareGroups = shareGroupService.findAll(pageable);
        assertEquals(1, shareGroups.getTotalElements());
    }
}
