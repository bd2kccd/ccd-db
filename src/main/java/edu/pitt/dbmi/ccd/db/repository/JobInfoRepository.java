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

import edu.pitt.dbmi.ccd.db.domain.job.JobInfoListItem;
import edu.pitt.dbmi.ccd.db.entity.JobInfo;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * Apr 11, 2018 5:42:12 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Repository
public interface JobInfoRepository extends JpaRepository<JobInfo, Long> {

    public List<JobInfo> findByUserAccount(UserAccount userAccount);

    @Query("SELECT new edu.pitt.dbmi.ccd.db.domain.job.JobInfoListItem(ji.id, ji.name, ji.algorithmType.name, ji.creationTime, ji.jobStatus.name) "
            + "FROM JobInfo ji "
            + "WHERE ji.userAccount = ?1")
    public List<JobInfoListItem> getJobInfoListItems(UserAccount userAccount);

    public JobInfo findByIdAndUserAccount(Long id, UserAccount userAccount);

}
