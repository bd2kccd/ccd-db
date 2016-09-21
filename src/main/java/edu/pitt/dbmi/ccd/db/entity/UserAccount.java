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

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * Jul 23, 2015 3:00:57 PM
 *
 * @since v0.4.0
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "UserAccount", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class UserAccount implements Serializable {

    private static final long serialVersionUID = -7488372819059058929L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "personId", nullable = false)
    private Person person;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userLoginId", nullable = false)
    private UserLogin userLogin;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userLoginAttemptId", nullable = false)
    private UserLoginAttempt userLoginAttempt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userRoleId", nullable = false)
    private UserRole userRole;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "disabled", nullable = false)
    private boolean disabled;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registrationDate", nullable = false, length = 19)
    private Date registrationDate;

    @Column(name = "registrationLocation")
    private Long registrationLocation;

    @Column(name = "account", nullable = false)
    private String account;

    @Column(name = "activationKey")
    private String activationKey;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private Set<ShareGroup> shareGroups = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "members")
    private Set<ShareGroup> shareGroupMemberships = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requesters")
    private Set<ShareGroup> shareGroupRequests = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<AnnotationTarget> annotationTargets = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userAccount")
    private Set<File> files = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userAccount")
    private Set<Address> addresses = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userAccount")
    private Set<Annotation> annotations = new HashSet<>(0);

    public UserAccount() {
    }

    public UserAccount(Person person, UserLogin userLogin, UserLoginAttempt userLoginAttempt, UserRole userRole, String username, String password, boolean active, boolean disabled, Date registrationDate, String account) {
        this.person = person;
        this.userLogin = userLogin;
        this.userLoginAttempt = userLoginAttempt;
        this.userRole = userRole;
        this.username = username;
        this.password = password;
        this.active = active;
        this.disabled = disabled;
        this.registrationDate = registrationDate;
        this.account = account;
    }

    public UserAccount(Person person, UserLogin userLogin, UserLoginAttempt userLoginAttempt, UserRole userRole, String username, String password, boolean active, boolean disabled, Date registrationDate, Long registrationLocation, String account, String activationKey, Set<ShareGroup> shareGroups, Set<AnnotationTarget> annotationTargets, Set<File> files, Set<Annotation> annotations) {
        this.person = person;
        this.userLogin = userLogin;
        this.userLoginAttempt = userLoginAttempt;
        this.userRole = userRole;
        this.username = username;
        this.password = password;
        this.active = active;
        this.disabled = disabled;
        this.registrationDate = registrationDate;
        this.registrationLocation = registrationLocation;
        this.account = account;
        this.activationKey = activationKey;
        this.shareGroups = shareGroups;
        this.annotationTargets = annotationTargets;
        this.files = files;
        this.annotations = annotations;
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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
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

    public Set<ShareGroup> getShareGroups() {
        return shareGroups;
    }

    public void setShareGroups(Set<ShareGroup> shareGroups) {
        this.shareGroups = shareGroups;
    }

    public Set<ShareGroup> getShareGroupMemberships() {
        return shareGroupMemberships;
    }

    public void setShareGroupMemberships(Set<ShareGroup> shareGroupMemberships) {
        this.shareGroupMemberships= shareGroupMemberships;
    }

    public Set<ShareGroup> getShareGroupRequests() {
        return shareGroupRequests;
    }

    public void setShareGroupRequests(Set<ShareGroup> shareGroupRequests) {
        this.shareGroupRequests = shareGroupRequests;
    }

    public Set<AnnotationTarget> getAnnotationTargets() {
        return annotationTargets;
    }

    public void setAnnotationTargets(Set<AnnotationTarget> annotationTargets) {
        this.annotationTargets = annotationTargets;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

}
