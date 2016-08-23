package edu.pitt.dbmi.ccd.db.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.ShareAccess;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CCDDatabaseApplication.class)
public class ShareAccessRepositoryTest {

    @Autowired(required=true)
    private ShareAccessRepository shareAccessRepository;

    @Test
    public void saveAndDelete() {
        // save
        final ShareAccess shareAccess = shareAccessRepository.save(new ShareAccess("TEST", "Test shareAccess"));
        assertNotNull(shareAccess.getId());

        // delete
        shareAccessRepository.delete(shareAccess);
        final Optional<ShareAccess> found = shareAccessRepository.findById(shareAccess.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        final Optional<ShareAccess> shareAccess = shareAccessRepository.findById(1L);
        assertTrue(shareAccess.isPresent());
    }

    @Test
    public void findByName() {
        final Optional<ShareAccess> shareAccess = shareAccessRepository.findByName("PUBLIC");
        assertTrue(shareAccess.isPresent());
    }

    @Test
    public void findAll() {
        final Pageable pageable = new PageRequest(0, 3);
        final Page<ShareAccess> page = shareAccessRepository.findAll(pageable);
        assertEquals(3, page.getTotalElements());
    }
}
