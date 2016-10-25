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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 *
 * Jul 23, 2015 5:33:37 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    public UserAccount findById(Long id);

    public UserAccount findByUsername(String username);

    public UserAccount findByAccountId(String accountId);

    @Query("SELECT ua FROM UserAccount ua WHERE ua.person.email = ?1")
    public UserAccount findByEmail(String email);

    public Long countByUsername(String username);

//    @Query(value="SELECT ua FROM UserAccount ua " +
//            "LEFT JOIN ua.groups AS m " +
//            "WHERE m.name = ?1")
//    public Page<UserAccount> findByGroupMembership(String group, Pageable pageable);
//
//    @Query(value="SELECT ua FROM UserAccount ua " +
//            "LEFT JOIN ua.moderates AS m " +
//            "WHERE m.name = ?1")
//    public Page<UserAccount> findByGroupModeration(String group, Pageable pageable);
//
//    @Query(value="SELECT ua FROM UserAccount ua " +
//            "LEFT JOIN ua.requesting AS r " +
//            "WHERE r.name = ?1")
//    public Page<UserAccount> findByGroupRequests(String group, Pageable pageable);

    public Page<UserAccount> findAll(Pageable pageable);

}
