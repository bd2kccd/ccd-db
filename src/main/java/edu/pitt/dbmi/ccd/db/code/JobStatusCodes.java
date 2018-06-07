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
package edu.pitt.dbmi.ccd.db.code;

/**
 *
 * May 22, 2018 4:20:03 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public class JobStatusCodes {

    public static final short QUEUED = 1;
    public static final short STARTED = 2;
    public static final short FINISHED = 3;
    public static final short CANCELED = 4;
    public static final short FAILED = 5;

    private JobStatusCodes() {
    }

}
