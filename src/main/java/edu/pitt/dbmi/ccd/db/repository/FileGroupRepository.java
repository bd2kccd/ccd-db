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

import edu.pitt.dbmi.ccd.db.domain.file.FileGroupListItem;
import edu.pitt.dbmi.ccd.db.entity.File;
import edu.pitt.dbmi.ccd.db.entity.FileGroup;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.VariableType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Jun 30, 2017 10:54:20 AM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Repository
public interface FileGroupRepository extends JpaRepository<FileGroup, Long> {

    public boolean existsByNameAndUserAccount(String name, UserAccount userAccount);

    @Transactional
    public Long deleteByIdAndUserAccount(Long id, UserAccount userAccount);

    public Long countByUserAccount(UserAccount userAccount);

    public FileGroup findByIdAndUserAccount(Long id, UserAccount userAccount);

    public List<FileGroup> findByUserAccount(UserAccount userAccount);

    @Query("SELECT fg.files FROM FileGroup fg WHERE fg.id = ?1 AND fg.userAccount = ?2")
    public List<File> getFiles(Long id, UserAccount userAccount);

    public boolean existsByIdAndUserAccount(Long id, UserAccount userAccount);

    @Query("SELECT new edu.pitt.dbmi.ccd.db.domain.file.FileGroupListItem(fg.id,fg.name,fg.creationTime) "
            + "FROM FileGroup fg "
            + "WHERE fg.userAccount = ?1 AND fg.variableType = ?2")
    public List<FileGroupListItem> getFileGroupListItems(UserAccount userAccount, VariableType variableType);

}
