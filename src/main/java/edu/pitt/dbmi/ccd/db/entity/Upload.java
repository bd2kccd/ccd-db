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
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Entity
public class Upload implements Serializable {
   
    private static final long serialVersionUID = 1143695321911902433L;
    
    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @Version
    private Integer version;

    @NotNull
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="userAccountId", nullable=false)
    private UserAccount user;

    @NotBlank
    @Size(max=255)
    @Column(length=255, unique=false, nullable=false)
    private String title;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="fileId", insertable=true, updatable=true, nullable=true)
    private DataFile file;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="urlId", insertable=true, updatable=true, nullable=true)
    private Url url;

    @OneToMany(mappedBy="target", fetch=FetchType.LAZY)
    @OrderBy
    private Set<Annotation> annotations = new HashSet<>(0);

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        modified = new Date();
    }

    public Upload() { }

    private Upload(UserAccount user) {
        this();
        this.user = user;
    }

    public Upload(UserAccount user, String title, DataFile file) {
        this(user);
        this.title = title;
        this.file = file;
    }

    public Upload(UserAccount user, String title, Url url) {
        this(user);
        this.title = title;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getUploader() {
        return user;
    }

    public void setUploader(UserAccount user) {
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

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }
}