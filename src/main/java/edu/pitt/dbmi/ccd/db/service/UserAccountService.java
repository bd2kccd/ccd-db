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

import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.UserInfo;
import edu.pitt.dbmi.ccd.db.entity.UserLogin;
import edu.pitt.dbmi.ccd.db.entity.UserRole;
import edu.pitt.dbmi.ccd.db.model.AccountRegistration;
import edu.pitt.dbmi.ccd.db.repository.UserAccountRepository;
import edu.pitt.dbmi.ccd.db.repository.UserInfoRepository;
import edu.pitt.dbmi.ccd.db.repository.UserLoginRepository;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Mar 20, 2017 6:44:21 AM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final UserInfoRepository userInfoRepository;
    private final UserLoginRepository userLoginRepository;
    private final UserRoleService userRoleService;

    @Autowired
    public UserAccountService(
            UserAccountRepository userAccountRepository,
            UserInfoRepository userInfoRepository,
            UserLoginRepository userLoginRepository,
            UserRoleService userRoleService) {
        this.userAccountRepository = userAccountRepository;
        this.userInfoRepository = userInfoRepository;
        this.userLoginRepository = userLoginRepository;
        this.userRoleService = userRoleService;
    }

    public UserAccount findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    public UserAccount createRegularUser(AccountRegistration accountRegistration) {
        UserInfo userInfo = createUserInfo(accountRegistration);
        UserRole userRole = userRoleService.getRegularRole();
        UserLogin userLogin = new UserLogin();

        userInfo = userInfoRepository.save(userInfo);
        userLogin = userLoginRepository.save(userLogin);

        UserAccount userAccount = createUserAccount(accountRegistration);
        userAccount.setUserInfo(userInfo);
        userAccount.setUserLogin(userLogin);
        userAccount.setUserRoles(Collections.singletonList(userRole));

        return userAccountRepository.save(userAccount);
    }

    private UserAccount createUserAccount(AccountRegistration accountRegistration) {
        String username = accountRegistration.getUsername();
        String password = accountRegistration.getPassword();
        boolean activated = accountRegistration.isActivated();
        Long registrationLocation = accountRegistration.getRegistrationLocation();

        String activationKey = activated ? null : UUID.randomUUID().toString();
        String account = UUID.randomUUID().toString();
        Date registrationDate = new Date(System.currentTimeMillis());

        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(account);
        userAccount.setActionKey(activationKey);
        userAccount.setActivated(activated);
        userAccount.setPassword(password);
        userAccount.setRegistrationDate(registrationDate);
        userAccount.setRegistrationLocation(registrationLocation);
        userAccount.setUsername(username);

        return userAccount;
    }

    private UserInfo createUserInfo(AccountRegistration accountRegistration) {
        String firstName = accountRegistration.getFirstName();
        String middleName = accountRegistration.getMiddleName();
        String lastName = accountRegistration.getLastName();
        String email = accountRegistration.getEmail();

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setFirstName(firstName);
        userInfo.setMiddleName(middleName);
        userInfo.setLastName(lastName);

        return userInfo;
    }

    public UserAccountRepository getRepository() {
        return userAccountRepository;
    }

}
