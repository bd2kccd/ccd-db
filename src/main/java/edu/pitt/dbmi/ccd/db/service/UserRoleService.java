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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.pitt.dbmi.ccd.db.entity.UserRole;
import edu.pitt.dbmi.ccd.db.repository.UserRoleRepository;

/**
 *
 * Jun 24, 2016 4:32:17 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    void delete(UserRole userRole) {
        userRoleRepository.delete(userRole);
    }

    Optional<UserRole> findById(Long id) {
        return userRoleRepository.findById(id);
    }

    Optional<UserRole> findByName(String name) {
        return userRoleRepository.findByName(name);
    }

    Page<UserRole> findAll(Pageable pageable) {
        return userRoleRepository.findAll(pageable);
    }
}
