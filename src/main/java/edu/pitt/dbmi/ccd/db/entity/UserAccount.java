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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * Aug 8, 2016 4:01:12 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "UserAccount", uniqueConstraints = {
    @UniqueConstraint(columnNames = "account"),
    @UniqueConstraint(columnNames = "username")}
)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 5825089221116140414L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personId", nullable = false)
    private Person person;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
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

    @Column(name = "activationKey")
    private String activationKey;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userAccount")
    private Set<UserLogin> userLogins = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userAccount", cascade = CascadeType.ALL)
    private Set<UserEventLog> userEventLogs = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "UserAccountUserRoleRel", joinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "userRoleId", nullable = false, updatable = false)})
    private Set<UserRole> userRoles = new HashSet<>(0);

    public UserAccount() {
    }

    public UserAccount(Person person, String username, String password, String account, boolean activated, boolean disabled, Date registrationDate) {
        this.person = person;
        this.username = username;
        this.password = password;
        this.account = account;
        this.activated = activated;
        this.disabled = disabled;
        this.registrationDate = registrationDate;
    }

    public UserAccount(Person person, String username, String password, String account, boolean activated, boolean disabled, Date registrationDate, String activationKey, Set<UserLogin> userLogins, Set<UserEventLog> userEventLogs, Set<UserRole> userRoles) {
        this.person = person;
        this.username = username;
        this.password = password;
        this.account = account;
        this.activated = activated;
        this.disabled = disabled;
        this.registrationDate = registrationDate;
        this.activationKey = activationKey;
        this.userLogins = userLogins;
        this.userEventLogs = userEventLogs;
        this.userRoles = userRoles;
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

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public Set<UserLogin> getUserLogins() {
        return userLogins;
    }

    public void setUserLogins(Set<UserLogin> userLogins) {
        this.userLogins = userLogins;
    }

    public Set<UserEventLog> getUserEventLogs() {
        return userEventLogs;
    }

    public void setUserEventLogs(Set<UserEventLog> userEventLogs) {
        this.userEventLogs = userEventLogs;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

}
