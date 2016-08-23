/*
 * Copyright (C) 2016 University of Pittsburgh.
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

import static edu.pitt.dbmi.ccd.db.specification.ShareGroupSpecification.searchSpec;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.pitt.dbmi.ccd.db.entity.ShareGroup;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.ShareGroupRepository;

/**
 *
 * Aug 3, 2016 4:29:55 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class ShareGroupService {

    private final ShareGroupRepository shareGroupRepository;

    @Autowired
    public ShareGroupService(ShareGroupRepository shareGroupRepository) {
        this.shareGroupRepository = shareGroupRepository;
    }


    public ShareGroup save(ShareGroup group) {
        return shareGroupRepository.save(group);
    }

    public List<ShareGroup> save(Set<ShareGroup> groups) {
        return shareGroupRepository.save(groups);
    }

    public Optional<ShareGroup> findById(Long id) {
        return shareGroupRepository.findById(id);
    }

    public List<Optional<ShareGroup>> findByIds(Iterable<Long> ids) {
        return StreamSupport.stream(ids.spliterator(), false)
                .map(this::findById)
                .collect(Collectors.toList());
    }

    public Optional<ShareGroup> findByName(String name) {
        return shareGroupRepository.findByName(name);
    }

    public List<Optional<ShareGroup>> findByNames(Iterable<String> names) {
        return StreamSupport.stream(names.spliterator(), false)
                .map(this::findByName)
                .collect(Collectors.toList());
    }

    public Page<ShareGroup> findByMember(UserAccount user, Pageable pageable) {
        return shareGroupRepository.findByMember(user.getUsername(), pageable);
    }

    public Page<ShareGroup> findByRequester(UserAccount user, Pageable pageable) {
        return shareGroupRepository.findByRequester(user.getUsername(), pageable);
    }

    public Page<ShareGroup> search(Set<String> matches, Set<String> nots, Pageable pageable) {
        return shareGroupRepository.findAll(searchSpec(matches, nots), pageable);
    }

    public Page<ShareGroup> findAll(Pageable pageable) {
        return shareGroupRepository.findAll(pageable);
    }

    public void delete(ShareGroup group) {
        shareGroupRepository.delete(group);
    }

    public void delete(Set<ShareGroup> groups) {
        shareGroupRepository.delete(groups);
    }

}
