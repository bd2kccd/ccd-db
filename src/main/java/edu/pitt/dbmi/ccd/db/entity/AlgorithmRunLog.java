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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * May 31, 2017 3:30:48 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "algorithm_run_log")
public class AlgorithmRunLog implements Serializable {

    private static final long serialVersionUID = -7529192840017249250L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "algo_parameter", nullable = false, length = 65535)
    private String algoParameter;

    @Column(name = "data_file_summary", nullable = false, length = 65535)
    private String dataFileSummary;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "submit_date", nullable = false, length = 19)
    private Date submitDate;

    @Column(name = "algorithm", nullable = false, length = 64)
    private String algorithm;

    @Column(name = "username", nullable = false)
    private String username;

    public AlgorithmRunLog() {
    }

    public AlgorithmRunLog(String algoParameter, String dataFileSummary, Date submitDate, String algorithm, String username) {
        this.algoParameter = algoParameter;
        this.dataFileSummary = dataFileSummary;
        this.submitDate = submitDate;
        this.algorithm = algorithm;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlgoParameter() {
        return algoParameter;
    }

    public void setAlgoParameter(String algoParameter) {
        this.algoParameter = algoParameter;
    }

    public String getDataFileSummary() {
        return dataFileSummary;
    }

    public void setDataFileSummary(String dataFileSummary) {
        this.dataFileSummary = dataFileSummary;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
