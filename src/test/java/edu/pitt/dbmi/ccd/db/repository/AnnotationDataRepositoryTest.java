package edu.pitt.dbmi.ccd.db.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.AnnotationData;
import edu.pitt.dbmi.ccd.db.entity.Attribute;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CCDDatabaseApplication.class)
public class AnnotationDataRepositoryTest {

    @Autowired
    private AnnotationDataRepository annotationDataRepository;
    @Autowired
    private AnnotationRepository annotationRepository;
    @Autowired
    private AttributeRepository attributeRepository;

    @Test
    public void saveAndDelete() {
        // save
        final Annotation annotation = annotationRepository.findOne(1L);
        final Attribute attribute = attributeRepository.findOne(1L);
        final AnnotationData data = annotationDataRepository.save(new AnnotationData(annotation, attribute, "Test"));
        assertNotNull(data.getId());

        // delete
        annotationDataRepository.delete(data);
        final Optional<AnnotationData> found = annotationDataRepository.findById(data.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        final Optional<AnnotationData> data = annotationDataRepository.findById(1L);
        assertTrue(data.isPresent());
    }

    @Test
    public void findByAnnotation() {
        final Annotation annotation = annotationRepository.findOne(4L);
        final Pageable pageable = new PageRequest(0, 100);
        final Page<AnnotationData> data = annotationDataRepository.findByAnnotation(annotation, pageable);
        assertEquals(2, data.getTotalElements());
    }
}
