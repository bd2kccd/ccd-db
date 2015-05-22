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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * May 18, 2015 12:12:43 PM
 *
 * @author Chirayu (Kong) Wongchokprasitti (chw20@pitt.edu)
 * 
 */
@Entity
public class FileInfo implements Serializable {
	
	private static final long serialVersionUID = -7162073551033209140L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
    @Column(name = "fileName", nullable = false)
	private String fileName;
	
	@Column(name = "filePath", nullable = false)
	private String filePath;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationTime", nullable = false, length = 19)
	private Date creationTime; 
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastAccessTime", nullable = false, length = 19)
	private Date lastAccessTime; 
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastModifiedTime", nullable = false, length = 19)
	private Date lastModifiedTime; 
	
	@Column(name = "fileSize", nullable = false)
	private Long fileSize;
	
    @Column(name = "md5CheckSum", nullable = false)
	private String md5CheckSum;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userAccountId", nullable = false)
    private UserAccount userAccount;
    
	public FileInfo() {
	}

    /**
	 * @param fileName
	 * @param absolutePath
	 * @param creationTime
	 * @param lastAccessTime
	 * @param lastModifiedTime
	 * @param fileSize
	 * @param md5CheckSum
	 * @param userAccount
	 */
	public FileInfo(String fileName, String absolutePath, Date creationTime,
			Date lastAccessTime, Date lastModifiedTime, Long fileSize,
			String md5CheckSum, UserAccount userAccount) {
		this.fileName = fileName;
		this.filePath = absolutePath;
		this.creationTime = creationTime;
		this.lastAccessTime = lastAccessTime;
		this.lastModifiedTime = lastModifiedTime;
		this.fileSize = fileSize;
		this.md5CheckSum = md5CheckSum;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getMd5CheckSum() {
		return md5CheckSum;
	}

	public void setMd5CheckSum(String md5CheckSum) {
		this.md5CheckSum = md5CheckSum;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
