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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

import edu.pitt.dbmi.ccd.db.entity.ShareAccess;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class ShareAccessServiceTest {

    @Autowired
    private ShareAccessService shareAccessService;

    @Test
    public void saveAndDelete() {
        // save
        ShareAccess shareAccess = new ShareAccess("TEST", "Test access");
        shareAccess = shareAccessService.save(shareAccess);
        assertNotNull(shareAccess.getId());

        // delete
        shareAccessService.delete(shareAccess);
        Optional<ShareAccess> found = shareAccessService.findById(shareAccess.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<ShareAccess> access = shareAccessService.findById(1L);
        assertTrue(access.isPresent());
    }

    @Test
    public void findByName() {
        Optional<ShareAccess> access = shareAccessService.findByName("PUBLIC");
        assertTrue(access.isPresent());
    }

    @Test
    public void findAll() {
        Pageable pageable = new PageRequest(0, 10);
        Page<ShareAccess> accesses = shareAccessService.findAll(pageable);
        assertEquals(3, accesses.getTotalElements());
    }
}
