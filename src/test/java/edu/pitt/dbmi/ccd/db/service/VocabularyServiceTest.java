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
import org.junit.runner.RunWith;
import org.junit.Test;
import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;
import edu.pitt.dbmi.ccd.db.repository.VocabularyRepository;
import edu.pitt.dbmi.ccd.db.service.VocabularyService;
import edu.pitt.dbmi.ccd.db.error.NotFoundException;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class VocabularyServiceTest {

    @Autowired(required=true)
    private VocabularyService vocabService;

    private static final String name = "TEST";
    private static final String description = "Test description";
    private static final String none = "Does Not Exist";

    @Test
    public void testCrud() {
        // create
        final Vocabulary vocab = vocabService.save(new Vocabulary(name, description));
        assertNotNull(vocab.getId());

        // findByName
        try {
            final Vocabulary found = vocabService.findByName(name);
            assertEquals(vocab.getId(), found.getId());
        } catch (NotFoundException ex) {
            vocabService.delete(vocab);
            fail(ex.getMessage());
        }

        // delete
        vocabService.delete(vocab);        
    }

    @Test(expected=NotFoundException.class)
    public void testNotFound() {
        vocabService.findByName(none);
    }
}
