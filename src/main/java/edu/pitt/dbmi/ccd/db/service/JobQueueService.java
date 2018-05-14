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

import edu.pitt.dbmi.ccd.db.JobQueueException;
import edu.pitt.dbmi.ccd.db.entity.AlgorithmType;
import edu.pitt.dbmi.ccd.db.entity.JobInfo;
import edu.pitt.dbmi.ccd.db.entity.JobLocation;
import edu.pitt.dbmi.ccd.db.entity.JobQueue;
import edu.pitt.dbmi.ccd.db.entity.JobStatus;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.JobQueueRepository;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Apr 11, 2018 2:39:49 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class JobQueueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobQueueService.class);

    private final JobQueueRepository jobQueueRepository;
    private final JobLocationService jobLocationService;
    private final JobStatusService jobStatusService;
    private final JobInfoService jobInfoService;
    private final AlgorithmTypeService algorithmTypeService;

    @Autowired
    public JobQueueService(JobQueueRepository jobQueueRepository, JobLocationService jobLocationService, JobStatusService jobStatusService, JobInfoService jobInfoService, AlgorithmTypeService algorithmTypeService) {
        this.jobQueueRepository = jobQueueRepository;
        this.jobLocationService = jobLocationService;
        this.jobStatusService = jobStatusService;
        this.jobInfoService = jobInfoService;
        this.algorithmTypeService = algorithmTypeService;
    }

    public JobQueue setPID(Long pid, JobQueue jobQueue) {
        jobQueue.setPid(pid);

        return jobQueueRepository.save(jobQueue);
    }

    public void setStatusStarted(JobQueue jobQueue) {
        JobInfo jobInfo = jobQueue.getJobInfo();
        jobInfo.setStartTime(new Date());
        jobInfo.setJobStatus(jobStatusService.findById(JobStatusService.STARTED_ID));

        jobQueue.setJobInfo(jobInfoService.getRepository().save(jobInfo));
    }

    public void setStatusFinished(JobQueue jobQueue) {
        JobInfo jobInfo = jobQueue.getJobInfo();
        jobInfo.setEndTime(new Date());
        jobInfo.setJobStatus(jobStatusService.findById(JobStatusService.FINISHED_ID));

        jobQueue.setJobInfo(jobInfoService.getRepository().save(jobInfo));
    }

    public void setStatusCanceled(JobQueue jobQueue) throws JobQueueException {
        if (jobQueue == null) {
            throw new JobQueueException("Job no longer exists in queue.");
        }

        JobInfo jobInfo = jobQueue.getJobInfo();
        long statusId = jobInfo.getJobStatus().getId();
        if (statusId != JobStatusService.CANCELED_ID) {
            if (statusId != JobStatusService.STARTED_ID
                    || statusId != JobStatusService.QUEUED_ID) {
                jobInfo.setEndTime(new Date());
                jobInfo.setJobStatus(jobStatusService.findById(JobStatusService.CANCELED_ID));
                jobInfo = jobInfoService.getRepository().save(jobInfo);

                jobQueue.setJobInfo(jobInfo);
            } else {
                throw new JobQueueException("Unable to cancel job.  Job has completed.");
            }
        }
    }

    public void setStatusFailed(JobQueue jobQueue) {
        JobInfo jobInfo = jobQueue.getJobInfo();
        jobInfo.setEndTime(new Date());
        jobInfo.setJobStatus(jobStatusService.findById(JobStatusService.FAILED_ID));

        jobQueue.setJobInfo(jobInfoService.getRepository().save(jobInfo));
    }

    @Transactional
    public JobQueue submitLocalTetradJob(String name, Long datasetId, boolean isSingleFile, String parameter, UserAccount userAccount) {
        JobLocation location = jobLocationService.findById(JobLocationService.LOCAL_ID);
        JobStatus status = jobStatusService.findById(JobStatusService.QUEUED_ID);
        AlgorithmType algoType = algorithmTypeService.findById(AlgorithmTypeService.TETRAD_ID);

        JobInfo jobInfo = new JobInfo(name, name, parameter, datasetId, isSingleFile, new Date(), algoType, location, status, userAccount);
        jobInfo.setAlgoParam(parameter);
        jobInfo = jobInfoService.getRepository().save(jobInfo);

        return jobQueueRepository.save(new JobQueue(jobInfo, userAccount));
    }

    public JobQueueRepository getRepository() {
        return jobQueueRepository;
    }

}
