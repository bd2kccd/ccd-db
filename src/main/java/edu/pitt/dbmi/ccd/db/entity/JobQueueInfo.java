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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * Aug 3, 2015 10:36:57 AM
 *
 * @author Chirayu Kong Wongchokprasitti, PhD (chw20@pitt.edu)
 */
@Entity
public class JobQueueInfo implements Serializable {

    private static final long serialVersionUID = -6904618236167984743L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "pid")
    private Long pid;

    @Column(name = "algorName", nullable = false)
    private String algorName;

    @Column(name = "commands", nullable = false, length = 65535)
    private String commands;

    @Column(name = "fileName", nullable = false)
    private String fileName;

    @Column(name = "tmpDirectory", nullable = false)
    private String tmpDirectory;

    @Column(name = "outputDirectory", nullable = false)
    private String outputDirectory;

    @Column(name = "status", nullable = false)
    private int status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "addedTime", nullable = false, length = 19)
    private Date addedTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserAccountJobQueueInfoRel", joinColumns = {
        @JoinColumn(name = "jobQueueInfoId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "userAccountId", nullable = false, updatable = false)})
    private Set<UserAccount> userAccounts = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "hpcParameterJobQueueInfoRel", joinColumns = {
        @JoinColumn(name = "jobQueueInfoId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "hpcParameterId", nullable = false, updatable = false)})
    private Set<HpcParameter> hpcParameters = new HashSet<>(0);
    
    public JobQueueInfo() {
    }

    public JobQueueInfo(String algorName, String commands, String fileName, String tmpDirectory, String outputDirectory, int status, Date addedTime) {
        this.algorName = algorName;
        this.commands = commands;
        this.fileName = fileName;
        this.tmpDirectory = tmpDirectory;
        this.outputDirectory = outputDirectory;
        this.status = status;
        this.addedTime = addedTime;
    }

    public JobQueueInfo(Long pid, String algorName, String commands, String fileName, String tmpDirectory, String outputDirectory, int status, Date addedTime, Set<UserAccount> userAccounts) {
        this.pid = pid;
        this.algorName = algorName;
        this.commands = commands;
        this.fileName = fileName;
        this.tmpDirectory = tmpDirectory;
        this.outputDirectory = outputDirectory;
        this.status = status;
        this.addedTime = addedTime;
        this.userAccounts = userAccounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getAlgorName() {
        return algorName;
    }

    public void setAlgorName(String algorName) {
        this.algorName = algorName;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTmpDirectory() {
        return tmpDirectory;
    }

    public void setTmpDirectory(String tmpDirectory) {
        this.tmpDirectory = tmpDirectory;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }

    public Set<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Set<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

}
