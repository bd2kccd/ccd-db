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

import edu.pitt.dbmi.ccd.db.entity.JobLocation;
import edu.pitt.dbmi.ccd.db.repository.JobLocationRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * Apr 11, 2018 2:30:49 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class JobLocationService {

    public static final Long LOCAL_ID = 1L;
    public static final Long PSC_BRIDGES_ID = 2L;
    public static final Long AWS_EC2_ID = 3L;

    private final JobLocationRepository jobLocationRepository;

    @Autowired
    public JobLocationService(JobLocationRepository jobLocationRepository) {
        this.jobLocationRepository = jobLocationRepository;

        // initialize database
        if (jobLocationRepository.findAll().isEmpty()) {
            jobLocationRepository.saveAll(Arrays.asList(
                    new JobLocation(LOCAL_ID, "Local"),
                    new JobLocation(PSC_BRIDGES_ID, "PSC Bridges"),
                    new JobLocation(AWS_EC2_ID, "AWS EC2")
            ));
        }
    }

    @Cacheable("JobLocationById")
    public JobLocation findById(Long id) {
        Optional<JobLocation> opt = jobLocationRepository.findById(id);

        return opt.isPresent() ? opt.get() : null;
    }

    @Cacheable("JobLocationAll")
    public List<JobLocation> findAll() {
        return jobLocationRepository.findAll();
    }

    public JobLocationRepository getRepository() {
        return jobLocationRepository;
    }

}
