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
import edu.pitt.dbmi.ccd.db.entity.FileType;
import edu.pitt.dbmi.ccd.db.entity.VariableFile;
import edu.pitt.dbmi.ccd.db.repository.DataFileRepository;
import edu.pitt.dbmi.ccd.db.repository.FileRepository;
import edu.pitt.dbmi.ccd.db.repository.FileTypeRepository;
import edu.pitt.dbmi.ccd.db.repository.VariableFileRepository;

/**
 *
 * Jul 18, 2016 5:28:59 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public abstract class AbstractFileService {

    protected final FileRepository fileRepository;
    protected final FileTypeRepository fileTypeRepository;
    protected final DataFileRepository dataFileRepository;
    protected final VariableFileRepository variableFileRepository;

    public AbstractFileService(FileRepository fileRepository, FileTypeRepository fileTypeRepository, DataFileRepository dataFileRepository, VariableFileRepository variableFileRepository) {
        this.fileRepository = fileRepository;
        this.fileTypeRepository = fileTypeRepository;
        this.dataFileRepository = dataFileRepository;
        this.variableFileRepository = variableFileRepository;
    }

    protected void removeNonAssociatedFileType(File file) {
        FileType fileType = file.getFileType();
        if (fileType != null) {
            FileTypeEnum fileTypeEnum = FileTypeEnum.valueOf(fileType.getName());
            switch (fileTypeEnum) {
                case DATASET:
                    deleteVariableFile(file);
                    break;
                case ALGORITHM_RESULT:
                    deleteVariableFile(file);
                    deleteDataFile(file);
                    break;
                case ALGORITHM_RESULT_COMPARISON:
                    deleteVariableFile(file);
                    deleteDataFile(file);
                    break;
                case PRIOR_KNOWLEDGE:
                    deleteVariableFile(file);
                    deleteDataFile(file);
                    break;
                case VARIABLE:
                    deleteDataFile(file);
                    break;
            }
        }
    }

    protected void deleteVariableFile(File file) {
        VariableFile variableFile = variableFileRepository.findByFile(file);
        if (variableFile != null) {
            variableFileRepository.delete(variableFile);
        }
    }

    protected void deleteDataFile(File file) {
        DataFile dataFile = dataFileRepository.findByFile(file);
        if (dataFile != null) {
            dataFileRepository.delete(dataFile);
        }
    }

}