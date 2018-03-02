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
import edu.pitt.dbmi.ccd.db.entity.FileFormat;
import edu.pitt.dbmi.ccd.db.entity.FileType;
import edu.pitt.dbmi.ccd.db.repository.FileFormatRepository;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * Feb 9, 2018 6:30:41 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class FileFormatService {

    public static final String TETRAD_TAB_SHORT_NAME = "tetrad-tab";
    public static final String TETRAD_VAR_SHORT_NAME = "tetrad-var";
    public static final String TETRAD_KNWL_SHORT_NAME = "tetrad-knwl";
    public static final String TETRAD_RESULT_SHORT_NAME = "tetrad-result";
    public static final String TDI_TAB_SHORT_NAME = "tdi-tab";
    public static final String TDI_RESULT_SHORT_NAME = "tdi-result";

    private final FileFormatRepository fileFormatRepository;

    @Autowired
    public FileFormatService(
            FileFormatRepository fileFormatRepository,
            FileTypeService fileTypeService,
            AlgorithmTypeService algorithmTypeService) {
        this.fileFormatRepository = fileFormatRepository;

        AlgorithmType tetrad = algorithmTypeService
                .findByShortName(AlgorithmTypeService.TETRAD_SHORT_NAME);
        AlgorithmType tdi = algorithmTypeService
                .findByShortName(AlgorithmTypeService.TDI_SHORT_NAME);

        FileType tabData = fileTypeService
                .findByShortName(FileTypeService.DATA_SHORT_NAME);
        FileType var = fileTypeService
                .findByShortName(FileTypeService.VARIABLE_SHORT_NAME);
        FileType knowledge = fileTypeService
                .findByShortName(FileTypeService.KNOWLEDGE_SHORT_NAME);
        FileType result = fileTypeService
                .findByShortName(FileTypeService.RESULT_SHORT_NAME);

        // initialize database
        if (fileFormatRepository.findAll().isEmpty()) {
            fileFormatRepository.save(Arrays.asList(
                    new FileFormat("Tetrad Tabular Data", TETRAD_TAB_SHORT_NAME, tabData, tetrad),
                    new FileFormat("Tetrad Variable", TETRAD_VAR_SHORT_NAME, var, tetrad),
                    new FileFormat("Tetrad Knowledge", TETRAD_KNWL_SHORT_NAME, knowledge, tetrad),
                    new FileFormat("Tetrad Result", TETRAD_RESULT_SHORT_NAME, result, tetrad),
                    new FileFormat("TDI Tabular Data", TDI_TAB_SHORT_NAME, tabData, tdi),
                    new FileFormat("TDI Result", TDI_RESULT_SHORT_NAME, result, tdi)
            ));
        }
    }

    @Cacheable("fileFormatAll")
    public List<FileFormat> findAll() {
        return fileFormatRepository.findAll();
    }

    @Cacheable("fileFormatOpts")
    public Map<FileType, List<FileFormat>> getFileFormatOptions() {
        return fileFormatRepository.findAll().stream()
                .filter(e -> !FileTypeService.RESULT_SHORT_NAME.equals(e.getFileType().getShortName()))
                .collect(Collectors.groupingBy(FileFormat::getFileType))
                .entrySet().stream()
                .sorted((e1, e2) -> e1.getKey().getShortName().compareTo(e2.getKey().getShortName()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
    }

    @Cacheable("fileFormatById")
    public FileFormat findById(Long id) {
        return fileFormatRepository.findOne(id);
    }

    @Cacheable("fileFormatByShortName")
    public FileFormat findByShortName(String shortName) {
        return fileFormatRepository.findByShortName(shortName);
    }

    @Cacheable("fileFormatsByAlgorithmType")
    public List<FileFormat> findByAlgorithmType(AlgorithmType algorithmType) {
        return fileFormatRepository.findByAlgorithmType(algorithmType);
    }

    public FileFormatRepository getRepository() {
        return fileFormatRepository;
    }

}
