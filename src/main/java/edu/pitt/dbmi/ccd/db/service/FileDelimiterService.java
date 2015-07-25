/*
 * Copyright (C) 2015 University of Pittsburgh.
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

import edu.pitt.dbmi.ccd.db.entity.FileDelimiter;
import edu.pitt.dbmi.ccd.db.repository.FileDelimiterRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Jul 24, 2015 1:26:38 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class FileDelimiterService {

    private final FileDelimiterRepository fileDelimiterRepository;

    @Autowired(required = true)
    public FileDelimiterService(FileDelimiterRepository fileDelimiterRepository) {
        this.fileDelimiterRepository = fileDelimiterRepository;

        List<FileDelimiter> delimiters = fileDelimiterRepository.findAll();
        if (delimiters.isEmpty()) {
            delimiters.add(new FileDelimiter("tab", "\t"));
            delimiters.add(new FileDelimiter("comma", ","));

            fileDelimiterRepository.save(delimiters);
        }
    }

    public List<FileDelimiter> findAll() {
        List<FileDelimiter> delimiters = fileDelimiterRepository.findAll();

        return delimiters;
    }

    public FileDelimiterRepository getFileDelimiterRepository() {
        return fileDelimiterRepository;
    }

}
