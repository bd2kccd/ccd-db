package edu.pitt.dbmi.ccd.db.repository;

import static edu.pitt.dbmi.ccd.db.specification.ShareGroupSpecification.searchSpec;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.ShareGroup;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShareGroupRepositoryTest {

    @Autowired
    private ShareGroupRepository shareGroupRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    @Ignore
    public void saveAndDelete() {
        // save
//        final ShareGroup shareGroup = shareGroupRepository.save(new ShareGroup("Test", "Test shareGroup"));
//        assertNotNull(shareGroup.getId());

        // delete
//        shareGroupRepository.delete(shareGroup);
//        final Optional<ShareGroup> found = shareGroupRepository.findById(shareGroup.getId());
//        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        final Optional<ShareGroup> shareGroup = shareGroupRepository.findById(1L);
        assertTrue(shareGroup.isPresent());
    }

    @Test
    public void findByName() {
        final Optional<ShareGroup> shareGroup = shareGroupRepository.findByName("Scientists");
        assertTrue(shareGroup.isPresent());
    }

    @Test
    public void findByMember() {
        final UserAccount user = userAccountRepository.findById(1L).get();
        final Pageable pageable = new PageRequest(0, 100);
        final Page<ShareGroup> shareGroups = shareGroupRepository.findByMember(user.getUsername(), pageable);
        assertTrue(shareGroups.getTotalElements() == 1);

        final ShareGroup shareGroup = shareGroups.iterator().next();
        assertTrue(shareGroup.getName().equals("Scientists"));
    }

    @Test
    public void findByRequester() {
        final UserAccount user = userAccountRepository.findById(2L).get();
        final Pageable pageable = new PageRequest(0, 100);
        final Page<ShareGroup> shareGroups = shareGroupRepository.findByRequester(user.getUsername(), pageable);
        assertTrue(shareGroups.getTotalElements() == 1);

        final ShareGroup shareGroup = shareGroups.iterator().next();
        assertTrue(shareGroup.getName().equals("Scientists"));
    }

    @Test
    public void search() {
        final Set<String> empty = Collections.<String>emptySet();
        final Set<String> searches = new HashSet<>(Arrays.asList("scientist"));
        final Pageable pageable = new PageRequest(0, 100);
        final Page<ShareGroup> shareGroups = shareGroupRepository.findAll(searchSpec(searches, empty), pageable);
        assertTrue(shareGroups.getTotalElements() == 1);

        final Set<String> nots = searches;
        final Page<ShareGroup> none = shareGroupRepository.findAll(searchSpec(empty, nots), pageable);
        assertTrue(none.getTotalElements() == 0);
    }

    @Test
    public void findAll() {
        final Pageable pageable = new PageRequest(0, 100);
        final Page<ShareGroup> shareGroups = shareGroupRepository.findAll(pageable);
        assertTrue(shareGroups.getTotalElements() == 1);
    }
}
