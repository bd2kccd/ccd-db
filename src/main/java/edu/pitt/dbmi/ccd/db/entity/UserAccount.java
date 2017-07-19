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

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
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
    @UniqueConstraint(columnNames = {"userLoginId"})
    , @UniqueConstraint(columnNames = {"userInfoId"})
    , @UniqueConstraint(columnNames = {"username"})
    , @UniqueConstraint(columnNames = {"account"})})
@XmlRootElement
public class UserAccount implements Serializable {

    private static final long serialVersionUID = -3343178943873392333L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "username", unique = true, nullable = false, length = 255)
    private String username;

    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Basic(optional = false)
    @Column(name = "account", unique = true, nullable = false, length = 255)
    private String account;

    @Basic(optional = false)
    @Column(name = "activated", nullable = false)
    private boolean activated;

    @Basic(optional = false)
    @Column(name = "disabled", nullable = false)
    private boolean disabled;

    @Basic(optional = false)
    @Column(name = "registrationDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Column(name = "registrationLocation")
    private Long registrationLocation;

    @Column(name = "actionKey", length = 255)
    private String actionKey;

    @JoinColumn(name = "userInfoId", unique = true, referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private UserInfo userInfo;

    @JoinColumn(name = "userLoginId", unique = true, referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private UserLogin userLogin;

    @JoinTable(name = "UserAccountUserRoleRel", joinColumns = {
        @JoinColumn(name = "userAccountId", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "userRoleId", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

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

    @XmlTransient
    @JsonIgnore
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

    @XmlTransient
    @JsonIgnore
    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @XmlTransient
    @JsonIgnore
    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @XmlTransient
    @JsonIgnore
    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

}
