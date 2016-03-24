package edu.pitt.dbmi.ccd.db.repository;

import static edu.pitt.dbmi.ccd.db.specification.VocabularySpecification.searchSpec;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class VocabularyRepositoryTest {

    @Autowired(required=true)
    private VocabularyRepository vocabularyRepository;

    private Vocabulary vocabulary;

    @Before
    public void setUp() {
        vocabulary = new Vocabulary("TEST", "Test description");
        vocabulary = vocabularyRepository.save(vocabulary);
        assertNotNull(vocabulary.getId());
    }

    @After
    public void tearDown() {
        vocabularyRepository.delete(vocabulary);
    }

    @Test
    public void findById() {
        final Long id = vocabulary.getId();
        final Optional<Vocabulary> found = vocabularyRepository.findById(id);
        assertTrue(found.isPresent());
    }

    @Test
    public void findByName() {
        final String name = vocabulary.getName();
        final Optional<Vocabulary> found = vocabularyRepository.findByName(name);
        assertTrue(found.isPresent());
    }

    @Test
    public void search() {
        final Long id = vocabulary.getId();
        final Set<String> searches = new HashSet<>(Arrays.asList("test"));
        final Pageable pageable = new PageRequest(0, 1000);
        final Page<Vocabulary> vocabularies = vocabularyRepository.findAll(searchSpec(searches, null), pageable);
        assertTrue(vocabularies.hasNext());
        assertTrue(StreamSupport.stream(vocabularies.spliterator(), false)
                                .anyMatch(v -> v.getId().equals(id)));
    }

    @Test
    public void findAll() {
        final Long id = vocabulary.getId();
        final Pageable pageable = new PageRequest(0, 1000);
        final Page<Vocabulary> vocabularies = vocabularyRepository.findAll(pageable);
        assertTrue(vocabularies.hasNext());
        assertTrue(StreamSupport.stream(vocabularies.spliterator(), false)
                                .anyMatch(v -> v.getId().equals(id)));
    }
}
