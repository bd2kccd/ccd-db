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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * Jan 28, 2018 4:58:46 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "File", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "userAccountId"})
    , @UniqueConstraint(columnNames = {"title", "userAccountId"})})
@XmlRootElement
public class File implements Serializable {

    private static final long serialVersionUID = -8323975281387418754L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Basic(optional = false)
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Basic(optional = false)
    @Column(name = "creationTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Basic(optional = false)
    @Column(name = "fileSize", nullable = false)
    private long fileSize;

    @Column(name = "md5CheckSum", length = 32)
    private String md5CheckSum;

    @JoinColumn(name = "userAccountId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @JoinColumn(name = "fileFormatId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private FileFormat fileFormat;

    public File() {
    }

    public File(String name, String title, Date creationTime, long fileSize, UserAccount userAccount) {
        this.name = name;
        this.title = title;
        this.creationTime = creationTime;
        this.fileSize = fileSize;
        this.userAccount = userAccount;
    }

    public File(String name, String title, Date creationTime, long fileSize, String md5CheckSum, UserAccount userAccount, FileFormat fileFormat) {
        this.name = name;
        this.title = title;
        this.creationTime = creationTime;
        this.fileSize = fileSize;
        this.md5CheckSum = md5CheckSum;
        this.userAccount = userAccount;
        this.fileFormat = fileFormat;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getMd5CheckSum() {
        return md5CheckSum;
    }

    public void setMd5CheckSum(String md5CheckSum) {
        this.md5CheckSum = md5CheckSum;
    }

    @JsonIgnore
    @XmlTransient
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @JsonIgnore
    @XmlTransient
    public FileFormat getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(FileFormat fileFormat) {
        this.fileFormat = fileFormat;
    }

}
