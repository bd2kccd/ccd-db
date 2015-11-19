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
import javax.persistence.OneToMany;

/**
 *
 * Oct 6, 2015 11:05:57 AM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
public class UserRole implements Serializable {

    private static final long serialVersionUID = -7218841574052268044L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "UserAccountUserRoleRel", joinColumns = {
    //     @JoinColumn(name = "userRoleId", nullable = false, updatable = false)}, inverseJoinColumns = {
    //     @JoinColumn(name = "userAccountId", nullable = false, updatable = false)})
    // private Set<UserAccount> userAccounts = new HashSet<>(0);

    @OneToMany(mappedBy="role", fetch=FetchType.LAZY)
    private Set<UserAccount> userAccounts = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserRoleRolePermissionRel", joinColumns = {
        @JoinColumn(name = "userRoleId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "rolePermissionId", nullable = false, updatable = false)})
    private Set<RolePermission> rolePermissions = new HashSet<>(0);

    public UserRole() {
    }

    public UserRole(String name) {
        this.name = name;
    }

    public UserRole(String name, String description) {
        this(name);
        this.description = description;
    }

    public UserRole(String name, String description, Set<UserAccount> userAccounts, Set<RolePermission> rolePermissions) {
        this(name, description);
        this.userAccounts = userAccounts;
        this.rolePermissions = rolePermissions;
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

    public Set<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Set<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

    public Set<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(Set<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

}