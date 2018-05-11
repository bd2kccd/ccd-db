/*
 * Copyright (C) 2018 University of Pittsburgh.
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

import edu.pitt.dbmi.ccd.db.entity.VariableType;
import edu.pitt.dbmi.ccd.db.repository.VariableTypeRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * Feb 9, 2018 6:31:22 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class VariableTypeService {

    public static final Long CONTINUOUS_ID = 1L;
    public static final Long DISCRETE_ID = 2L;
    public static final Long MIXED_ID = 3L;

    private final VariableTypeRepository variableTypeRepository;

    @Autowired
    public VariableTypeService(VariableTypeRepository variableTypeRepository) {
        this.variableTypeRepository = variableTypeRepository;

        // initialize database
        if (variableTypeRepository.findAll().isEmpty()) {
            variableTypeRepository.saveAll(Arrays.asList(
                    new VariableType(CONTINUOUS_ID, "Continuous"),
                    new VariableType(DISCRETE_ID, "Discrete"),
                    new VariableType(MIXED_ID, "Mixed")
            ));
        }
    }

    @Cacheable("VariableTypeById")
    public VariableType findById(Long id) {
        Optional<VariableType> opt = variableTypeRepository.findById(id);

        return opt.isPresent() ? opt.get() : null;
    }

    @Cacheable("VariableTypeAll")
    public List<VariableType> findAll() {
        return variableTypeRepository.findAll();
    }

    public VariableTypeRepository getRepository() {
        return variableTypeRepository;
    }

}
