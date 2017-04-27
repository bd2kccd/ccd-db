/*
 * Copyright (C) 2017 University of Pittsburgh.
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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Mar 31, 2017 5:22:24 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class UserRoleService {

    private static final String ADMIN_ROLE_NAME = "admin";
    private static final String REGULAR_ROLE_NAME = "regular";

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;

        List<UserRole> userRoles = userRoleRepository.findAll();
        if (userRoles.isEmpty()) {
            userRoles.add(new UserRole(ADMIN_ROLE_NAME, "Administrator."));
            userRoles.add(new UserRole(REGULAR_ROLE_NAME, "Regular user."));

            userRoleRepository.save(userRoles);
        }
    }

    public UserRole getAdminRole() {
        return userRoleRepository.findByName(ADMIN_ROLE_NAME);
    }

    public UserRole getRegularRole() {
        return userRoleRepository.findByName(REGULAR_ROLE_NAME);
    }

    public UserRoleRepository getUserRoleRepository() {
        return userRoleRepository;
    }

}
