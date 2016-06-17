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

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Jul 23, 2015 1:37:42 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 5707342040828691260L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(nullable = true)
    private String middleName;

    @NotBlank
    @Column(name = "lastName", nullable = false)
    private String lastName;

    @NotNull
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(name = "workspace", nullable = false)
    private String workspace;

    @Size(max = 500, message = "Description must be no longer than 500 characters")
    @Column(length = 500, nullable = true)
    private String description;

    public Person() {
    }

    public Person(String firstName, String lastName, String email, String workspace) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.workspace = workspace;
    }

    public Person(String firstName, String middleName, String lastName, String email, String workspace) {
        this(firstName, lastName, email, workspace);
        this.middleName = middleName;
    }

    public Person(String firstName, String middleName, String lastName, String email, String workspace, String description) {
        this(firstName, middleName, lastName, email, workspace);
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        if (middleName != null) {
            return String.format("%s %s %s", firstName, middleName, lastName);
        } else {
            return String.format("%s %s", firstName, lastName);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
