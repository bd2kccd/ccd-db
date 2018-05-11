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

import edu.pitt.dbmi.ccd.db.entity.UserRole;
import edu.pitt.dbmi.ccd.db.repository.UserRoleRepository;
import java.util.Arrays;
import java.util.Optional;
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

    private static final Long ADMIN_ID = 1L;
    private static final Long REGULAR_ID = 2L;

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;

        // initialize database
        if (userRoleRepository.findAll().isEmpty()) {
            userRoleRepository.saveAll(Arrays.asList(
                    new UserRole(ADMIN_ID, "Admin"),
                    new UserRole(REGULAR_ID, "Regular")
            ));
        }
    }

    @Cacheable("UserRoleById")
    public UserRole findById(Long id) {
        Optional<UserRole> opt = userRoleRepository.findById(id);

        return opt.isPresent() ? opt.get() : null;
    }

    public UserRole getAdminRole() {
        return findById(ADMIN_ID);
    }

    public UserRole getRegularRole() {
        return findById(REGULAR_ID);
    }

    public UserRoleRepository getRepository() {
        return userRoleRepository;
    }

}
