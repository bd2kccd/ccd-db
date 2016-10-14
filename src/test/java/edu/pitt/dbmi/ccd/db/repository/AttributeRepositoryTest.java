package edu.pitt.dbmi.ccd.db.repository;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
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
        final Attribute found = attributeRepository.findById(attribute.getId());
        assertNull(found);
    }

    @Test
    public void findById() {
        final Attribute attribute = attributeRepository.findById(1L);
        assertNotNull(attribute);
        assertEquals((Long) 1L, attribute.getId());
    }

    @Test
    public void findAll() {
        final Pageable pageable = new PageRequest(0, 10);
        final Page<Attribute> attributes = attributeRepository.findAll(pageable);
        assertEquals(1, attributes.getTotalElements());
    }
}
