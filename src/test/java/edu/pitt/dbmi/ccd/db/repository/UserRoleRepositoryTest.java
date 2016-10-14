package edu.pitt.dbmi.ccd.db.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.UserRole;

/**
 * Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void findById() {
        UserRole role = userRoleRepository.findById(1L);
        assertNotNull(role);
        assertEquals((Long) 1L, role.getId());
    }

    @Test
    public void findByName() {
        UserRole role = userRoleRepository.findByName("ADMIN");
        assertNotNull(role);
        assertEquals("ADMIN", role.getName());
    }

    @Test
    @Ignore
    public void findByUserAccounts() {
        UserAccount isaac = userAccountRepository.findOne(1L);
        UserAccount alan = userAccountRepository.findOne(2L);
        Set<UserAccount> users = new HashSet<>(Arrays.asList(isaac, alan));
        UserRole role = userRoleRepository.findByUserAccounts(users);
    }
}
