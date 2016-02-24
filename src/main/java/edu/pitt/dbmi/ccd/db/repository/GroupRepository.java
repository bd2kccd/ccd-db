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
import org.springframework.stereotype.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.pitt.dbmi.ccd.db.entity.Group;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Repository
@RepositoryRestResource(exported=false)
public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {

    /**
     * Find group by id
     * @param  id group id
     * @return    group
     */
    public Optional<Group> findById(Long id);

    /**
     * Find group by name
     * @param  name group name
     * @return      group
     */
    public Optional<Group> findByName(String name);

    /**
     * Find all groups by spec
     * @param  spec      group specification
     * @param  pageable  page request
     * @return           page of groups matching parameters
     */
    public Page<Group> findAll(Specification<Group> spec, Pageable  pageable);

    /**
     * Find all groups
     * @param  pageable  page request
     * @return           page of groups
     */
    public Page<Group> findAll(Pageable pageable);
}
