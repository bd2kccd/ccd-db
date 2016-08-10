/*
 * Copyright (C) 2016 University of Pittsburgh.
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

import edu.pitt.dbmi.ccd.db.domain.FileTypeName;
import edu.pitt.dbmi.ccd.db.entity.FileType;
import edu.pitt.dbmi.ccd.db.repository.FileTypeRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * Aug 9, 2016 1:38:46 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class FileTypeService {

    private final FileTypeRepository fileTypeRepository;

    @Autowired
    public FileTypeService(FileTypeRepository fileTypeRepository) {
        this.fileTypeRepository = fileTypeRepository;

        List<FileType> fileTypes = fileTypeRepository.findAll();
        if (fileTypes.isEmpty()) {
            fileTypes.add(new FileType(FileTypeName.ALGORITHM_RESULT.name()));
            fileTypes.add(new FileType(FileTypeName.DATASET.name()));
            fileTypes.add(new FileType(FileTypeName.PRIOR_KNOWLEDGE.name()));
            fileTypes.add(new FileType(FileTypeName.VARIABLE.name()));

            fileTypeRepository.save(fileTypes);
        }
    }

    public List<FileType> findAll() {
        return fileTypeRepository.findAll();
    }

    public FileType findById(Long id) {
        return fileTypeRepository.findOne(id);
    }

    @Cacheable("fileTypeByFileTypeName")
    public FileType findByFileTypeName(FileTypeName fileTypeName) {
        return fileTypeRepository.findByName(fileTypeName.name());
    }

}
