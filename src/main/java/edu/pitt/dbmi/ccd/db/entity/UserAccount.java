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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * Jul 23, 2015 3:00:57 PM
 *
 * @since v0.4.0
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1515341631013388861L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_login_id", nullable = false)
    private UserLogin userLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_login_attempt_id", nullable = false)
    private UserLoginAttempt userLoginAttempt;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "disabled", nullable = false)
    private boolean disabled;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date", nullable = false, length = 19)
    private Date registrationDate;

    @Column(name = "registration_location")
    private Integer registrationLocation;

    @Column(name = "account", nullable = false)
    private String account;

    public UserAccount() {
    }

    public UserAccount(Person person, UserLogin userLogin, UserLoginAttempt userLoginAttempt, String username, String password, boolean active, boolean disabled, Date registrationDate, String account) {
        this.person = person;
        this.userLogin = userLogin;
        this.userLoginAttempt = userLoginAttempt;
        this.username = username;
        this.password = password;
        this.active = active;
        this.disabled = disabled;
        this.registrationDate = registrationDate;
        this.account = account;
    }

    public UserAccount(Person person, UserLogin userLogin, UserLoginAttempt userLoginAttempt, String username, String password, boolean active, boolean disabled, Date registrationDate, Integer registrationLocation, String account) {
        this.person = person;
        this.userLogin = userLogin;
        this.userLoginAttempt = userLoginAttempt;
        this.username = username;
        this.password = password;
        this.active = active;
        this.disabled = disabled;
        this.registrationDate = registrationDate;
        this.registrationLocation = registrationLocation;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public UserLoginAttempt getUserLoginAttempt() {
        return userLoginAttempt;
    }

    public void setUserLoginAttempt(UserLoginAttempt userLoginAttempt) {
        this.userLoginAttempt = userLoginAttempt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getRegistrationLocation() {
        return registrationLocation;
    }

    public void setRegistrationLocation(Integer registrationLocation) {
        this.registrationLocation = registrationLocation;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
