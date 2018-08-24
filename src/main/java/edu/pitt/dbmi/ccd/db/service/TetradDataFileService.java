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

import edu.pitt.dbmi.ccd.db.code.FileFormatCodes;
import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.FileFormat;
import edu.pitt.dbmi.ccd.db.entity.TetradDataFile;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.TetradDataFileRepository;
import edu.pitt.dbmi.ccd.db.repository.TetradVariableFileRepository;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * May 10, 2017 2:57:14 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class TetradDataFileService {

    private final TetradDataFileRepository repository;
    private final TetradVariableFileRepository tetradVariableFileRepository;
    private final FileService fileService;
    private final FileFormatService fileFormatService;
    private final VariableTypeService variableTypeService;

    @Autowired
    public TetradDataFileService(TetradDataFileRepository repository, TetradVariableFileRepository tetradVariableFileRepository, FileService fileService, FileFormatService fileFormatService, VariableTypeService variableTypeService) {
        this.repository = repository;
        this.tetradVariableFileRepository = tetradVariableFileRepository;
        this.fileService = fileService;
        this.fileFormatService = fileFormatService;
        this.variableTypeService = variableTypeService;
    }

    /**
     * Get a Tetrad tabular files grouped by variable type ids.
     *
     * @param userAccount
     * @return map where key is VariableType ID, value is a list of files
     */
    public Map<Long, List<File>> getFileGroupedByVariableTypeId(UserAccount userAccount) {
        Map<Long, List<File>> map = new HashMap<>();

        // initialize map
        variableTypeService.findAll()
                .forEach(e -> map.put(e.getId(), new LinkedList<>()));

        repository.findByUserAccount(userAccount)
                .forEach(e -> map.get(e.getVariableType().getId()).add(e.getFile()));

        return map;
    }

    private TetradDataFile saveAsNew(TetradDataFile tetradDataFile) {
        File file = tetradDataFile.getFile();

        // update file format
        FileFormat fileFormat = fileFormatService.findByCode(FileFormatCodes.TETRAD_TAB);
        file.setFileFormat(fileFormat);
        file.setTetradDataFile(null);
        file.setTetradVariableFile(null);
        file = fileService.getRepository().save(file);

        tetradDataFile.setFile(file);

        return repository.save(tetradDataFile);
    }

    @Transactional
    public TetradDataFile save(TetradDataFile tetradDataFile) {
        TetradDataFile savedDataFile;

        File file = tetradDataFile.getFile();
        FileFormat prevFileFmt = file.getFileFormat();
        if (prevFileFmt == null) {
            savedDataFile = saveAsNew(tetradDataFile);
        } else {
            switch (prevFileFmt.getCode()) {
                case FileFormatCodes.TETRAD_TAB:
                    TetradDataFile dataFile = repository.findByFile(file);
                    dataFile.setDataDelimiter(tetradDataFile.getDataDelimiter());
                    dataFile.setVariableType(tetradDataFile.getVariableType());
                    dataFile.setHasHeader(tetradDataFile.isHasHeader());
                    dataFile.setMissingMarker(tetradDataFile.getMissingMarker());
                    dataFile.setNumOfVars(tetradDataFile.getNumOfVars());
                    dataFile.setNumOfCases(tetradDataFile.getNumOfCases());
                    dataFile.setQuoteChar(tetradDataFile.getQuoteChar());
                    dataFile.setCommentMarker(tetradDataFile.getCommentMarker());

                    savedDataFile = repository.save(dataFile);
                    break;
                case FileFormatCodes.TETRAD_VAR:
                    savedDataFile = saveAsNew(tetradDataFile);
                    tetradVariableFileRepository.deleteByFile(file);
                    break;
                default:
                    savedDataFile = saveAsNew(tetradDataFile);
            }
        }

        return savedDataFile;
    }

    public TetradDataFileRepository getRepository() {
        return repository;
    }

}
