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
package edu.pitt.dbmi.ccd.db.repository;

import edu.pitt.dbmi.ccd.db.domain.job.JobQueueListItem;
import edu.pitt.dbmi.ccd.db.entity.JobQueue;
import edu.pitt.dbmi.ccd.db.entity.JobStatus;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * Apr 11, 2018 2:10:00 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Repository
public interface JobQueueRepository extends JpaRepository<JobQueue, Long> {

    public List<JobQueue> findByUserAccount(UserAccount userAccount);

    @Query("SELECT jq FROM JobQueue jq WHERE jq.jobInfo.jobStatus = ?1")
    public List<JobQueue> findByJobStatus(JobStatus jobStatus);

    public JobQueue findByIdAndUserAccount(Long id, UserAccount userAccount);

    @Query("SELECT new edu.pitt.dbmi.ccd.db.domain.job.JobQueueListItem(jq.id, jq.jobInfo.name, jq.jobInfo.creationTime, jq.jobInfo.jobStatus.name, jq.jobInfo.jobLocation.name) "
            + "FROM JobQueue jq "
            + "WHERE jq.userAccount = ?1")
    public List<JobQueueListItem> getJobQueueListItems(UserAccount userAccount);

    public boolean existsByIdAndUserAccount(Long id, UserAccount userAccount);

}
