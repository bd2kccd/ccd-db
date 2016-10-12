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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "Groups")
public class Group implements Serializable {
    private static final long serialVersionUID = 8879813170966961889L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, unique = true, nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GroupModeratoreration",
        joinColumns = {@JoinColumn(name = "groupId")},
        inverseJoinColumns = {@JoinColumn(name = "userAccountId")})
    @OrderBy("username")
    private Set<UserAccount> moderators = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GroupMembership",
            joinColumns = {@JoinColumn(name = "groupId")},
            inverseJoinColumns = {@JoinColumn(name = "userAccountId")})
    @OrderBy("username")
    private Set<UserAccount> members = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GroupRequests",
            joinColumns = {@JoinColumn(name = "groupId")},
            inverseJoinColumns = {@JoinColumn(name = "userAccountId")})
    @OrderBy("username")
    private Set<UserAccount> requesters = new HashSet<>(0);

    public Group() {

    }

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserAccount> getMembers() {
        return members;
    }

    public void setMembers(Set<UserAccount> members) {
        this.members = members;
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

    public Set<UserAccount> getModerators() {
        return moderators;
    }

    public void setModerators(Set<UserAccount> moderators) {
        this.moderators = moderators;
    }


    public boolean hasModerator(UserAccount mod) {
        return moderators.contains(mod);
    }

    public boolean hasModerators(UserAccount... moderators) {
        return hasModerators(Arrays.asList(moderators));
    }

    public boolean hasModerators(Collection<UserAccount> moderators) {
        return this.moderators.containsAll(moderators);
    }

    public void addModerator(UserAccount mod) {
        moderators.add(mod);
    }

    public void addModerators(UserAccount... moderators) {
        for (UserAccount a : moderators) {
            addModerator(a);
        }
    }

    public void addModerators(Collection<UserAccount> moderators) {
        this.moderators.addAll(moderators);
    }

    public void removeModerator(UserAccount mod) {
        moderators.remove(mod);
    }

    public void removeModerators(UserAccount... moderators) {
        for (UserAccount a : moderators) {
            removeModerator(a);
        }
    }

    public void removeModerators(Collection<UserAccount> moderators) {
        this.moderators.removeAll(moderators);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserAccount> getRequesters() {
        return requesters;
    }

    public void setRequesters(Set<UserAccount> requesters) {
        this.requesters = requesters;
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
