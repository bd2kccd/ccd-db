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
import edu.pitt.dbmi.ccd.db.entity.Access;
import edu.pitt.dbmi.ccd.db.repository.AccessRepository;
import edu.pitt.dbmi.ccd.db.error.NotFoundException;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AccessService {

    private final AccessRepository accessRepository;

    @Autowired(required=true)
    public AccessService(AccessRepository accessRepository) {
        this.accessRepository = accessRepository;

        List<Access> accessControls = accessRepository.findAll();
        if (accessControls.isEmpty()) {
            accessControls.add(new Access("PUBLIC", "Visable to anyone"));
            accessControls.add(new Access("GROUP", "Visable to specified group"));
            accessControls.add(new Access("PRIVATE", "Visable only to creator"));

            accessRepository.save(accessControls);
        }
    }

    public Access save(Access access) {
        return accessRepository.save(access);
    }

    public Access findOne(Long id) {
        Optional<Access> access = accessRepository.findById(id);
        return access.orElseThrow(() -> new NotFoundException("Access", "id", id));
    }

    public Access findByName(String name) {
        Optional<Access> access = accessRepository.findByName(name);
        return access.orElseThrow(() -> new NotFoundException("Access", "name", name));
    }

    public Page<Access> findAll(Pageable pageable) {
        return accessRepository.findAll(pageable);
    }

    protected void delete(Access access) {
        accessRepository.delete(access);
    }
}
