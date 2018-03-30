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

import edu.pitt.dbmi.ccd.db.repository.FileFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public FileFormatService(FileFormatRepository fileFormatRepository) {
        this.fileFormatRepository = fileFormatRepository;
    }

    public FileFormatRepository getRepository() {
        return fileFormatRepository;
    }

}
