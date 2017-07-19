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

import edu.pitt.dbmi.ccd.db.entity.Algorithm;
import edu.pitt.dbmi.ccd.db.repository.AlgorithmRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * Jul 19, 2017 1:44:12 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class AlgorithmService {

    public static final String FGES_CONTINUOUS_NAME = "fgesc";
    public static final String FGES_DISCRETE_NAME = "fgesd";
    public static final String FGES_MIXED_NAME = "fgesm";

    public static final String GFCI_CONTINUOUS_NAME = "gfcic";
    public static final String GFCI_DISCRETE_NAME = "gfcid";
    public static final String GFCI_MIXED_NAME = "gfcim";

    private final AlgorithmRepository algorithmRepository;

    @Autowired
    public AlgorithmService(AlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;
    }

    @Cacheable("algorithmAll")
    public List<Algorithm> findAll() {
        return algorithmRepository.findAll();
    }

    @Cacheable("algorithmByName")
    public Algorithm findByName(String name) {
        return algorithmRepository.findByName(name);
    }

    public AlgorithmRepository getRepository() {
        return algorithmRepository;
    }

}
