package edu.pitt.dbmi.ccd.db.repository;

import static edu.pitt.dbmi.ccd.db.specification.AnnotationSpecification.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class AnnotationRepositoryTest {

    @Autowired
    private AnnotationRepository annotationRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    private UserAccount owner;
    private UserAccount viewer;
    private final Pageable pageable = new PageRequest(0, 100);
    final Set<String> emptySet = Collections.<String>emptySet();

    @Before
    public void setup() {
        owner = userAccountRepository.findOne(1L);
        viewer = userAccountRepository.findOne(2L);
    }

    @Test
    public void findByIdPublic() {
        Annotation annotation;

        // owner
        Specification<Annotation> spec = idSpec(owner, 1L);
        annotation = annotationRepository.findOne(spec);
        assertNotNull(annotation);

        // non-owner
        spec = idSpec(viewer, 1L);
        annotation = annotationRepository.findOne(spec);
        assertNotNull(annotation);
    }

    @Test
    public void findByIdGroup() {
        Annotation annotation;

        // member
        Specification<Annotation> spec = idSpec(owner, 3L);

        annotation = annotationRepository.findOne(spec);
        assertNotNull(annotation);

        // non-member
        spec = idSpec(viewer, 3L);
        annotation = annotationRepository.findOne(spec);
        assertNull(annotation);
    }

    @Test
    public void findByIdPrivate()  {
        Annotation annotation;

        // owner
        Specification<Annotation> spec = idSpec(owner, 4L);
        annotation = annotationRepository.findOne(spec);
        assertNotNull(annotation);

        // non-owner
        spec = idSpec(viewer, 4L);
        annotation = annotationRepository.findOne(spec);
        assertNull(annotation);
    }

    @Test
    public void findByParent() {
        Specification<Annotation> one = idSpec(owner, 1L);
        final Annotation parent = annotationRepository.findOne(one);

        Specification<Annotation> spec = parentSpec(owner, 1L, false);
        final Page<Annotation> annotations = annotationRepository.findAll(spec, pageable);
        assertEquals(1, annotations.getTotalElements());

        // make sure parent is actually parent
        final Annotation child = annotations.iterator().next();
        assertTrue(child.getParent().getId().equals(parent.getId()));
    }

    @Test
    public void filterUser() {
        // has annotations
        final Page<Annotation> annotations = annotationRepository.findAll(filterSpec(owner, owner.getUsername(), null, null, null, null, null, null, false, false), pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not have annotations
        final Page<Annotation> empty = annotationRepository.findAll(filterSpec(owner, viewer.getUsername(), null, null, null, null, null, null, false, false), pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterGroup() {
        // has annotations
        final Page<Annotation> annotations = annotationRepository.findAll(filterSpec(owner, null, "scientists", null, null, null, null, null, false, false), pageable);
        assertEquals(1, annotations.getTotalElements());

        // does not have annotations
        final Page<Annotation> empty = annotationRepository.findAll(filterSpec(owner, null, "Does Not Exist", null, null, null, null, null, false, false), pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterTarget() {
        // has annotations
        final Page<Annotation> annotations = annotationRepository.findAll(filterSpec(owner, null, null, 1L, null, null, null, null, false, false), pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not have annotations
        final Page<Annotation> empty = annotationRepository.findAll(filterSpec(owner, null, null, 100L, null, null, null, null, false, false), pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterVocabulary() {
        // has annotations
        final Page<Annotation> annotations = annotationRepository.findAll(filterSpec(owner, null, null, null, "Plaintext", null, null, null, false, false), pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not have annotations
        final Page<Annotation> empty = annotationRepository.findAll(filterSpec(owner, null, null, null, "Does Not Exist", null, null, null, false, false), pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterAttributeName() {
        // has annotations
        final Page<Annotation> annotations = annotationRepository.findAll(filterSpec(owner, null, null, null, null, null, "text", null, false, false), pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not have annotations
        final Page<Annotation> empty = annotationRepository.findAll(filterSpec(owner, null, null, null, null, null, "Does Not Exist", null, false, false), pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void search() {
        // matches
        final Set<String> search = new HashSet<>(Arrays.asList("annotation"));
        Page<Annotation> annotations = annotationRepository.findAll(searchSpec(owner, null, null, null, null, null, null, null, false, false, search, emptySet), pageable);
        assertEquals(4, annotations.getTotalElements());

        // nots
        final Set<String> nots = search;
        annotations = annotationRepository.findAll(searchSpec(owner, null, null, null, null, null, null, null, false, false, emptySet, nots), pageable);
        assertEquals(0, annotations.getTotalElements());
    }

    @Test
    public void findAll() {
        Specification<Annotation> spec = authSpec(owner);
        Page<Annotation> annotations = annotationRepository.findAll(spec, pageable);
        assertEquals(4, annotations.getTotalElements());

        spec = authSpec(viewer);
        annotations = annotationRepository.findAll(spec, pageable);
        assertEquals(2, annotations.getTotalElements());
    }
}
