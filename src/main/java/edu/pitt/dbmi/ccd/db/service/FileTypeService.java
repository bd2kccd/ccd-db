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
import java.util.Optional;
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

    public static final Long DATA_ID = 1L;
    public static final Long VARIABLE_ID = 2L;
    public static final Long KNOWLEDGE_ID = 3L;
    public static final Long RESULT_ID = 4L;

    private final FileTypeRepository fileTypeRepository;

    @Autowired
    public FileTypeService(FileTypeRepository fileTypeRepository) {
        this.fileTypeRepository = fileTypeRepository;

        // initialize database
        if (fileTypeRepository.findAll().isEmpty()) {
            fileTypeRepository.saveAll(Arrays.asList(
                    new FileType(DATA_ID, "Data"),
                    new FileType(VARIABLE_ID, "Variable"),
                    new FileType(KNOWLEDGE_ID, "Knowledge"),
                    new FileType(RESULT_ID, "Result")
            ));
        }
    }

    @Cacheable("FileTypeById")
    public FileType findById(Long id) {
        Optional<FileType> opt = fileTypeRepository.findById(id);

        return opt.isPresent() ? opt.get() : null;
    }

    @Cacheable("FileTypeAll")
    public List<FileType> findAll() {
        return fileTypeRepository.findAll();
    }

    public FileTypeRepository getRepository() {
        return fileTypeRepository;
    }

}
