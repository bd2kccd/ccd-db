package edu.pitt.dbmi.ccd.db.service;

import static org.junit.Assert.*;

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

public class UserRoleServiceTest {

    @Autowired
    private UserRoleService userRoleService;

    private final Pageable pageable = new PageRequest(0, 2);

    @Test
    public void saveAndDelete() {
        // save
        UserRole userRole = new UserRole("TEST");
        userRole.setDescription("Test UserRole");
        userRole = userRoleService.save(userRole);
        assertNotNull(userRole.getId());

        // delete
        userRoleService.delete(userRole);
        UserRole found = userRoleService.findById(userRole.getId());
        assertNull(found);
    }

    @Test
    public void findById() {
        UserRole userRole = userRoleService.findById(1L);
        assertNotNull(userRole);
    }

    @Test
    public void findByName() {
        UserRole userRole = userRoleService.findByName("ADMIN");
        assertNotNull(userRole);
    }

    @Test
    public void findAll() {
        Page<UserRole> userRoles = userRoleService.findAll(pageable);
        assertEquals(2, userRoles.getTotalElements());
    }
}
