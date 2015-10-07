/*
 * Copyright (C) 2015 University of Pittsburgh.
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * Jul 23, 2015 3:00:57 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 4024214155038709306L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personId", nullable = false)
    private Person person;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "publicKey", length = 592)
    private String publicKey;

    @Column(name = "privateKey", length = 448)
    private String privateKey;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "accountId")
    private String accountId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", nullable = false, length = 19)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastLoginDate", length = 19)
    private Date lastLoginDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UserAccountDataFileRel", joinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "dataFileId", nullable = false, updatable = false)})
    private Set<DataFile> dataFiles = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserAccountUserRoleRel", catalog = "ccd", joinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "userRoleId", nullable = false, updatable = false)})
    private Set<UserRole> userRoles = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UserAccountJobQueueInfoRel", joinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "jobQueueInfoId", nullable = false, updatable = false)})
    private Set<JobQueueInfo> jobQueueInfos = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UserAccountSecurityAnswerRel", joinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "securityAnswerId", nullable = false, updatable = false)})
    private Set<SecurityAnswer> securityAnswers = new HashSet<>(0);

    public UserAccount() {
    }

    public UserAccount(Person person, String username, String password, boolean active, Date createdDate, Date lastLoginDate) {
        this.person = person;
        this.username = username;
        this.password = password;
        this.active = active;
        this.createdDate = createdDate;
        this.lastLoginDate = lastLoginDate;
    }

    public UserAccount(Person person, String username, String password, String publicKey, String privateKey, boolean active, String accountId, Date createdDate, Date lastLoginDate, Set<DataFile> dataFiles, Set<UserRole> userRoles, Set<JobQueueInfo> jobQueueInfos, Set<SecurityAnswer> securityAnswers) {
        this.person = person;
        this.username = username;
        this.password = password;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.active = active;
        this.accountId = accountId;
        this.createdDate = createdDate;
        this.lastLoginDate = lastLoginDate;
        this.dataFiles = dataFiles;
        this.userRoles = userRoles;
        this.jobQueueInfos = jobQueueInfos;
        this.securityAnswers = securityAnswers;
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

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Set<DataFile> getDataFiles() {
        return dataFiles;
    }

    public void setDataFiles(Set<DataFile> dataFiles) {
        this.dataFiles = dataFiles;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<JobQueueInfo> getJobQueueInfos() {
        return jobQueueInfos;
    }

    public void setJobQueueInfos(Set<JobQueueInfo> jobQueueInfos) {
        this.jobQueueInfos = jobQueueInfos;
    }

    public Set<SecurityAnswer> getSecurityAnswers() {
        return securityAnswers;
    }

    public void setSecurityAnswers(Set<SecurityAnswer> securityAnswers) {
        this.securityAnswers = securityAnswers;
    }

}
