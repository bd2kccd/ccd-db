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

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.pitt.dbmi.ccd.db.entity.ShareGroup;

/**
 *
 * Aug 3, 2016 3:58:09 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Repository
public interface ShareGroupRepository extends JpaRepository<ShareGroup, Long> {
    /**
     * Find group by id
     * @param  id group id
     * @return    group
     */
    public Optional<ShareGroup> findById(Long id);

    /**
     * Find group by name
     * @param  name group name
     * @return      group
     */
    public Optional<ShareGroup> findByName(String name);

    @Query(value="SELECT g FROM Group AS g " +
            "LEFT JOIN g.members AS m " +
            "WHERE m.username = ?1")
    public Page<ShareGroup> findByMember(String username, Pageable pageable);

    @Query(value="SELECT g FROM Group AS g " +
            "LEFT JOIN g.requesters AS r " +
            "WHERE r.username = ?1")
    public Page<ShareGroup> findByRequester(String username, Pageable pageable);

    /**
     * Find all groups by spec
     * @param  spec      group specification
     * @param  pageable  page request
     * @return           page of groups matching parameters
     */
    public Page<ShareGroup> findAll(Specification<ShareGroup> spec, Pageable  pageable);

    /**
     * Find all groups
     * @param  pageable  page request
     * @return           page of groups
     */
    public Page<ShareGroup> findAll(Pageable pageable);
}
