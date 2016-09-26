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

import edu.pitt.dbmi.ccd.db.domain.AccountRegistration;
import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.UserRole;
import edu.pitt.dbmi.ccd.db.repository.PersonRepository;
import edu.pitt.dbmi.ccd.db.repository.UserAccountRepository;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;
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

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, PersonRepository personRepository) {
        this.userAccountRepository = userAccountRepository;
        this.personRepository = personRepository;
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

    public UserAccount save(UserAccount userAccount) {
        personRepository.save(userAccount.getPerson());

        return userAccountRepository.save(userAccount);
    }

    public UserAccount updateAccount(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    public Person updatePerson(UserAccount userAccount) {
        return personRepository.save(userAccount.getPerson());
    }

    public UserAccount createNewAccount(AccountRegistration accountRegistration, UserRole userRole) {
        UserAccount userAccount = createUserAccount(accountRegistration);
        userAccount.getUserRoles().add(userRole);

        return save(userAccount);
    }

    protected UserAccount createUserAccount(AccountRegistration accountRegistration) {
        String username = accountRegistration.getUsername();
        String password = accountRegistration.getPassword();
        boolean activated = accountRegistration.isActivated();

        String activationKey = activated ? null : UUID.randomUUID().toString();
        String account = UUID.randomUUID().toString();
        Date registrationDate = new Date(System.currentTimeMillis());
        Person person = createPerson(accountRegistration, account);

        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(account);
        userAccount.setActivated(activated);
        userAccount.setActivationKey(activationKey);
        userAccount.setPassword(password);
        userAccount.setPerson(person);
        userAccount.setRegistrationDate(registrationDate);
        userAccount.setUsername(username);

        return userAccount;
    }

    protected Person createPerson(AccountRegistration accountRegistration, String account) {
        String firstName = accountRegistration.getFirstName();
        String middleName = accountRegistration.getMiddleName();
        String lastName = accountRegistration.getLastName();
        String email = accountRegistration.getEmail();
        String workspace = Paths.get(accountRegistration.getWorkspace(), account.replace("-", "_")).toAbsolutePath().toString();

        Person person = new Person();
        person.setEmail(email);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setLastName(lastName);
        person.setMiddleName(middleName);
        person.setWorkspace(workspace);

        return person;
    }

}
