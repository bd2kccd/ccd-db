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
import java.util.Optional;
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

    public static final Long TETRAD_TAB_ID = 1L;
    public static final Long TETRAD_VAR_ID = 2L;
    public static final Long TETRAD_KNWL_ID = 3L;
    public static final Long TETRAD_RESULT_ID = 4L;

    private final FileFormatRepository fileFormatRepository;

    @Autowired
    public FileFormatService(
            FileFormatRepository fileFormatRepository,
            FileTypeService fileTypeService,
            AlgorithmTypeService algorithmTypeService) {
        this.fileFormatRepository = fileFormatRepository;

        AlgorithmType tetrad = algorithmTypeService.findById(AlgorithmTypeService.TETRAD_ID);

        FileType tabData = fileTypeService.findById(FileTypeService.DATA_ID);
        FileType var = fileTypeService.findById(FileTypeService.VARIABLE_ID);
        FileType knowledge = fileTypeService.findById(FileTypeService.KNOWLEDGE_ID);
        FileType result = fileTypeService.findById(FileTypeService.RESULT_ID);

        // initialize database
        if (fileFormatRepository.findAll().isEmpty()) {
            fileFormatRepository.saveAll(Arrays.asList(
                    new FileFormat(TETRAD_TAB_ID, "Tetrad Tabular Data", tabData, tetrad),
                    new FileFormat(TETRAD_VAR_ID, "Tetrad Variable", var, tetrad),
                    new FileFormat(TETRAD_KNWL_ID, "Tetrad Knowledge", knowledge, tetrad),
                    new FileFormat(TETRAD_RESULT_ID, "Tetrad Result", result, tetrad)
            ));
        }
    }

    @Cacheable("FileFormatById")
    public FileFormat findById(Long id) {
        Optional<FileFormat> opt = fileFormatRepository.findById(id);

        return opt.isPresent() ? opt.get() : null;
    }

    @Cacheable("FileFormatAll")
    public List<FileFormat> findAll() {
        return fileFormatRepository.findAll();
    }

    @Cacheable("FileFormatsByAlgorithmType")
    public List<FileFormat> findByAlgorithmType(AlgorithmType algorithmType) {
        return fileFormatRepository.findByAlgorithmType(algorithmType);
    }

    @Cacheable("FileFormatOpts")
    public Map<FileType, List<FileFormat>> getFileFormatOptions() {
        return fileFormatRepository.findAll().stream()
                .filter(e -> e.getFileType().getId().longValue() != FileTypeService.RESULT_ID)
                .collect(Collectors.groupingBy(FileFormat::getFileType))
                .entrySet().stream()
                .sorted((e1, e2) -> e1.getKey().getName().compareTo(e2.getKey().getName()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
    }

    public FileFormatRepository getRepository() {
        return fileFormatRepository;
    }

}
