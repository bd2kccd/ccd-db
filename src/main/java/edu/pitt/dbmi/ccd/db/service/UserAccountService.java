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

import edu.pitt.dbmi.ccd.db.dto.UserAccountRegistration;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.UserProfile;
import edu.pitt.dbmi.ccd.db.entity.UserRegistration;
import edu.pitt.dbmi.ccd.db.entity.UserRole;
import edu.pitt.dbmi.ccd.db.repository.UserAccountRepository;
import edu.pitt.dbmi.ccd.db.util.InetUtils;
import java.util.Collections;
import java.util.Date;
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

    private final UserAccountRepository repository;
    private final UserProfileService userProfileService;
    private final UserRegistrationService userRegistrationService;
    private final UserRoleService userRoleService;

    @Autowired
    public UserAccountService(UserAccountRepository repository, UserProfileService userProfileService, UserRegistrationService userRegistrationService, UserRoleService userRoleService) {
        this.repository = repository;
        this.userProfileService = userProfileService;
        this.userRegistrationService = userRegistrationService;
        this.userRoleService = userRoleService;
    }

    public void clearActivationKey(UserAccount userAccount) {
        if (userAccount.getActivationKey() != null) {
            userAccount.setActivationKey(null);
            repository.save(userAccount);
        }
    }

    @Transactional
    public UserAccount registerRegularUser(UserAccountRegistration registration) {
        UserRole userRole = userRoleService.getRegularRole();

        UserAccount userAccount = createUserAccount(registration);
        userAccount.setUserRoles(Collections.singletonList(userRole));
        userAccount = repository.save(userAccount);

        UserProfile userProfile = createUserProfile(registration);
        userProfile.setUserAccount(userAccount);
        userProfileService.getRepository().save(userProfile);

        UserRegistration userRegistration = createUserRegistration(registration);
        userRegistration.setUserAccount(userAccount);
        userRegistrationService.getRepository().save(userRegistration);

        return userAccount;
    }

    private UserAccount createUserAccount(UserAccountRegistration registration) {
        String username = registration.getUsername();
        String password = registration.getPassword();
        boolean activated = registration.isActivated();

        String account = UUID.randomUUID().toString();
        String activationKey = activated ? null : UUID.randomUUID().toString();
        boolean disabled = false;

        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(account);
        userAccount.setActivated(activated);
        userAccount.setActivationKey(activationKey);
        userAccount.setDisabled(disabled);
        userAccount.setPassword(password);
        userAccount.setUsername(username);

        return userAccount;
    }

    private UserProfile createUserProfile(UserAccountRegistration registration) {
        String email = registration.getEmail();
        String firstName = registration.getFirstName();
        String middleName = registration.getMiddleName();
        String lastName = registration.getLastName();

        UserProfile userProfile = new UserProfile();
        userProfile.setEmail(email);
        userProfile.setFirstName(firstName);
        userProfile.setMiddleName(middleName);
        userProfile.setLastName(lastName);

        return userProfile;
    }

    private UserRegistration createUserRegistration(UserAccountRegistration registration) {
        String ipAddress = registration.getIpAddress();

        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setRegistrationDate(new Date());
        userRegistration.setRegistrationLocation(InetUtils.getInetNTOA(ipAddress));

        return userRegistration;
    }

    public UserAccountRepository getRepository() {
        return repository;
    }

}
