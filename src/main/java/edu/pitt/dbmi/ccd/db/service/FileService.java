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
import edu.pitt.dbmi.ccd.db.entity.VariableFile;
import edu.pitt.dbmi.ccd.db.repository.DataFileRepository;
import edu.pitt.dbmi.ccd.db.repository.FileRepository;
import edu.pitt.dbmi.ccd.db.repository.FileTypeRepository;
import edu.pitt.dbmi.ccd.db.repository.VariableFileRepository;
import java.util.LinkedList;
import java.util.List;
import javax.transaction.Transactional;
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
public class FileService extends AbstractFileService {

    @Autowired
    public FileService(FileRepository fileRepository, FileTypeRepository fileTypeRepository, DataFileRepository dataFileRepository, VariableFileRepository variableFileRepository) {
        super(fileRepository, fileTypeRepository, dataFileRepository, variableFileRepository);
    }

    public Long countByUserAccount(UserAccount userAccount) {
        return fileRepository.countByUserAccount(userAccount);
    }

    public Long countByFileTypeAndUserAccount(FileType fileType, UserAccount userAccount) {
        return fileRepository.countByFileTypeAndUserAccount(fileType, userAccount);
    }

    public Long countUntypedFileByUserAccount(UserAccount userAccount) {
        return fileRepository.countUntypedFileByUserAccount(userAccount);
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

    public File findByAbsolutePathAndName(String absolutePath, String name) {
        return fileRepository.findByAbsolutePathAndName(absolutePath, name);
    }

    public File findByIdAndUserAccount(Long id, UserAccount userAccount) {
        return fileRepository.findByIdAndUserAccount(id, userAccount);
    }

    public List<File> findByUserAccount(UserAccount userAccount) {
        return fileRepository.findByUserAccount(userAccount);
    }

    public List<File> findUntypedFileByUserAccount(UserAccount userAccount) {
        return fileRepository.findUntypedFileByUserAccount(userAccount);
    }

    public List<File> findByIdsAndUserAccount(List<Long> ids, UserAccount userAccount) {
        return fileRepository.findByIdsAndUserAccount(ids, userAccount);
    }

    public List<File> findByFileTypeAndUserAccount(FileType fileType, UserAccount userAccount) {
        return fileRepository.findByFileTypeAndUserAccount(fileType, userAccount);
    }

    public void delete(Long id) {
        File file = fileRepository.findOne(id);
        if (file != null) {
            delete(file);
        }
    }

    public void delete(File file) {
        deleteAssociatedFiles(file);
        fileRepository.delete(file);
    }

    public void delete(List<File> files) {
        deleteAssociatedFiles(files);
        fileRepository.delete(files);
    }

    public File changeFileType(File file, FileType fileType) {
        file.setFileType(fileType);
        file = fileRepository.save(file);

        removeNonAssociatedFileType(file);

        return file;
    }

    public void deleteAssociatedFiles(File file) {
        DataFile dataFile = dataFileRepository.findByFile(file);
        if (dataFile != null) {
            dataFileRepository.delete(dataFile);
        }

        VariableFile variableFile = variableFileRepository.findByFile(file);
        if (variableFile != null) {
            variableFileRepository.delete(variableFile);
        }
    }

    public void deleteAssociatedFiles(List<File> files) {
        deleteAssociatedVarFiles(files);
        deleteAssociatedDataFiles(files);
    }

    public void deleteAssociatedVarFiles(List<File> files) {
        List<VariableFile> fileList = new LinkedList<>();
        files.forEach(file -> {
            VariableFile variableFile = variableFileRepository.findByFile(file);
            if (variableFile != null) {
                fileList.add(variableFile);
            }
        });
        if (!fileList.isEmpty()) {
            variableFileRepository.delete(fileList);
        }
    }

    public void deleteAssociatedDataFiles(List<File> files) {
        List<DataFile> fileList = new LinkedList<>();
        files.forEach(file -> {
            DataFile dataFile = dataFileRepository.findByFile(file);
            if (dataFile != null) {
                fileList.add(dataFile);
            }
        });
        if (!fileList.isEmpty()) {
            dataFileRepository.delete(fileList);
        }
    }

}
