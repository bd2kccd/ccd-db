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
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
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
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.annotations.NaturalId;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @NaturalId(mutable=true)
    private String name;

    @NotBlank
    @Size(max=500)
    @Column(length=500, nullable=false)
    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_membership", 
               joinColumns = { @JoinColumn(name="groupId") },
               inverseJoinColumns = { @JoinColumn(name="userAccountId")})
    private Set<UserAccount> members = new HashSet<>(0);

    public Group() { }

    public Group(String name) {
        this.name = name;
    }

    public Group(String name, String description) {
        this(name);
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

    public Set<UserAccount> getMembers() {
        return members;
    }

    public boolean hasMember(UserAccount member) {
        return members.contains(member);
    }

    public boolean hasMembers(Collection<UserAccount> members) {
        return this.members.containsAll(members);
    }

    public void addMember(UserAccount member) {
        this.members.add(member);
    }

    public void addMembers(UserAccount... members) {
        for (UserAccount m: members) {
            this.members.add(m);
        }
    }

    public void addMembers(Collection<UserAccount> members) {
        this.members.addAll(members);
    }

    public void removeMember(UserAccount member) {
        members.remove(member);
    }

    public void removeMembers(UserAccount... members) {
        for (UserAccount m: members) {
            this.members.remove(m);
        }
    }

    public void removeMembers(Collection<UserAccount> members) {
        this.members.removeAll(members);
    }
}