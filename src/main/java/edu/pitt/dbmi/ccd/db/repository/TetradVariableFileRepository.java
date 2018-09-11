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

import edu.pitt.dbmi.ccd.db.domain.file.TetradVarListItem;
import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.TetradVariableFile;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * May 10, 2017 2:59:17 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Repository
public interface TetradVariableFileRepository extends JpaRepository<TetradVariableFile, Long> {

    public TetradVariableFile findByFile(File file);

    public TetradVariableFile findByIdAndUserAccount(Long id, UserAccount userAccount);

    public void deleteByFile(File file);

    public void deleteByFileIn(List<File> files);

    @Query("SELECT new edu.pitt.dbmi.ccd.db.domain.file.TetradVarListItem(tvf.id,tvf.file.name,tvf.file.creationTime,tvf.numOfVars)"
            + " FROM TetradVariableFile tvf"
            + " WHERE tvf.userAccount = ?1")
    public List<TetradVarListItem> getTetradVarListItems(UserAccount userAccount);

    public boolean existsByIdAndUserAccount(Long id, UserAccount userAccount);

}
