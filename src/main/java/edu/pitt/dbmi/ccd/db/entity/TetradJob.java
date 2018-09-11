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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * Aug 27, 2018 2:31:40 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "TetradJob", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"jobDetailId"})})
@XmlRootElement
public class TetradJob implements Serializable {

    private static final long serialVersionUID = -2878484166188015948L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "tetradDataFileId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private TetradDataFile tetradDataFile;

    @JoinColumn(name = "fileGroupId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private FileGroup fileGroup;

    @JoinColumn(name = "tetradVariableFileId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private TetradVariableFile tetradVariableFile;

    @JoinColumn(name = "knowledgeFileId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private File knowledgeFile;

    @Column(name = "jvmParameter", length = 255)
    private String jvmParameter;

    @Basic(optional = false)
    @Column(name = "algorithm", nullable = false, length = 255)
    private String algorithm;

    @Lob
    @Column(name = "algorithmParameter", length = 65535)
    private String algorithmParameter;

    @JoinColumn(name = "jobDetailId", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private JobDetail jobDetail;

    public TetradJob() {
    }

    public TetradJob(String algorithm, JobDetail jobDetail) {
        this.algorithm = algorithm;
        this.jobDetail = jobDetail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TetradDataFile getTetradDataFile() {
        return tetradDataFile;
    }

    public void setTetradDataFile(TetradDataFile tetradDataFile) {
        this.tetradDataFile = tetradDataFile;
    }

    public FileGroup getFileGroup() {
        return fileGroup;
    }

    public void setFileGroup(FileGroup fileGroup) {
        this.fileGroup = fileGroup;
    }

    public TetradVariableFile getTetradVariableFile() {
        return tetradVariableFile;
    }

    public void setTetradVariableFile(TetradVariableFile tetradVariableFile) {
        this.tetradVariableFile = tetradVariableFile;
    }

    public File getKnowledgeFile() {
        return knowledgeFile;
    }

    public void setKnowledgeFile(File knowledgeFile) {
        this.knowledgeFile = knowledgeFile;
    }

    public String getJvmParameter() {
        return jvmParameter;
    }

    public void setJvmParameter(String jvmParameter) {
        this.jvmParameter = jvmParameter;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithmParameter() {
        return algorithmParameter;
    }

    public void setAlgorithmParameter(String algorithmParameter) {
        this.algorithmParameter = algorithmParameter;
    }

    @JsonIgnore
    @XmlTransient
    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }

}
