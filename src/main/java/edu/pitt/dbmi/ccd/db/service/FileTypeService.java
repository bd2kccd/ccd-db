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

import edu.pitt.dbmi.ccd.db.code.FileTypeCodes;
import edu.pitt.dbmi.ccd.db.entity.FileType;
import edu.pitt.dbmi.ccd.db.repository.FileTypeRepository;
import java.util.Arrays;
import java.util.List;
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
public class FileTypeService {

    private final FileTypeRepository repository;

    @Autowired
    public FileTypeService(FileTypeRepository repository) {
        this.repository = repository;

        // initialize database
        if (repository.findAll().isEmpty()) {
            repository.saveAll(Arrays.asList(
                    new FileType("Data", FileTypeCodes.DATA),
                    new FileType("Variable", FileTypeCodes.VARIABLE),
                    new FileType("Knowledge", FileTypeCodes.KNOWLEDGE),
                    new FileType("Result", FileTypeCodes.RESULT)
            ));
        }
    }

    @Cacheable("FileTypeByCode")
    public FileType findByCode(short code) {
        return repository.findByCode(code);
    }

    @Cacheable("FileTypeAll")
    public List<FileType> findAll() {
        return repository.findAll();
    }

    public FileTypeRepository getRepository() {
        return repository;
    }

}
