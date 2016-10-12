package edu.pitt.dbmi.ccd.db.repository;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;
import java.util.Optional;
import static org.junit.Assert.*;
import org.junit.Ignore;
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
public class AttributeRepositoryTest {

    @Autowired(required = true)
    private AttributeRepository attributeRepository;

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Test
    @Ignore
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
