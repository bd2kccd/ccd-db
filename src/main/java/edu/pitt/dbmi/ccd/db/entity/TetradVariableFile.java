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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * May 10, 2017 2:32:30 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "TetradVariableFile", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fileId"})})
@XmlRootElement
public class TetradVariableFile implements Serializable {

    private static final long serialVersionUID = 564704246069425855L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "numOfVariables")
    private Integer numOfVariables;

    @JoinColumn(name = "fileId", referencedColumnName = "id", unique = true, nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private File file;

    public TetradVariableFile() {
    }

    public TetradVariableFile(File file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumOfVariables() {
        return numOfVariables;
    }

    public void setNumOfVariables(Integer numOfVariables) {
        this.numOfVariables = numOfVariables;
    }

    @JsonIgnore
    @XmlTransient
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
