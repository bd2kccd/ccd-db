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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * Aug 23, 2017 12:00:07 AM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "JobParameter")
@XmlRootElement
public class JobParameter implements Serializable {

    private static final long serialVersionUID = -8905069378581351993L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "param", nullable = false, length = 64)
    private String param;

    @Column(name = "paramValue", length = 64)
    private String paramValue;

    @JoinColumn(name = "jobParameterTypeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private JobParameterType jobParameterType;

    @JoinColumn(name = "jobSubmissionId", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private JobSubmission jobSubmission;

    public JobParameter() {
    }

    public JobParameter(String param, JobParameterType jobParameterType, JobSubmission jobSubmission) {
        this.param = param;
        this.jobParameterType = jobParameterType;
        this.jobSubmission = jobSubmission;
    }

    public JobParameter(String param, String paramValue, JobParameterType jobParameterType, JobSubmission jobSubmission) {
        this.param = param;
        this.paramValue = paramValue;
        this.jobParameterType = jobParameterType;
        this.jobSubmission = jobSubmission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @XmlTransient
    @JsonIgnore
    public JobParameterType getJobParameterType() {
        return jobParameterType;
    }

    public void setJobParameterType(JobParameterType jobParameterType) {
        this.jobParameterType = jobParameterType;
    }

    @XmlTransient
    @JsonIgnore
    public JobSubmission getJobSubmission() {
        return jobSubmission;
    }

    public void setJobSubmission(JobSubmission jobSubmission) {
        this.jobSubmission = jobSubmission;
    }

}
