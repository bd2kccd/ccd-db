package edu.pitt.dbmi.ccd.db.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import edu.pitt.dbmi.ccd.db.entity.Access;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
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
        final Access found = accessRepository.findById(access.getId());
        assertNull(found);
    }

    @Test
    public void findById() {
        final Access access = accessRepository.findById(1L);
        assertNotNull(access);
        assertEquals((Long) 1L, access.getId());
    }

    @Test
    public void findByName() {
        final Access access = accessRepository.findByName("PUBLIC");
        assertNotNull(access);
        assertEquals("PUBLIC", access.getName());
    }

    @Test
    public void findAll() {
        final Pageable pageable = new PageRequest(0, 3);
        final Page<Access> page = accessRepository.findAll(pageable);
        assertEquals(3, page.getTotalElements());
    }
}
