/*
 * Copyright (C) 2018 University of Pittsburgh.
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * Jun 29, 2017 5:19:10 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "userAccountId"})})
@XmlRootElement
public class FileGroup implements Serializable {

    private static final long serialVersionUID = -2212881295372876665L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @JoinColumn(name = "fileTypeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FileType fileType;

    @JoinColumn(name = "userAccountId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @JoinTable(name = "FileGroupFileRel", joinColumns = {
        @JoinColumn(name = "fileGroupId", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "fileId", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<File> files;

    public FileGroup() {
    }

    public FileGroup(String name, Date creationTime, FileType fileType, UserAccount userAccount) {
        this.name = name;
        this.creationTime = creationTime;
        this.fileType = fileType;
        this.userAccount = userAccount;
    }

    public FileGroup(String name, Date creationTime, FileType fileType, UserAccount userAccount, List<File> files) {
        this.name = name;
        this.creationTime = creationTime;
        this.fileType = fileType;
        this.userAccount = userAccount;
        this.files = files;
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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @XmlTransient
    @JsonIgnore
    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    @XmlTransient
    @JsonIgnore
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @XmlTransient
    @JsonIgnore
    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

}
