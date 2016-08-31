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
package edu.pitt.dbmi.ccd.db.service;

import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.entity.AttributeLevel;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;
import edu.pitt.dbmi.ccd.db.repository.AttributeRepository;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

/**
 *
 * Aug 3, 2016 4:27:46 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AttributeService {

    private final AttributeRepository attributeRepository;

    @Autowired
    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public Attribute save(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    public Set<Attribute> save(Set<Attribute> attributes) {
        return attributeRepository.save(attributes).stream().collect(Collectors.toSet());
    }

    public Optional<Attribute> findById(Long id) {
        return attributeRepository.findById(id);
    }

    public Page<Attribute> search(Vocabulary vocabulary, AttributeLevel attributeLevel, String name,Boolean required, Attribute parent, Pageable pageable) {
        return attributeRepository.search(vocabulary, attributeLevel, name, required, parent, pageable);
    }

    public List<Attribute> findAll() {
        return attributeRepository.findAll();
    }

    public void delete(Attribute attribute) {
        attributeRepository.delete(attribute);
    }

    public void delete(Set<Attribute> attributes) {
        attributeRepository.delete(attributes);
    }
}
