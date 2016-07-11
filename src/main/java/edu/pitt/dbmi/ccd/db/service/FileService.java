/*
 * Copyright (C) 2016 University of Pittsburgh.
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
import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.FileType;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.DataFileRepository;
import edu.pitt.dbmi.ccd.db.repository.FileRepository;
import edu.pitt.dbmi.ccd.db.repository.FileTypeRepository;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Jul 5, 2016 1:37:29 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    private final FileRepository fileRepository;

    private final FileTypeRepository fileTypeRepository;

    private final DataFileRepository dataFileRepository;

    @Autowired
    public FileService(FileRepository fileRepository, FileTypeRepository fileTypeRepository, DataFileRepository dataFileRepository) {
        this.fileRepository = fileRepository;
        this.fileTypeRepository = fileTypeRepository;
        this.dataFileRepository = dataFileRepository;
    }

    public File save(File file) {
        FileType fileType = file.getFileType();
        if (fileType != null) {
            fileTypeRepository.save(fileType);
        }

        return fileRepository.save(file);
    }

    public List<File> save(List<File> files) {
        if (files == null || files.isEmpty()) {
            return new LinkedList<>();
        }

        return fileRepository.save(files);
    }

    public File findById(Long id) {
        return fileRepository.findOne(id);
    }

    public List<File> findByUserAccounts(Set<UserAccount> userAccounts) {
        return fileRepository.findByUserAccounts(userAccounts);
    }

    public File findByIdAndUserAccounts(Long id, Set<UserAccount> userAccounts) {
        return fileRepository.findByIdAndUserAccounts(id, userAccounts);
    }

    public void delete(Long id) {
        File file = fileRepository.findOne(id);
        if (file != null) {
            delete(file);
        }
    }

    public void delete(File file) {
        DataFile dataFile = dataFileRepository.findByFile(file);
        if (dataFile != null) {
            dataFileRepository.delete(dataFile);
        }

        fileRepository.delete(file);
    }

    public void delete(List<File> files) {
        List<DataFile> dataFiles = new LinkedList<>();
        files.forEach(file -> {
            DataFile dataFile = dataFileRepository.findByFile(file);
            if (dataFile != null) {
                dataFiles.add(dataFile);
            }
        });
        if (!dataFiles.isEmpty()) {
            dataFileRepository.delete(dataFiles);
        }

        fileRepository.delete(files);
    }

}
