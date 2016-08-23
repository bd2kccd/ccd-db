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

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.pitt.dbmi.ccd.db.entity.ShareAccess;
import edu.pitt.dbmi.ccd.db.repository.ShareAccessRepository;

/**
 *
 * Aug 3, 2016 4:29:30 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class ShareAccessService {

    private final ShareAccessRepository shareAccessRepository;

    @Autowired
    public ShareAccessService(ShareAccessRepository shareAccessRepository) {
        this.shareAccessRepository = shareAccessRepository;
    }

    public ShareAccess save(ShareAccess access) {
        return shareAccessRepository.save(access);
    }

    public Optional<ShareAccess> findById(Long id) {
        return shareAccessRepository.findById(id);
    }

    public Optional<ShareAccess> findByName(String name) {
        return shareAccessRepository.findByName(name);
    }

    public Page<ShareAccess> findAll(Pageable pageable) {
        return shareAccessRepository.findAll(pageable);
    }

    public void delete(ShareAccess access) {
        shareAccessRepository.delete(access);
    }

}
