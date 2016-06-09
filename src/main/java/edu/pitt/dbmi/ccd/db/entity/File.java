/*
 * Copyright (C) 2016 University of Pittsburgh.
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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * Jun 9, 2016 3:51:12 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
public class File implements Serializable {

    private static final long serialVersionUID = 2772020223622509764L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fileTypeId")
    private FileType fileType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "absolutePath", nullable = false)
    private String absolutePath;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationTime", nullable = false, length = 19)
    private Date creationTime;

    @Column(name = "fileSize", nullable = false)
    private long fileSize;

    @Column(name = "md5CheckSum", length = 32)
    private String md5checkSum;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UserAccountFileRel", joinColumns = {
        @JoinColumn(name = "fileId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)})
    private Set<UserAccount> userAccounts = new HashSet<>(0);

    public File() {
    }

    public File(String name, String absolutePath, Date creationTime, long fileSize) {
        this.name = name;
        this.absolutePath = absolutePath;
        this.creationTime = creationTime;
        this.fileSize = fileSize;
    }

    public File(FileType fileType, String name, String absolutePath, Date creationTime, long fileSize, String md5checkSum, Set<UserAccount> userAccounts) {
        this.fileType = fileType;
        this.name = name;
        this.absolutePath = absolutePath;
        this.creationTime = creationTime;
        this.fileSize = fileSize;
        this.md5checkSum = md5checkSum;
        this.userAccounts = userAccounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMd5checkSum() {
        return md5checkSum;
    }

    public void setMd5checkSum(String md5checkSum) {
        this.md5checkSum = md5checkSum;
    }

    public Set<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Set<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

}
