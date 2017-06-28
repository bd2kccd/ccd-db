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

import edu.pitt.dbmi.ccd.db.entity.UserRolePermission;
import edu.pitt.dbmi.ccd.db.repository.UserRolePermissionRepository;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * May 10, 2017 3:04:23 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class UserRolePermissionService {

    public static final String PERMISSION_ALL = "*";

    private final UserRolePermissionRepository userRolePermissionRepository;

    @Autowired
    public UserRolePermissionService(UserRolePermissionRepository userRolePermissionRepository) {
        this.userRolePermissionRepository = userRolePermissionRepository;

        if (userRolePermissionRepository.findAll().isEmpty()) {
            userRolePermissionRepository.save(Arrays.asList(new UserRolePermission(PERMISSION_ALL, "All")));
        }
    }

    @Cacheable("findAllUserRolePermissions")
    public List<UserRolePermission> findAll() {
        return userRolePermissionRepository.findAll();
    }

    public UserRolePermissionRepository getRepository() {
        return userRolePermissionRepository;
    }

}
