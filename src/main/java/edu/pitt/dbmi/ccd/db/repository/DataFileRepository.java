/*
 * Copyright (C) 2015 University of Pittsburgh.
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

import edu.pitt.dbmi.ccd.db.entity.DataFile;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * Jul 23, 2015 5:32:43 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Repository
public interface DataFileRepository extends JpaRepository<DataFile, Long> {

    public DataFile findById(Long id);

    public DataFile findByNameAndUserAccounts(String name, Set<UserAccount> userAccounts);

    public List<DataFile> findByAbsolutePath(String absolutePath);

    public DataFile findByAbsolutePathAndName(String absolutePath, String name);

    @Query("SELECT df FROM DataFile df WHERE df.absolutePath.name = ?1 AND df.dataFileInfo.fileDelimiter.name = ?2")
    public List<DataFile> findByAbsolutePathAndFileDelimiterName(String absolutePath, String name);

    public List<DataFile> findByUserAccounts(Set<UserAccount> userAccounts);

    public DataFile findByIdAndUserAccounts(Long id, Set<UserAccount> userAccounts);

}
