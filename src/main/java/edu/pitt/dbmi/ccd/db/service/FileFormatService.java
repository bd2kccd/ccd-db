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

import edu.pitt.dbmi.ccd.db.entity.FileFormat;
import edu.pitt.dbmi.ccd.db.entity.FileType;
import edu.pitt.dbmi.ccd.db.repository.FileFormatRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * May 10, 2017 2:55:57 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class FileFormatService {

    public static final String TETRAD_TABULAR_NAME = "tetrad-tabular";
    public static final String TETRAD_COVARIANCE_NAME = "tetrad-covariance";
    public static final String TETRAD_VARIABLE_NAME = "tetrad-variable";
    public static final String TETRAD_KNOWLEDGE_NAME = "tetrad-knowledge";
    public static final String TETRAD_RESULT_TXT_NAME = "tetrad-result-txt";
    public static final String TETRAD_RESULT_JSON_NAME = "tetrad-result-json";

    public static final String TDI_TABULAR_NAME = "tdi-tabular";
    public static final String TDI_RESULT_TXT_NAME = "tdi-result-txt";

    private final FileFormatRepository fileFormatRepository;

    @Autowired
    public FileFormatService(FileFormatRepository fileFormatRepository) {
        this.fileFormatRepository = fileFormatRepository;
    }

    @Cacheable("fileFormatAll")
    public List<FileFormat> findAll() {
        return fileFormatRepository.findAll();
    }

    @Cacheable("fileFormatByName")
    public FileFormat findByName(String name) {
        return fileFormatRepository.findByName(name);
    }

    @Cacheable("fileFormatByFileTypeNot")
    public List<FileFormat> findByFileTypeNot(FileType fileType) {
        return fileFormatRepository.findByFileTypeNot(fileType);
    }

    public FileFormatRepository getRepository() {
        return fileFormatRepository;
    }

}
