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

import edu.pitt.dbmi.ccd.db.entity.AlgorithmType;
import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.FileFormat;
import edu.pitt.dbmi.ccd.db.entity.TetradDataFile;
import edu.pitt.dbmi.ccd.db.entity.TetradVariableFile;
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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    private final FileRepository fileRepository;
    private final TetradDataFileRepository tetradDataFileRepository;
    private final TetradVariableFileRepository tetradVariableFileRepository;
    private final FileFormatService fileFormatService;
    private final AlgorithmTypeService algorithmTypeService;

    @Autowired
    public FileService(
            FileRepository fileRepository,
            TetradDataFileRepository tetradDataFileRepository,
            TetradVariableFileRepository tetradVariableFileRepository,
            FileFormatService fileFormatService,
            AlgorithmTypeService algorithmTypeService) {
        this.fileRepository = fileRepository;
        this.tetradDataFileRepository = tetradDataFileRepository;
        this.tetradVariableFileRepository = tetradVariableFileRepository;
        this.fileFormatService = fileFormatService;
        this.algorithmTypeService = algorithmTypeService;
    }

    public Map<String, String> getAdditionalInfo(File file) {
        Map<String, String> addInfo = new LinkedHashMap<>();

        FileFormat fileFormat = file.getFileFormat();
        if (fileFormat != null) {
            switch (fileFormat.getShortName()) {
                case FileFormatService.TETRAD_TAB_SHORT_NAME:
                    TetradDataFile dataFile = tetradDataFileRepository.findByFile(file);
                    if (dataFile != null) {
                        addInfo.put("Number of Variables", String.valueOf(dataFile.getNumOfVars()));
                        addInfo.put("Number of Cases", String.valueOf(dataFile.getNumOfCases()));
                        addInfo.put("Delimiter", dataFile.getDataDelimiter().getName());
                        addInfo.put("Variable Type", dataFile.getVariableType().getName());
                        addInfo.put("Has Header", dataFile.isHasHeader() ? "Yes" : "No");
                        addInfo.put("Quote Character", String.valueOf(dataFile.getQuoteChar()));
                        addInfo.put("Missing Value Marker", dataFile.getMissingMarker());
                        addInfo.put("Comment Marker", dataFile.getCommentMarker());
                    }
                    break;
                case FileFormatService.TETRAD_VAR_SHORT_NAME:
                    TetradVariableFile varFile = tetradVariableFileRepository.findByFile(file);
                    if (varFile != null) {
                        addInfo.put("Number of Variables", String.valueOf(varFile.getNumOfVars()));
                    }
                    break;
            }
        }

        return addInfo;
    }

    @Transactional
    public File changeFileFormat(File file, FileFormat fileFormat) {
        FileFormat prevFileFormat = file.getFileFormat();
        if (prevFileFormat != null) {
            switch (prevFileFormat.getShortName()) {
                case FileFormatService.TETRAD_TAB_SHORT_NAME:
                    tetradDataFileRepository.deleteByFile(file);
                    break;
                case FileFormatService.TETRAD_VAR_SHORT_NAME:
                    tetradVariableFileRepository.deleteByFile(file);
                    break;
            }
        }

        file.setFileFormat(fileFormat);

        return fileRepository.save(file);
    }

    public Map<FileFormat, Long> countGroupByFileFormat(UserAccount userAccount) {
        Map<FileFormat, Long> countMap = new LinkedHashMap<>();

        // get counts of uncategorized files
        Long count = fileRepository
                .countByUserAccountAndFileFormatIsNull(userAccount);
        countMap.put(null, count);

        // order by algorithm types
        List<AlgorithmType> algoTypes = algorithmTypeService.findAll();
        algoTypes.forEach(algoType -> {
            fileFormatService.findByAlgorithmType(algoType).stream()
                    .filter(e -> !FileTypeService.RESULT_SHORT_NAME.equals(e.getFileType().getShortName()))
                    .forEach(fmt -> countMap.put(fmt, fileRepository.countByUserAccountAndFileFormat(userAccount, fmt)));
        });

        return countMap;
    }

    public List<File> persistLocalFiles(List<Path> files, UserAccount userAccount) {
        List<File> fileEntities = new LinkedList<>();
        files.forEach(file -> {
            File fileEntity = createFileEntity(file, userAccount);
            if (fileEntity != null) {
                fileEntities.add(fileEntity);
            }
        });

        return fileRepository.saveAll(fileEntities);
    }

    public List<File> persistLocalFiles(List<Path> files, FileFormat fileFormat, UserAccount userAccount) {
        List<File> fileEntities = new LinkedList<>();
        files.forEach(file -> {
            File fileEntity = createFileEntity(file, userAccount);
            if (fileEntity != null) {
                fileEntity.setFileFormat(fileFormat);
                fileEntities.add(fileEntity);
            }
        });

        return fileRepository.saveAll(fileEntities);
    }

    public File persistLocalFile(Path file, UserAccount userAccount) {
        File fileEntity = createFileEntity(file, userAccount);
        if (fileEntity != null) {
            fileEntity = fileRepository.save(fileEntity);
        }

        return fileEntity;
    }

    public File createFileEntity(Path file, UserAccount userAccount) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
            String name = file.getFileName().toString();
            Date creationTime = new Date(attrs.creationTime().toMillis());
            long fileSize = attrs.size();
            String md5checkSum = FileUtils.computeMD5Hash(file);

            File fileEntity = new File();
            fileEntity.setCreationTime(creationTime);
            fileEntity.setFileSize(fileSize);
            fileEntity.setMd5CheckSum(md5checkSum);
            fileEntity.setName(name);
            fileEntity.setTitle(name);
            fileEntity.setUserAccount(userAccount);

            return fileEntity;
        } catch (IOException | NoSuchAlgorithmException exception) {
            LOGGER.error("Unable to persist physical file information", exception);

            return null;
        }
    }

    public FileRepository getRepository() {
        return fileRepository;
    }

}
