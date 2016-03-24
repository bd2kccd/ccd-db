package edu.pitt.dbmi.ccd.db.repository;

import static edu.pitt.dbmi.ccd.db.specification.AnnotationSpecification.filterSpec;
import static edu.pitt.dbmi.ccd.db.specification.AnnotationSpecification.searchSpec;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;
import java.util.stream.StreamSupport;

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
    public void findById() {
        // owner request
        final Optional<Annotation> annotation = annotationRepository.findById(owner, 3L, owner.getGroups());
        assertTrue(annotation.isPresent());

        // non-owner request
        final Optional<Annotation> empty = annotationRepository.findById(viewer, 3L, viewer.getGroups());
        assertFalse(empty.isPresent());

    }

    @Test
    public void findByParent() {
        final Annotation parent = annotationRepository.findById(owner, 1L, owner.getGroups()).get();
        final Page<Annotation> annotations = annotationRepository.findByParent(owner, parent.getId(), owner.getGroups(), false, pageable);
        assertTrue(annotations.getTotalElements() == 1);

        // make sure parent is actually parent
        final Annotation child = annotations.iterator().next();
        assertTrue(child.getParent().getId().equals(parent.getId()));
    }

    @Test
    public void findAllPublic() {
        final Page<Annotation> annotations = annotationRepository.findAllPublic(false, pageable);
        assertTrue(annotations.getTotalElements() == 2);

        // make sure all are public
        assertTrue(StreamSupport.stream(annotations.spliterator(), false).allMatch(a -> a.getAccess().getName().equals("PUBLIC")));
    }

    @Test
    public void filterUser() {
        // has annotations
        final Page<Annotation> annotations = annotationRepository.findAll(filterSpec(owner, owner.getUsername(), null, null, null, null, null, null, false), pageable);
        assertTrue(annotations.getTotalElements() == 3);

        // does not have annotations
        final Page<Annotation> empty = annotationRepository.findAll(filterSpec(owner, viewer.getUsername(), null, null, null, null, null, null, false), pageable);
        assertTrue(annotations.getTotalElements() == 0);
    }

    @Test
    public void filterUpload() {
        // has annotations
        final Page<Annotation> annotations = annotationRepository.findAll(filterSpec(owner, null, null, 1L, null, null, null, null, false), pageable);
        assertTrue(annotations.getTotalElements() == 3);

        // does not have annotations
        final Page<Annotation> empty = annotationRepository.findAll(filterSpec(owner, null, null, 100L, null, null, null, null, false), pageable);
        assertTrue(annotations.getTotalElements() == 0);
    }

    @Test
    public void filterVocabulary() {
        // has annotations
        final Page<Annotation> annotations = annotationRepository.findAll(filterSpec(owner, null, null, null, "Plaintext", null, null, null, false), pageable);
        assertTrue(annotations.getTotalElements() == 3);

        // does not have annotations
        final Page<Annotation> empty = annotationRepository.findAll(filterSpec(owner, null, null, null, "Does Not Exist", null, null, null, false), pageable);
        assertTrue(empty.getTotalElements() == 0);
    }

    @Test
    public void filterAttributeName() {
        // has annotations
        final Page<Annotation> annotations = annotationRepository.findAll(filterSpec(owner, null, null, null, null, null, "text", null, false), pageable);
        assertTrue(annotations.getTotalElements() == 3);

        // does not have annotations
        final Page<Annotation> empty = annotationRepository.findAll(filterSpec(owner, null, null, null, null, null, "text", null, false), pageable);
        assertTrue(empty.getTotalElements() == 0);
    }

    @Test
    public void search() {
        // matches
        final Set<String> search = new HashSet<>(Arrays.asList("annotation"));
        Page<Annotation> annotations = annotationRepository.findAll(searchSpec(owner, null, null, null, null, null, null, null, false, search, emptySet), pageable);
        assertTrue(annotations.getTotalElements() == 3);

        // nots
        final Set<String> nots = search;
        annotations = annotationRepository.findAll(searchSpec(owner, null, null, null, null, null, null, null, false, emptySet, nots), pageable);
        assertTrue(annotations.getTotalElements() == 0);
    }

    @Test
    public void findAll() {
        Page<Annotation> annotations = annotationRepository.findAll(owner, owner.getGroups(), false, pageable);
        assertTrue(annotations.getTotalElements() == 3);

        annotations = annotationRepository.findAll(viewer, viewer.getGroups(), false, pageable);
        assertTrue(annotations.getTotalElements() == 2);
    }
}
