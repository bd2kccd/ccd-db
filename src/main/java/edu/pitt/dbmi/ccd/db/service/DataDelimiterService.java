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

    public static final String TAB_DELIM_SHORT_NAME = "tab";
    public static final String SPACE_DELIM_SHORT_NAME = "space";
    public static final String WHITESPACE_DELIM_SHORT_NAME = "whitespace";
    public static final String COMMA_DELIM_SHORT_NAME = "comma";
    public static final String COLON_DELIM_SHORT_NAME = "colon";
    public static final String SEMICOLON_DELIM_SHORT_NAME = "semicolon";
    public static final String PIPE_DELIM_SHORT_NAME = "pipe";

    private final DataDelimiterRepository dataDelimiterRepository;

    @Autowired
    public DataDelimiterService(DataDelimiterRepository dataDelimiterRepository) {
        this.dataDelimiterRepository = dataDelimiterRepository;

        // initialize database
        if (dataDelimiterRepository.findAll().isEmpty()) {
            dataDelimiterRepository.save(Arrays.asList(
                    new DataDelimiter("Tab", TAB_DELIM_SHORT_NAME),
                    new DataDelimiter("Space", SPACE_DELIM_SHORT_NAME),
                    new DataDelimiter("Whitespace", WHITESPACE_DELIM_SHORT_NAME),
                    new DataDelimiter("Comma", COMMA_DELIM_SHORT_NAME),
                    new DataDelimiter("Colon", COLON_DELIM_SHORT_NAME),
                    new DataDelimiter("Semicolon", SEMICOLON_DELIM_SHORT_NAME),
                    new DataDelimiter("Pipe", PIPE_DELIM_SHORT_NAME)
            ));
        }
    }

    @Cacheable("dataDelimiterAll")
    public List<DataDelimiter> findAll() {
        return dataDelimiterRepository.findAll();
    }

    @Cacheable("dataDelimiterById")
    public DataDelimiter findById(Long id) {
        return dataDelimiterRepository.findOne(id);
    }

    @Cacheable("dataDelimiterByShortName")
    public DataDelimiter findByShortName(String shortName) {
        return dataDelimiterRepository.findByShortName(shortName);
    }

    public DataDelimiterRepository getRepository() {
        return dataDelimiterRepository;
    }

}
