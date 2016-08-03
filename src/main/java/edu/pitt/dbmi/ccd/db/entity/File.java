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
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * Jun 9, 2016 3:51:12 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "File", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "userAccountId"}),
    @UniqueConstraint(columnNames = {"title", "userAccountId"})}
)
public class File implements Serializable {

    private static final long serialVersionUID = 6534837967736800011L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fileTypeId")
    private FileType fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userAccountId", nullable = false)
    private UserAccount userAccount;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "absolutePath", nullable = false)
    private String absolutePath;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationTime", nullable = false, length = 19)
    private Date creationTime;

    @Column(name = "fileSize", nullable = false)
    private long fileSize;

    @Column(name = "md5CheckSum", length = 32)
    private String md5checkSum;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file")
    private Set<VariableFile> variableFiles = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file")
    private Set<AnnotationTarget> annotationTargets = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file")
    private Set<DataFile> dataFiles = new HashSet<>(0);

    public File() {
    }

    public File(UserAccount userAccount, String name, String title, String absolutePath, Date creationTime, long fileSize) {
        this.userAccount = userAccount;
        this.name = name;
        this.title = title;
        this.absolutePath = absolutePath;
        this.creationTime = creationTime;
        this.fileSize = fileSize;
    }

    public File(FileType fileType, UserAccount userAccount, String name, String title, String absolutePath, Date creationTime, long fileSize, String md5checkSum, Set<VariableFile> variableFiles, Set<AnnotationTarget> annotationTargets, Set<DataFile> dataFiles) {
        this.fileType = fileType;
        this.userAccount = userAccount;
        this.name = name;
        this.title = title;
        this.absolutePath = absolutePath;
        this.creationTime = creationTime;
        this.fileSize = fileSize;
        this.md5checkSum = md5checkSum;
        this.variableFiles = variableFiles;
        this.annotationTargets = annotationTargets;
        this.dataFiles = dataFiles;
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

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
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

    public Set<VariableFile> getVariableFiles() {
        return variableFiles;
    }

    public void setVariableFiles(Set<VariableFile> variableFiles) {
        this.variableFiles = variableFiles;
    }

    public Set<AnnotationTarget> getAnnotationTargets() {
        return annotationTargets;
    }

    public void setAnnotationTargets(Set<AnnotationTarget> annotationTargets) {
        this.annotationTargets = annotationTargets;
    }

    public Set<DataFile> getDataFiles() {
        return dataFiles;
    }

    public void setDataFiles(Set<DataFile> dataFiles) {
        this.dataFiles = dataFiles;
    }

}
