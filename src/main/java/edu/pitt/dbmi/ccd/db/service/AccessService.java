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

import edu.pitt.dbmi.ccd.db.entity.Access;
import edu.pitt.dbmi.ccd.db.repository.AccessRepository;

import java.util.List;
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
public class AccessService {

    private AccessRepository accessRepository;

    @Autowired(required = true)
    public AccessService(AccessRepository accessRepository) {
        this.accessRepository = accessRepository;

        List<Access> accesses = accessRepository.findAll();
        if (accesses.isEmpty()) {
            accesses.add(new Access("PUBLIC", "Visible to all users"));
            accesses.add(new Access("GROUP", "Visible to members of specified group"));
            accesses.add(new Access("PRIVATE", "Visible only to creator"));

            accessRepository.save(accesses);
        }
    }

    public Access save(Access access) {
        return accessRepository.save(access);
    }

    public Access findById(Long id) {
        return accessRepository.findById(id);
    }

    public Access findByName(String name) {
        return accessRepository.findByName(name);
    }

    public Page<Access> findAll(Pageable pageable) {
        return accessRepository.findAll(pageable);
    }

    public void delete(Access access) {
        accessRepository.delete(access);
    }
}
