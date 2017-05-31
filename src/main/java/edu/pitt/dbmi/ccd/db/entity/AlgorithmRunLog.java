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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "algorithm_type_id", nullable = false)
    private Algorithm algorithm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;

    public AlgorithmRunLog() {
    }

    public AlgorithmRunLog(String algoParameter, String dataFileSummary, Date submitDate, Algorithm algorithm, UserAccount userAccount) {
        this.algorithm = algorithm;
        this.userAccount = userAccount;
        this.algoParameter = algoParameter;
        this.dataFileSummary = dataFileSummary;
        this.submitDate = submitDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
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

}
