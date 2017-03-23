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
import edu.pitt.dbmi.ccd.db.entity.JobQueueInfo;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.HpcParameterRepository;
import edu.pitt.dbmi.ccd.db.repository.JobQueueInfoRepository;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Aug 3, 2015 10:59:47 AM
 *
 * @author Chirayu Kong Wongchokprasitti, PhD (chw20@pitt.edu)
 *
 */
@Service
@Transactional
public class JobQueueInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobQueueInfoService.class);

    private final JobQueueInfoRepository jobQueueInfoRepository;

    private final HpcParameterRepository hpcParameterRepository;

    /**
     * @param jobQueueInfoRepository
     */
    @Autowired(required = true)
    public JobQueueInfoService(JobQueueInfoRepository jobQueueInfoRepository, HpcParameterRepository hpcParameterRepository) {
        this.jobQueueInfoRepository = jobQueueInfoRepository;
        this.hpcParameterRepository = hpcParameterRepository;
    }

    public JobQueueInfo saveJobIntoQueue(JobQueueInfo jobQueueInfo) {
        Set<HpcParameter> hpcParameters = jobQueueInfo.getHpcParameters();
        if (hpcParameters != null && !hpcParameters.isEmpty()) {
            hpcParameters.forEach(param -> {
                hpcParameterRepository.save(param);
            });
        }
        return jobQueueInfoRepository.save(jobQueueInfo);
    }

    public List<JobQueueInfo> saveAll(List<JobQueueInfo> jobQueueInfos) {
        return jobQueueInfoRepository.save(jobQueueInfos);
    }

    public int getNumRunningJobs() {
        return jobQueueInfoRepository.findByStatus(1).size();
    }

    public JobQueueInfo findByPid(Long pid) {
        return jobQueueInfoRepository.findByPid(pid);
    }

    public List<JobQueueInfo> findByStatus(int status) {
        return jobQueueInfoRepository.findByStatus(status);
    }

    public JobQueueInfo findFirstJobInQueue() {
        return jobQueueInfoRepository.findByStatus(0).get(0);
    }

    public List<JobQueueInfo> findAll() {
        return jobQueueInfoRepository.findAll();
    }

    public boolean deleteJobInQueue(JobQueueInfo jobQueueInfo) {
        try {
            jobQueueInfoRepository.delete(jobQueueInfo);
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage());

            return false;
        }

        return true;
    }

    public boolean deleteJobById(Long id) {
        try {
            jobQueueInfoRepository.delete(id);
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage());

            return false;
        }

        return true;
    }

    public JobQueueInfo findOne(Long id) {
        return jobQueueInfoRepository.findOne(id);
    }

    public JobQueueInfo findByIdAndUseraccount(Long id, UserAccount userAccount) {
        return jobQueueInfoRepository.findByIdAndUserAccounts(id, Collections.singleton(userAccount));
    }

    public List<JobQueueInfo> findByUserAccounts(Set<UserAccount> userAccounts) {
        return jobQueueInfoRepository.findByUserAccounts(userAccounts);
    }

}
