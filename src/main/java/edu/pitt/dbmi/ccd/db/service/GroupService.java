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

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.pitt.dbmi.ccd.db.entity.Group;
import edu.pitt.dbmi.ccd.db.repository.GroupRepository;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class GroupService {

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
        return groupRepository.save(group);
    }

    public Optional<Group> findOne(Long id) {
        return groupRepository.findById(id);
    }

    public Optional<Group> findByName(String name) {
        return groupRepository.findByName(name);
    }

    public Page<Group> searchNames(String terms, Pageable pageable) {
        return groupRepository.findByNameContains(terms, pageable);
    }

    public Page<Group> searchDescriptions(String terms, Pageable pageable) {
        return groupRepository.findByDescriptionContains(terms, pageable);
    }

    public Page<Group> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }
}
