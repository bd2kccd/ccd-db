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
 * May 23, 2018 4:55:07 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public class FileListItem extends ListItem {

    protected final long size;
    protected final Date creationTime;
    protected final boolean categorized;

    public FileListItem(Long id, String name, long size, Date creationTime, boolean categorized) {
        super(id, name);
        this.size = size;
        this.creationTime = creationTime;
        this.categorized = categorized;
    }

    public long getSize() {
        return size;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public boolean isCategorized() {
        return categorized;
    }

}
