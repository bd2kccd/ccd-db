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

import edu.pitt.dbmi.ccd.db.code.FileFormatCodes;
import edu.pitt.dbmi.ccd.db.domain.file.FileListItem;
import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.FileFormat;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.FileRepository;
import edu.pitt.dbmi.ccd.db.repository.TetradDataFileRepository;
import edu.pitt.dbmi.ccd.db.repository.TetradVariableFileRepository;
import edu.pitt.dbmi.ccd.db.util.FileUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Jan 28, 2018 7:30:10 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    private final FileRepository repository;
    private final TetradDataFileRepository tetradDataFileRepository;
    private final TetradVariableFileRepository tetradVariableFileRepository;

    @Autowired
    public FileService(FileRepository repository, TetradDataFileRepository tetradDataFileRepository, TetradVariableFileRepository tetradVariableFileRepository) {
        this.repository = repository;
        this.tetradDataFileRepository = tetradDataFileRepository;
        this.tetradVariableFileRepository = tetradVariableFileRepository;
    }

    public List<FileListItem> getAllFiles(UserAccount userAccount) {
        List<FileListItem> list = new LinkedList<>();

        list.addAll(repository.getCategorizedFiles(userAccount));
        list.addAll(repository.getUncategorizedFiles(userAccount));

        return list;
    }

    @Transactional
    public File changeFileFormat(File file, FileFormat fileFormat) {
        FileFormat prevFileFmt = file.getFileFormat();

        file.setFileFormat(fileFormat);
        file.setTetradDataFile(null);
        file.setTetradVariableFile(null);
        file = repository.save(file);

        if (prevFileFmt != null) {
            switch (prevFileFmt.getCode()) {
                case FileFormatCodes.TETRAD_TAB:
                    tetradDataFileRepository.deleteByFile(file);
                    break;
                case FileFormatCodes.TETRAD_VAR:
                    tetradVariableFileRepository.deleteByFile(file);
                    break;
            }
        }

        return file;
    }

    public File persistLocalFile(Path file, String relativePath, UserAccount userAccount) {
        String fileName = file.getFileName().toString();
        File entity = repository.findByFileNameAndUserAccount(fileName, userAccount);
        if (entity == null) {
            entity = new File();
            entity.setFileName(fileName);
            entity.setName(fileName);
        }

        setFileAttributes(entity, file, relativePath, userAccount);

        return repository.save(entity);
    }

    public List<File> persistLocalFiles(List<Path> files, String relativePath, UserAccount userAccount) {
        List<File> entites = new LinkedList<>();

        files.forEach(file -> {
            String fileName = file.getFileName().toString();
            File entity = repository.findByFileNameAndUserAccount(fileName, userAccount);
            if (entity == null) {
                entity = new File();
                entity.setFileName(fileName);
                entity.setName(fileName);
            }

            setFileAttributes(entity, file, relativePath, userAccount);

            entites.add(entity);
        });

        return repository.saveAll(entites);
    }

    private void setFileAttributes(File entity, Path file, String relativePath, UserAccount userAccount) {
        entity.setRelativePath(relativePath);
        entity.setUserAccount(userAccount);

        try {
            BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);

            long fileSize = attrs.size();
            Date creationTime = new Date(attrs.creationTime().toMillis());

            entity.setCreationTime(creationTime);

            entity.setFileSize(fileSize);
        } catch (IOException exception) {
            String msg = String.format("Failed to get attributes for file %s.", file.toString());
            LOGGER.error(msg, exception);
        }

        try {
            entity.setMd5CheckSum(FileUtils.computeMD5Hash(file));
        } catch (IOException | NoSuchAlgorithmException exception) {
            String msg = String.format("Failed to compute MD5 hash for file %s.", file.toString());
            LOGGER.error(msg, exception);
        }
    }

    public FileRepository getRepository() {
        return repository;
    }

}
