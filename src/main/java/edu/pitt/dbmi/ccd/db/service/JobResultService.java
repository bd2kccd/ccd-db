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

import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.FileFormat;
import edu.pitt.dbmi.ccd.db.entity.JobInfo;
import edu.pitt.dbmi.ccd.db.entity.JobResult;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.JobResultRepository;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Apr 25, 2018 5:58:23 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class JobResultService {

    private final JobResultRepository jobResultRepository;
    private final FileService fileService;

    @Autowired
    public JobResultService(JobResultRepository jobResultRepository, FileService fileService) {
        this.jobResultRepository = jobResultRepository;
        this.fileService = fileService;
    }

    @Transactional
    public JobResult addResultFiles(JobInfo jobInfo, List<Path> resultFiles, FileFormat fileFormat, UserAccount userAccount) {
        List<File> files = fileService.persistLocalFiles(resultFiles, fileFormat, userAccount);

        return jobResultRepository.save(new JobResult(files, jobInfo, userAccount));
    }

    public JobResultRepository getRepository() {
        return jobResultRepository;
    }

}
