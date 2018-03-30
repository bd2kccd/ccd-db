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

import edu.pitt.dbmi.ccd.db.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Jan 15, 2018 3:23:30 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    private final UserRoleService userRoleService;
    private final UserInformationService userInformationService;
    private final UserRegistrationService userRegistrationService;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, UserRoleService userRoleService, UserInformationService userInformationService, UserRegistrationService userRegistrationService) {
        this.userAccountRepository = userAccountRepository;
        this.userRoleService = userRoleService;
        this.userInformationService = userInformationService;
        this.userRegistrationService = userRegistrationService;
    }

    public UserAccountRepository getRepository() {
        return userAccountRepository;
    }

}
