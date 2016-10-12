package edu.pitt.dbmi.ccd.db.repository;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Access;
import java.util.Optional;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class AccessRepositoryTest {

    @Autowired(required = true)
    private AccessRepository accessRepository;

    @Test
    public void saveAndDelete() {
        // save
        final Access access = accessRepository.save(new Access("TEST", "Test access"));
        assertNotNull(access.getId());

        // delete
        accessRepository.delete(access);
        final Optional<Access> found = accessRepository.findById(access.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        final Optional<Access> access = accessRepository.findById(1L);
        assertTrue(access.isPresent());
    }

    @Test
    public void findByName() {
        final Optional<Access> access = accessRepository.findByName("PUBLIC");
        assertTrue(access.isPresent());
    }

    @Test
    public void findAll() {
        final Pageable pageable = new PageRequest(0, 3);
        final Page<Access> page = accessRepository.findAll(pageable);
        assertEquals(3, page.getTotalElements());
    }
}
