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

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.entity.AttributeLevel;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;

/**
 *
 * Aug 3, 2016 3:54:24 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    public Optional<Attribute> findById(Long id);

    @Query(value = "SELECT a FROM Attribute AS a " +
            "WHERE (:vocabulary IS NULL OR a.vocabulary = :vocabulary " +
            "AND (:level IS NULL OR a.attributeLevel = :level " +
            "AND (:name IS NULL OR a.name LIKE :name " +
            "AND (:required IS NULL OR a.required = :required) " +
            "AND (:parent IS NULL OR a.parentAttribute = :parent")
    public Page<Attribute> search(@Param("vocabulary") Vocabulary vocabulary, @Param("level") AttributeLevel level, @Param("name") String name, @Param("required") Boolean required, @Param("parent") Attribute parent, Pageable pageable);

    public Page<Attribute> findAll(Pageable pageable);

    public List<Attribute> findAll();
}
