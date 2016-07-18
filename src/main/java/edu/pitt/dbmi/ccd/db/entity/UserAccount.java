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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.pitt.dbmi.ccd.db.validation.Username;

/**
 *
 * Oct 08, 2015 6:25:18 PM
 *
 * @since v0.4.0
 * @author Kevin V. Bui (kvb2@pitt.edu)
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Entity
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1515341631013388861L;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_login_id", nullable = false)
    private UserLogin userLogin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_login_attempt_id", nullable = false)
    private UserLoginAttempt userLoginAttempt;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "disabled", nullable = false)
    private boolean disabled;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date", nullable = false, length = 19)
    private Date registrationDate;

    @Column(name = "registration_location")
    private Long registrationLocation;

    @Column(name = "account", nullable = false)
    private String account;

    @Column(name = "activation_key")
    private String activationKey;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserAccountUserRoleRel", joinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "userRoleId", nullable = false, updatable = false)})
    private Set<UserRole> userRoles = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UserAccountFileRel", joinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "fileId", nullable = false, updatable = false)})
    private Set<File> files = new HashSet<>(0);

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

    public UserAccount(Person person, UserLogin userLogin, UserLoginAttempt userLoginAttempt, String username, String password, boolean active, boolean disabled, Date registrationDate, Long registrationLocation, String account, String activationKey, Set<UserRole> userRoles, Set<File> files) {
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
        this.activationKey = activationKey;
        this.userRoles = userRoles;
        this.files = files;
    }

    @PrePersist
    private void onCreate() {
        registrationDate = new Date();
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

    public Long getRegistrationLocation() {
        return registrationLocation;
    }

    public void setRegistrationLocation(Long registrationLocation) {
        this.registrationLocation = registrationLocation;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
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
