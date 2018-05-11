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

import edu.pitt.dbmi.ccd.db.entity.DataDelimiter;
import edu.pitt.dbmi.ccd.db.repository.DataDelimiterRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * Feb 9, 2018 6:30:17 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class DataDelimiterService {

    public static final Long TAB_DELIM_ID = 1L;
    public static final Long SPACE_DELIM_ID = 2L;
    public static final Long WHITESPACE_DELIM_ID = 3L;
    public static final Long COMMA_DELIM_ID = 4L;
    public static final Long COLON_DELIM_ID = 5L;
    public static final Long SEMICOLON_DELIM_ID = 6L;
    public static final Long PIPE_DELIM_ID = 7L;

    private final DataDelimiterRepository dataDelimiterRepository;

    @Autowired
    public DataDelimiterService(DataDelimiterRepository dataDelimiterRepository) {
        this.dataDelimiterRepository = dataDelimiterRepository;

        // initialize database
        if (dataDelimiterRepository.findAll().isEmpty()) {
            dataDelimiterRepository.saveAll(Arrays.asList(
                    new DataDelimiter(TAB_DELIM_ID, "Tab"),
                    new DataDelimiter(SPACE_DELIM_ID, "Space"),
                    new DataDelimiter(WHITESPACE_DELIM_ID, "Whitespace"),
                    new DataDelimiter(COMMA_DELIM_ID, "Comma"),
                    new DataDelimiter(COLON_DELIM_ID, "Colon"),
                    new DataDelimiter(SEMICOLON_DELIM_ID, "Semicolon"),
                    new DataDelimiter(PIPE_DELIM_ID, "Pipe")
            ));
        }
    }

    @Cacheable("DataDelimiterById")
    public DataDelimiter findById(Long id) {
        Optional<DataDelimiter> opt = dataDelimiterRepository.findById(id);

        return opt.isPresent() ? opt.get() : null;
    }

    @Cacheable("DataDelimiterAll")
    public List<DataDelimiter> findAll() {
        return dataDelimiterRepository.findAll();
    }

    public DataDelimiterRepository getRepository() {
        return dataDelimiterRepository;
    }

}
