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
 * Jul 18, 2018 2:43:38 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public class TetradVarListItem extends ListItem {

    protected final Date creationTime;

    protected final Integer numOfVars;

    public TetradVarListItem(Long id, String name, Date creationTime, Integer numOfVars) {
        super(id, name);
        this.creationTime = creationTime;
        this.numOfVars = numOfVars;
    }

    @Override
    public String toString() {
        return "TetradVarListItem{" + "id=" + id + ", name=" + name + ", creationTime=" + creationTime + ", numOfVars=" + numOfVars + '}';
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Integer getNumOfVars() {
        return numOfVars;
    }

}
