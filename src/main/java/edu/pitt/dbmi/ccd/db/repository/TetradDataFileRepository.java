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

import edu.pitt.dbmi.ccd.db.domain.ListItem;
import edu.pitt.dbmi.ccd.db.domain.file.TetradDataListItem;
import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.TetradDataFile;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.VariableType;
import java.util.List;
import java.util.Set;
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

    public TetradDataFile findByIdAndUserAccount(Long id, UserAccount userAccount);

    public List<TetradDataFile> findByUserAccount(UserAccount userAccount);

    public List<TetradDataFile> findByFileIn(List<File> files);

    @Query("SELECT tdf"
            + " FROM TetradDataFile tdf"
            + " WHERE tdf.userAccount = ?1 AND tdf.variableType = ?2 AND tdf.file.id IN ?3")
    public List<TetradDataFile> find(UserAccount userAccount, VariableType variableType, List<Long> ids);

    public void deleteByFile(File file);

    public void deleteByFileIn(Set<File> files);

    @Query("SELECT new edu.pitt.dbmi.ccd.db.domain.file.TetradDataListItem(tdf.id,tdf.file.title,tdf.file.creationTime,tdf.numOfCases,tdf.numOfVars)"
            + " FROM TetradDataFile tdf"
            + " WHERE tdf.userAccount = ?1 AND tdf.variableType = ?2")
    public List<TetradDataListItem> getTetradDataListFiles(UserAccount userAccount, VariableType variableType);

    @Query("SELECT new edu.pitt.dbmi.ccd.db.domain.ListItem(tdf.file.id,tdf.file.title)"
            + " FROM TetradDataFile tdf"
            + " WHERE tdf.userAccount = ?1 AND tdf.variableType = ?2")
    public List<ListItem> getTetradDataListItem(UserAccount userAccount, VariableType variableType);

}
