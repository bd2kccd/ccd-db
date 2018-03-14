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
package edu.pitt.dbmi.ccd.db.domain.file;

import java.util.Date;

/**
 *
 * Mar 12, 2018 4:00:57 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public class TetradDataListItem {

    private final Long fileId;

    private final String title;

    private final Date creationTime;

    private final Integer numOfCases;

    private final Integer numOfVars;

    public TetradDataListItem(Long fileId, String title, Date creationTime, Integer numOfCases, Integer numOfVars) {
        this.fileId = fileId;
        this.title = title;
        this.creationTime = creationTime;
        this.numOfCases = numOfCases;
        this.numOfVars = numOfVars;
    }

    public Long getFileId() {
        return fileId;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Integer getNumOfCases() {
        return numOfCases;
    }

    public Integer getNumOfVars() {
        return numOfVars;
    }

}
