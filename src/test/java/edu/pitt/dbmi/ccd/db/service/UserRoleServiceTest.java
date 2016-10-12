package edu.pitt.dbmi.ccd.db.service;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.UserRole;
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
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
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
