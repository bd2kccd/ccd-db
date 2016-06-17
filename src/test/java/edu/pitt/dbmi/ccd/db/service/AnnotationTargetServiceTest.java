package edu.pitt.dbmi.ccd.db.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.AnnotationTarget;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
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
        AnnotationTarget annotationTarget = new AnnotationTarget(userAccount, "Test annotationTarget", "https://google.com");
        annotationTarget = annotationTargetService.save(annotationTarget);
        assertNotNull(annotationTarget.getId());

        // delete
        annotationTargetService.delete(annotationTarget);
        Optional<AnnotationTarget> found = annotationTargetService.findById(annotationTarget.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<AnnotationTarget> upload = annotationTargetService.findById(1L);
        assertTrue(upload.isPresent());
    }

    @Test
    public void filterUser() {
        Page<AnnotationTarget> uploads = annotationTargetService.filter("isaac", null, pageable);
        assertEquals(1, uploads.getTotalElements());

        uploads = annotationTargetService.filter("alan", null, pageable);
        assertEquals(0, uploads.getTotalElements());
    }

    @Test
    public void filterType() {
        Page<AnnotationTarget> uploads = annotationTargetService.filter(null, "url", pageable);
        assertEquals(1, uploads.getTotalElements());

        uploads = annotationTargetService.filter(null, "file", pageable);
        assertEquals(0, uploads.getTotalElements());
    }

    @Test
    public void search() {
        Set<String> searches = new HashSet<>(Arrays.asList("Biomedical"));
        Page<AnnotationTarget> uploads = annotationTargetService.search(null, null, searches, new HashSet<String>(0), pageable);
        assertEquals(1, uploads.getTotalElements());

        uploads = annotationTargetService.search(null, null, new HashSet<String>(0), searches, pageable);
        assertEquals(0, uploads.getTotalElements());
    }

    @Test
    public void findAll() {
        Page<AnnotationTarget> uploads = annotationTargetService.findAll(pageable);
        assertEquals(1, uploads.getTotalElements());
    }
}
