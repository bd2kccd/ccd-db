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

import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.pitt.dbmi.ccd.db.entity.UserRole;
import edu.pitt.dbmi.ccd.db.repository.UserRoleRepository;
import edu.pitt.dbmi.ccd.db.error.NotFoundException;

/**
 *
 * Oct 6, 2015 11:40:23 AM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Autowired(required = true)
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;

        List<UserRole> roles = userRoleRepository.findAll();
        if (roles.isEmpty()) {
            List<UserRole> defaultRoles = Arrays.asList(
                new UserRole("USER",  "Standard user"),
                new UserRole("ADMIN", "Administrator"));
            roles.addAll(defaultRoles);

            userRoleRepository.save(roles);
        }
    }

    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    public UserRole findOne(Long id) {
        Optional<UserRole> role = userRoleRepository.findById(id);
        return role.orElseThrow(() -> new NotFoundException("UserRole", "id", id));
    }

    public UserRole findByName(String name) {
        Optional<UserRole> role = userRoleRepository.findByName(name);
        return role.orElseThrow(() -> new NotFoundException("UserRole", "name", name));
    }

    public UserRole save(UserRole UserRole) {
        return userRoleRepository.save(UserRole);
    }

    public void delete(UserRole UserRole) {
        userRoleRepository.delete(UserRole);
    }

}