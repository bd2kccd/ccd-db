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
import java.util.Optional;
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

    public static final Long QUEUED_ID = 1L;
    public static final Long STARTED_ID = 2L;
    public static final Long FINISHED_ID = 3L;
    public static final Long CANCELED_ID = 4L;
    public static final Long FAILED_ID = 5L;

    private final JobStatusRepository jobStatusRepository;

    @Autowired
    public JobStatusService(JobStatusRepository JobStatusRepository) {
        this.jobStatusRepository = JobStatusRepository;

        // initialize database
        if (JobStatusRepository.findAll().isEmpty()) {
            JobStatusRepository.saveAll(Arrays.asList(
                    new JobStatus(QUEUED_ID, "Queued"),
                    new JobStatus(STARTED_ID, "Started"),
                    new JobStatus(FINISHED_ID, "Finished"),
                    new JobStatus(CANCELED_ID, "Cancel"),
                    new JobStatus(FAILED_ID, "Failed")
            ));
        }
    }

    @Cacheable("JobStatusById")
    public JobStatus findById(Long id) {
        Optional<JobStatus> opt = jobStatusRepository.findById(id);

        return opt.isPresent() ? opt.get() : null;
    }

    public JobStatusRepository getRepository() {
        return jobStatusRepository;
    }

}
