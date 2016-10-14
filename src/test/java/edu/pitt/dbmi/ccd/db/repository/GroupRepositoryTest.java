package edu.pitt.dbmi.ccd.db.repository;

import static edu.pitt.dbmi.ccd.db.specification.GroupSpecification.searchSpec;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import edu.pitt.dbmi.ccd.db.entity.Group;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    public void saveAndDelete() {
        // save
        final Group group = groupRepository.save(new Group("Test", "Test group"));
        assertNotNull(group.getId());

        // delete
        groupRepository.delete(group);
        final Group found = groupRepository.findById(group.getId());
        assertNull(found);
    }

    @Test
    public void findById() {
        final Group group = groupRepository.findById(1L);
        assertNotNull(group);
        assertEquals((Long) 1L, group.getId());
    }

    @Test
    public void findByName() {
        final Group group = groupRepository.findByName("Scientists");
        assertNotNull(group);
        assertEquals("Scientists", group.getName());
    }

    @Test
    @Ignore
    public void findByMember() {
//        final UserAccount user = userAccountRepository.findById(1L);
//        final Pageable pageable = new PageRequest(0, 100);
//        final Page<Group> groups = groupRepository.findByMember(user.getUsername(), pageable);
//        assertTrue(groups.getTotalElements() == 1);
//
//        final Group group = groups.iterator().next();
//        assertTrue(group.getName().equals("Scientists"));
    }

    @Test
    @Ignore
    public void findByModerator() {
//        final UserAccount user = userAccountRepository.findById(1L).get();
//        final Pageable pageable = new PageRequest(0, 100);
//        final Page<Group> groups = groupRepository.findByModerator(user.getUsername(), pageable);
//        assertTrue(groups.getTotalElements() == 1);
//
//        final Group group = groups.iterator().next();
//        assertTrue(group.getName().equals("Scientists"));
    }

    @Test
    @Ignore
    public void findByRequester() {
//        final UserAccount user = userAccountRepository.findById(2L).get();
//        final Pageable pageable = new PageRequest(0, 100);
//        final Page<Group> groups = groupRepository.findByRequester(user.getUsername(), pageable);
//        assertTrue(groups.getTotalElements() == 1);
//
//        final Group group = groups.iterator().next();
//        assertTrue(group.getName().equals("Scientists"));
    }

    @Test
    public void search() {
        final Set<String> empty = Collections.<String>emptySet();
        final Set<String> searches = new HashSet<>(Arrays.asList("scientist"));
        final Pageable pageable = new PageRequest(0, 100);
        final Page<Group> groups = groupRepository.findAll(searchSpec(searches, empty), pageable);
        assertTrue(groups.getTotalElements() == 1);

        final Set<String> nots = searches;
        final Page<Group> none = groupRepository.findAll(searchSpec(empty, nots), pageable);
        assertTrue(none.getTotalElements() == 0);
    }

    @Test
    public void findAll() {
        final Pageable pageable = new PageRequest(0, 100);
        final Page<Group> groups = groupRepository.findAll(pageable);
        assertTrue(groups.getTotalElements() == 1);
    }
}
