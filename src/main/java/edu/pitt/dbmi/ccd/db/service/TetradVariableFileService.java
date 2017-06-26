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

import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.TetradVariableFile;
import edu.pitt.dbmi.ccd.db.repository.FileFormatRepository;
import edu.pitt.dbmi.ccd.db.repository.FileRepository;
import edu.pitt.dbmi.ccd.db.repository.TetradDataFileRepository;
import edu.pitt.dbmi.ccd.db.repository.TetradVariableFileRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * May 10, 2017 3:00:55 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class TetradVariableFileService {

    private final FileRepository fileRepository;
    private final FileFormatRepository fileFormatRepository;
    private final TetradDataFileRepository tetradDataFileRepository;
    private final TetradVariableFileRepository tetradVariableFileRepository;

    @Autowired
    public TetradVariableFileService(FileRepository fileRepository, FileFormatRepository fileFormatRepository, TetradDataFileRepository tetradDataFileRepository, TetradVariableFileRepository tetradVariableFileRepository) {
        this.fileRepository = fileRepository;
        this.fileFormatRepository = fileFormatRepository;
        this.tetradDataFileRepository = tetradDataFileRepository;
        this.tetradVariableFileRepository = tetradVariableFileRepository;
    }

    @Transactional
    public TetradVariableFile save(TetradVariableFile tetradVariableFile) {
        File file = tetradVariableFile.getFile();

        TetradVariableFile varFile = tetradVariableFileRepository.findByFile(file);
        if (varFile == null) {
            file.setFileFormat(fileFormatRepository.findByName(FileFormatService.TETRAD_VARIABLE));
            fileRepository.save(file);

            tetradDataFileRepository.deleteByFile(file);

            tetradVariableFile.setFile(file);
            varFile = tetradVariableFile;
        } else {
            varFile.setNumOfVariables(tetradVariableFile.getNumOfVariables());
        }

        return tetradVariableFileRepository.save(varFile);
    }

    public TetradVariableFileRepository getRepository() {
        return tetradVariableFileRepository;
    }

}
