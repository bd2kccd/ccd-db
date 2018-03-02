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

import edu.pitt.dbmi.ccd.db.entity.AlgorithmType;
import edu.pitt.dbmi.ccd.db.repository.AlgorithmTypeRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * Feb 9, 2018 6:29:53 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class AlgorithmTypeService {

    public static final String TDI_SHORT_NAME = "tdi";
    public static final String TETRAD_SHORT_NAME = "tetrad";

    private final AlgorithmTypeRepository algorithmTypeRepository;

    @Autowired
    public AlgorithmTypeService(AlgorithmTypeRepository algorithmTypeRepository) {
        this.algorithmTypeRepository = algorithmTypeRepository;

        // initialize database
        if (algorithmTypeRepository.findAll().isEmpty()) {
            algorithmTypeRepository.save(Arrays.asList(
                    new AlgorithmType("TDI", TDI_SHORT_NAME),
                    new AlgorithmType("Tetrad", TETRAD_SHORT_NAME)
            ));
        }
    }

    @Cacheable("algorithmTypeAll")
    public List<AlgorithmType> findAll() {
        return algorithmTypeRepository.findAll();
    }

    @Cacheable("algorithmTypeByShortName")
    public AlgorithmType findByShortName(String shortName) {
        return algorithmTypeRepository.findByShortName(shortName);
    }

    public AlgorithmTypeRepository getRepository() {
        return algorithmTypeRepository;
    }

}
