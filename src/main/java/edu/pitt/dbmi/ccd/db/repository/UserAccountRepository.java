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
package edu.pitt.dbmi.ccd.db.repository;

import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * Jul 23, 2015 5:33:37 PM
 *
 * @since v0.4.0
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    public UserAccount findByUsername(String username);

    public UserAccount findByAccount(String account);

    public UserAccount findByActivationKey(String activationKey);

    @Query("SELECT ua FROM UserAccount ua WHERE ua.person.email = ?1")
    public UserAccount findByEmail(String email);

}
