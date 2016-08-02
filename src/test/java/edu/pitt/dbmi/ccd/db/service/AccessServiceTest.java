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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Access;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CCDDatabaseApplication.class)
public class AccessServiceTest {

    @Autowired
    private AccessService accessService;

    @Test
    public void saveAndDelete() {
        // save
        Access access = new Access("TEST", "Test access");
        access = accessService.save(access);
        assertNotNull(access.getId());

        // delete
        accessService.delete(access);
        Optional<Access> found = accessService.findById(access.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<Access> access = accessService.findById(1L);
        assertTrue(access.isPresent());
    }

    @Test
    public void findByName() {
        Optional<Access> access = accessService.findByName("PUBLIC");
        assertTrue(access.isPresent());
    }

    @Test
    public void findAll() {
        Pageable pageable = new PageRequest(0, 10);
        Page<Access> accesses = accessService.findAll(pageable);
        assertEquals(3, accesses.getTotalElements());
    }
}
