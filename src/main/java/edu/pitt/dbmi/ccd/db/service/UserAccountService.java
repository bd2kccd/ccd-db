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

import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.PersonRepository;
import edu.pitt.dbmi.ccd.db.repository.UserAccountRepository;
import edu.pitt.dbmi.ccd.db.repository.UserLoginAttemptRepository;
import edu.pitt.dbmi.ccd.db.repository.UserLoginRepository;
import edu.pitt.dbmi.ccd.db.repository.UserRoleRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Jun 24, 2016 4:29:45 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PersonRepository personRepository;
    private final UserLoginAttemptRepository userLoginAttemptRepository;
    private final UserLoginRepository userLoginRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, PersonRepository personRepository, UserLoginAttemptRepository userLoginAttemptRepository, UserLoginRepository userLoginRepository, UserRoleRepository userRoleRepository) {
        this.userAccountRepository = userAccountRepository;
        this.personRepository = personRepository;
        this.userLoginAttemptRepository = userLoginAttemptRepository;
        this.userLoginRepository = userLoginRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    public UserAccount findByAccount(String account) {
        return userAccountRepository.findByAccount(account);
    }

    public UserAccount findByActivationKey(String activationKey) {
        return userAccountRepository.findByActivationKey(activationKey);
    }

    public UserAccount findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    public UserAccount saveUserAccount(UserAccount userAccount) {
        userLoginRepository.save(userAccount.getUserLogin());
        userLoginAttemptRepository.save(userAccount.getUserLoginAttempt());
        personRepository.save(userAccount.getPerson());
        userRoleRepository.save(userAccount.getUserRoles());

        return userAccountRepository.save(userAccount);
    }

}
