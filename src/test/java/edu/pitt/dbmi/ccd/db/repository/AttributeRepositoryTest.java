package edu.pitt.dbmi.ccd.db.repository;

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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CCDDatabaseApplication.class)
public class AttributeRepositoryTest {

    @Autowired(required=true)
    private AttributeRepository attributeRepository;

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Test
    public void saveAndDelete() {
        // save
        final Vocabulary vocabulary = vocabularyRepository.findOne(1L);
        final Attribute attribute = attributeRepository.save(new Attribute(vocabulary, null, "TEST", null));
        assertNotNull(attribute.getId());

        // delete
        attributeRepository.delete(attribute);
        final Optional<Attribute> found = attributeRepository.findById(attribute.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        final Optional<Attribute> attribute = attributeRepository.findById(1L);
        assertTrue(attribute.isPresent());
    }

    @Test
    public void findAll() {
        final Pageable pageable = new PageRequest(0, 10);
        final Page<Attribute> attributes = attributeRepository.findAll(pageable);
        assertTrue(attributes.getTotalElements() == 1);
    }
}
