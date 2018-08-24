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

import edu.pitt.dbmi.ccd.db.code.JobStatusCodes;
import edu.pitt.dbmi.ccd.db.entity.JobStatus;
import edu.pitt.dbmi.ccd.db.repository.JobStatusRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * Apr 11, 2018 2:12:04 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class JobStatusService {

    private final JobStatusRepository repository;

    @Autowired
    public JobStatusService(JobStatusRepository repository) {
        this.repository = repository;

        // initialize database
        if (repository.findAll().isEmpty()) {
            repository.saveAll(Arrays.asList(
                    new JobStatus("Canceled", JobStatusCodes.CANCELED),
                    new JobStatus("Failed", JobStatusCodes.FAILED),
                    new JobStatus("Finished", JobStatusCodes.FINISHED),
                    new JobStatus("Queued", JobStatusCodes.QUEUED),
                    new JobStatus("Started", JobStatusCodes.STARTED)
            ));
        }
    }

    @Cacheable("JobStatusByCode")
    public JobStatus findByCode(short code) {
        return repository.findByCode(code);
    }

    @Cacheable("JobStatusAll")
    public List<JobStatus> findAll() {
        return repository.findAll();
    }

    public JobStatusRepository getRepository() {
        return repository;
    }

}
