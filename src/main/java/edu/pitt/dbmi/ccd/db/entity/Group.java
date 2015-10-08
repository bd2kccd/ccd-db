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

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;

/** 
* @author Mark Silvis  (marksilvis@pitt.edu)
*/

@Entity
@Table(name="Groups")
public class Group extends Identified {
    
    private static final long serialVersionUID = 8879813170966961889L;

    @NotNull
    @Size(min=4, max=128)
    @Column(length=128, unique=true, nullable=false)
    @NaturalId(mutable=true)
    private String name;

    @Column(nullable=true,
            columnDefinition="TEXT")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_membership", 
               joinColumns = { @JoinColumn(name="groupId") },
               inverseJoinColumns = { @JoinColumn(name="profileId")})
    private Set<Person> members;

    public Group() {}

    public Group(String name) {
        this.name = name;
        members = new HashSet<>();
    }

    public Group(String name, Optional<String> description) {
        this(name);
        this.description = description.orElse(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(Optional<String> description) {
        this.description = description.orElse(null);
    }

    public Set<Person> getMembers() {
        return members;
    }

    public boolean hasMember(Person member) {
        return members.contains(member);
    }

    public boolean hasMembers(Collection<Person> members) {
        return this.members.containsAll(members);
    }

    public void addMember(Person member) {
        this.members.add(member);
    }

    public void addMembers(Person... members) {
        for (Person m: members) {
            this.members.add(m);
        }
    }

    public void addMembers(Collection<Person> members) {
        this.members.addAll(members);
    }

    public void removeMember(Person member) {
        members.remove(member);
    }

    public void removeMembers(Person... members) {
        for (Person m: members) {
            this.members.remove(m);
        }
    }

    public void removeMembers(Collection<Person> members) {
        this.members.removeAll(members);
    }
}