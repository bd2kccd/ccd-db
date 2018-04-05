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
        FileFormat fileFormat = fileFormatService.findByShortName(FileFormatService.TETRAD_VAR_SHORT_NAME);
        file.setFileFormat(fileFormat);
        file = fileService.getRepository().save(file);

        tetradVariableFile.setFile(file);

        return tetradVariableFileRepository.save(tetradVariableFile);
    }

    @Transactional
    public TetradVariableFile save(TetradVariableFile tetradVariableFile) {
        File file = tetradVariableFile.getFile();
        FileFormat prevFileFormat = file.getFileFormat();
        if (prevFileFormat == null) {
            return saveAsNew(tetradVariableFile);
        } else {
            switch (prevFileFormat.getShortName()) {
                case FileFormatService.TETRAD_TAB_SHORT_NAME:
                    tetradDataFileRepository.deleteByFile(file);

                    return saveAsNew(tetradVariableFile);
                case FileFormatService.TETRAD_VAR_SHORT_NAME:
                    TetradVariableFile varFile = tetradVariableFileRepository.findByFile(file);
                    varFile.setNumOfVars(tetradVariableFile.getNumOfVars());

                    return tetradVariableFileRepository.save(varFile);
                default:
                    return saveAsNew(tetradVariableFile);
            }
        }
    }

    public TetradVariableFileRepository getRepository() {
        return tetradVariableFileRepository;
    }

}
