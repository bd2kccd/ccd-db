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
import java.util.List;
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

    private final FileTypeRepository fileTypeRepository;

    @Autowired
    public FileTypeService(FileTypeRepository fileTypeRepository) {
        this.fileTypeRepository = fileTypeRepository;

        List<FileType> types = fileTypeRepository.findAll();
        if (types.isEmpty()) {
            String[] typeNames = {
                "tetrad dataset", "tdi dataset",
                "tetrad variable",
                "tetrad prior knowledge", "tdi prior knowledge",
                "tetrad result", "tdi result"
            };
            for (String name : typeNames) {
                types.add(new FileType(name));
            }
            fileTypeRepository.save(types);
        }
    }

    public FileTypeRepository getFileTypeRepository() {
        return fileTypeRepository;
    }

}