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
import org.springframework.stereotype.Repository;

import edu.pitt.dbmi.ccd.db.entity.Address;
import edu.pitt.dbmi.ccd.db.entity.AnnotationTarget;
import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 *
 * Aug 3, 2016 3:53:14 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Repository
public interface AnnotationTargetRepository extends JpaRepository<AnnotationTarget, Long> {

    public Optional<AnnotationTarget> findById(Long id);

    public Optional<AnnotationTarget> findByFile(File file);

    public Optional<AnnotationTarget> findByAddress(Address address);

    public Page<AnnotationTarget> findByTitleContains(String terms, Pageable pageable);

    public Page<AnnotationTarget> findByUser(UserAccount user, Pageable pageable);

    // search and filter

    public Page<AnnotationTarget> findAll(Specification<AnnotationTarget> spec, Pageable pageable);

    public Page<AnnotationTarget> findAll(Pageable pageable);
}
