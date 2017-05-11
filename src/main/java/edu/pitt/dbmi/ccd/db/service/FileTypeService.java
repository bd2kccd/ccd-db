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

import edu.pitt.dbmi.ccd.db.entity.FileType;
import edu.pitt.dbmi.ccd.db.repository.FileTypeRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Apr 27, 2017 4:28:46 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class FileTypeService {

    public static final String DATA_NAME = "data";
    public static final String VARIABLE_NAME = "var";
    public static final String KNOWLEDGE_NAME = "knwl";
    public static final String RESULT_NAME = "result";

    private final FileTypeRepository fileTypeRepository;

    @Autowired
    public FileTypeService(FileTypeRepository fileTypeRepository) {
        this.fileTypeRepository = fileTypeRepository;

        if (fileTypeRepository.findAll().isEmpty()) {
            fileTypeRepository.save(Arrays.asList(
                    new FileType(DATA_NAME, "Data"),
                    new FileType(VARIABLE_NAME, "Variable"),
                    new FileType(KNOWLEDGE_NAME, "Knowledge"),
                    new FileType(RESULT_NAME, "Result")
            ));
        }
    }

    public FileTypeRepository getFileTypeRepository() {
        return fileTypeRepository;
    }

}
