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

import edu.pitt.dbmi.ccd.db.entity.Algorithm;
import edu.pitt.dbmi.ccd.db.repository.AlgorithmRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * May 31, 2017 3:17:55 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class AlgorithmService {

    public static final String FGESC_ALGO_NAME = "fgesc";
    public static final String FGESD_ALGO_NAME = "fgesd";
    public static final String FGESM_CG_ALGO_NAME = "fgesm_cg";
    public static final String GFCIC_ALGO_NAME = "gfcic";
    public static final String GFCID_ALGO_NAME = "gfcid";
    public static final String GFCIM_CG_ALGO_NAME = "gfcim_cg";

    private final AlgorithmRepository algorithmRepository;

    @Autowired
    public AlgorithmService(AlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;

        if (algorithmRepository.findAll().isEmpty()) {
            algorithmRepository.save(Arrays.asList(
                    new Algorithm(FGESC_ALGO_NAME, "FGES Continuous"),
                    new Algorithm(FGESD_ALGO_NAME, "FGES Discrete"),
                    new Algorithm(FGESM_CG_ALGO_NAME, "FGES Mixed with Conditional Gaussian"),
                    new Algorithm(GFCIC_ALGO_NAME, "GFCI Continuous"),
                    new Algorithm(GFCID_ALGO_NAME, "GFCI Discrete"),
                    new Algorithm(GFCIM_CG_ALGO_NAME, "GFCI Mixed with Conditional Gaussian")
            ));
        }
    }

    public Algorithm findByName(String name) {
        return algorithmRepository.findByName(name);
    }

    public AlgorithmRepository getAlgorithmRepository() {
        return algorithmRepository;
    }

}
