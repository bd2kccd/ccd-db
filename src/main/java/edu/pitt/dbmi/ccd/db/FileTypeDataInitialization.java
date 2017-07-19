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

import edu.pitt.dbmi.ccd.db.entity.AlgorithmType;
import edu.pitt.dbmi.ccd.db.entity.FileFormat;
import edu.pitt.dbmi.ccd.db.entity.FileType;
import edu.pitt.dbmi.ccd.db.repository.AlgorithmTypeRepository;
import edu.pitt.dbmi.ccd.db.repository.FileFormatRepository;
import edu.pitt.dbmi.ccd.db.repository.FileTypeRepository;
import edu.pitt.dbmi.ccd.db.service.AlgorithmTypeService;
import edu.pitt.dbmi.ccd.db.service.FileFormatService;
import edu.pitt.dbmi.ccd.db.service.FileTypeService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *
 * May 11, 2017 3:51:04 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Component
public class FileTypeDataInitialization implements ApplicationRunner {

    private final FileTypeService fileTypeService;
    private final AlgorithmTypeService algorithmTypeService;
    private final FileFormatService fileFormatService;

    @Autowired
    public FileTypeDataInitialization(FileTypeService fileTypeService, AlgorithmTypeService algorithmTypeService, FileFormatService fileFormatService) {
        this.fileTypeService = fileTypeService;
        this.algorithmTypeService = algorithmTypeService;
        this.fileFormatService = fileFormatService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        FileFormatRepository fileFormatRepo = fileFormatService.getRepository();
        if (fileFormatRepo.findAll().isEmpty()) {
            FileTypeRepository fileTypeRepo = fileTypeService.getRepository();
            FileType data = fileTypeRepo.findByName(FileTypeService.DATA_NAME);
            FileType knowledge = fileTypeRepo.findByName(FileTypeService.KNOWLEDGE_NAME);
            FileType result = fileTypeRepo.findByName(FileTypeService.RESULT_NAME);
            FileType variable = fileTypeRepo.findByName(FileTypeService.VARIABLE_NAME);

            AlgorithmTypeRepository algoTypeRepo = algorithmTypeService.getRepository();
            AlgorithmType tetrad = algoTypeRepo.findByName(AlgorithmTypeService.TETRAD_NAME);
            AlgorithmType tdi = algoTypeRepo.findByName(AlgorithmTypeService.TDI_NAME);

            fileFormatRepo.save(Arrays.asList(
                    new FileFormat(FileFormatService.TETRAD_TABULAR_NAME, "Tetrad Tabular", data, Arrays.asList(tetrad)),
                    new FileFormat(FileFormatService.TETRAD_COVARIANCE_NAME, "Tetrad Covariance", data, Arrays.asList(tetrad)),
                    new FileFormat(FileFormatService.TETRAD_VARIABLE_NAME, "Tetrad Variable", variable, Arrays.asList(tetrad)),
                    new FileFormat(FileFormatService.TETRAD_KNOWLEDGE_NAME, "Tetrad Knowledge", knowledge, Arrays.asList(tetrad)),
                    new FileFormat(FileFormatService.TETRAD_RESULT_TXT_NAME, "Tetrad Text Result", result, Arrays.asList(tetrad)),
                    new FileFormat(FileFormatService.TETRAD_RESULT_JSON_NAME, "Tetrad JSON Result", result, Arrays.asList(tetrad)),
                    new FileFormat(FileFormatService.TDI_TABULAR_NAME, "TDI Tabular", data, Arrays.asList(tdi, tetrad)),
                    new FileFormat(FileFormatService.TDI_RESULT_TXT_NAME, "TDI Text Result", result, Arrays.asList(tdi))
            ));
        }
    }

}
