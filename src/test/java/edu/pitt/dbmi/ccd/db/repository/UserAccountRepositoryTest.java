package edu.pitt.dbmi.ccd.db.repository;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.*;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CCDDatabaseApplication.class)
public class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    private final Pageable pageable = new PageRequest(0, 10);

    @Test
    public void saveAndDelete() {
        final UserRole userRole = userRoleRepository.findByName("ADMIN").get();
        final Long registrationLocation = new Long(323223552);

        // save
        Person person = new Person("Albert", null, "Einstein", "einstein@example.com", "~/ccd_workspace/", "Physicist");
        UserAccount user = new UserAccount(person, new UserLogin(), new UserLoginAttempt(), userRole, "einstein", "$2a$10$mTPRrCa1THQJyk60QrhIQOgvQAnSpDkcm1QK5zwKc6m9xBu87hKqG", true, false, new Date(), UUID.randomUUID().toString());
        user = userAccountRepository.save(user);
        assertNotNull(user.getId());

        // delete
        userAccountRepository.delete(user);
        Optional<UserAccount> found = userAccountRepository.findById(user.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        Optional<UserAccount> user = userAccountRepository.findById(1L);
        assertTrue(user.isPresent());
    }

    @Test
    public void findByUsername() {
        Optional<UserAccount> user = userAccountRepository.findByUsername("isaac");
        assertTrue(user.isPresent());
    }

    @Test
    public void findByUsernameAndActivationKey() {
        Optional<UserAccount> user = userAccountRepository.findByUsernameAndActivationKey("isaac", "abcde");
        assertTrue(user.isPresent());
    }

//    @Test
//    public void findByGroupMembership() {
//        Page<UserAccount> users = userAccountRepository.findByGroupMembership("Scientists", pageable);
//        assertTrue(users.getTotalElements() == 1);
//        assertEquals(users.iterator().next().getUsername(), "isaac");
//    }
//
//    @Test
//    public void findByGroupModeration() {
//        Page<UserAccount> users = userAccountRepository.findByGroupModeration("Scientists", pageable);
//        assertTrue(users.getTotalElements() == 1);
//        assertEquals(users.iterator().next().getUsername(), "isaac");
//    }
//
//    @Test
//    public void findByGroupRequests() {
//        Page<UserAccount> users = userAccountRepository.findByGroupRequests("Scientists", pageable);
//        assertTrue(users.getTotalElements() == 1);
//        assertEquals(users.iterator().next().getUsername(), "alan");
//    }

    @Test
    public void findByEmail() {
        Optional<UserAccount> user = userAccountRepository.findByEmail("isaac@example.com");
        assertTrue(user.isPresent());
    }

    @Test
    public void findAll() {
        Page<UserAccount> users = userAccountRepository.findAll(pageable);
        assertTrue(users.getTotalElements() == 2);
    }
}
