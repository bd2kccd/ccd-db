package edu.pitt.dbmi.ccd.db.repository;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;
import static edu.pitt.dbmi.ccd.db.specification.VocabularySpecification.searchSpec;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
        Optional<Vocabulary> found = vocabularyRepository.findById(vocabulary.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        final Optional<Vocabulary> found = vocabularyRepository.findById(1L);
        assertTrue(found.isPresent());
    }

    @Test
    public void findByName() {
        final Optional<Vocabulary> found = vocabularyRepository.findByName("Plaintext");
        assertTrue(found.isPresent());
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
