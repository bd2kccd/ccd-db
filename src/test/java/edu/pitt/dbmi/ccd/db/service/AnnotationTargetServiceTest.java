package edu.pitt.dbmi.ccd.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Address;
import edu.pitt.dbmi.ccd.db.entity.AnnotationTarget;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CCDDatabaseApplication.class)
public class AnnotationTargetServiceTest {

    @Autowired
    private AnnotationTargetService annotationTargetService;
    @Autowired
    private UserAccountService userAccountService;

    private final Pageable pageable = new PageRequest(0, 1);

    @Test
    public void saveAndDelete() {
        // save
        final UserAccount userAccount = userAccountService.findById(1L).get();
        AnnotationTarget annotationTarget = new AnnotationTarget(userAccount, null, new Address(userAccount, "https://google.com", "Test annotationTarget"));
        annotationTarget = annotationTargetService.save(annotationTarget);
        assertNotNull(annotationTarget.getId());

        // delete
        annotationTargetService.delete(annotationTarget);
        Optional<AnnotationTarget> found = annotationTargetService.findById(annotationTarget.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<AnnotationTarget> target = annotationTargetService.findById(1L);
        assertTrue(target.isPresent());
    }

    @Test
    public void filterUser() {
        Page<AnnotationTarget> targets = annotationTargetService.filter("isaac", null, pageable);
        assertEquals(1, targets.getTotalElements());

        targets = annotationTargetService.filter("alan", null, pageable);
        assertEquals(0, targets.getTotalElements());
    }

    @Test
    public void filterType() {
        Page<AnnotationTarget> targets = annotationTargetService.filter(null, "url", pageable);
        assertEquals(1, targets.getTotalElements());

        targets = annotationTargetService.filter(null, "file", pageable);
        assertEquals(0, targets.getTotalElements());
    }

    @Test
    @Ignore // ignoring until title field is sorted out
    public void search() {
        Set<String> searches = new HashSet<>(Arrays.asList("Biomedical"));
        Page<AnnotationTarget> targets = annotationTargetService.search(null, null, searches, new HashSet<String>(0), pageable);
        assertEquals(1, targets.getTotalElements());

        targets = annotationTargetService.search(null, null, new HashSet<String>(0), searches, pageable);
        assertEquals(0, targets.getTotalElements());
    }

    @Test
    public void findAll() {
        Page<AnnotationTarget> targets = annotationTargetService.findAll(pageable);
        assertEquals(1, targets.getTotalElements());
    }
}
