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

import edu.pitt.dbmi.ccd.db.entity.FileDelimiterType;
import edu.pitt.dbmi.ccd.db.repository.FileDelimiterTypeRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * Apr 27, 2017 4:35:26 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class FileDelimiterTypeService {

    public static final String TAB_DELIM_NAME = "tab";
    public static final String SPACE_DELIM_NAME = "space";
    public static final String WHITESPACE_DELIM_NAME = "whitespace";
    public static final String COMMA_DELIM_NAME = "comma";
    public static final String COLON_DELIM_NAME = "colon";
    public static final String SEMICOLON_DELIM_NAME = "semicolon";
    public static final String PIPE_DELIM_NAME = "pipe";

    private final FileDelimiterTypeRepository fileDelimiterTypeRepository;

    @Autowired
    public FileDelimiterTypeService(FileDelimiterTypeRepository fileDelimiterTypeRepository) {
        this.fileDelimiterTypeRepository = fileDelimiterTypeRepository;

        if (fileDelimiterTypeRepository.findAll().isEmpty()) {
            fileDelimiterTypeRepository.save(Arrays.asList(
                    new FileDelimiterType(TAB_DELIM_NAME, "Tab"),
                    new FileDelimiterType(SPACE_DELIM_NAME, "Space"),
                    new FileDelimiterType(WHITESPACE_DELIM_NAME, "Whitespace"),
                    new FileDelimiterType(COMMA_DELIM_NAME, "Comma"),
                    new FileDelimiterType(COLON_DELIM_NAME, "Colon"),
                    new FileDelimiterType(SEMICOLON_DELIM_NAME, "Semicolon"),
                    new FileDelimiterType(PIPE_DELIM_NAME, "Pipe")
            ));
        }
    }

    @Cacheable("fileDelimiterAll")
    public List<FileDelimiterType> findAll() {
        return fileDelimiterTypeRepository.findAll();
    }

    @Cacheable("fileDelimiterTypeByName")
    public FileDelimiterType findByName(String name) {
        return fileDelimiterTypeRepository.findByName(name);
    }

    public FileDelimiterTypeRepository getRepository() {
        return fileDelimiterTypeRepository;
    }

}
