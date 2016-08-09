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
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * Aug 9, 2016 1:17:10 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "UserLogInOut")
public class UserLogInOut implements Serializable {

    private static final long serialVersionUID = -7789006530259070070L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "logInDate", length = 19)
    private Date logInDate;

    @Column(name = "logInLocation")
    private Long logInLocation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "logOutDate", length = 19)
    private Date logOutDate;

    @Column(name = "logOutLocation")
    private Long logOutLocation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userLogInOut")
    private Set<UserAccount> userAccounts = new HashSet<>(0);

    public UserLogInOut() {
    }

    public UserLogInOut(Date logInDate, Long logInLocation, Date logOutDate, Long logOutLocation, Set<UserAccount> userAccounts) {
        this.logInDate = logInDate;
        this.logInLocation = logInLocation;
        this.logOutDate = logOutDate;
        this.logOutLocation = logOutLocation;
        this.userAccounts = userAccounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLogInDate() {
        return logInDate;
    }

    public void setLogInDate(Date logInDate) {
        this.logInDate = logInDate;
    }

    public Long getLogInLocation() {
        return logInLocation;
    }

    public void setLogInLocation(Long logInLocation) {
        this.logInLocation = logInLocation;
    }

    public Date getLogOutDate() {
        return logOutDate;
    }

    public void setLogOutDate(Date logOutDate) {
        this.logOutDate = logOutDate;
    }

    public Long getLogOutLocation() {
        return logOutLocation;
    }

    public void setLogOutLocation(Long logOutLocation) {
        this.logOutLocation = logOutLocation;
    }

    public Set<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Set<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

}
