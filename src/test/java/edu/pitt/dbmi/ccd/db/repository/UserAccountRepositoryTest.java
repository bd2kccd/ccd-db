package edu.pitt.dbmi.ccd.db.repository;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;
    private final Pageable pageable = new PageRequest(0, 10);

    @Test
    public void saveAndDelete() {
        // save
        Person person = new Person("Albert", "Einstein", "einstein@example.com", "~/ccd_workspace/");
        UserAccount user = new UserAccount(person, "einstein", "$2a$10$mTPRrCa1THQJyk60QrhIQOgvQAnSpDkcm1QK5zwKc6m9xBu87hKqG", true, new Date());
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
        Optional<UserAccount> user = userAccountRepository.findByUsernameAndActivationKey("isaac", "abcd");
        assertTrue(user.isPresent());
    }

    @Test
    public void findByGroupMembership() {
        Page<UserAccount> users = userAccountRepository.findByGroupMembership("Scientists", pageable);
        assertTrue(users.getTotalElements() == 1);
        assertEquals(users.iterator().next().getUsername(), "isaac");
    }

    @Test
    public void findByGroupModeration() {
        Page<UserAccount> users = userAccountRepository.findByGroupModeration("Scientists", pageable);
        assertTrue(users.getTotalElements() == 1);
        assertEquals(users.iterator().next().getUsername(), "isaac");
    }

    @Test
    public void findByGroupRequests() {
        Page<UserAccount> users = userAccountRepository.findByGroupRequests("Scientists", pageable);
        assertTrue(users.getTotalElements() == 1);
        assertEquals(users.iterator().next().getUsername(), "alan");
    }

    @Test
    public void findByEmail() {
        Optional<UserAccount> user = userAccountRepository.findByEmail("isaac@example.com");
        assertTrue(user.isPresent());
    }

    @Test
    public void findByFirstNameAndLastName() {
        Optional<UserAccount> user = userAccountRepository.findByFirstNameAndLastName("Alan", "Turing");
        assertTrue(user.isPresent());
    }

    @Test
    public void findAll() {
        Page<UserAccount> users = userAccountRepository.findAll(pageable);
        assertTrue(users.getTotalElements() == 2);
    }
}
