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

    public static final String TETRAD_TAB_FMT_NAME = "Tetrad Tabular";
    public static final String TETRAD_COVAR_FMT_NAME = "Tetrad Covariance";
    public static final String TETRAD_VAR_FMT_NAME = "Tetrad Variable";
    public static final String TETRAD_KNOWLEGE_FMT_NAME = "Tetrad Knowledge";
    public static final String TETRAD_TEXT_RESULT_FMT_NAME = "Tetrad Text Result";
    public static final String TETRAD_JSON_RESULT_FMT_NAME = "Tetrad JSON Result";

    public static final String TDI_TAB_FMT_NAME = "tab";

    private final FileFormatRepository fileFormatRepository;
    private final FileTypeService fileTypeService;
    private final AlgorithmService algorithmService;

    @Autowired
    public FileFormatService(FileFormatRepository fileFormatRepository, FileTypeService fileTypeService, AlgorithmService algorithmService) {
        this.fileFormatRepository = fileFormatRepository;
        this.fileTypeService = fileTypeService;
        this.algorithmService = algorithmService;

        List<FileFormat> fileFormats = fileFormatRepository.findAll();
        if (fileFormats.isEmpty()) {
            fileTypeService.getFileTypeRepository().findByName(FileTypeService.DATA_NAME);
        }
    }

    public FileFormatRepository getFileFormatRepository() {
        return fileFormatRepository;
    }

}
