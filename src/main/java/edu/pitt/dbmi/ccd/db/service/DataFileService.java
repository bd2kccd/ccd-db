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
import edu.pitt.dbmi.ccd.db.entity.DataFile;
import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.VariableType;
import edu.pitt.dbmi.ccd.db.repository.DataFileRepository;
import edu.pitt.dbmi.ccd.db.repository.FileRepository;
import edu.pitt.dbmi.ccd.db.repository.FileTypeRepository;
import edu.pitt.dbmi.ccd.db.repository.VariableFileRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Jul 11, 2016 2:49:27 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class DataFileService extends AbstractFileService {

    @Autowired
    public DataFileService(FileRepository fileRepository, FileTypeRepository fileTypeRepository, DataFileRepository dataFileRepository, VariableFileRepository variableFileRepository) {
        super(fileRepository, fileTypeRepository, dataFileRepository, variableFileRepository);
    }

    public DataFile findByFile(File file) {
        return dataFileRepository.findByFile(file);
    }

    public DataFile findByVariableType(VariableType variableType) {
        return dataFileRepository.findByVariableType(variableType);
    }

    public DataFile save(DataFile dataFile) {
        File file = dataFile.getFile();
        file.setFileType(fileTypeRepository.findByName(FileTypeEnum.DATASET.name()));
        file = fileRepository.save(file);

        removeNonAssociatedFileType(file);

        DataFile currentDataFile = dataFileRepository.findByFile(file);
        if (currentDataFile == null) {
            currentDataFile = dataFile;
        } else {
            currentDataFile.setFileDelimiter(dataFile.getFileDelimiter());
            currentDataFile.setNumOfColumns(dataFile.getNumOfColumns());
            currentDataFile.setNumOfRows(dataFile.getNumOfRows());
            currentDataFile.setVariableType(dataFile.getVariableType());
        }
        currentDataFile.setFile(file);

        return dataFileRepository.save(currentDataFile);
    }

}
