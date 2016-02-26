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

import static edu.pitt.dbmi.ccd.db.specification.GroupSpecification.searchSpec;

import java.util.Set;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.dao.DuplicateKeyException;
import edu.pitt.dbmi.ccd.db.entity.Group;
import edu.pitt.dbmi.ccd.db.repository.GroupRepository;
import edu.pitt.dbmi.ccd.db.error.NotFoundException;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class GroupService {

    private static final String DUPLICATE = "Group already exists with name: %s";
    private static final String DUPLICATES = "Groups already exist with names: ";

    private final GroupRepository groupRepository;

    @Autowired(required=true)
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;

        List<Group> groups = groupRepository.findAll();
        if (groups.isEmpty()) {
            groups.add(
                new Group("global", "Group of all users")
            );
            groupRepository.save(groups);
        }
    }

    public Group save(Group group) {
        groupRepository.findByName(group.getName())
                       .ifPresent(g -> {
                         throw new DuplicateKeyException(String.format(DUPLICATE, g.getName()));
                       });
        return groupRepository.save(group);
    }

    public List<Group> save(Iterable<Group> groups) {
        final List<String> found = StreamSupport.stream(groups.spliterator(), false)
                                                .distinct()
                                                .filter(g -> groupRepository.findByName(g.getName()).isPresent())
                                                .map(Group::getName)
                                                .collect(Collectors.toList());
        if (found.size() > 0) {
            throw new DuplicateKeyException(DUPLICATES + String.join(", ", found));
        } else {
            return groupRepository.save(groups);
        }
    }

    public Group findById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        return group.orElseThrow(() -> new NotFoundException("Group", "id", id));
    }

    public Group findByName(String name) {
        Optional<Group> group = groupRepository.findByName(name);
        return group.orElseThrow(() -> new NotFoundException("Group", "name", name));
    }

    public List<Group> findByNames(Iterable<String> names) {
        return StreamSupport.stream(names.spliterator(), false)
                            .distinct()
                            .map(n -> findByName(n))
                            .collect(Collectors.toList());
    }

    public Page<Group> search(Set<String> matches, Set<String> nots, Pageable pageable) {
        return groupRepository.findAll(searchSpec(matches, nots), pageable);
    }

    public Page<Group> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }
}
