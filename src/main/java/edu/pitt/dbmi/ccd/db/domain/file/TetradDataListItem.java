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

import edu.pitt.dbmi.ccd.db.domain.ListItem;
import java.util.Date;

/**
 *
 * Mar 12, 2018 4:00:57 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public class TetradDataListItem extends ListItem {

    protected final Date creationTime;

    protected final Integer numOfCases;

    protected final Integer numOfVars;

    public TetradDataListItem(Long id, String name, Date creationTime, Integer numOfCases, Integer numOfVars) {
        super(id, name);
        this.creationTime = creationTime;
        this.numOfCases = numOfCases;
        this.numOfVars = numOfVars;
    }

    @Override
    public String toString() {
        return "TetradDataListItem{" + "id=" + id + ", name=" + name + ", creationTime=" + creationTime + ", numOfCases=" + numOfCases + ", numOfVars=" + numOfVars + '}';
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
