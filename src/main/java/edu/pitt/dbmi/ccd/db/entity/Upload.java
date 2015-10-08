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
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Entity
public class Upload extends Versioned {
   
    private static final long serialVersionUID = 1143695321911902433L;
    
    @NotNull
    @ManyToOne(optional=false)
    @JoinColumn(name="profileId", nullable=false)
    private Person uploader;

    @NotNull
    @Size(max=255)
    @Column(length=255, unique=false, nullable=false)
    private String title;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="fileId", insertable=true, updatable=true, nullable=true)
    private DataFile file;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="urlId", insertable=true, updatable=true, nullable=true)
    private Url url;

    // @OneToMany(mappedBy="upload", fetch=FetchType.LAZY)
    // private Set<Annotation> annotations;

    public Upload() {
        // annotations = new HashSet<>();
    }

    private Upload(Person uploader) {
        this();
        this.uploader = uploader;
    }

    public Upload(Person uploader, String title, DataFile file) {
        this(uploader);
        this.title = title;
        this.file = file;
    }

    public Upload(Person uploader, String title, Url url) {
        this(uploader);
        this.title = title;
        this.url = url;
    }

    public Person getUploader() {
        return uploader;
    }

    public void setUploader(Person uploader) {
        this.uploader = uploader;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Optional<DataFile> getFile() {
        return Optional.ofNullable(file);
    }

    public void setFile(DataFile file) {
        this.file = file;
    }

    public Optional<Url> getUrl() {
        return Optional.ofNullable(url);
    }

    public void setUrl(Url url) {
        this.url = url;
    }
}