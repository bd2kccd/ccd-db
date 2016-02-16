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
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.CreatedBy;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.annotations.NaturalId;

/** 
* @author Mark Silvis  (marksilvis@pitt.edu)
*/
@Entity
@Table(name="Groups")
public class Group implements Serializable {
    
    private static final long serialVersionUID = 8879813170966961889L;

    @Id
    @GeneratedValue
    public Long id;

    @NotBlank
    @Size(min=4, max=128)
    @Column(length=128, unique=true, nullable=false)
    @NaturalId(mutable=false)
    private String name;

    @NotBlank
    @Size(max=500)
    @Column(length=500, nullable=false)
    private String description;

    @CreatedBy
    private UserAccount creator;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "GroupAdministration",
        joinColumns = { @JoinColumn(name="groupId") },
        inverseJoinColumns = { @JoinColumn(name="userAccountId") })
    private Set<UserAccount> admins = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "GroupMembership",
        joinColumns = { @JoinColumn(name="groupId") },
        inverseJoinColumns = { @JoinColumn(name="userAccountId") })
    private Set<UserAccount> members = new HashSet<>(0);

    public Group() { }

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
        this.creator = null;
    }

    public Group(String name, String description, UserAccount creator) {
        this(name, description);
        this.creator = creator;
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

    public UserAccount getCreator() {
        return creator;
    }

    public Set<UserAccount> getAdmins() {
        return admins;
    }

    public boolean hasAdmin(UserAccount admin) {
        return admins.contains(admin);
    }

    public boolean hasAdmins(UserAccount... admins) {
        return hasAdmins(Arrays.asList(admins));
    }

    public boolean hasAdmins(Collection<UserAccount> admins) {
        return this.admins.containsAll(admins);
    }

    public void addAdmin(UserAccount admin) {
        admins.add(admin);
    }

    public void addAdmins(UserAccount... admins) {
        for (UserAccount a : admins) {
            addAdmin(a);
        }
    }

    public void addAdmins(Collection<UserAccount> admins) {
        this.admins.addAll(admins);
    }

    public void removeAdmin(UserAccount admin) {
        admins.remove(admin);
    }

    public void removeAdmins(UserAccount... admins) {
        for (UserAccount a : admins) {
            removeAdmin(a);
        }
    }

    public void removeAdmins(Collection<UserAccount> admins) {
        this.admins.removeAll(admins);
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
}
