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

    public Optional<Attribute> findByVocabAndId(Vocabulary vocab, Long id);

    public Optional<Attribute> findByVocabAndLevelAndName(Vocabulary vocab, String level, String name);

    public Page<Attribute> findByVocabAndName(Vocabulary vocab, String name, Pageable pageable);

    public Page<Attribute> findByVocabAndNameStartsWith(Vocabulary vocab, String terms, Pageable pageable);

    public Page<Attribute> findByVocabAndNameContains(Vocabulary vocab, String terms, Pageable pageable);

    public Page<Attribute> findByVocabAndLevel(Vocabulary vocab, String level, Pageable pageable);

    public Page<Attribute> findByVocabAndLevelStartsWith(Vocabulary vocab, String terms, Pageable pageable);

    public Page<Attribute> findByVocabAndLevelContains(Vocabulary vocab, String terms, Pageable pageable);

    public Page<Attribute> findByVocabAndRequirementLevel(Vocabulary vocab, String requirementLevel, Pageable pageable);

    public Page<Attribute> findByVocabAndRequirementLevelStartsWith(Vocabulary vocab, String terms, Pageable pageable);

    public Page<Attribute> findByVocabAndRequirementLevelContains(Vocabulary vocab, String terms, Pageable pageable);

    public Page<Attribute> findByVocabAndLevelAndName(Vocabulary vocab, String level, String name, Pageable pageable);

    public Page<Attribute> findByVocabAndLevelAndRequirementLevel(Vocabulary vocab, String level, String requirementLevel, Pageable pageable);

    public Page<Attribute> findByVocabAndRequirementLevelAndName(Vocabulary vocab, String requirementLevel, String name, Pageable pageable);

    public Page<Attribute> findByVocabAndParentIsNull(Vocabulary vocab, Pageable pageable);

    public Page<Attribute> findByParent(Attribute parent, Pageable pageable);

    public Page<Attribute> findByChildrenIn(Set<Attribute> children, Pageable pageable);

    public Page<Attribute> findAll(Pageable pageable);
}
