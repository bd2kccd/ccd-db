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

package edu.pitt.dbmi.ccd.db.service;

import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;
import edu.pitt.dbmi.ccd.db.repository.AttributeRepository;
import edu.pitt.dbmi.ccd.db.error.NotFoundException;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AttributeService {

    private final AttributeRepository attributeRepository;

    @Autowired(required=true)
    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public Attribute save(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    public Attribute findOne(Long id) {
        Optional<Attribute> attribute = attributeRepository.findById(id);
        return attribute.orElseThrow(() -> new NotFoundException("Attribute", "id", id));
    }

    public Attribute findByVocabAndLevelAndName(String vocabName, String level, String name) {
        Optional<Attribute> attribute = attributeRepository.findByVocabAndLevelAndName(vocabName, level, name);
        return attribute.orElseThrow(() -> new NotFoundException("Attribute",
                                                                 new String[]{"vocab", "level", "name"},
                                                                 new Object[]{vocabName, level, name}));
    }

    public Page<Attribute> findByVocab(String vocabName, Pageable pageable) {
        return attributeRepository.findByVocab(vocabName, pageable);
    }

    public Page<Attribute> findByVocabAndParent(String vocabName, Long parent, Pageable pageable) {
        return attributeRepository.findByVocabAndParent(vocabName, parent, pageable);
    }

    public Page<Attribute> findByVocabAndParent(String vocabName, Attribute parent, Pageable pageable) {
        return attributeRepository.findByVocabAndParent(vocabName, parent, pageable);
    }

    public Page<Attribute> findByVocabAndParentIsNull(String vocabName, Pageable pageable) {
        return attributeRepository.findByVocabAndParentIsNull(vocabName, pageable);
    }

    /**
     * Find Attributes matching non-null parameters
     * @param  vocab            vocabulary (nullable)
     * @param  level            level (nullable)
     * @param  name             name (nullable)
     * @param  requirementLevel requirement level (nullable)
     * @param  pageable         page request
     * @return                  matching attributes
     */
    public Page<Attribute> findByVocabAndLevelAndNameAndRequirementLevel(String vocabName, String level, String name, String requirementLevel, Pageable pageable) {
        return attributeRepository.findByVocabAndLevelAndNameAndRequirementLevel(vocabName, level, name, requirementLevel, pageable);
    }

    /**
     * Find Attributes containing non-null parameters
     * @param  vocab            vocabulary (nullable)
     * @param  level            level contains (nullable)
     * @param  name             name contains (nullable)
     * @param  requirementLevel requirement level contains (nullable)
     * @param  pageable         page request
     * @return                  matching attributes
     */
    public Page<Attribute> findByVocabAndLevelContainsAndNameContainsAndRequirementLevelContains(String vocabName, String level, String name, String requirementLevel, Pageable pageable) {
        return attributeRepository.findByVocabAndLevelContainsAndNameContainsAndRequirementLevelContains(vocabName, level, name, requirementLevel, pageable);
    }

    public Page<Attribute> findByParent(Attribute parent, Pageable pageable) {
        return attributeRepository.findByParent(parent, pageable);
    }

    public Page<Attribute> findByChildrenIn(Set<Attribute> children, Pageable pageable) {
        return attributeRepository.findByChildrenIn(children, pageable);
    }

    public Page<Attribute> findAllByParentIsNull(Pageable pageable) {
        return attributeRepository.findAllByParentIsNull(pageable);
    }

    public Page<Attribute> findAll(Pageable pageable) {
        return attributeRepository.findAll(pageable);
    }
}
