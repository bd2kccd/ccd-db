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

import edu.pitt.dbmi.ccd.db.code.AlgorithmTypeCodes;
import edu.pitt.dbmi.ccd.db.code.JobStatusCodes;
import edu.pitt.dbmi.ccd.db.entity.AlgorithmType;
import edu.pitt.dbmi.ccd.db.entity.JobDetail;
import edu.pitt.dbmi.ccd.db.entity.JobRun;
import edu.pitt.dbmi.ccd.db.entity.JobStatus;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.JobRunRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Jul 23, 2018 4:00:29 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class JobRunService {

    private final JobRunRepository repository;
    private final AlgorithmTypeService algorithmTypeService;
    private final JobStatusService jobStatusService;
    private final JobDetailService jobDetailService;

    @Autowired
    public JobRunService(JobRunRepository repository, AlgorithmTypeService algorithmTypeService, JobStatusService jobStatusService, JobDetailService jobDetailService) {
        this.repository = repository;
        this.algorithmTypeService = algorithmTypeService;
        this.jobStatusService = jobStatusService;
        this.jobDetailService = jobDetailService;
    }

    @Transactional
    public JobRun submitTetradJobRun(String name, String description, String cmdParams, UserAccount userAccount) {
        AlgorithmType algorithmType = algorithmTypeService.findByCode(AlgorithmTypeCodes.TETRAD);
        JobStatus jobStatus = jobStatusService.findByCode(JobStatusCodes.QUEUED);

        JobDetail jobDetail = new JobDetail(name, cmdParams, new Date(), userAccount, jobStatus, algorithmType);
        jobDetail.setDescription(description);
        jobDetail = jobDetailService.getRepository().save(jobDetail);

        return repository.save(new JobRun(jobDetail, userAccount));
    }

    public JobRunRepository getRepository() {
        return repository;
    }

}
