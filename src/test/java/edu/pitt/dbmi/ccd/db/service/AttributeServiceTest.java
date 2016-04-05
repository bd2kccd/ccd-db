package edu.pitt.dbmi.ccd.db.service;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class AttributeServiceTest {

    @Autowired
    private AttributeService attributeService;
    @Autowired
    private VocabularyService vocabularyService;

    private final Pageable pageable = new PageRequest(0, 1);

    @Test
    public void saveAndDelete() {
        // save
        final Vocabulary vocabulary = vocabularyService.findById(1L).get();
        Attribute attribute = new Attribute(vocabulary, null, "TEST", null);
        attribute = attributeService.save(attribute);

        // delete
        attributeService.delete(attribute);
        Optional<Attribute> found = attributeService.findById(attribute.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<Attribute> attribute = attributeService.findById(1L);
        assertTrue(attribute.isPresent());
    }

    @Test
    public void findByVocab() {
        final Vocabulary vocabulary = vocabularyService.findById(1L).get();
        Page<Attribute> attributes = attributeService.findByVocab(vocabulary, pageable);
        assertEquals(1, attributes.getTotalElements());
    }

    @Test
    public void findByVocabAndParentIsNull() {
        final Vocabulary vocabulary = vocabularyService.findById(1L).get();
        Page<Attribute> attributes = attributeService.findByVocabAndParentIsNull(vocabulary, pageable);
        assertEquals(1, attributes.getTotalElements());
    }

    @Test
    @Ignore
    public void findByVocabAndLevelContainsAndNameContainsAndRequirementLevelContains() {

    }

    @Test
    @Ignore
    public void findAll() {

    }
}