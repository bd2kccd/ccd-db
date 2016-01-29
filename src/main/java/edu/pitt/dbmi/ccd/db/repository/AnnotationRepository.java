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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Repository
public interface AnnotationRepository extends JpaRepository<Annotation, Long> {
   
    @Query(value="SELECT a FROM Annotation AS a " +
                 "WHERE a.id = :annoId " +                                                       // WHERE  annotation has specified id
                 "AND (a.user = :user " +                                                        // AND    annotation belongs to the user
                 "OR (a.accessControl.name = 'PUBLIC') " +                                       // OR     annotation is public
                 "OR (a.accessControl.name = 'GROUP' AND a.group IN :#{#user.getGroups()})) " +  // OR     user belongs to the annotation's group
                 "GROUP BY a.id")
    public Optional<Annotation> findById(@Param("user") UserAccount user, @Param("annoId") Long annoId);

    @Query(value="SELECT a FROM Annotation AS a " +
                 "WHERE a.user = :user " +                                                      // WHERE  annotation belongs to the user
                 "OR (a.accessControl.name = 'PUBLIC') " +                                      // OR     annotation is public
                 "OR (a.accessControl.name = 'GROUP' AND a.group IN :#{#user.getGroups()}) " +  // OR     user belongs to the annotation's group 
                 "GROUP BY a.id")
    public Page<Annotation> findAll(@Param("user") UserAccount user, Pageable pageable);
}