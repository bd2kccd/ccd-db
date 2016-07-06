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

import edu.pitt.dbmi.ccd.db.entity.DataFile;
import edu.pitt.dbmi.ccd.db.entity.DataFileInfo;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.DataFileInfoRepository;
import edu.pitt.dbmi.ccd.db.repository.DataFileRepository;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Jul 24, 2015 1:28:47 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class DataFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataFileService.class);

    private final DataFileRepository dataFileRepository;

    private final DataFileInfoRepository dataFileInfoRepository;

    @Autowired(required = true)
    public DataFileService(
            DataFileRepository dataFileRepository,
            DataFileInfoRepository dataFileInfoRepository) {
        this.dataFileRepository = dataFileRepository;
        this.dataFileInfoRepository = dataFileInfoRepository;
    }

    public DataFile findByName(String name) {
        return dataFileRepository.findByName(name);
    }

    public List<DataFile> findByAbsolutePath(String absolutePath) {
        return dataFileRepository.findByAbsolutePath(absolutePath);
    }

    public List<DataFile> findByUserAccounts(Set<UserAccount> userAccounts) {
        return dataFileRepository.findByUserAccounts(userAccounts);
    }

    public DataFile findByIdAndUserAccount(Long id, UserAccount userAccount) {
        return dataFileRepository.findByIdAndUserAccounts(id, Collections.singleton(userAccount));
    }

    public List<DataFile> findByUserAccount(UserAccount userAccount) {
        return dataFileRepository.findByUserAccounts(Collections.singleton(userAccount));
    }

    public DataFile findByAbsolutePathAndName(String absolutePath, String name) {
        return dataFileRepository.findByAbsolutePathAndName(absolutePath, name);
    }

    public DataFile saveDataFile(DataFile dataFile) {
        DataFileInfo dataFileInfo = dataFile.getDataFileInfo();
        if (dataFileInfo != null) {
            dataFileInfoRepository.save(dataFileInfo);
        }

        return dataFileRepository.save(dataFile);
    }

    public List<DataFile> saveDataFile(List<DataFile> dataFiles) {
        List<DataFileInfo> dataFileInfos = new LinkedList<>();
        dataFiles.forEach(dataFile -> {
            DataFileInfo dataFileInfo = dataFile.getDataFileInfo();
            if (dataFileInfo != null) {
                dataFileInfos.add(dataFileInfo);
            }
        });
        dataFileInfoRepository.save(dataFileInfos);

        return dataFileRepository.save(dataFiles);
    }

    public boolean deleteDataFileByNameAndAbsolutePath(String absolutePath, String name) {
        try {
            DataFile dataFile = dataFileRepository.findByAbsolutePathAndName(absolutePath, name);
            DataFileInfo dataFileInfo = dataFile.getDataFileInfo();
            if (dataFileInfo != null) {
                dataFileInfoRepository.delete(dataFileInfo);
            }

            dataFileRepository.delete(dataFile);
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage());

            return false;
        }

        return true;
    }

    public boolean deleteDataFile(List<DataFile> dataFiles) {
        if (dataFiles == null) {
            return false;
        }

        try {
            // remove data file info first
            List<DataFileInfo> dataFileInfos = new LinkedList<>();
            dataFiles.forEach(dataFile -> {
                DataFileInfo dataFileInfo = dataFile.getDataFileInfo();
                if (dataFileInfo != null) {
                    dataFileInfos.add(dataFile.getDataFileInfo());
                }
            });
            dataFileInfoRepository.delete(dataFileInfos);

            dataFileRepository.delete(dataFiles);
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage());

            return false;
        }

        return true;
    }

    public boolean deleteDataFile(DataFile dataFile) {
        try {
            DataFileInfo dataFileInfo = dataFile.getDataFileInfo();
            if (dataFileInfo != null) {
                dataFileInfoRepository.delete(dataFileInfo);
            }

            dataFileRepository.delete(dataFile);
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage());
            return false;
        }

        return true;
    }

}
