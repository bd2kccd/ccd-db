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

package edu.pitt.dbmi.ccd.db.repository;

import static edu.pitt.dbmi.ccd.db.specification.VocabularySpecification.searchSpec;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import edu.pitt.dbmi.ccd.db.entity.Vocabulary;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VocabularyRepositoryTest {

    @Autowired(required=true)
    private VocabularyRepository vocabularyRepository;

    private final Pageable pageable = new PageRequest(0, 100);

    @Test
    public void saveAndDelete() {
        // save
        Vocabulary vocabulary = vocabularyRepository.save(new Vocabulary("TEST", "Test vocabulary", "1"));
        assertNotNull(vocabulary.getId());

        // delete
        vocabularyRepository.delete(vocabulary);
        Optional<Vocabulary> found = vocabularyRepository.findById(vocabulary.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<Vocabulary> vocabulary = vocabularyRepository.findById(1L);
        assertTrue(vocabulary.isPresent());
    }

    @Test
    public void findByName() {
        Optional<Vocabulary> vocabulary = vocabularyRepository.findByName("Plaintext");
        assertTrue(vocabulary.isPresent());
    }

    @Test
    public void search() {
        final Set<String> searchText = new HashSet<>(Arrays.asList("text"));

        // Search for text
        Page<Vocabulary> vocabularies = vocabularyRepository.findAll(searchSpec(searchText, null), pageable);
        assertEquals(1, vocabularies.getTotalElements());

        // Search for negated text
        Page<Vocabulary> empty = vocabularyRepository.findAll(searchSpec(null, searchText), pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void findAll() {
        Page<Vocabulary> vocabularies = vocabularyRepository.findAll(pageable);
        assertEquals(1, vocabularies.getTotalElements());
    }
}
