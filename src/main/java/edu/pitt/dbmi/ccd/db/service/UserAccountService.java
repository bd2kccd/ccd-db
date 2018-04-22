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

import edu.pitt.dbmi.ccd.db.domain.account.UserAccountRegistration;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.UserInformation;
import edu.pitt.dbmi.ccd.db.entity.UserRegistration;
import edu.pitt.dbmi.ccd.db.entity.UserRole;
import edu.pitt.dbmi.ccd.db.repository.UserAccountRepository;
import edu.pitt.dbmi.ccd.db.util.InetUtils;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void clearActionKey(UserAccount userAccount) {
        if (userAccount.getActionKey() != null) {
            userAccount.setActionKey(null);
            userAccountRepository.save(userAccount);
        }
    }

    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    @Transactional
    public UserAccount registerRegularUser(UserAccountRegistration registration) {
        UserRole userRole = userRoleService.getRegularRole();

        UserAccount userAccount = persistUserAccount(registration, Collections.singletonList(userRole));
        persistUserInformation(registration, userAccount);
        persistUserRegistration(registration, userAccount);

        return userAccount;
    }

    protected UserRegistration persistUserRegistration(UserAccountRegistration registration, UserAccount userAccount) {
        String ipAddress = registration.getIpAddress();

        UserRegistration userRegistration = new UserRegistration(new Date(), userAccount);
        userRegistration.setRegistrationLocation(InetUtils.getInetNTOA(ipAddress));

        return userRegistrationService.getRepository().save(userRegistration);
    }

    protected UserInformation persistUserInformation(UserAccountRegistration registration, UserAccount userAccount) {
        String email = registration.getEmail();
        String firstName = registration.getFirstName();
        String middleName = registration.getMiddleName();
        String lastName = registration.getLastName();

        UserInformation userInformation = new UserInformation(email, userAccount);
        userInformation.setFirstName(firstName);
        userInformation.setMiddleName(middleName);
        userInformation.setLastName(lastName);

        return userInformationService.getRepository().save(userInformation);
    }

    protected UserAccount persistUserAccount(UserAccountRegistration registration, List<UserRole> userRoles) {
        String username = registration.getUsername();
        String password = registration.getPassword();
        boolean activated = registration.isActivated();

        String account = UUID.randomUUID().toString();
        String activationKey = activated ? null : UUID.randomUUID().toString();
        boolean disabled = false;

        UserAccount userAccount = new UserAccount(username, password, account, activated, disabled);
        userAccount.setActionKey(activationKey);
        userAccount.setUserRoles(userRoles);

        return userAccountRepository.save(userAccount);
    }

    public UserAccountRepository getRepository() {
        return userAccountRepository;
    }

}
