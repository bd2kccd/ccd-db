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

import edu.pitt.dbmi.ccd.db.repository.FileFormatRepository;
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

    public static final String TETRAD_TAB_FMT_NAME = "tetrad-tab";
    public static final String TETRAD_COVAR_FMT_NAME = "tetrad-covar";
    public static final String TETRAD_VAR_FMT_NAME = "tetrad-var";
    public static final String TETRAD_KNOWLEGE_FMT_NAME = "tetrad-knwl";
    public static final String TETRAD_TEXT_RESULT_FMT_NAME = "tetrad-txt-result";
    public static final String TETRAD_JSON_RESULT_FMT_NAME = "tetrad-json-result";

    public static final String TDI_TAB_FMT_NAME = "tdi-tab";
    public static final String TDI_TXT_RESULT_FMT_NAME = "tdi-txt-result";

    private final FileFormatRepository fileFormatRepository;

    @Autowired
    public FileFormatService(FileFormatRepository fileFormatRepository) {
        this.fileFormatRepository = fileFormatRepository;
    }

    public FileFormatRepository getFileFormatRepository() {
        return fileFormatRepository;
    }

}
