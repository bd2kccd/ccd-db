/*
 * Copyright (C) 2015 University of Pittsburgh.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package edu.pitt.dbmi.ccd.db.service;

import edu.pitt.dbmi.ccd.db.entity.Group;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.GroupRepository;
import static edu.pitt.dbmi.ccd.db.specification.GroupSpecification.searchSpec;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public List<Group> save(Set<Group> groups) {
        return groupRepository.save(groups);
    }

//    public Group update(Group group, String name, String description) {
//        // update name
//        if (!(isNullOrEmpty(name) || group.getName().equals(name))) {
//            // check if name isn't just case change
//            if (!group.getName().equalsIgnoreCase(name)) {
//                // check that name doesn't already exist
//                groupRepository.findByName(name)
//                               .ifPresent(g -> {throw new DuplicateKeyException(String.format(DUPLICATE, name));});
//            }
//            group.setName(name);
//        }
//        // update description
//        if (!isNullOrEmpty(description)) {
//            group.setDescription(description);
//        }
//        return save(group);
//    }
    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    public List<Optional<Group>> findByIds(Iterable<Long> ids) {
        return StreamSupport.stream(ids.spliterator(), false)
                .map(this::findById)
                .collect(Collectors.toList());
    }

    public Optional<Group> findByName(String name) {
        return groupRepository.findByName(name);
    }

    public List<Optional<Group>> findByNames(Iterable<String> names) {
        return StreamSupport.stream(names.spliterator(), false)
                .map(this::findByName)
                .collect(Collectors.toList());
    }

    public Page<Group> findByMember(UserAccount user, Pageable pageable) {
        return groupRepository.findByMember(user.getUsername(), pageable);
    }

    public Page<Group> findByModerator(UserAccount user, Pageable pageable) {
        return groupRepository.findByModerator(user.getUsername(), pageable);
    }

    public Page<Group> findByRequester(UserAccount user, Pageable pageable) {
        return groupRepository.findByRequester(user.getUsername(), pageable);
    }

    public Page<Group> search(Set<String> matches, Set<String> nots, Pageable pageable) {
        return groupRepository.findAll(searchSpec(matches, nots), pageable);
    }

    public Page<Group> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    public void delete(Group group) {
        groupRepository.delete(group);
    }

    public void delete(Set<Group> groups) {
        groupRepository.delete(groups);
    }
}
