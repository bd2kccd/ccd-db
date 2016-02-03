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
   
    /**
     * Find annotation by id if viewable by requester
     * @param requester requester
     * @param annoId    annotation id 
     * @return          Optional<Annotation>
     */
    @Query(value="SELECT DISTINCT a FROM Annotation AS a " +
                 "LEFT JOIN a.group g ON g IN :#{#requester.getGroups()} " +     // LEFT JOIN annotation groups on requester groups
                 "WHERE a.id = :annoId " +                                       // WHERE     annotation has specified id
                 "AND (a.user = :requester " +                                   // AND       [annotation belongs to the requester
                   "OR (a.accessControl.name = 'PUBLIC') " +                     // OR        annotation has public access
                   "OR (a.accessControl.name = 'GROUP' AND a.group = g))")       // OR        annotation has group access and requester belong to group]
    public Optional<Annotation> findById(@Param("requester") UserAccount requester, @Param("annoId") Long annoId);

    /**
     * Find annotations by user viewable by requester
     * @param  requester requester
     * @param  username  username
     * @param  pageable  page request
     * @return           page of annotations
     */
    @Query(value="SELECT DISTINCT a FROM Annotation AS a " +
                 "LEFT JOIN a.group g ON g IN :#{#requester.getGroups()} " +     // LEFT JOIN annotation group on set of users' groups
                 "WHERE a.user.username = :username " +                          // WHERE     annotation belongs to user
                 "AND (a.accessControl.name = 'PUBLIC' " +                       // AND       annotation has public acccess
                   "OR (a.accessControl.name = 'GROUP' AND a.group = g))")       // OR        annotation has group acccess and requester belongs to group
    public Page<Annotation> findByUser(@Param("requester") UserAccount requester, @Param("username") String username, Pageable pageable);

    /**
     * Find annotations by group viewable by requester
     * @param  requester requester
     * @param  groupName group name
     * @return           page of annotations
     */
    @Query(value="SELECT DISTINCT a FROM Annotation AS a " +
                 "INNER JOIN a.group g ON g IN :#{#requester.getGroups()} " +    // INNER JOIN annotation group on user groups
                 "WHERE a.group.name = :groupName " +                            // WHERE      annotation is in the specified group
                 "AND (a.accessControl.name = 'GROUP' AND a.group = g)")         // AND        annotation has group access and requester belongs to group
    public Page<Annotation> findByGroup(@Param("requester") UserAccount requester, @Param("groupName") String groupName, Pageable pageable);

    /**
     * Find annotations by upload viewable by requester
     * @param  requester requester
     * @param  uploadId  upload id
     * @param  pageable  page request
     * @return           page of annotations
     */
    @Query(value="SELECT DISTINCT a FROM Annotation AS a " +
                 "LEFT JOIN a.group g ON g IN :#{#requester.getGroups()} " +     // LEFT JOIN annotation group on requester groups
                 "WHERE a.target.id = :uploadId " +                              // WHERE     annotation targets upload
                 "AND (a.user = :requester " +                                   // AND      [annotation belongs to the requester
                   "OR a.accessControl.name = 'PUBLIC' " +                       // OR        annotation has public access
                   "OR (a.accessControl.name = 'GROUP' AND a.group = g))")       // OR        annotation has group access and requester belongs to group]
    public Page<Annotation> findByUpload(@Param("requester") UserAccount requester, @Param("uploadId") Long uploadId, Pageable pageable);

    /**
     * Find annotations by user and group viewable by requester
     * @param  requester requester
     * @param  username  username
     * @param  groupName group name
     * @param  pageable  page request
     * @return           page of annotations
     */
    @Query(value="SELECT DISTINCT a FROM Annotation AS a " +
                 "INNER JOIN a.group g ON g IN :#{#requester.getGroups()} " +    // INNER JOIN annotation group on reqester groups
                 "WHERE a.user.username = :username " +                          // WHERE      annotation belongs to user
                 "AND a.group.name = :groupName " +                              // AND        annotation belongs to group
                 "AND (a.accessControl.name = 'GROUP' AND a.group = g)")         // AND        annotation has group access and requester belongs to group
    public Page<Annotation> findByUserAndGroup(@Param("requester") UserAccount requester, @Param("username") String username, @Param("groupName") String groupName, Pageable pageable);

    /**
     * Find annotations by user and upload viewable by requester
     * @param  requester requester
     * @param  username  username
     * @param  uploadId  upload id
     * @param  pageable  page request
     * @return           page of annotations
     */
    @Query(value="SELECT DISTINCT a FROM Annotation AS a " +
                 "LEFT JOIN a.group g ON g IN :#{#requester.getGroups()} " +     // LEFT JOIN annotation group on requester groups
                 "WHERE a.user.username = :username " +                          // WHERE     annotation belongs to user
                 "AND a.target.id = :uploadId " +                                // AND       annotation targets upload
                 "AND (a.accessControl.name = 'PUBLIC' " +                       // AND      [annotation has public access
                    "OR (a.accessControl.name = 'GROUP' AND a.group = g))")      // OR        annotation has group access and reqester belongs to group]
    public Page<Annotation> findByUserAndUpload(@Param("requester") UserAccount requester, @Param("username") String username, @Param("uploadId") Long uploadId, Pageable pageable);

    /**
     * Find annotations by group and upload viewable by requester
     * @param requester  requester
     * @param groupName  group name
     * @param uploadId   upload id
     * @param pageable   page request
     * @return           page of annotations
     */
    @Query(value="SELECT DISTINCT a FROM Annotation AS a " +
                 "INNER JOIN a.group g ON g IN :#{#requester.getGroups()} " +     // LEFT JOIN annotation group on requester groups
                 "WHERE a.target.id = :uploadId " +                              // WHERE     annotation targets upload
                 "AND a.group.name = :groupName " +                              // AND       annotation belongs to group
                 "AND (a.accessControl.name = 'GROUP' AND a.group = g)")         // AND       annotation has group access and requester belongs to group
    public Page<Annotation> findByGroupAndUpload(@Param("requester") UserAccount requester, @Param("groupName") String groupName, @Param("uploadId") Long uploadId, Pageable pageable);

    /**
     * Find annotations belonging to requester
     * @param  requester requester
     * @param  pageable  page request
     * @return           page of annotations
     */
    @Query(value="SELECT a FROM Annotation AS a " +
                 "WHERE a.user = :requester")                  // WHERE annotation belongs to requester
    public Page<Annotation> findByRequester(@Param("requester") UserAccount requester, Pageable pageable);

    /**
     * Find publicly viewable annotations, does not require requester information
     * @param pageable page request
     * @return         page of annotations
     */
    @Query(value="SELECT a FROM Annotation AS a " +
                 "WHERE a.accessControl.name = 'PUBLIC'")      // WHERE annotation has public access
    public Page<Annotation> findAllPublic(Pageable pageable);

    /**
     * Find all annotations viewable by requester
     * @param requester requester
     * @param pageable  page request
     * @return          page of annotations
     */
    @Query(value="SELECT DISTINCT a FROM Annotation AS a " +
                 "LEFT JOIN a.group g ON g IN :#{#requester.getGroups()} " +     // LEFT JOIN annotation groups on user groups
                 "WHERE a.user = :requester " +                                  // WHERE     annotation belongs to the user
                 "OR a.accessControl.name = 'PUBLIC' " +                         // OR        annotation has public access
                 "OR (a.accessControl.name = 'GROUP' AND a.group = g)")          // OR        annotation has group access and requester belong to group
    public Page<Annotation> findAll(@Param("requester") UserAccount requester, Pageable pageable);
}
