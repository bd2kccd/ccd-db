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

import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.PersonRepository;
import edu.pitt.dbmi.ccd.db.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Jul 24, 2015 1:29:56 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    private final PersonRepository personRepository;

    @Autowired(required = true)
    public UserAccountService(
            UserAccountRepository userAccountRepository,
            PersonRepository personRepository) {
        this.userAccountRepository = userAccountRepository;
        this.personRepository = personRepository;
    }

    public UserAccount findById(Long id) {
        return userAccountRepository.findById(id);
    }

    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    public UserAccount findUserAccountByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    public UserAccount findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    public UserAccount findByAccountId(String accountId) {
        return userAccountRepository.findByAccountId(accountId);
    }

    public Page<UserAccount> findAll(Pageable pageable) {
        return userAccountRepository.findAll(pageable);
    }

    public UserAccount saveUserAccount(UserAccount userAccount) {
        Person person = personRepository.save(userAccount.getPerson());
        userAccount.setPerson(person);

        return userAccountRepository.save(userAccount);
    }

    public UserAccount save(UserAccount userAccount) {
        Person person = personRepository.save(userAccount.getPerson());
        userAccount.setPerson(person);
        return userAccountRepository.save(userAccount);
    }

    public void delete(UserAccount userAccount) {
        userAccountRepository.delete(userAccount);
    }

}
