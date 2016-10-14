package edu.pitt.dbmi.ccd.db.repository;

import static edu.pitt.dbmi.ccd.db.specification.VocabularySpecification.searchSpec;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    @Autowired(required = true)
    private VocabularyRepository vocabularyRepository;

    @Test
    public void saveAndDelete() {
        // save
        final Vocabulary vocabulary = vocabularyRepository.save(new Vocabulary("TEST", "Test vocabulary"));
        assertNotNull(vocabulary.getId());

        // delete
        vocabularyRepository.delete(vocabulary);
        Vocabulary found = vocabularyRepository.findById(vocabulary.getId());
        assertNull(found);
    }

    @Test
    public void findById() {
        final Vocabulary found = vocabularyRepository.findById(1L);
        assertNotNull(found);
        assertEquals((Long) 1L, found.getId());
    }

    @Test
    public void findByName() {
        final Vocabulary found = vocabularyRepository.findByName("Plaintext");
        assertNotNull(found);
        assertEquals("Plaintext", found.getName());
    }

    @Test
    public void search() {
        final Set<String> searches = new HashSet<>(Arrays.asList("text"));
        final Pageable pageable = new PageRequest(0, 1000);
        final Page<Vocabulary> vocabularies = vocabularyRepository.findAll(searchSpec(searches, null), pageable);
        assertTrue(vocabularies.getTotalElements() == 1);

        final Set<String> nots = searches;
        final Page<Vocabulary> empty = vocabularyRepository.findAll(searchSpec(null, nots), pageable);
        assertTrue(empty.getTotalElements() == 0);
    }

    @Test
    public void findAll() {
        final Pageable pageable = new PageRequest(0, 1000);
        final Page<Vocabulary> vocabularies = vocabularyRepository.findAll(pageable);
        assertTrue(vocabularies.getTotalElements() == 1);
    }
}
