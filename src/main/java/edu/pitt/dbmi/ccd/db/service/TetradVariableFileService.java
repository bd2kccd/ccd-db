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

import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.FileFormat;
import edu.pitt.dbmi.ccd.db.entity.TetradVariableFile;
import edu.pitt.dbmi.ccd.db.repository.TetradDataFileRepository;
import edu.pitt.dbmi.ccd.db.repository.TetradVariableFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * May 10, 2017 3:00:55 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class TetradVariableFileService {

    private final TetradDataFileRepository tetradDataFileRepository;
    private final TetradVariableFileRepository tetradVariableFileRepository;
    private final FileService fileService;
    private final FileFormatService fileFormatService;

    @Autowired
    public TetradVariableFileService(TetradDataFileRepository tetradDataFileRepository, TetradVariableFileRepository tetradVariableFileRepository, FileService fileService, FileFormatService fileFormatService) {
        this.tetradDataFileRepository = tetradDataFileRepository;
        this.tetradVariableFileRepository = tetradVariableFileRepository;
        this.fileService = fileService;
        this.fileFormatService = fileFormatService;
    }

    private TetradVariableFile saveAsNew(TetradVariableFile tetradVariableFile) {
        File file = tetradVariableFile.getFile();

        // update file format
        FileFormat fileFormat = fileFormatService.findById(FileFormatService.TETRAD_VAR_ID);
        file.setFileFormat(fileFormat);
        file.setTetradDataFile(null);
        file.setTetradVariableFile(null);
        file = fileService.getRepository().save(file);

        tetradVariableFile.setFile(file);

        return tetradVariableFileRepository.save(tetradVariableFile);
    }

    @Transactional
    public TetradVariableFile save(TetradVariableFile tetradVariableFile) {
        TetradVariableFile savedVarFile;

        File file = tetradVariableFile.getFile();
        FileFormat prevFileFmt = file.getFileFormat();
        if (prevFileFmt == null) {
            savedVarFile = saveAsNew(tetradVariableFile);
        } else {
            long id = prevFileFmt.getId();
            if (id == FileFormatService.TETRAD_TAB_ID) {
                savedVarFile = saveAsNew(tetradVariableFile);
                tetradDataFileRepository.deleteByFile(file);
            } else if (id == FileFormatService.TETRAD_VAR_ID) {
                TetradVariableFile varFile = tetradVariableFileRepository.findByFile(file);
                varFile.setNumOfVars(tetradVariableFile.getNumOfVars());

                savedVarFile = tetradVariableFileRepository.save(varFile);
            } else {
                savedVarFile = saveAsNew(tetradVariableFile);
            }
        }

        return savedVarFile;
    }

    public TetradVariableFileRepository getRepository() {
        return tetradVariableFileRepository;
    }

}
