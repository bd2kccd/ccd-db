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
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.pitt.dbmi.ccd.db.entity.AnnotationData;
import edu.pitt.dbmi.ccd.db.entity.Annotation;
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
                 "LEFT JOIN a.group g ON g IN :#{#requester.getGroups()} " +     // LEFT JOIN annotation groups on requester groups
                 "WHERE a.id = :id " +                                           // WHERE     annotation has specified id
                 "AND (a.user = :requester " +                                   // AND       [annotation belongs to the requester
                   "OR (a.accessControl.name = 'PUBLIC') " +                     // OR         annotation has public access
                   "OR (a.accessControl.name = 'GROUP' AND a.group = g))")       // OR         annotation has group access and requester belongs to group]
    public Optional<Annotation> findById(@Param("requester") UserAccount requester, @Param("id") Long id);

    /**
     * Find annotations by user viewable by requester
     * @param  requester requester
     * @param  username  username
     * @param  pageable  page request
     * @return           page of annotations
     */
    @Query(value="SELECT DISTINCT a FROM Annotation AS a " +
                 "LEFT JOIN a.group g ON g IN :#{#requester.getGroups()} " +           // LEFT JOIN annotation group on set of users' groups
                 "WHERE a.user.username = :username " +                                // WHERE     annotation belongs to user
                 "AND (a.accessControl.name = 'PUBLIC' " +                             // AND       annotation has public acccess
                   "OR (a.accessControl.name = 'PRIVATE' AND a.user = :requester) " +  // OR        annotation has private access and belongs to requester
                   "OR (a.accessControl.name = 'GROUP' AND a.group = g))")             // OR        annotation has group acccess and requester belongs to group
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

    // @Query(value="SELECT DISTINCT a FROM Annotation AS a " +
    //              "LEFT JOIN a.group g ON g IN :#{#requester.getGroups()} " +              // LEFT JOIN annotation group on requester groups
    //              "WHERE (:username IS NULL OR a.user.username = :username) " +            // WHERE     username param is null OR annotation belongs to user 
    //              "AND (:group IS NULL OR a.group.name = :group) " +                       // AND       group param is null OR annotation belongs to group
    //              "AND (:upload IS NULL OR a.target.id = :upload) " +                      // AND       upload param is null OR annotation targets upload
    //              "AND (:vocab IS NULL OR a.vocab.name = :vocab) " +                       // AND       vocab param is null OR annotation belongs to vocabulary
    //              "AND (:terms IS NULL " +                                                 // AND       terms param is null
    //                "OR a IN (SELECT DISTINCT d.annotation FROM AnnotationData AS d " +    //           OR annotation data value contains terms
    //                "WHERE d.value LIKE CONCAT('%', :terms, '%'))) " +
    //              "AND (:attributeLevel IS NULL " +                                        // AND       attribute level param is null
    //                "OR a IN (SELECT DISTINCT d.annotation FROM AnnotationData AS d " +    //           OR annotation data has attribute level
    //                "WHERE d.attribute.level LIKE :attributeLevel)) " +
    //              "AND (:attributeName IS NULL " +                                         // AND       attribute name param is null
    //                "OR a IN (SELECT DISTINCT d.annotation FROM AnnotationData AS d " +    //           OR annotation data has attribute name 
    //                "WHERE d.attribute.name LIKE :attributeName)) " +
    //              "AND (:attributeReqLevel IS NULL " +                                     // AND       attribute requirement level param is null
    //                "OR a IN (SELECT DISTINCT d.annotation FROM AnnotationData AS d " +    //           OR annotation data has attribute requirement level
    //                "WHERE d.attribute.requirementLevel LIKE :attributeReqLevel)) " +
    //              "AND ((a.user = :requester AND a.accessControl.name = 'PRIVATE') " +     // AND       annotation belongs to the requester
    //                "OR (a.accessControl.name = 'PUBLIC') " +                              //           OR annotation has public access
    //                "OR (a.accessControl.name = 'GROUP' AND a.group = g))" )               //           OR annotation has group access AND requester belongs to group]
    // public Page<Annotation> search(
    //     @Param("requester") UserAccount requester,
    //     @Param("username") String username,
    //     @Param("group") String group,
    //     @Param("upload") Long upload,
    //     @Param("vocab") String vocab,
    //     @Param("terms") String value,
    //     @Param("attributeLevel") String attributeLevel,
    //     @Param("attributeName") String attributeName,
    //     @Param("attributeReqLevel") String attributeRequirementLevel,
    //     Pageable pageable);

    @Query(value="SELECT a FROM Annotation AS a " +
                 "LEFT JOIN a.group g ON g IN :#{#requester.getGroups()} " +
                 "WHERE a.vocab.name LIKE :vocab " +
                 "AND (a.user = :requester " +
                   "OR (a.accessControl.name = 'PUBLIC') " +
                   "OR (a.accessControl.name = 'GROUP' AND a.group = g))")
    public Page<Annotation> findByVocab(@Param("requester") UserAccount requester, @Param("vocab") String vocab, Pageable pageable);

    @Query(value="SELECT a FROM Annotation AS a " +
                 "Left JOIN a.group g ON g IN :#{#requester.getGroups()} " +
                 "WHERE a.parent.id = :id " +
                 "AND (a.user = :requester " +
                   "OR (a.accessControl.name = 'PUBLIC') " +
                   "OR (a.accessControl.name = 'GROUP' AND a.group = g))")
    public Page<Annotation> findByParent(@Param("requester") UserAccount requester, @Param("id") Long id, Pageable pageable);

    /**
     * Find publicly viewable annotations, does not require requester information
     * @param pageable page request
     * @return         page of annotations
     */
    @Query(value="SELECT a FROM Annotation AS a " +
                 "WHERE a.accessControl.name = 'PUBLIC'")      // WHERE annotation has public access
    public Page<Annotation> findAllPublic(Pageable pageable);

    // spec (search or filter)
    public Page<Annotation> findAll(Specification<Annotation> spec, Pageable pageable);

    /**
     * Find all annotations viewable by requester
     * @param requester requester
     * @param pageable  page request
     * @return          page of annotations
     */
    @Query(value="SELECT DISTINCT a FROM Annotation AS a " +
                 "LEFT JOIN a.group g ON g IN :#{#requester.getGroups()} " +     // LEFT JOIN annotation groups on user groups
                 "WHERE a.user = :requester " +                                  // WHERE     annotation belongs to the user
                 "OR (a.accessControl.name LIKE 'PUBLIC') " +                         // OR        annotation has public access
                 "OR (a.accessControl.name LIKE 'GROUP' AND a.group = g)")          // OR        annotation has group access and requester belong to group
    public Page<Annotation> findAll(@Param("requester") UserAccount requester, Pageable pageable);
}
