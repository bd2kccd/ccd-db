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

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pitt.dbmi.ccd.db.entity.FileInfoDB;

/**
 *
 * May 18, 2015 1:11:19 PM
 *
 * @author Chirayu (Kong) Wongchokprasitti (chw20@pitt.edu)
 * 
 */
public interface FileInfoRepository extends JpaRepository<FileInfoDB, Long> {

    public List<FileInfoDB> findByFileName(String fileName);
    public FileInfoDB findByFileAbsolutePath(String fileAbsolutePath);

}
