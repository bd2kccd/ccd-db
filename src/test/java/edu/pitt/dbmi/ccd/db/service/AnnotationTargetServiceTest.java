package edu.pitt.dbmi.ccd.db.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

import edu.pitt.dbmi.ccd.db.entity.AnnotationTarget;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.specification.AnnotationTargetSpecification;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnotationTargetServiceTest {

    @Autowired
    private AnnotationTargetService annotationTargetService;
    @Autowired
    private UserAccountService userAccountService;
    private UserAccount user;
    private UserAccount userNonMatching;

    private final Pageable pageable = new PageRequest(0, 1);

    @Before
    public void setup() {
        user = userAccountService.findById(1L);
        userNonMatching = userAccountService.findById(2L);
    }

    @Test
    public void saveAndDelete() {
        // save
        AnnotationTarget annotationTarget = new AnnotationTarget(user, "Test annotationTarget", "https://google.com");
        annotationTarget = annotationTargetService.save(annotationTarget);
        assertNotNull(annotationTarget.getId());

        // delete
        annotationTargetService.delete(annotationTarget);
        AnnotationTarget found = annotationTargetService.findById(annotationTarget.getId());
        assertNull(found);
    }

    @Test
    public void findById() {
        AnnotationTarget target = annotationTargetService.findById(1L);
        assertNotNull(target);
        assertEquals((Long) 1L, target.getId());
    }

    @Test
    public void filterUser() {
        final Specification<AnnotationTarget> userSpecification = new AnnotationTargetSpecification.SearchBuilder().user(user.getAccountId()).build();
        final Page<AnnotationTarget> annotationTargets = annotationTargetService.search(userSpecification, pageable);
        assertEquals(1, annotationTargets.getTotalElements());

        final Specification<AnnotationTarget> userSpecificationNoTargets = new AnnotationTargetSpecification.SearchBuilder().user(userNonMatching.getAccountId()).build();
        final Page<AnnotationTarget> empty = annotationTargetService.search(userSpecificationNoTargets, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void filterType() {
        final Specification<AnnotationTarget> typeSpecification = new AnnotationTargetSpecification.SearchBuilder().type("url").build();
        final Page<AnnotationTarget> annotationTargets = annotationTargetService.search(typeSpecification, pageable);
        assertEquals(1, annotationTargets.getTotalElements());

        final Specification<AnnotationTarget> typeSpecificationNoTargets = new AnnotationTargetSpecification.SearchBuilder().type("file").build();
        final Page<AnnotationTarget> empty = annotationTargetService.search(typeSpecificationNoTargets, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void search() {
        Set<String> searches = new HashSet<>(Arrays.asList("Biomedical"));

        final Specification<AnnotationTarget> searchSpecification = new AnnotationTargetSpecification.SearchBuilder().containing(searches).build();
        final Page<AnnotationTarget> annotationTargets = annotationTargetService.search(searchSpecification, pageable);
        assertEquals(1, annotationTargets.getTotalElements());

        final Specification<AnnotationTarget> negatedSearchSpecification = new AnnotationTargetSpecification.SearchBuilder().notContaining(searches).build();
        final Page<AnnotationTarget> empty = annotationTargetService.search(negatedSearchSpecification, pageable);
        assertEquals(0, empty.getTotalElements());
    }

    @Test
    public void findAll() {
        Page<AnnotationTarget> targets = annotationTargetService.findAll(pageable);
        assertEquals(1, targets.getTotalElements());
    }
}
