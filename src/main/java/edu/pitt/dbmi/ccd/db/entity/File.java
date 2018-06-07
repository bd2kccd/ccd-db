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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    , @UniqueConstraint(columnNames = {"fileName", "userAccountId"})})
@XmlRootElement
public class File implements Serializable {

    private static final long serialVersionUID = -5133608873242264691L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "fileName", nullable = false, length = 255)
    private String fileName;

    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Lob
    @Column(name = "description", length = 65535)
    private String description;

    @Basic(optional = false)
    @Column(name = "creationTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Basic(optional = false)
    @Column(name = "fileSize", nullable = false)
    private long fileSize;

    @Column(name = "md5CheckSum", length = 32)
    private String md5CheckSum;

    @Basic(optional = false)
    @Column(name = "relativePath", nullable = false, length = 255)
    private String relativePath;

    @JoinColumn(name = "fileFormatId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private FileFormat fileFormat;

    @JoinColumn(name = "userAccountId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "files", fetch = FetchType.LAZY)
    private List<FileGroup> fileGroups;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "file", fetch = FetchType.LAZY)
    private TetradDataFile tetradDataFile;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "file", fetch = FetchType.LAZY)
    private TetradVariableFile tetradVariableFile;

    public File() {
    }

    public File(String fileName, String name, Date creationTime, long fileSize, String relativePath, UserAccount userAccount) {
        this.fileName = fileName;
        this.name = name;
        this.creationTime = creationTime;
        this.fileSize = fileSize;
        this.relativePath = relativePath;
        this.userAccount = userAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    @JsonIgnore
    @XmlTransient
    public FileFormat getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(FileFormat fileFormat) {
        this.fileFormat = fileFormat;
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
    public List<FileGroup> getFileGroups() {
        return fileGroups;
    }

    public void setFileGroups(List<FileGroup> fileGroups) {
        this.fileGroups = fileGroups;
    }

    @JsonIgnore
    @XmlTransient
    public TetradDataFile getTetradDataFile() {
        return tetradDataFile;
    }

    public void setTetradDataFile(TetradDataFile tetradDataFile) {
        this.tetradDataFile = tetradDataFile;
    }

    @JsonIgnore
    @XmlTransient
    public TetradVariableFile getTetradVariableFile() {
        return tetradVariableFile;
    }

    public void setTetradVariableFile(TetradVariableFile tetradVariableFile) {
        this.tetradVariableFile = tetradVariableFile;
    }

}
