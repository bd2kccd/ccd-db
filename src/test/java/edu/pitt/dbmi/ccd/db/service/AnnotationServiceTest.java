package edu.pitt.dbmi.ccd.db.service;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
import edu.pitt.dbmi.ccd.db.entity.*;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class AnnotationServiceTest {

    @Autowired
    private AnnotationService annotationService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private AnnotationTargetService annotationTargetService;
    @Autowired
    private AccessService accessService;
    @Autowired
    private VocabularyService vocabularyService;

    private UserAccount owner;
    private UserAccount viewer;
    private Pageable pageable = new PageRequest(0, 10);

    @Before
    public void setup() {
        owner = userAccountService.findById(1L).get();
        viewer = userAccountService.findById(2L).get();
    }

    @Test
    public void saveAndDelete() {
        // save
        final AnnotationTarget annotationTarget = annotationTargetService.findById(1L).get();
        final Access access = accessService.findById(1L).get();
        final Vocabulary vocabulary = vocabularyService.findById(1L).get();
        Annotation annotation = new Annotation(owner, annotationTarget, null, access, null, vocabulary);
        annotation = annotationService.save(annotation);

        // delete
        annotationService.delete(annotation);
        Optional<Annotation> found = annotationService.findById(owner, annotation.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findByIdPublic() {
        final Long id = 1L;
        Optional<Annotation> annotation;

        // owner
        annotation = annotationService.findById(owner, id);
        assertTrue(annotation.isPresent());

        // non-owner
        annotation = annotationService.findById(viewer, id);
        assertTrue(annotation.isPresent());
    }

    @Test
    public void findByIdGroup() {
        final Long id = 3L;
        Optional<Annotation> annotation;

        // member
        annotation = annotationService.findById(owner, id);
        assertTrue(annotation.isPresent());

        // non-member
        annotation = annotationService.findById(viewer, id);
        assertFalse(annotation.isPresent());
    }

    @Test
    public void findByIdPrivate()  {
        final Long id = 4L;
        Optional<Annotation> annotation;

        // owner
        annotation = annotationService.findById(owner, id);
        assertTrue(annotation.isPresent());

        // non-owner
        annotation = annotationService.findById(viewer, id);
        assertFalse(annotation.isPresent());
    }

    @Test
    public void findByParent() {
        final Annotation parent = annotationService.findById(owner, 1L).get();

        final Page<Annotation> annotations = annotationService.findByParent(owner, parent, false, pageable);
        assertEquals(1, annotations.getTotalElements());

        // make sure parent is actually parent
        final Annotation child = annotations.iterator().next();
        assertEquals(parent.getId(), child.getParent().getId());
    }

    @Test
    public void filterUser() {
        // has annotations
        final Page<Annotation> annotations = annotationService.filter(owner, owner.getUsername(), null, null, null, null, null, null, false, false, pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not have annotations
        final Page<Annotation> empty = annotationService.filter(owner, viewer.getUsername(), null, null, null, null, null, null, false, false, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterGroup() {
        // has annotations
        final Page<Annotation> annotations = annotationService.filter(owner, null, "scientists", null, null, null, null, null, false, false, pageable);
        assertEquals(1, annotations.getTotalElements());

        // does not have annotations
        final Page<Annotation> empty = annotationService.filter(owner, null, "Does Not Exist", null, null, null, null, null, false, false, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterTarget() {
        // has annotations
        final Page<Annotation> annotations = annotationService.filter(owner, null, null, 1L, null, null, null, null, false, false, pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not have annotations
        final Page<Annotation> empty = annotationService.filter(owner, null, null, 100L, null, null, null, null, false, false, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterVocabulary() {
        // has annotations
        final Page<Annotation> annotations = annotationService.filter(owner, null, null, null, "Plaintext", null, null, null, false, false, pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not have annotations
        final Page<Annotation> empty = annotationService.filter(owner, null, null, null, "Does Not Exist", null, null, null, false, false, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterAttributeName() {
        // has annotations
        final Page<Annotation> annotations = annotationService.filter(owner, null, null, null, null, null, "text", null, false, false, pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not have annotations
        final Page<Annotation> empty = annotationService.filter(owner, null, null, null, null, null, "Does Not Exist", null, false, false, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void search() {
        // matches
        final Set<String> search = new HashSet<>(Arrays.asList("annotation"));
        Page<Annotation> annotations = annotationService.search(owner, null, null, null, null, null, null, null, false, false, search, new HashSet<String>(0), pageable);
        assertEquals(4, annotations.getTotalElements());

        // nots
        final Set<String> nots = search;
        annotations = annotationService.search(owner, null, null, null, null, null, null, null, false, false, new HashSet<String>(0), nots, pageable);
        assertEquals(0, annotations.getTotalElements());
    }

    @Test
    public void findAll() {
        Page<Annotation> annotations = annotationService.findAll(owner, pageable);
        assertEquals(4, annotations.getTotalElements());

        annotations = annotationService.findAll(viewer, pageable);
        assertEquals(2, annotations.getTotalElements());
    }
}
