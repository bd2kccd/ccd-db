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

import edu.pitt.dbmi.ccd.db.entity.AlgorithmType;
import edu.pitt.dbmi.ccd.db.repository.AlgorithmTypeRepository;
import java.util.Arrays;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * May 10, 2017 2:53:41 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class AlgorithmTypeService {

    public static final String TDI_ALGO = "tdi";
    public static final String TETRAD_ALGO = "tetrad";

    private final AlgorithmTypeRepository algorithmTypeRepository;

    @Autowired
    public AlgorithmTypeService(AlgorithmTypeRepository algorithmTypeRepository) {
        this.algorithmTypeRepository = algorithmTypeRepository;

        if (algorithmTypeRepository.findAll().isEmpty()) {
            algorithmTypeRepository.save(Arrays.asList(
                    new AlgorithmType(TDI_ALGO, "TDI"),
                    new AlgorithmType(TETRAD_ALGO, "Tetrad")
            ));
        }
    }

    public AlgorithmTypeRepository getRepository() {
        return algorithmTypeRepository;
    }

}
