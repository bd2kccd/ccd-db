/*
 * Copyright (C) 2017 University of Pittsburgh.
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * Aug 23, 2017 12:01:24 AM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "JobSubmission")
@XmlRootElement
public class JobSubmission implements Serializable {

    private static final long serialVersionUID = 8279332110087598424L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "description", length = 255)
    private String description;

    @Basic(optional = false)
    @Column(name = "submitDate", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date submitDate;

    @Column(name = "startDate", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "endDate", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @JoinColumn(name = "jobLocationId", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private JobLocation jobLocationId;

    @JoinColumn(name = "jobStatusId", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private JobStatus jobStatusId;

    @JoinColumn(name = "algorithmTypeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private AlgorithmType algorithmTypeId;

    @JoinColumn(name = "userAccountId", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserAccount userAccountId;

    public JobSubmission() {
    }

    public JobSubmission(Date submitDate, JobLocation jobLocationId, JobStatus jobStatusId, AlgorithmType algorithmTypeId, UserAccount userAccountId) {
        this.submitDate = submitDate;
        this.jobLocationId = jobLocationId;
        this.jobStatusId = jobStatusId;
        this.algorithmTypeId = algorithmTypeId;
        this.userAccountId = userAccountId;
    }

    public JobSubmission(String description, Date submitDate, Date startDate, Date endDate, JobLocation jobLocationId, JobStatus jobStatusId, AlgorithmType algorithmTypeId, UserAccount userAccountId) {
        this.description = description;
        this.submitDate = submitDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobLocationId = jobLocationId;
        this.jobStatusId = jobStatusId;
        this.algorithmTypeId = algorithmTypeId;
        this.userAccountId = userAccountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public JobLocation getJobLocationId() {
        return jobLocationId;
    }

    public void setJobLocationId(JobLocation jobLocationId) {
        this.jobLocationId = jobLocationId;
    }

    public JobStatus getJobStatusId() {
        return jobStatusId;
    }

    public void setJobStatusId(JobStatus jobStatusId) {
        this.jobStatusId = jobStatusId;
    }

    public AlgorithmType getAlgorithmTypeId() {
        return algorithmTypeId;
    }

    public void setAlgorithmTypeId(AlgorithmType algorithmTypeId) {
        this.algorithmTypeId = algorithmTypeId;
    }

    @XmlTransient
    @JsonIgnore
    public UserAccount getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(UserAccount userAccountId) {
        this.userAccountId = userAccountId;
    }

}
