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
package edu.pitt.dbmi.ccd.db.repository;

import java.util.Optional;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 *
 * Jul 23, 2015 5:33:37 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Repository
@RepositoryRestResource(path="users")
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    public Optional<UserAccount> findById(Long id);

    public Optional<UserAccount> findByUsername(String username);

    public Optional<UserAccount> findByUsernameAndActivationKey(String username, String activationKey);

    @Query("SELECT ua FROM UserAccount ua WHERE ua.person.email = ?1")
    public Optional<UserAccount> findByEmail(String email);

    @Query("SELECT ua FROM UserAccount ua WHERE ua.person.firstName = ?1 AND ua.person.lastName = ?2")
    public Optional<UserAccount> findByFirstNameAndLastName(String first, String last);

    // @Query("SELECT ua FROM UserAccount ua WHERE ua.person.firstName = ?1 AND ua.person.middleName = ?2 AND ua.person.lastName = ?3")
    // public Optional<UserAccount> findByFirstNameAndMiddleNameAndLastName(String first, String middle, String last);

    public Page<UserAccount> findAll(Pageable pageable);
}
