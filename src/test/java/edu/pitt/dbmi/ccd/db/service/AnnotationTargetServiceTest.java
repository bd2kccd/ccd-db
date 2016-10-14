package edu.pitt.dbmi.ccd.db.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import edu.pitt.dbmi.ccd.db.entity.AnnotationTarget;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

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

    private final Pageable pageable = new PageRequest(0, 1);

    @Test
    public void saveAndDelete() {
        // save
        final UserAccount userAccount = userAccountService.findById(1L);
        AnnotationTarget annotationTarget = new AnnotationTarget(userAccount, "Test annotationTarget", "https://google.com");
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
