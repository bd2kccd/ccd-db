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

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.pitt.dbmi.ccd.db.validation.Username;

/**
 * Oct 08, 2015 6:25:18 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Entity
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 7491138787468687010L;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserAccountUserRoleRel", joinColumns = {
            @JoinColumn(name = "userAccountId", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "userRoleId", nullable = false)})
    Set<UserRole> roles = new HashSet<>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "personId", nullable = false)
    private Person person;

    @Username
    @NotNull
    @Size(max = 255)
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @Column(name = "activationKey")
    private String activationKey;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", nullable = false, length = 19)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastLoginDate", length = 19)
    private Date lastLoginDate;

    @Column(name = "account", unique = true, nullable = false)
    private String account;

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

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    @OrderBy("name")
    private Set<Group> groups = new HashSet<>(0);

    @ManyToMany(mappedBy = "mods", fetch = FetchType.EAGER)
    @OrderBy("name")
    private Set<Group> moderates = new HashSet<>(0);

    @ManyToMany(mappedBy = "requesters", fetch = FetchType.LAZY)
    private Set<Group> requesting = new HashSet<>(0);

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @OrderBy("created")
    private Set<AnnotationTarget> annotationTargets = new HashSet<>(0);

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @OrderBy("created")
    private Set<Annotation> annotations = new HashSet<>(0);

    protected UserAccount() {
    }

    public UserAccount(Person person, String username, String password, String account) {
        this.person = person;
        this.username = username;
        this.password = password;
        this.account = account;
    }

    public UserAccount(Person person, String username, String password, String account, boolean active) {
        this(person, username, password, account);
        this.active = active;
    }

    public UserAccount(Person person, String username, String password, String account, String activationKey) {
        this(person, username, password, account);
        this.activationKey = activationKey;
    }

    public UserAccount(Person person, String username, String password, String account, boolean active, Date createdDate) {
        this(person, username, password, account);
        this.active = active;
        this.createdDate = createdDate;
    }

    public UserAccount(Long id, Person person, String username, String password, String account, Boolean active, String activationKey, Date createdDate, Date lastLoginDate) {
        this(person, username, password, account, active, createdDate);
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
        this.account = account.getAccount();
        this.roles = account.getRoles();
        this.dataFiles = account.getDataFiles();
        this.jobQueueInfos = account.getJobQueueInfos();
        this.securityAnswers = account.getSecurityAnswers();
        this.groups = account.getGroups();
        this.annotationTargets = account.getAnnotationTargets();
        this.annotations = account.getAnnotations();
    }

    @PrePersist
    private void onCreate() {
        createdDate = new Date();
        lastLoginDate = new Date();
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public boolean hasRole(UserRole role) {
        return this.roles.contains(role);
    }

    public boolean hasRoles(Set<UserRole> roles) {
        return this.roles.containsAll(roles);
    }

    public void addRole(UserRole role) {
        this.roles.add(role);
    }

    public void addRoles(UserRole... roles) {
        for (UserRole r : roles) {
            addRole(r);
        }
    }

    public void addRoles(Set<UserRole> roles) {
        this.roles.addAll(roles);
    }

    public void removeRole(UserRole role) {
        this.roles.remove(role);
    }

    public void removeRoles(UserRole... roles) {
        for (UserRole r : roles) {
            removeRole(r);
        }
    }

    public void removeRoles(Set<UserRole> roles) {
        this.roles.removeAll(roles);
    }

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

    public Set<Group> getMods() {
        return moderates;
    }

    public boolean isMod(Group group) {
        return moderates.contains(group);
    }

    public boolean isMod(Collection<Group> groups) {
        return this.moderates.containsAll(groups);
    }

    public void addMod(Group group) {
        moderates.add(group);
    }

    public void addMods(Collection<Group> groups) {
        this.moderates.addAll(groups);
    }

    public void addMods(Group... groups) {
        for (Group g : groups) {
            addMod(g);
        }
    }

    public void removeMod(Group group) {
        moderates.remove(group);
    }

    public void removeMods(Collection<Group> groups) {
        this.moderates.removeAll(groups);
    }

    public void removeMods(Group... groups) {
        for (Group g : groups) {
            removeMod(g);
        }
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
        for (Group g : groups) {
            addGroup(g);
        }
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public void removeGroups(Collection<Group> groups) {
        this.groups.removeAll(groups);
    }

    public void removeGroups(Group... groups) {
        for (Group g : groups) {
            removeGroup(g);
        }
    }

    public Set<Group> getRequesting() {
        return requesting;
    }

    public Set<AnnotationTarget> getAnnotationTargets() {
        return annotationTargets;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }
}
