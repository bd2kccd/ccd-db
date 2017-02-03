package edu.pitt.dbmi.ccd.db.repository;

import static edu.pitt.dbmi.ccd.db.specification.AnnotationSpecification.authSpec;
import static edu.pitt.dbmi.ccd.db.specification.AnnotationSpecification.idSpec;
import static edu.pitt.dbmi.ccd.db.specification.AnnotationSpecification.parentSpec;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.specification.AnnotationSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnotationRepositoryTest {

    @Autowired
    private AnnotationRepository annotationRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    private UserAccount owner;
    private UserAccount viewer;
    private final Pageable pageable = new PageRequest(0, 100);

    @Before
    public void setup() {
        owner = userAccountRepository.findById(1L);
        viewer = userAccountRepository.findById(2L);
    }

    @Test
    public void findByIdPublic() {
        Annotation annotation;

        // owner
        Specification<Annotation> spec = idSpec(owner, 1L);
        annotation = annotationRepository.findOne(spec);
        assertNotNull(annotation);
        assertEquals((Long) 1L, annotation.getId());

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
        assertEquals((Long) 3L, annotation.getId());

        // non-member
        spec = idSpec(viewer, 3L);
        annotation = annotationRepository.findOne(spec);
        assertNull(annotation);
    }

    @Test
    public void findByIdPrivate() {
        Annotation annotation;

        // owner
        Specification<Annotation> spec = idSpec(owner, 4L);
        annotation = annotationRepository.findOne(spec);
        assertNotNull(annotation);
        assertEquals((Long) 4L, annotation.getId());

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
        final Specification<Annotation> userSpecification = new AnnotationSpecification.SearchBuilder(owner).user(owner.getAccountId()).build();
        final Page<Annotation> annotations = annotationRepository.findAll(userSpecification, pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not have annotations
        final Specification<Annotation> userSpecificationNoAnnotations = new AnnotationSpecification.SearchBuilder(owner).user(viewer.getAccountId()).build();
        final Page<Annotation> empty = annotationRepository.findAll(userSpecificationNoAnnotations, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterGroup() {
        // has annotations
        final Specification<Annotation> groupSpecification = new AnnotationSpecification.SearchBuilder(owner).group(1L).build();
        final Page<Annotation> annotations = annotationRepository.findAll(groupSpecification, pageable);
        assertEquals(1, annotations.getTotalElements());

        // does not have annotations
        final Specification<Annotation> groupSpecificationNoAnnotations = new AnnotationSpecification.SearchBuilder(owner).group(0L).build();
        final Page<Annotation> empty = annotationRepository.findAll(groupSpecificationNoAnnotations, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterTarget() {
        // has annotations
        final Specification<Annotation> targetSpecification = new AnnotationSpecification.SearchBuilder(owner).target(1L).build();
        final Page<Annotation> annotations = annotationRepository.findAll(targetSpecification, pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not have annotations
        final Specification<Annotation> targetSpecificationNoAnnotations = new AnnotationSpecification.SearchBuilder(owner).target(0L).build();
        final Page<Annotation> empty = annotationRepository.findAll(targetSpecificationNoAnnotations, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterVocabulary() {
        // has annotations
        final Specification<Annotation> vocabularySpecification = new AnnotationSpecification.SearchBuilder(owner).vocabulary(1L).build();
        final Page<Annotation> annotations = annotationRepository.findAll(vocabularySpecification, pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not have annotations
        final Specification<Annotation> vocabularySpecificationNoAnnotations = new AnnotationSpecification.SearchBuilder(owner).vocabulary(0L).build();
        final Page<Annotation> empty = annotationRepository.findAll(vocabularySpecificationNoAnnotations, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterAttribute() {
        // has annotations
        final Specification<Annotation> attributeSpecification = new AnnotationSpecification.SearchBuilder(owner).attribute(1L).build();
        final Page<Annotation> annotations = annotationRepository.findAll(attributeSpecification, pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not have annotations
        final Specification<Annotation> attributeSpecificationNoAnnotations = new AnnotationSpecification.SearchBuilder(owner).attribute(0L).build();
        final Page<Annotation> empty = annotationRepository.findAll(attributeSpecificationNoAnnotations, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterAttributeName() {
        // has annotations
        final Specification<Annotation> attributeNameSpecification = new AnnotationSpecification.SearchBuilder(owner).attributeName("text").build();
        final Page<Annotation> annotations = annotationRepository.findAll(attributeNameSpecification, pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not have annotations
        final Specification<Annotation> attributeNameSpecificationNoAnnotations = new AnnotationSpecification.SearchBuilder(owner).attributeName("DOES NOT EXIST").build();
        final Page<Annotation> empty = annotationRepository.findAll(attributeNameSpecificationNoAnnotations, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterCreatedDate() {
        Calendar before = new GregorianCalendar(2017, Calendar.FEBRUARY, 04);
        Calendar after = new GregorianCalendar(2017, Calendar.FEBRUARY, 01, 23, 59, 59);

        // date before
        Date beforeDate = before.getTime();
        // date after
        Date afterDate = after.getTime();

        final Specification<Annotation> createDateSpecification = new AnnotationSpecification.SearchBuilder(owner).createdRange(beforeDate, afterDate).build();
        final Page<Annotation> annotations = annotationRepository.findAll(createDateSpecification, pageable);
        assertEquals(2, annotations.getTotalElements());
    }

    @Test
    public void filterModifiedDate() {
        Calendar before = new GregorianCalendar(2017, Calendar.FEBRUARY, 04);
        Calendar after = new GregorianCalendar(2017, Calendar.FEBRUARY, 01, 23, 59, 59);

        // date before
        Date beforeDate = before.getTime();
        // date after
        Date afterDate = after.getTime();

        final Specification<Annotation> modifiedDateSpecification = new AnnotationSpecification.SearchBuilder(owner).modifiedRange(beforeDate, afterDate).build();
        final Page<Annotation> annotations = annotationRepository.findAll(modifiedDateSpecification, pageable);
        assertEquals(2, annotations.getTotalElements());
    }

    @Test
    public void search() {
        final Set<String> search = new HashSet<>(Arrays.asList("annotation"));
        Page<Annotation> annotations;

        // matches search term
        final Specification<Annotation> searchSpecification = new AnnotationSpecification.SearchBuilder(owner).containing(search).build();
        annotations = annotationRepository.findAll(searchSpecification, pageable);
        assertEquals(4, annotations.getTotalElements());

        // does not match search term
        final Specification<Annotation> negatedSearchSpecification = new AnnotationSpecification.SearchBuilder(owner).notContaining(search).build();
        annotations = annotationRepository.findAll(negatedSearchSpecification, pageable);
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
