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

import edu.pitt.dbmi.ccd.db.repository.FileRepository;
import edu.pitt.dbmi.ccd.db.repository.TetradDataFileRepository;
import edu.pitt.dbmi.ccd.db.repository.TetradVariableFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Jan 28, 2018 7:30:10 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    private final FileRepository fileRepository;
    private final TetradDataFileRepository tetradDataFileRepository;
    private final TetradVariableFileRepository tetradVariableFileRepository;
    private final FileFormatService fileFormatService;
    private final AlgorithmTypeService algorithmTypeService;

    @Autowired
    public FileService(
            FileRepository fileRepository,
            TetradDataFileRepository tetradDataFileRepository,
            TetradVariableFileRepository tetradVariableFileRepository,
            FileFormatService fileFormatService,
            AlgorithmTypeService algorithmTypeService) {
        this.fileRepository = fileRepository;
        this.tetradDataFileRepository = tetradDataFileRepository;
        this.tetradVariableFileRepository = tetradVariableFileRepository;
        this.fileFormatService = fileFormatService;
        this.algorithmTypeService = algorithmTypeService;
    }

    public FileRepository getRepository() {
        return fileRepository;
    }

}
