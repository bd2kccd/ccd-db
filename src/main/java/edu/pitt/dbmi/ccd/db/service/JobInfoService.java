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

import edu.pitt.dbmi.ccd.db.entity.JobInfo;
import edu.pitt.dbmi.ccd.db.repository.JobInfoRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Apr 11, 2018 5:43:19 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class JobInfoService {

    private final JobInfoRepository jobInfoRepository;
    private final JobStatusService jobStatusService;

    @Autowired
    public JobInfoService(JobInfoRepository jobInfoRepository, JobStatusService jobStatusService) {
        this.jobInfoRepository = jobInfoRepository;
        this.jobStatusService = jobStatusService;
    }

    public JobInfo setStartJob(JobInfo jobInfo) {
        jobInfo.setStartTime(new Date());
        jobInfo.setJobStatus(jobStatusService.findByShortName(JobStatusService.STARTED_SHORT_NAME));

        return jobInfoRepository.save(jobInfo);
    }

    public JobInfo setEndJob(JobInfo jobInfo) {
        jobInfo.setEndTime(new Date());
        jobInfo.setJobStatus(jobStatusService.findByShortName(JobStatusService.FINISHED_SHORT_NAME));

        return jobInfoRepository.save(jobInfo);
    }

    public JobInfo setCancelJob(JobInfo jobInfo) {
        jobInfo.setEndTime(new Date());
        jobInfo.setJobStatus(jobStatusService.findByShortName(JobStatusService.CANCELLED_SHORT_NAME));

        return jobInfoRepository.save(jobInfo);
    }

    public JobInfo setTerminateJob(JobInfo jobInfo) {
        jobInfo.setEndTime(new Date());
        jobInfo.setJobStatus(jobStatusService.findByShortName(JobStatusService.TERMINATED_SHORT_NAME));

        return jobInfoRepository.save(jobInfo);
    }

    public JobInfoRepository getRepository() {
        return jobInfoRepository;
    }

}
