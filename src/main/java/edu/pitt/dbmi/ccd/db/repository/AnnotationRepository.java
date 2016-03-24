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
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.Group;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Repository
@RepositoryRestResource(exported=false)
public interface AnnotationRepository extends JpaRepository<Annotation, Long>, JpaSpecificationExecutor<Annotation> {

    /**
     * Find annotation by id if viewable by requester
     * @param requester requester
     * @param id        annotation id
     * @return          Optional<Annotation>
     */
    @Query(value="SELECT DISTINCT a FROM Annotation AS a " +
                 "WHERE a.id = :id " +                                             // WHERE     annotation has specified id
                 "AND (a.user = :requester " +                                     // AND       [annotation belongs to the requester
                   "OR (a.accessControl.name = 'PUBLIC') " +                       // OR         annotation has public access
                   "OR (a.accessControl.name = 'GROUP' AND a.group IN :groups))")  // OR         annotation has group access and requester belongs to group]
    public Optional<Annotation> findById(@Param("requester") UserAccount requester, @Param("id") Long id, @Param("groups") Set<Group> groups);

    /**
     * Find annotations by parent annotation
     * @param requester requester
     * @param id        annotation id
     * @param pageable  page request
     * @return          page of annotations
     */
    @Query(value="SELECT a FROM Annotation AS a " +
                 "WHERE a.parent.id = :id " +
                 "AND (a.redacted = false OR a.redacted = :showRedacted) " +
                 "AND (a.user = :requester " +
                   "OR (a.accessControl.name = 'PUBLIC') " +
                   "OR (a.accessControl.name = 'GROUP' AND a.group IN :groups))")
    public Page<Annotation> findByParent(@Param("requester") UserAccount requester, @Param("id") Long id, @Param("groups") Set<Group> groups, @Param("showRedacted") boolean showRedacted, Pageable pageable);

    /**
     * Find publicly viewable annotations, does not require requester information
     * @param pageable page request
     * @return         page of annotations
     */
    @Query(value="SELECT a FROM Annotation AS a " +
                 "WHERE a.accessControl.name = 'PUBLIC' " +                   // WHERE annotation has public access
                 "AND (a.redacted = false OR a.redacted = ?1)")
    public Page<Annotation> findAllPublic(boolean showRedacted, Pageable pageable);

    // spec (search or filter)
    public Page<Annotation> findAll(Specification<Annotation> spec, Pageable pageable);

    /**
     * Find all annotations viewable by requester
     * @param requester requester
     * @param pageable  page request
     * @return          page of annotations
     */
    @Query(value="SELECT DISTINCT a FROM Annotation AS a " +
                 "WHERE (a.redacted = false OR a.redacted = :showRedacted) " +
                 "AND (a.user = :requester " +                                  // WHERE     annotation belongs to the user
                 "OR (a.accessControl.name LIKE 'PUBLIC') " +                         // OR        annotation has public access
                 "OR (a.accessControl.name LIKE 'GROUP' AND a.group IN :groups))")          // OR        annotation has group access and requester belong to group
    public Page<Annotation> findAll(@Param("requester") UserAccount requester, @Param("groups") Set<Group> groups, @Param("showRedacted") boolean showRedacted, Pageable pageable);
}
