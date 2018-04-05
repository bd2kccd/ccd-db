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

import edu.pitt.dbmi.ccd.db.entity.FileType;
import edu.pitt.dbmi.ccd.db.repository.FileTypeRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * Feb 9, 2018 6:31:05 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class FileTypeService {

    public static final String DATA_SHORT_NAME = "data";
    public static final String VARIABLE_SHORT_NAME = "var";
    public static final String KNOWLEDGE_SHORT_NAME = "knwl";
    public static final String RESULT_SHORT_NAME = "result";

    private final FileTypeRepository fileTypeRepository;

    @Autowired
    public FileTypeService(FileTypeRepository fileTypeRepository) {
        this.fileTypeRepository = fileTypeRepository;

        // initialize database
        if (fileTypeRepository.findAll().isEmpty()) {
            fileTypeRepository.saveAll(Arrays.asList(
                    new FileType("Data", DATA_SHORT_NAME),
                    new FileType("Variable", VARIABLE_SHORT_NAME),
                    new FileType("Knowledge", KNOWLEDGE_SHORT_NAME),
                    new FileType("Result", RESULT_SHORT_NAME)
            ));
        }
    }

    @Cacheable("fileTypeAll")
    public List<FileType> findAll() {
        return fileTypeRepository.findAll();
    }

    @Cacheable("fileTypeByShortName")
    public FileType findByShortName(String shortName) {
        return fileTypeRepository.findByShortName(shortName);
    }

    public FileTypeRepository getRepository() {
        return fileTypeRepository;
    }

}
