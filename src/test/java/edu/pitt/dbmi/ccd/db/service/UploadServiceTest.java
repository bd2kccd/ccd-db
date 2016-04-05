package edu.pitt.dbmi.ccd.db.service;

import static edu.pitt.dbmi.ccd.db.specification.UploadSpecification.filterSpec;
import static edu.pitt.dbmi.ccd.db.specification.UploadSpecification.searchSpec;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Upload;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class UploadServiceTest {

    @Autowired
    private UploadService uploadService;
    @Autowired
    private UserAccountService userAccountService;

    private final Pageable pageable = new PageRequest(0, 1);

    @Test
    public void saveAndDelete() {
        // save
        final UserAccount userAccount = userAccountService.findById(1L).get();
        Upload upload = new Upload(userAccount, "Test upload", "https://google.com");
        upload = uploadService.save(upload);
        assertNotNull(upload.getId());

        // delete
        uploadService.delete(upload);
        Optional<Upload> found = uploadService.findById(upload.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<Upload> upload = uploadService.findById(1L);
        assertTrue(upload.isPresent());
    }

    @Test
    public void filterUser() {
        Page<Upload> uploads = uploadService.filter("isaac", null, pageable);
        assertEquals(1, uploads.getTotalElements());

        uploads = uploadService.filter("alan", null, pageable);
        assertEquals(0, uploads.getTotalElements());
    }

    @Test
    public void filterType() {
        Page<Upload> uploads = uploadService.filter(null, "url", pageable);
        assertEquals(1, uploads.getTotalElements());

        uploads = uploadService.filter(null, "file", pageable);
        assertEquals(0, uploads.getTotalElements());
    }

    @Test
    public void search() {
        Set<String> searches = new HashSet<>(Arrays.asList("Biomedical"));
        Page<Upload> uploads = uploadService.search(null, null, searches, new HashSet<String>(0), pageable);
        assertEquals(1, uploads.getTotalElements());

        uploads = uploadService.search(null, null, new HashSet<String>(0), searches, pageable);
        assertEquals(0, uploads.getTotalElements());
    }

    @Test
    public void findAll() {
        Page<Upload> uploads = uploadService.findAll(pageable);
        assertEquals(1, uploads.getTotalElements());
    }
}