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

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.PersonRepository;
import edu.pitt.dbmi.ccd.db.repository.UserAccountRepository;
import edu.pitt.dbmi.ccd.db.repository.UserLoginAttemptRepository;
import edu.pitt.dbmi.ccd.db.repository.UserLoginRepository;

/**
 *
 * Jun 24, 2016 4:29:45 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PersonRepository personRepository;
    private final UserLoginAttemptRepository userLoginAttemptRepository;
    private final UserLoginRepository userLoginRepository;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, PersonRepository personRepository, UserLoginAttemptRepository userLoginAttemptRepository, UserLoginRepository userLoginRepository) {
        this.userAccountRepository = userAccountRepository;
        this.personRepository = personRepository;
        this.userLoginAttemptRepository = userLoginAttemptRepository;
        this.userLoginRepository = userLoginRepository;
    }

    public UserAccount saveUserAccount(UserAccount userAccount) {
        userLoginRepository.save(userAccount.getUserLogin());
        userLoginAttemptRepository.save(userAccount.getUserLoginAttempt());
        personRepository.save(userAccount.getPerson());

        return userAccountRepository.save(userAccount);
    }

    public List<UserAccount> saveUserAccounts(Set<UserAccount> userAccounts) {
        return userAccountRepository.save(userAccounts);
    }

    public Optional<UserAccount> findById(Long id) {
        return userAccountRepository.findById(id);
    }

    public Optional<UserAccount> findByAccount(String account) {
        return userAccountRepository.findByAccount(account);
    }

    public Optional<UserAccount> findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    public Optional<UserAccount> findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    public Page<UserAccount> findAll(Pageable pageable) {
        return userAccountRepository.findAll(pageable);
    }

    public void delete(UserAccount account) {
        userAccountRepository.delete(account);
    }

    public void delete(Set<UserAccount> userAccounts) {
        userAccountRepository.delete(userAccounts);
    }
}
