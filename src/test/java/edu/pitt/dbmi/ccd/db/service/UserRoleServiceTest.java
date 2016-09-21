package edu.pitt.dbmi.ccd.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import edu.pitt.dbmi.ccd.db.entity.UserRole;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class UserRoleServiceTest {

    @Autowired
    private UserRoleService userRoleService;

    private final Pageable pageable = new PageRequest(0, 2);

    @Test
    public void saveAndDelete() {
        // save
        UserRole userRole = new UserRole("TEST", "Test UserRole");
        userRole = userRoleService.save(userRole);
        assertNotNull(userRole.getId());

        // delete
        userRoleService.delete(userRole);
        Optional<UserRole> found = userRoleService.findById(userRole.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<UserRole> userRole = userRoleService.findById(1L);
        assertTrue(userRole.isPresent());
    }

    @Test
    public void findByName() {
        Optional<UserRole> userRole = userRoleService.findByName("ADMIN");
        assertTrue(userRole.isPresent());
    }

    @Test
    public void findAll() {
        Page<UserRole> userRoles = userRoleService.findAll(pageable);
        assertEquals(2, userRoles.getTotalElements());
    }
}