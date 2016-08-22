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
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * Aug 3, 2016 12:30:59 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "ShareGroup", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ShareGroup implements Serializable {

    private static final long serialVersionUID = 2370827482314435777L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userAccountId", nullable = false)
    private UserAccount userAccount;

    @Column(name = "name", unique = true, nullable = false, length = 127)
    private String name;

    @Column(name = "description", nullable = false, length = 511)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ShareGroupMembership", joinColumns = {
        @JoinColumn(name = "shareGroupId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)})
    private Set<UserAccount> shareGroupMemberships = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shareGroup")
    private Set<Annotation> annotations = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ShareGroupRequest", joinColumns = {
        @JoinColumn(name = "shareGroupId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)})
    private Set<UserAccount> shareGroupRequests = new HashSet<>(0);

    public ShareGroup() {
    }

    public ShareGroup(UserAccount userAccount, String name, String description) {
        this.userAccount = userAccount;
        this.name = name;
        this.description = description;
    }

    public ShareGroup(UserAccount userAccount, String name, String description, Set<UserAccount> userAccounts, Set<Annotation> annotations, Set<UserAccount> userAccounts_1) {
        this.userAccount = userAccount;
        this.name = name;
        this.description = description;
        this.shareGroupMemberships = userAccounts;
        this.annotations = annotations;
        this.shareGroupRequests = userAccounts_1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserAccount> getShareGroupMemberships() {
        return shareGroupMemberships;
    }

    public void setShareGroupMemberships(Set<UserAccount> shareGroupMemberships) {
        this.shareGroupMemberships = shareGroupMemberships;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

    public Set<UserAccount> getShareGroupRequests() {
        return shareGroupRequests;
    }

    public void setShareGroupRequests(Set<UserAccount> shareGroupRequests) {
        this.shareGroupRequests = shareGroupRequests;
    }

}
