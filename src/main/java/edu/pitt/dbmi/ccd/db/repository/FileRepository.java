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
import edu.pitt.dbmi.ccd.db.entity.FileFormat;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * Apr 27, 2017 4:49:17 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    public List<File> findByUserAccount(UserAccount userAccount);

    @Query("SELECT f FROM File f WHERE f.userAccount = ?1 AND f.fileFormat IS NULL")
    public List<File> findUntypedFilesByUserAccount(UserAccount userAccount);

    public File findByIdAndUserAccount(Long id, UserAccount userAccount);

    @Query("SELECT f FROM File f WHERE f.id IN ?1 AND f.userAccount = ?2")
    public List<File> findByIdsAndUserAccount(List<Long> ids, UserAccount userAccount);

    public Long countByUserAccount(UserAccount userAccount);

    public Long countByFileFormatAndUserAccount(FileFormat fileFormat, UserAccount userAccount);

    @Query("SELECT COUNT(f) FROM File f WHERE f.userAccount = ?1 AND f.fileFormat IS NULL")
    public Long countUntypedFilesByUserAccount(UserAccount userAccount);

}
