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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * May 10, 2017 2:34:12 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "TetradDataFile")
public class TetradDataFile implements Serializable {

    private static final long serialVersionUID = 151820174223230277L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "numOfRows")
    private Integer numOfRows;

    @Column(name = "numOfColumns")
    private Integer numOfColumns;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fileId", nullable = false)
    private File file;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fileDelimiterTypeId", nullable = false)
    private FileDelimiterType fileDelimiterType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fileVariableTypeId", nullable = false)
    private FileVariableType fileVariableType;

    public TetradDataFile() {
    }

    public TetradDataFile(File file, FileDelimiterType fileDelimiterType, FileVariableType fileVariableType) {
        this.file = file;
        this.fileDelimiterType = fileDelimiterType;
        this.fileVariableType = fileVariableType;
    }

    public TetradDataFile(Integer numOfRows, Integer numOfColumns, File file, FileDelimiterType fileDelimiterType, FileVariableType fileVariableType) {
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
        this.file = file;
        this.fileDelimiterType = fileDelimiterType;
        this.fileVariableType = fileVariableType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }

    public Integer getNumOfColumns() {
        return numOfColumns;
    }

    public void setNumOfColumns(Integer numOfColumns) {
        this.numOfColumns = numOfColumns;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileDelimiterType getFileDelimiterType() {
        return fileDelimiterType;
    }

    public void setFileDelimiterType(FileDelimiterType fileDelimiterType) {
        this.fileDelimiterType = fileDelimiterType;
    }

    public FileVariableType getFileVariableType() {
        return fileVariableType;
    }

    public void setFileVariableType(FileVariableType fileVariableType) {
        this.fileVariableType = fileVariableType;
    }

}
