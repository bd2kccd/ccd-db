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

import edu.pitt.dbmi.ccd.db.entity.HpcParameter;
import edu.pitt.dbmi.ccd.db.repository.HpcParameterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Feb 20, 2017 6:26:57 PM
 *
 * @author Chirayu Kong Wongchokprasitti, PhD (chw20@pitt.edu)
 *
 */
@Service
@Transactional
public class HpcParameterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HpcParameterService.class);

    private final HpcParameterRepository hpcParameterRepository;

    @Autowired(required = true)
    public HpcParameterService(HpcParameterRepository hpcParameterRepository) {
        this.hpcParameterRepository = hpcParameterRepository;
    }

    public HpcParameter findById(Long id) {
        return hpcParameterRepository.findById(id);
    }

    public void save(HpcParameter hpcParamter) {
        hpcParameterRepository.save(hpcParamter);
    }

}
