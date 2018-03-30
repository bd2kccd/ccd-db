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

import edu.pitt.dbmi.ccd.db.repository.VariableTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Feb 9, 2018 6:31:22 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class VariableTypeService {

    public static final String CONTINUOUS_SHORT_NAME = "continuous";
    public static final String DISCRETE_SHORT_NAME = "discrete";
    public static final String MIXED_SHORT_NAME = "mixed";

    private final VariableTypeRepository variableTypeRepository;

    @Autowired
    public VariableTypeService(VariableTypeRepository variableTypeRepository) {
        this.variableTypeRepository = variableTypeRepository;
    }

    public VariableTypeRepository getRepository() {
        return variableTypeRepository;
    }

}
