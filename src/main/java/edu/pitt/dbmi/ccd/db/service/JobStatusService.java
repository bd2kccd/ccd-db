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

    public static final String QUEUED_SHORT_NAME = "queued";
    public static final String STARTED_SHORT_NAME = "started";
    public static final String FINISHED_SHORT_NAME = "finished";
    public static final String CANCELED_SHORT_NAME = "canceled";
    public static final String FAILED_SHORT_NAME = "failed";

    private final JobStatusRepository jobStatusRepository;

    @Autowired
    public JobStatusService(JobStatusRepository JobStatusRepository) {
        this.jobStatusRepository = JobStatusRepository;

        // initialize database
        if (JobStatusRepository.findAll().isEmpty()) {
            JobStatusRepository.saveAll(Arrays.asList(
                    new JobStatus("Queued", QUEUED_SHORT_NAME),
                    new JobStatus("Started", STARTED_SHORT_NAME),
                    new JobStatus("Finished", FINISHED_SHORT_NAME),
                    new JobStatus("Canceled", CANCELED_SHORT_NAME),
                    new JobStatus("Terminated", FAILED_SHORT_NAME)
            ));
        }
    }

    @Cacheable("jobStatusAll")
    public List<JobStatus> findAll() {
        return jobStatusRepository.findAll();
    }

    @Cacheable("jobStatusByShortName")
    public JobStatus findByShortName(String shortName) {
        return jobStatusRepository.findByShortName(shortName);
    }

    public JobStatusRepository getRepository() {
        return jobStatusRepository;
    }

}
