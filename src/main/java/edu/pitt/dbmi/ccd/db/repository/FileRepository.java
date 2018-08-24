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

import edu.pitt.dbmi.ccd.db.domain.file.FileListItem;
import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.FileFormat;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * Jan 28, 2018 7:29:23 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    public List<File> findByUserAccount(UserAccount userAccount);

    public File findByFileNameAndUserAccount(String fileName, UserAccount userAccount);

    public boolean existsByFileNameAndUserAccount(String fileName, UserAccount userAccount);

    public boolean existsByIdAndFileFormatAndUserAccount(Long id, FileFormat fileFormat, UserAccount userAccount);

    public File findByIdAndUserAccount(Long id, UserAccount userAccount);

    @Query("SELECT f.fileName FROM File f WHERE f.userAccount = ?1")
    public Set<String> getFileNames(UserAccount userAccount);

    @Query("SELECT new edu.pitt.dbmi.ccd.db.domain.file.FileListItem(f.id,f.name,f.fileSize,f.creationTime,f.fileFormat IS NOT NULL) "
            + "FROM File f "
            + "WHERE f.userAccount = ?1")
    public List<FileListItem> getAllFiles(UserAccount userAccount);

    @Query("SELECT new edu.pitt.dbmi.ccd.db.domain.file.FileListItem(f.id,f.name,f.fileSize,f.creationTime,f.fileFormat IS NOT NULL) "
            + "FROM File f "
            + "WHERE f.userAccount = ?1 AND f.fileFormat IS NULL")
    public List<FileListItem> getUncategorized(UserAccount userAccount);

    @Query("SELECT new edu.pitt.dbmi.ccd.db.domain.file.FileListItem(f.id,f.name,f.fileSize,f.creationTime,f.fileFormat IS NOT NULL) "
            + "FROM File f "
            + "WHERE f.fileFormat = ?1 AND f.userAccount = ?2")
    public List<FileListItem> getByFileFormat(FileFormat fileFormat, UserAccount userAccount);

}
