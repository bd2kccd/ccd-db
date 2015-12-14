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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.repository.AttributeRepository;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AttributeService {

    private final AttributeRepository attribRepository;

    @Autowired(required=true)
    public AttributeService(AttributeRepository attribRepository) {
        this.attribRepository = attribRepository;
    }

    public Attribute save(Attribute attrib) {
        return attribRepository.save(attrib);
    }

    public Attribute findOne(Long id) {
        return attribRepository.findOne(id);
    }

    public List<Attribute> findAll() {
        return attribRepository.findAll();
    }

    public Page<Attribute> findAll(Pageable pageable) {
        return attribRepository.findAll(pageable);
    }
}
