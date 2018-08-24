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

import edu.pitt.dbmi.ccd.db.code.AlgorithmTypeCodes;
import edu.pitt.dbmi.ccd.db.code.FileFormatCodes;
import edu.pitt.dbmi.ccd.db.code.FileTypeCodes;
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

    private final FileFormatRepository repository;

    @Autowired
    public FileFormatService(FileFormatRepository repository, AlgorithmTypeService algorithmTypeService, FileTypeService fileTypeService) {
        this.repository = repository;

        AlgorithmType tetrad = algorithmTypeService.findByCode(AlgorithmTypeCodes.TETRAD);

        FileType tabData = fileTypeService.findByCode(FileTypeCodes.DATA);
        FileType var = fileTypeService.findByCode(FileTypeCodes.VARIABLE);
        FileType knowledge = fileTypeService.findByCode(FileTypeCodes.KNOWLEDGE);

        // initialize database
        if (repository.findAll().isEmpty()) {
            repository.saveAll(Arrays.asList(
                    new FileFormat("Tetrad Tabular Data", FileFormatCodes.TETRAD_TAB, tabData, tetrad),
                    new FileFormat("Tetrad Variable", FileFormatCodes.TETRAD_VAR, var, tetrad),
                    new FileFormat("Tetrad Knowledge", FileFormatCodes.TETRAD_KNWL, knowledge, tetrad)
            ));
        }
    }

    @Cacheable("FileFormatByCode")
    public FileFormat findByCode(short code) {
        return repository.findByCode(code);
    }

    @Cacheable("FileFormatAll")
    public List<FileFormat> findAll() {
        return repository.findAll();
    }

    @Cacheable("FileFormatOpts")
    public Map<FileType, List<FileFormat>> getFileFormatOptions() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(FileFormat::getFileType))
                .entrySet().stream()
                .sorted((e1, e2) -> e1.getKey().getName().compareTo(e2.getKey().getName()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
    }

    public FileFormatRepository getRepository() {
        return repository;
    }

}
