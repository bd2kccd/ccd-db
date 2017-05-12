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
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.FileRepository;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public Map<String, File> getUserFiles(UserAccount userAccount) {
        Map<String, File> files = new HashMap<>();

        List<File> dbFileList = fileRepository.findByUserAccount(userAccount);
        dbFileList.forEach(file -> {
            files.put(file.getName(), file);
        });

        return files;
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
            fileEntity.setMd5checkSum(md5checkSum);
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

    public FileRepository getFileRepository() {
        return fileRepository;
    }

}
