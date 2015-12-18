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

    public Attribute save(Attribute attrib) {
        return attributeRepository.save(attrib);
    }

    public Optional<Attribute> findOne(Long id) {
        return attributeRepository.findById(id);
    }

    public Optional<Attribute> findByVocabAndInnerId(Vocabulary vocab, Long innerId) {
        return attributeRepository.findByVocabAndInnerId(vocab, innerId);
    }

    public Optional<Attribute> findByVocabAndLevelAndName(Vocabulary vocab, String level, String name) {
        return attributeRepository.findByVocabAndLevelAndName(vocab, level, name);
    }

    public Page<Attribute> findByVocabAndName(Vocabulary vocab, String name, Pageable pageable) {
        return attributeRepository.findByVocabAndName(vocab, name, pageable);
    }

    public Page<Attribute> findByVocabAndLevel(Vocabulary vocab, String level, Pageable pageable) {
        return attributeRepository.findByVocabAndLevel(vocab, level, pageable);
    }

    public Page<Attribute> findByVocabAndRequirementLevel(Vocabulary vocab, String requirementLevel, Pageable pageable) {
        return attributeRepository.findByVocabAndRequirementLevel(vocab, requirementLevel, pageable);
    }

    public Page<Attribute> findAllOrphans(Vocabulary vocab, Pageable pageable) {
        return attributeRepository.findByVocabAndParentIsNull(vocab, pageable);
    }

    public Page<Attribute> findByParent(Attribute parent, Pageable pageable) {
        return attributeRepository.findByParent(parent, pageable);
    }

    public Page<Attribute> findByChildrenIn(Set<Attribute> children, Pageable pageable) {
        return attributeRepository.findByChildrenIn(children, pageable);
    }

    public Page<Attribute> findAllParentless(Vocabulary vocab, Pageable pageable) {
        return attributeRepository.findByVocabAndParentIsNull(vocab, pageable);
    }

    public Page<Attribute> findAll(Pageable pageable) {
        return attributeRepository.findAll(pageable);
    }
}
