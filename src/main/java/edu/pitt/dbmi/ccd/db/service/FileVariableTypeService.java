/*
 * Copyright (C) 2017 University of Pittsburgh.
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

import edu.pitt.dbmi.ccd.db.entity.FileVariableType;
import edu.pitt.dbmi.ccd.db.repository.FileVariableTypeRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Apr 27, 2017 4:42:26 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class FileVariableTypeService {

    public static final String CONTINUOUS_NAME = "continuous";
    public static final String DISCRETE_NAME = "discrete";
    public static final String MIXED_NAME = "mixed";

    private final FileVariableTypeRepository fileVariableTypeRepository;

    @Autowired
    public FileVariableTypeService(FileVariableTypeRepository fileVariableTypeRepository) {
        this.fileVariableTypeRepository = fileVariableTypeRepository;

        if (fileVariableTypeRepository.findAll().isEmpty()) {
            fileVariableTypeRepository.save(Arrays.asList(
                    new FileVariableType(CONTINUOUS_NAME, "Continuous"),
                    new FileVariableType(DISCRETE_NAME, "Discrete"),
                    new FileVariableType(MIXED_NAME, "Mixed")
            ));
        }
    }

    public FileVariableTypeRepository getRepository() {
        return fileVariableTypeRepository;
    }

}
