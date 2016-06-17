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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;

import edu.pitt.dbmi.ccd.db.validation.Name;

/**
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "Groups")
public class Group implements Serializable {

    private static final long serialVersionUID = 8879813170966961889L;

    @Id
    @GeneratedValue
    public Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 4, max = 128, message = "Name must be between 4 and 128 characters")
    @Name
    @Column(length = 128, unique = true, nullable = false)
    @NaturalId(mutable = true)
    private String name;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 500, message = "Description must be no longer than 500 characters")
    @Column(length = 500, nullable = false)
    private String description;

    // Group moderators
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GroupModeration",
            joinColumns = {@JoinColumn(name = "groupId")},
            inverseJoinColumns = {@JoinColumn(name = "userAccountId")})
    @OrderBy("username")
    private Set<UserAccount> mods = new HashSet<>(0);

    // Group members
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GroupMembership",
            joinColumns = {@JoinColumn(name = "groupId")},
            inverseJoinColumns = {@JoinColumn(name = "userAccountId")})
    @OrderBy("username")
    private Set<UserAccount> members = new HashSet<>(0);

    // Users requesting Group membership
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GroupRequests",
            joinColumns = {@JoinColumn(name = "groupId")},
            inverseJoinColumns = {@JoinColumn(name = "userAccountId")})
    private Set<UserAccount> requesters = new HashSet<>(0);

    public Group() {
    }

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<UserAccount> getMods() {
        return mods;
    }

    public boolean hasMod(UserAccount mod) {
        return mods.contains(mod);
    }

    public boolean hasMods(UserAccount... mods) {
        return hasMods(Arrays.asList(mods));
    }

    public boolean hasMods(Collection<UserAccount> mods) {
        return this.mods.containsAll(mods);
    }

    public void addMod(UserAccount mod) {
        mods.add(mod);
    }

    public void addMods(UserAccount... mods) {
        for (UserAccount a : mods) {
            addMod(a);
        }
    }

    public void addMods(Collection<UserAccount> mods) {
        this.mods.addAll(mods);
    }

    public void removeMod(UserAccount mod) {
        mods.remove(mod);
    }

    public void removeMods(UserAccount... mods) {
        for (UserAccount a : mods) {
            removeMod(a);
        }
    }

    public void removeMods(Collection<UserAccount> mods) {
        this.mods.removeAll(mods);
    }

    public Set<UserAccount> getMembers() {
        return members;
    }

    public boolean hasMember(UserAccount member) {
        return members.contains(member);
    }

    public boolean hasMembers(UserAccount... members) {
        return hasMembers(Arrays.asList(members));
    }

    public boolean hasMembers(Collection<UserAccount> members) {
        return this.members.containsAll(members);
    }

    public void addMember(UserAccount member) {
        members.add(member);
    }

    public void addMembers(UserAccount... members) {
        for (UserAccount m : members) {
            addMember(m);
        }
    }

    public void addMembers(Collection<UserAccount> members) {
        this.members.addAll(members);
    }

    public void removeMember(UserAccount member) {
        members.remove(member);
    }

    public void removeMembers(UserAccount... members) {
        for (UserAccount m : members) {
            removeMember(m);
        }
    }

    public void removeMembers(Collection<UserAccount> members) {
        this.members.removeAll(members);
    }

    public Set<UserAccount> getRequesters() {
        return requesters;
    }

    public boolean hasRequester(UserAccount requester) {
        return requesters.contains(requester);
    }

    public boolean hasRequesters(UserAccount... requesters) {
        return hasRequesters(Arrays.asList(requesters));
    }

    public boolean hasRequesters(Collection<UserAccount> requesters) {
        return this.requesters.containsAll(requesters);
    }

    public void addRequester(UserAccount requester) {
        requesters.add(requester);
    }

    public void addRequesters(UserAccount... requesters) {
        for (UserAccount a : requesters) {
            addRequester(a);
        }
    }

    public void addRequesters(Collection<UserAccount> requesters) {
        this.requesters.addAll(requesters);
    }

    public void removeRequester(UserAccount requester) {
        requesters.remove(requester);
    }

    public void removeRequesters(UserAccount... requesters) {
        for (UserAccount a : requesters) {
            removeRequester(a);
        }
    }

    public void removeRequesters(Collection<UserAccount> requesters) {
        this.requesters.removeAll(requesters);
    }
}
