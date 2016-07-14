/*
 * Copyright (C) 2016 University of Pittsburgh.
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
package edu.pitt.dbmi.ccd.db.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * Feb 21, 2016 8:03:08 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
public class UserLoginAttempt implements Serializable {

    private static final long serialVersionUID = -1971864276255876577L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "attemptDate", length = 19)
    private Date attemptDate;

    @Column(name = "attemptLocation")
    private Long attemptLocation;

    @Column(name = "attemptCount")
    private Byte attemptCount;

    public UserLoginAttempt() {
    }

    public UserLoginAttempt(Long id, Date attemptDate, Long attemptLocation, Byte attemptCount) {
        this.id = id;
        this.attemptDate = attemptDate;
        this.attemptLocation = attemptLocation;
        this.attemptCount = attemptCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(Date attemptDate) {
        this.attemptDate = attemptDate;
    }

    public Long getAttemptLocation() {
        return attemptLocation;
    }

    public void setAttemptLocation(Long attemptLocation) {
        this.attemptLocation = attemptLocation;
    }

    public Byte getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(Byte attemptCount) {
        this.attemptCount = attemptCount;
    }

}
