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
package edu.pitt.dbmi.ccd.db.service;

import edu.pitt.dbmi.ccd.db.entity.VariableType;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * Aug 5, 2016 3:59:30 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VariableTypeServiceTest {

    @Autowired
    private VariableTypeService service;

    public VariableTypeServiceTest() {
    }

    /**
     * Test of findAll method, of class VariableTypeService.
     */
    @Test
    public void testFindAll() {
        List<VariableType> result = service.findAll();
        Assert.assertFalse(result.isEmpty());
    }

    /**
     * Test of findById method, of class VariableTypeService.
     */
    @Test
    public void testFindById() {
        Long id = 1L;
        VariableType result = service.findById(id);
        Assert.assertNotNull(result);
    }

    /**
     * Test of findByName method, of class VariableTypeService.
     */
    @Test
    public void testFindByName() {
        String name = VariableTypeService.CONTINUOUS_VAR_NAME;
        VariableType result = service.findByName(name);
        Assert.assertNotNull(result);
    }

}
