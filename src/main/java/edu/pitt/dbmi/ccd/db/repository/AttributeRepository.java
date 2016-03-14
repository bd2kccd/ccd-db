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
import org.springframework.stereotype.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;
import edu.pitt.dbmi.ccd.db.entity.Attribute;

/**
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Repository
@RepositoryRestResource(exported=false)
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    public Optional<Attribute> findById(Long id);

    // public Optional<Attribute> findByVocabAndId(Vocabulary vocabulary, Long id);

    // @Query(value="SELECT a FROM Attribute AS a " +
    //              "WHERE a.vocab.name = ?1 " +
    //              "AND a.id = ?2")
    // public Optional<Attribute> findByVocabAndId(String vocabName, Long id);


    @Query(value="SELECT a FROM Attribute AS a " +
                 "WHERE a.vocab.name = ?1 " +
                 "AND a.level LIKE ?2 " +
                 "AND a.name LIKE ?3")
    public Optional<Attribute> findByVocabAndLevelAndName(String vocabName, String level, String name);

    @Query(value="SELECT a FROM Attribute AS a " +
                 "WHERE a.vocab.name = ?1")
    public Page<Attribute> findByVocab(String vocabName, Pageable pageable);

    @Query(value="SELECT a FROM Attribute AS a " +
                 "WHERE a.vocab.name = ?1 " +
                 "AND a.parent.id = ?2")
    public Page<Attribute> findByVocabAndParent(String vocabName, Long parent, Pageable pageable);

    @Query(value="SELECT a FROM Attribute AS a " +
                 "WHERE a.vocab.name = ?1 " +
                 "AND a.parent = ?2")
    public Page<Attribute> findByVocabAndParent(String vocabName, Attribute parent, Pageable pageable);

    @Query(value="SELECT a FROM Attribute AS a " +
                 "WHERE a.vocab.name = ?1 " +
                 "AND a.parent IS NULL")
    public Page<Attribute> findByVocabAndParentIsNull(String vocabName, Pageable pageable);

    @Query(value="SELECT a FROM Attribute AS a " +
                 "WHERE (:vocab IS NULL OR a.vocab.name = :vocab) " +
                 "AND (:level IS NULL OR a.level LIKE :level) " +
                 "AND (:name IS NULL OR a.name LIKE :name) " +
                 "AND (:requirement IS NULL OR a.requirementLevel LIKE :requirement)")
    public Page<Attribute> findByVocabAndLevelAndNameAndRequirementLevel(@Param("vocab") String vocabName, @Param("level") String level, @Param("name") String name, @Param("requirement") String requirementLevel, Pageable pageable);

    @Query(value="SELECT a FROM Attribute AS a " +
                 "WHERE (:vocab IS NULL OR a.vocab.name = :vocab) " +
                 "AND (:level IS NULL OR a.level LIKE CONCAT('%', :level, '%')) " +
                 "AND (:name IS NULL OR a.name LIKE CONCAT('%', :name, '%')) " +
                 "AND (:requirement IS NULL OR a.requirementLevel LIKE CONCAT('%', :requirement, '%'))")
    public Page<Attribute> findByVocabAndLevelContainsAndNameContainsAndRequirementLevelContains(@Param("vocab") String vocabName, @Param("level") String level, @Param("name") String name, @Param("requirement") String requirementLevel, Pageable pageable);

    public Page<Attribute> findByParent(Attribute parent, Pageable pageable);

    public Page<Attribute> findByChildrenIn(Set<Attribute> children, Pageable pageable);

    public Page<Attribute> findAllByParentIsNull(Pageable pageable);

    public Page<Attribute> findAll(Pageable pageable);
}
