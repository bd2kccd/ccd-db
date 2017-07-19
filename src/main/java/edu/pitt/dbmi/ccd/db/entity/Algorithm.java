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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Jul 19, 2017 12:17:54 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@XmlRootElement
@Table(name = "Algorithm", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})})
public class Algorithm implements Serializable {

    private static final long serialVersionUID = -1648477051201124058L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", unique = true, nullable = false, length = 32)
    private String name;

    @Basic(optional = false)
    @Column(name = "title", nullable = false)
    private String title;

    @Basic(optional = false)
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "algorithmTypeId", referencedColumnName = "id", nullable = false)
    private AlgorithmType algorithmType;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "fileVariableTypeId", referencedColumnName = "id", nullable = false)
    private FileVariableType fileVariableType;

    public Algorithm() {
    }

    public Algorithm(String name, String title, String description, AlgorithmType algorithmType, FileVariableType fileVariableType) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.algorithmType = algorithmType;
        this.fileVariableType = fileVariableType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(AlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
    }

    public FileVariableType getFileVariableType() {
        return fileVariableType;
    }

    public void setFileVariableType(FileVariableType fileVariableType) {
        this.fileVariableType = fileVariableType;
    }

}
