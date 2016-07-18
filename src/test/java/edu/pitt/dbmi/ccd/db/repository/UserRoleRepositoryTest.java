package edu.pitt.dbmi.ccd.db.repository;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.UserRole;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
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

    @Test
    @Ignore
    public void findByUserAccounts() {
        UserAccount isaac = userAccountRepository.findOne(1L);
        UserAccount alan = userAccountRepository.findOne(2L);
        Set<UserAccount> users = new HashSet<>(Arrays.asList(isaac, alan));
        Optional<UserRole> role = userRoleRepository.findByUserAccounts(users);
        assertTrue(role.isPresent());
        assertEquals("USER", role.get().getName());
    }
}