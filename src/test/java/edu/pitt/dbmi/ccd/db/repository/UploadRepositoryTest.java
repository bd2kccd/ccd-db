package edu.pitt.dbmi.ccd.db.repository;

import static edu.pitt.dbmi.ccd.db.specification.UploadSpecification.filterSpec;
import static edu.pitt.dbmi.ccd.db.specification.UploadSpecification.searchSpec;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
public class UploadRepositoryTest {

    @Autowired
    private UploadRepository uploadRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    Pageable pageable = new PageRequest(0, 10);

    @Test
    public void saveAndDelete() {
        // save
        UserAccount user = userAccountRepository.findOne(1L);
        Upload upload = uploadRepository.save(new Upload(user, "TEST", "http://www.google.com"));
        assertNotNull(upload.getId());

        // delete
        uploadRepository.delete(upload);
        Optional<Upload> found = uploadRepository.findById(upload.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<Upload> upload = uploadRepository.findById(1L);
        assertTrue(upload.isPresent());
    }

    @Test
    public void findByTitleContains() {
        Page<Upload> uploads = uploadRepository.findByTitleContains("Biomedical", pageable);
        assertTrue(uploads.getTotalElements() == 1);
    }

    @Test
    public void findByUser() {
        UserAccount user = userAccountRepository.findOne(1L);
        Page<Upload> uploads = uploadRepository.findByUser(user, pageable);
        assertTrue(uploads.getTotalElements() == 1);
    }

    @Test
    public void filterType() {
        Specification<Upload> spec = filterSpec(null, "url");
        Page<Upload> uploads = uploadRepository.findAll(spec, pageable);
        assertTrue(uploads.getTotalElements() == 1);

        spec = filterSpec(null, "file");
        uploads = uploadRepository.findAll(spec, pageable);
        assertTrue(uploads.getTotalElements() == 0);
    }

    @Test
    public void search() {
        Set<String> searches = new HashSet<>(Arrays.asList("Biomedical"));
        Specification<Upload> spec = searchSpec(null, null, searches, null);
        Page<Upload> uploads = uploadRepository.findAll(spec, pageable);
        assertTrue(uploads.getTotalElements() == 1);

        Set<String> nots = searches;
        spec = searchSpec(null, null, null, nots);
        uploads = uploadRepository.findAll(spec, pageable);
        assertTrue(uploads.getTotalElements() == 0);
    }

    @Test
    public void findAll() {
        Page<Upload> uploads = uploadRepository.findAll(pageable);
        assertTrue(uploads.getTotalElements() == 1);
    }
}