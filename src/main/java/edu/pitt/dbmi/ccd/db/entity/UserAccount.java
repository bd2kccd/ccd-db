/*
 * Copyright (C) 2017 University of Pittsburgh.
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
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * Mar 19, 2017 7:00:21 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "UserAccount", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username")
    , @UniqueConstraint(columnNames = "account")})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = -3343178943873392333L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    @XmlTransient
    private String password;

    @Column(name = "account", unique = true, nullable = false)
    private String account;

    @Column(name = "activated", nullable = false)
    private boolean activated;

    @Column(name = "disabled", nullable = false)
    private boolean disabled;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registrationDate", nullable = false, length = 19)
    private Date registrationDate;

    @Column(name = "registrationLocation")
    private Long registrationLocation;

    @Column(name = "actionKey")
    private String actionKey;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userInfoId", nullable = false)
    @XmlTransient
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userLoginId", nullable = false)
    @XmlTransient
    private UserLogin userLogin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserAccountUserRoleRel", joinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "userRoleId", nullable = false, updatable = false)})
    @XmlTransient
    private List<UserRole> userRoles = new LinkedList<>();

    public UserAccount() {
    }

    public UserAccount(String username, String password, String account, boolean activated, boolean disabled, Date registrationDate, UserInfo userInfo, UserLogin userLogin) {
        this.username = username;
        this.password = password;
        this.account = account;
        this.activated = activated;
        this.disabled = disabled;
        this.registrationDate = registrationDate;
        this.userInfo = userInfo;
        this.userLogin = userLogin;
    }

    public UserAccount(String username, String password, String account, boolean activated, boolean disabled, Date registrationDate, Long registrationLocation, String actionKey, UserInfo userInfo, UserLogin userLogin, List<UserRole> userRoles) {
        this.username = username;
        this.password = password;
        this.account = account;
        this.activated = activated;
        this.disabled = disabled;
        this.registrationDate = registrationDate;
        this.registrationLocation = registrationLocation;
        this.actionKey = actionKey;
        this.userInfo = userInfo;
        this.userLogin = userLogin;
        this.userRoles = userRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
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

    public Long getRegistrationLocation() {
        return registrationLocation;
    }

    public void setRegistrationLocation(Long registrationLocation) {
        this.registrationLocation = registrationLocation;
    }

    public String getActionKey() {
        return actionKey;
    }

    public void setActionKey(String actionKey) {
        this.actionKey = actionKey;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

}
