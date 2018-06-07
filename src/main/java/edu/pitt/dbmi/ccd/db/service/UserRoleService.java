/*
 * Copyright (C) 2018 University of Pittsburgh.
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

import edu.pitt.dbmi.ccd.db.code.UserRoleCodes;
import edu.pitt.dbmi.ccd.db.entity.UserRole;
import edu.pitt.dbmi.ccd.db.repository.UserRoleRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * Jan 15, 2018 3:25:15 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class UserRoleService {

    private final UserRoleRepository repository;

    @Autowired
    public UserRoleService(UserRoleRepository repository) {
        this.repository = repository;

        // initialize database
        if (repository.findAll().isEmpty()) {
            repository.saveAll(Arrays.asList(
                    new UserRole("Admin", UserRoleCodes.ADMIN),
                    new UserRole("Regular", UserRoleCodes.REGULAR)
            ));
        }
    }

    @Cacheable("UserRoleByCode")
    public UserRole findByCode(short code) {
        return repository.findByCode(code);
    }

    @Cacheable("UserRoleAll")
    public List<UserRole> findAll() {
        return repository.findAll();
    }

    public UserRole getRegularRole() {
        return findByCode(UserRoleCodes.REGULAR);
    }

    public UserRole getAdminRole() {
        return findByCode(UserRoleCodes.ADMIN);
    }

    public UserRoleRepository getRepository() {
        return repository;
    }

}
