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
package edu.pitt.dbmi.ccd.db.domain.job;

import java.util.Date;

/**
 *
 * Apr 12, 2018 3:38:57 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public class JobInfoListItem {

    private final Long id;
    private final String name;
    private final String algoType;
    private final Date creationTime;
    private final String status;

    public JobInfoListItem(Long id, String name, String algoType, Date creationTime, String status) {
        this.id = id;
        this.name = name;
        this.algoType = algoType;
        this.creationTime = creationTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlgoType() {
        return algoType;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public String getStatus() {
        return status;
    }

}
