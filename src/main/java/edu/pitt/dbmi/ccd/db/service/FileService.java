/*
 * Copyright (C) 2017 University of Pittsburgh.
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
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.FileFormatRepository;
import edu.pitt.dbmi.ccd.db.repository.FileRepository;
import edu.pitt.dbmi.ccd.db.repository.TetradDataFileRepository;
import edu.pitt.dbmi.ccd.db.repository.TetradVariableFileRepository;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Apr 27, 2017 4:50:10 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    private final FileRepository fileRepository;
    private final FileFormatRepository fileFormatRepository;
    private final TetradDataFileRepository tetradDataFileRepository;
    private final TetradVariableFileRepository tetradVariableFileRepository;

    @Autowired
    public FileService(FileRepository fileRepository, FileFormatRepository fileFormatRepository, TetradDataFileRepository tetradDataFileRepository, TetradVariableFileRepository tetradVariableFileRepository) {
        this.fileRepository = fileRepository;
        this.fileFormatRepository = fileFormatRepository;
        this.tetradDataFileRepository = tetradDataFileRepository;
        this.tetradVariableFileRepository = tetradVariableFileRepository;
    }

    @Transactional
    public File updateFileFormat(File file, FileFormat fileFormat) {
        if (file == null) {
            return file;
        }

        FileFormat oldFileFmt = file.getFileFormat();
        String oldFmtName = (oldFileFmt == null) ? "" : oldFileFmt.getName();
        String currFmtName = (fileFormat == null) ? "" : fileFormat.getName();
        if (oldFmtName.equals(currFmtName)) {
            return file;
        }

        tetradDataFileRepository.deleteByFile(file);
        tetradVariableFileRepository.deleteByFile(file);

        file.setFileFormat(fileFormat);

        return fileRepository.save(file);
    }

    public List<File> findByUserAccountAndFileFormatName(String fileFormatName, UserAccount userAccount) {
        FileFormat fileFormat = (fileFormatName == null)
                ? null
                : fileFormatRepository.findByName(fileFormatName);

        return (fileFormat == null)
                ? fileRepository.findByUserAccountAndFileFormatIsNull(userAccount)
                : fileRepository.findByUserAccountAndFileFormat(userAccount, fileFormat);
    }

    public File findByIdAndUserAccount(Long id, UserAccount userAccount) {
        if (id == null || userAccount == null) {
            return null;
        }

        return fileRepository.findByIdAndUserAccount(id, userAccount);
    }

    public List<File> persistLocalFiles(List<Path> files, UserAccount userAccount) {
        List<File> fileEntities = new LinkedList<>();
        files.forEach(file -> {
            File fileEntity = createFileEntity(file, userAccount);
            if (fileEntity != null) {
                fileEntities.add(fileEntity);
            }
        });

        return fileRepository.save(fileEntities);
    }

    public File persistLocalFile(Path file, UserAccount userAccount) {
        File fileEntity = createFileEntity(file, userAccount);
        if (fileEntity != null) {
            fileEntity = fileRepository.save(fileEntity);
        }

        return fileEntity;
    }

    public File createFileEntity(Path file, UserAccount userAccount) {
        File fileEntity = null;

        try {
            BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
            String name = file.getFileName().toString();
            Date creationTime = new Date(attrs.creationTime().toMillis());
            long fileSize = attrs.size();
            String md5checkSum = getFileChecksum(file);

            fileEntity = new File();
            fileEntity.setCreationTime(creationTime);
            fileEntity.setFileSize(fileSize);
            fileEntity.setMd5CheckSum(md5checkSum);
            fileEntity.setName(name);
            fileEntity.setTitle(name);
            fileEntity.setUserAccount(userAccount);
        } catch (IOException | NoSuchAlgorithmException exception) {
            LOGGER.error("Unable to persist physical file information", exception);
        }

        return fileEntity;
    }

    private String getFileChecksum(Path file) throws IOException, NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        try (InputStream is = Files.newInputStream(file)) {
            DigestInputStream dis = new DigestInputStream(is, messageDigest);
            dis.on(true); // no need to call md.update(buffer) when set ON
            byte[] buffer = new byte[8192];
            while (dis.read(buffer) != -1) {
                // a call to the read method results in an update on the message digest
            }

            byte[] digest = messageDigest.digest();
            for (byte b : digest) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
        }

        return sb.toString();
    }

    public FileRepository getRepository() {
        return fileRepository;
    }

}
