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
import edu.pitt.dbmi.ccd.db.repository.FileFormatRepository;
import java.util.List;
import javax.transaction.Transactional;
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
@Transactional
public class FileFormatService {

    public static final String TETRAD_TABULAR = "tetrad-tabular";
    public static final String TETRAD_COVARIANCE = "tetrad-covariance";
    public static final String TETRAD_VARIABLE = "tetrad-variable";
    public static final String TETRAD_KNOWLEDGE = "tetrad-knowledge";
    public static final String TETRAD_RESULT_TXT = "tetrad-result-txt";
    public static final String TETRAD_RESULT_JSON = "tetrad-result-json";

    public static final String TDI_TABULAR = "tdi-tabular";
    public static final String TDI_RESULT_TXT = "tdi-result-txt";

    private final FileFormatRepository fileFormatRepository;

    @Autowired
    public FileFormatService(FileFormatRepository fileFormatRepository) {
        this.fileFormatRepository = fileFormatRepository;
    }

    @Cacheable("findAllFileFormats")
    public List<FileFormat> findAll() {
        return fileFormatRepository.findAll();
    }

    @Cacheable("fileFormatByName")
    public FileFormat findByName(String name) {
        return fileFormatRepository.findByName(name);
    }

    public FileFormatRepository getRepository() {
        return fileFormatRepository;
    }

}
