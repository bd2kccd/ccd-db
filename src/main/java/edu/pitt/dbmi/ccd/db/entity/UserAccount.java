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
import java.util.Collection;
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
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 *
 * Oct 08, 2015 6:25:18 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Entity
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 7491138787468687010L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
    @JoinColumn(name = "personId", nullable = false)
    private Person person;

    @NotNull
    @Size(max=255)
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @NotNull
    @Size(max=60)
    @Column(name = "password", length=60, nullable = false, columnDefinition="CHAR(60)")
    private String password;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "activationKey")
    private String activationKey;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", nullable = false)//, length = 19)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastLoginDate")//, length = 19)
    private Date lastLoginDate;

    // @ManyToMany(fetch=FetchType.EAGER)
    // @JoinTable(name="UserAccountUserRoleRel", joinColumns = {
    //     @JoinColumn(name="userAccountId", nullable=false)}, inverseJoinColumns = {
    //     @JoinColumn(name="userRoleId", nullable=false)})
    // Set<UserRole> userRoles = new HashSet<>(0);

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="role", nullable=false)
    private UserRole role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UserAccountDataFileRel", joinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "dataFileId", nullable = false, updatable = false)})
    private Set<DataFile> dataFiles = new HashSet<>(0);

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

    @ManyToMany(mappedBy="members", fetch=FetchType.LAZY)
    private Set<Group> groups = new HashSet<>(0);

    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private Set<Upload> uploads = new HashSet<>(0);

    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private Set<Annotation> annotations = new HashSet<>(0);

    public UserAccount() { }

    public UserAccount(Person person, String username, String password, boolean active, Date createdDate) {
        this.person = person;
        this.username = username;
        this.password = password;
        this.active = active;
        this.createdDate = createdDate;
    }

    public UserAccount(Long id, Person person, String username, String password, Boolean active, String activationKey, Date createdDate, Date lastLoginDate) {
        this(person, username, password, active, createdDate);
        this.id = id;
        this.activationKey = activationKey;
        this.lastLoginDate = lastLoginDate;
    }

    public UserAccount(UserAccount account) {
        this.id = account.getId();
        this.person = account.getPerson();
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.active = account.getActive();
        this.activationKey = account.getActivationKey();
        this.createdDate = account.getCreatedDate();
        this.lastLoginDate = account.getLastLoginDate();
        // this.userRoles = account.getUserRoles();
        this.role = account.getRole();
        this.dataFiles = account.getDataFiles();
        this.jobQueueInfos = account.getJobQueueInfos();
        this.securityAnswers = account.getSecurityAnswers();
        this.groups = account.getGroups();
        this.uploads = account.getUploads();
        this.annotations = account.getAnnotations();
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    // public Set<UserRole> getUserRoles() {
    //     return userRoles;
    // }

    // public void setUserRoles(Set<UserRole> userRoles) {
    //     this.userRoles = userRoles;
    // }

    // public boolean hasUserRole(UserRole role) {
    //     return userRoles.contains(role);
    // }

    // public boolean hasUserRoles(Collection<UserRole> roles) {
    //     return userRoles.containsAll(roles);
    // }

    // public void addUserRole(UserRole role) {
    //     this.userRoles.add(role);
    // }

    // public void addUserRoles(Collection<UserRole> roles) {
    //     this.userRoles.addAll(roles);
    // }

    // public void removeUserRole(UserRole role) {
    //     userRoles.remove(role);
    // }

    // public void removeUserRoles(Collection<UserRole> roles) {
    //     userRoles.removeAll(roles);
    // }

    public Set<DataFile> getDataFiles() {
        return dataFiles;
    }

    public void setDataFiles(Set<DataFile> dataFiles) {
        this.dataFiles = dataFiles;
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

    public Set<Group> getGroups() {
        return groups;
    }

    public boolean isMember(Group group) {
        return groups.contains(group);
    }

    public boolean isMember(Collection<Group> groups) {
        return this.groups.containsAll(groups);
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void addGroups(Collection<Group> groups) {
        this.groups.addAll(groups);
    }

    public void addGroups(Group... groups) {
        for (Group g: groups) {
            this.groups.add(g);
        }
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public void removeGroups(Collection<Group> groups) {
        this.groups.removeAll(groups);
    }

    public void removeGroups(Group... groups) {
        for (Group g: groups) {
            this.groups.remove(g);
        }
    }

    public Set<Upload> getUploads() {
        return uploads;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }
}
