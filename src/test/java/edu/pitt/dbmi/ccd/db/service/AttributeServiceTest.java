package edu.pitt.dbmi.ccd.db.service;

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
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AttributeServiceTest {

    @Autowired
    private AttributeService attributeService;
    @Autowired
    private VocabularyService vocabularyService;

    private final Pageable pageable = new PageRequest(0, 1);

    @Test
    public void saveAndDelete() {
        // save
        final Vocabulary vocabulary = vocabularyService.findById(1L);
        Attribute attribute = new Attribute(vocabulary, null, "TEST", null);
        attribute = attributeService.save(attribute);

        // delete
        attributeService.delete(attribute);
        Attribute found = attributeService.findById(attribute.getId());
        assertNull(found);
    }

    @Test
    public void findById() {
        Attribute attribute = attributeService.findById(1L);
        assertNotNull(attribute);
        assertEquals((Long) 1L, attribute.getId());
    }

    @Test
    @Ignore
    public void findByVocab() {
//        final Vocabulary vocabulary = vocabularyService.findById(1L);
//        Page<Attribute> attributes = attributeService.findByVocab(vocabulary, pageable);
    }

    @Test
    @Ignore
    public void findByVocabAndParentIsNull() {
//        final Vocabulary vocabulary = vocabularyService.findById(1L);
//        Page<Attribute> attributes = attributeService.findByVocabAndParentIsNull(vocabulary, pageable);
    }

    @Test
    @Ignore
    public void findByVocabAndLevelContainsAndNameContainsAndRequirementLevelContains() {

    }

    @Test
    @Ignore
    public void findAll() {
        Page<Attribute> page = attributeService.findAll(pageable);
        assertEquals(1L, page.getTotalElements());
    }
}
