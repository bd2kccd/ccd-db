/*
 * Copyright (C) 2015 University of Pittsburgh.
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

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pitt.dbmi.ccd.db.entity.Group;
import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.PersonRepository;
import edu.pitt.dbmi.ccd.db.repository.UserAccountRepository;

// logging

/**
 *
 * Jul 24, 2015 1:29:56 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class UserAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountService.class);

    private final UserAccountRepository userAccountRepository;

    private final PersonRepository personRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    // Number of rounds when performing BCrypt on passwords (Default is 10)
    private static final int BCRYPT_ROUNDS = 10;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, PersonRepository personRepository) {
        this.userAccountRepository = userAccountRepository;
        this.personRepository = personRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(BCRYPT_ROUNDS);
    }

    public UserAccount create(Person person, String username, String password) {
        // encode password
        final String encodedPassword = passwordEncoder.encode(password);
        final UserAccount account = new UserAccount(person, username, encodedPassword, true, new Date());
        return saveUserAccount(account);
    }

    public UserAccount create(Person person, String username, String password, String activationKey) {
        UserAccount userAccount = create(person, username, password);
        userAccount.setActivationKey(activationKey);
        return save(userAccount);
    }

    public boolean updatePassword(UserAccount principal, String currPass, String newPass) {
        if (passwordEncoder.matches(currPass, principal.getPassword())) {
            final String encodedPassword = passwordEncoder.encode(newPass);
            principal.setPassword(encodedPassword);
            save(principal);
            return true;
        } else {
            LOGGER.info("Failed password change by user: " + principal.getId());
            return false;
        }
    }

    public Optional<UserAccount> findById(Long id) {
        return userAccountRepository.findById(id);
    }

    public Optional<UserAccount> findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    public Optional<UserAccount> findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    public Page<UserAccount> findByGroupMembership(Group group, Pageable pageable) {
        return userAccountRepository.findByGroupMembership(group.getName(), pageable);
    }

    public Page<UserAccount> findByGroupModeration(Group group, Pageable pageable) {
        return userAccountRepository.findByGroupModeration(group.getName(), pageable);
    }

    public Page<UserAccount> findByGroupRequests(Group group, Pageable pageable) {
        return userAccountRepository.findByGroupRequests(group.getName(), pageable);
    }

    public Page<UserAccount> findAll(Pageable pageable) {
        return userAccountRepository.findAll(pageable);
    }

    public UserAccount save(UserAccount account) {
        return userAccountRepository.save(account);
    }

    public UserAccount saveUserAccount(UserAccount userAccount) {
        Person person = personRepository.save(userAccount.getPerson());
        userAccount.setPerson(person);

        return userAccountRepository.save(userAccount);
    }

    public void delete(UserAccount account) {
        userAccountRepository.delete(account);
    }
}
