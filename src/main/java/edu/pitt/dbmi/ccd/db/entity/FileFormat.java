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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * May 10, 2017 12:27:02 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "FileFormat", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@XmlRootElement
public class FileFormat implements Serializable {

    private static final long serialVersionUID = -2456952936310636255L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "name", unique = true, nullable = false, length = 32)
    private String name;

    @Basic(optional = false)
    @Column(name = "displayName", nullable = false, length = 64)
    private String displayName;

    @JoinColumn(name = "fileTypeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private FileType fileType;

    @JoinTable(name = "FileFormatAlgorithmRel", joinColumns = {
        @JoinColumn(name = "fileFormatId", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "algorithmTypeId", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AlgorithmType> algorithmTypes;

    public FileFormat() {
    }

    public FileFormat(String name, String displayName, FileType fileType) {
        this.name = name;
        this.displayName = displayName;
        this.fileType = fileType;
    }

    public FileFormat(String name, String displayName, FileType fileType, List<AlgorithmType> algorithmTypes) {
        this.name = name;
        this.displayName = displayName;
        this.fileType = fileType;
        this.algorithmTypes = algorithmTypes;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<AlgorithmType> getAlgorithmTypes() {
        return algorithmTypes;
    }

    public void setAlgorithmTypes(List<AlgorithmType> algorithmTypes) {
        this.algorithmTypes = algorithmTypes;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

}
