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
 * Feb 21, 2016 7:55:17 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
public class UserLogin implements Serializable {

    private static final long serialVersionUID = 2852673190168508153L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "loginDate", length = 19)
    private Date loginDate;

    @Column(name = "loginLocation")
    private Long loginLocation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastLoginDate", length = 19)
    private Date lastLoginDate;

    @Column(name = "lastLoginLocation")
    private Long lastLoginLocation;

    public UserLogin() {
    }

    public UserLogin(Long id, Date loginDate, Long loginLocation, Date lastLoginDate, Long lastLoginLocation) {
        this.id = id;
        this.loginDate = loginDate;
        this.loginLocation = loginLocation;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginLocation = lastLoginLocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Long getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(Long loginLocation) {
        this.loginLocation = loginLocation;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Long getLastLoginLocation() {
        return lastLoginLocation;
    }

    public void setLastLoginLocation(Long lastLoginLocation) {
        this.lastLoginLocation = lastLoginLocation;
    }

}
