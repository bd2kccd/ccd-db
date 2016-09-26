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

import edu.pitt.dbmi.ccd.db.domain.FileTypeEnum;
import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.VariableFile;
import edu.pitt.dbmi.ccd.db.repository.DataFileRepository;
import edu.pitt.dbmi.ccd.db.repository.FileRepository;
import edu.pitt.dbmi.ccd.db.repository.FileTypeRepository;
import edu.pitt.dbmi.ccd.db.repository.VariableFileRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Jul 18, 2016 2:54:31 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class VariableFileService extends AbstractFileService {

    @Autowired
    public VariableFileService(FileRepository fileRepository,
            FileTypeRepository fileTypeRepository,
            DataFileRepository dataFileRepository,
            VariableFileRepository variableFileRepository) {
        super(fileRepository, fileTypeRepository, dataFileRepository, variableFileRepository);
    }

    public VariableFile findByFile(File file) {
        return variableFileRepository.findByFile(file);
    }

    public VariableFile save(VariableFile variableFile) {
        File file = variableFile.getFile();
        file.setFileType(fileTypeRepository.findByName(FileTypeEnum.VARIABLE.name()));
        file = fileRepository.save(file);

        removeNonAssociatedFileType(file);

        VariableFile currentVariableFile = variableFileRepository.findByFile(file);
        if (currentVariableFile == null) {
            currentVariableFile = variableFile;
        } else {
            currentVariableFile.setNumOfVars(variableFile.getNumOfVars());
        }
        currentVariableFile.setFile(file);

        return variableFileRepository.save(currentVariableFile);
    }

}
