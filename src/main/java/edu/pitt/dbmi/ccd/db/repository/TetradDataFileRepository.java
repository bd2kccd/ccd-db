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
package edu.pitt.dbmi.ccd.db.repository;

import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.FileVariableType;
import edu.pitt.dbmi.ccd.db.entity.TetradDataFile;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * May 10, 2017 2:57:37 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Repository
public interface TetradDataFileRepository extends JpaRepository<TetradDataFile, Long> {

    public TetradDataFile findByFile(File file);

    public List<TetradDataFile> findByFileIn(List<File> files);

    public void deleteByFile(File file);

    public void deleteByFileIn(List<File> files);

    @Query("SELECT tdf FROM TetradDataFile tdf WHERE tdf.file.userAccount = ?1")
    public List<TetradDataFile> findByUserAccount(UserAccount userAccount);

    @Query("SELECT tdf FROM TetradDataFile tdf WHERE tdf.fileVariableType = ?1 AND tdf.file.id IN ?2 AND tdf.file.userAccount = ?3")
    public List<TetradDataFile> findByFileVariableTypeAndAndFileIdsAndUserAccount(FileVariableType fileVariableType, List<Long> ids, UserAccount userAccount);

}
