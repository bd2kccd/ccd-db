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
 * Apr 12, 2018 12:06:51 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public class JobQueueListItem {

    private final Long id;
    private final String name;
    private final Date creationTime;
    private final String status;
    private final String location;

    public JobQueueListItem(Long id, String name, Date creationTime, String status, String location) {
        this.id = id;
        this.name = name;
        this.creationTime = creationTime;
        this.status = status;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

}
