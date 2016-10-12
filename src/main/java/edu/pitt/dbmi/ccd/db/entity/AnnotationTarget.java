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
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
public class AnnotationTarget implements Serializable {

    private static final long serialVersionUID = 1143695321911902433L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp created;

    private Timestamp modified;

    @Version
    private Integer version;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userAccountId", nullable = false)
    private UserAccount user;

    @NotBlank
    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    @Column(length = 255, unique = false, nullable = false)
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fileId", insertable = true, updatable = true, nullable = true)
    private DataFile file;

    @URL
    @Size(min = 2, max = 2083, message = "Valid URLs are at least 2 characters and no more than 2083 characters")
    @Column(length = 2083, unique = true, nullable = true)
    private String address;

    @OneToMany(mappedBy = "target", fetch = FetchType.LAZY)
    private Set<Annotation> annotations = new HashSet<>(0);

    public AnnotationTarget() {
    }

    public AnnotationTarget(UserAccount user, String title, DataFile file) {
        this.user = user;
        this.title = title;
        this.file = file;
        this.address = null;
    }

    public AnnotationTarget(UserAccount user, String title, String address) {
        this.user = user;
        this.title = title;
        this.file = null;
        this.address = address;
    }

    @PrePersist
    protected void onCreate() {
        created = new Timestamp((new Date()).getTime());
    }

    @PreUpdate
    protected void onUpdate() {
        modified = new Timestamp((new Date()).getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getModified() {
        return modified;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DataFile getFile() {
        return file;
    }

    public void setFile(DataFile file) {
        this.file = file;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
