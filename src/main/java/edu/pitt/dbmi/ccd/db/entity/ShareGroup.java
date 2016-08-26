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
    @JoinColumn(name = "owner", nullable = false)
    private UserAccount owner;

    @Column(name = "name", unique = true, nullable = false, length = 127)
    private String name;

    @Column(name = "description", nullable = false, length = 511)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ShareGroupMembership", joinColumns = {
        @JoinColumn(name = "shareGroupId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)})
    private Set<UserAccount> members = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ShareGroupRequests", joinColumns = {
        @JoinColumn(name = "shareGroupId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)})
    private Set<UserAccount> requesters = new HashSet<>(0);

    public ShareGroup() {
    }

    public ShareGroup(UserAccount owner, String name, String description) {
        this.owner = owner;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getOwner() {
        return owner;
    }

    public void setOwner(UserAccount owner) {
        this.owner = owner;
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

    public Set<UserAccount> getMembers() {
        return members;
    }

    public void setMembers(Set<UserAccount> members) {
        this.members = members;
    }

    public boolean hasMember(UserAccount userAccount) {
        return this.members.contains(userAccount);
    }

    public boolean addMember(UserAccount userAccount) {
        return this.members.add(userAccount);
    }

    public boolean removeMember(UserAccount userAccount) {
        return this.members.remove(userAccount);
    }

    public Set<UserAccount> getRequesters() {
        return requesters;
    }

    public void setRequesters(Set<UserAccount> requesters) {
        this.requesters = requesters;
    }

    public boolean hasRequester(UserAccount userAccount) {
        return this.requesters.contains(userAccount);
    }

    public boolean addRequester(UserAccount userAccount) {
        return this.requesters.add(userAccount);
    }

    public boolean removeRequester(UserAccount userAccount) {
        return this.requesters.remove(userAccount);
    }
}
