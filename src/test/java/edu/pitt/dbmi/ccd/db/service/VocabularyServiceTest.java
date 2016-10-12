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
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
public class VocabularyServiceTest {

    @Autowired
    private VocabularyService vocabularyService;

    private final Pageable pageable = new PageRequest(0, 1);

    @Test
    public void saveAndDelete() {
        // save
        Vocabulary vocabulary = new Vocabulary("TEST", "Test vocabulary");
        vocabulary = vocabularyService.save(vocabulary);

        // delete
        vocabularyService.delete(vocabulary);
        Optional<Vocabulary> found = vocabularyService.findById(vocabulary.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<Vocabulary> vocabulary = vocabularyService.findById(1L);
        assertTrue(vocabulary.isPresent());
    }

    @Test
    public void findByName() {
        Optional<Vocabulary> vocabulary = vocabularyService.findByName("Plaintext");
        assertTrue(vocabulary.isPresent());
    }

    @Test
    public void search() {
        // matches
        final Set<String> search = new HashSet<>(Arrays.asList("Plaintext"));
        Page<Vocabulary> vocabularies = vocabularyService.search(search, new HashSet<String>(0), pageable);
        assertEquals(1, vocabularies.getTotalElements());

        // nots
        vocabularies = vocabularyService.search(new HashSet<String>(0), search, pageable);
        assertEquals(0, vocabularies.getTotalElements());
    }

    @Test
    public void findAll() {
        Page<Vocabulary> vocabularies = vocabularyService.findAll(pageable);
        assertEquals(1, vocabularies.getTotalElements());
    }
}
