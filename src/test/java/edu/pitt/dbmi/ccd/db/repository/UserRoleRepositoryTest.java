package edu.pitt.dbmi.ccd.db.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.UserRole;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CCDDatabaseApplication.class)
public class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void saveAndDelete() {
        // save
        UserRole userRole = userRoleRepository.save(new UserRole("TEST", "Test user role"));
        assertNotNull(userRole.getId());

        // delete
        userRoleRepository.delete(userRole);
        Optional<UserRole> found = userRoleRepository.findById(userRole.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<UserRole> role = userRoleRepository.findById(1L);
        assertTrue(role.isPresent());
    }

    @Test
    public void findByName() {
        Optional<UserRole> role = userRoleRepository.findByName("ADMIN");
        assertTrue(role.isPresent());
    }
}