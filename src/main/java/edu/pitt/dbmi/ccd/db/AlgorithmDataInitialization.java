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
package edu.pitt.dbmi.ccd.db;

import edu.pitt.dbmi.ccd.db.entity.Algorithm;
import edu.pitt.dbmi.ccd.db.entity.AlgorithmType;
import edu.pitt.dbmi.ccd.db.entity.FileVariableType;
import edu.pitt.dbmi.ccd.db.repository.AlgorithmRepository;
import edu.pitt.dbmi.ccd.db.service.AlgorithmService;
import edu.pitt.dbmi.ccd.db.service.AlgorithmTypeService;
import edu.pitt.dbmi.ccd.db.service.FileVariableTypeService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *
 * Jul 19, 2017 3:04:50 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Component
public class AlgorithmDataInitialization implements ApplicationRunner {

    private final AlgorithmService algorithmService;
    private final AlgorithmTypeService algorithmTypeService;
    private final FileVariableTypeService fileVariableTypeService;

    @Autowired
    public AlgorithmDataInitialization(AlgorithmService algorithmService, AlgorithmTypeService algorithmTypeService, FileVariableTypeService fileVariableTypeService) {
        this.algorithmService = algorithmService;
        this.algorithmTypeService = algorithmTypeService;
        this.fileVariableTypeService = fileVariableTypeService;
    }

    @Override
    public void run(ApplicationArguments aa) throws Exception {
        AlgorithmRepository algorithmRepository = algorithmService.getRepository();
        if (algorithmRepository.findAll().isEmpty()) {
            AlgorithmType tetrad = algorithmTypeService.findByName(AlgorithmTypeService.TETRAD_NAME);

            FileVariableType continuous = fileVariableTypeService.findByName(FileVariableTypeService.CONTINUOUS_NAME);
            FileVariableType discrete = fileVariableTypeService.findByName(FileVariableTypeService.DISCRETE_NAME);
            FileVariableType mixed = fileVariableTypeService.findByName(FileVariableTypeService.MIXED_NAME);

            algorithmRepository.save(Arrays.asList(
                    new Algorithm(AlgorithmService.FGES_CONTINUOUS_NAME, "FGES Continuous", "Fast Greedy Equivalence Search (FGES) Algorithm for Continuous Variables", tetrad, continuous),
                    new Algorithm(AlgorithmService.FGES_DISCRETE_NAME, "FGES Discrete", "Fast Greedy Equivalence Search (FGES) Algorithm for Discrete Variables", tetrad, discrete),
                    new Algorithm(AlgorithmService.FGES_MIXED_NAME, "FGES Mixed", "Fast Greedy Equivalence Search (FGES) Algorithm for Mixed Variables", tetrad, mixed),
                    new Algorithm(AlgorithmService.GFCI_CONTINUOUS_NAME, "GFCI Continuous", "Greedy Fast Causal Inference (GFCI) Algorithm for Continuous Variables", tetrad, continuous),
                    new Algorithm(AlgorithmService.GFCI_DISCRETE_NAME, "GFCI Discrete", "Greedy Fast Causal Inference (GFCI) Algorithm for Discrete Variables", tetrad, discrete),
                    new Algorithm(AlgorithmService.GFCI_MIXED_NAME, "GFCI Mixed", "Greedy Fast Causal Inference (GFCI) Algorithm for Mixed Variables", tetrad, mixed)
            ));
        }
    }

}
