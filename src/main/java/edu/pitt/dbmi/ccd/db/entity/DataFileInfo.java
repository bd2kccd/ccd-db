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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * Jul 20, 2015 10:12:45 AM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
public class DataFileInfo implements Serializable {

    private static final long serialVersionUID = -5136383168139014898L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dataFileId", nullable = false)
    private DataFile dataFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fileDelimiterId")
    private FileDelimiter fileDelimiter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variableTypeId")
    private VariableType variableType;

    @Column(name = "md5CheckSum", length = 128)
    private String md5checkSum;

    @Column(name = "numOfRows")
    private Integer numOfRows;

    @Column(name = "numOfColumns")
    private Integer numOfColumns;

    @Column(name = "missingValue")
    private Boolean missingValue;

    public DataFileInfo() {
    }

    public DataFileInfo(DataFile dataFile) {
        this.dataFile = dataFile;
    }

    public DataFileInfo(DataFile dataFile, FileDelimiter fileDelimiter, VariableType variableType, String md5checkSum, Integer numOfRows, Integer numOfColumns, Boolean missingValue) {
        this.dataFile = dataFile;
        this.fileDelimiter = fileDelimiter;
        this.variableType = variableType;
        this.md5checkSum = md5checkSum;
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
        this.missingValue = missingValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(DataFile dataFile) {
        this.dataFile = dataFile;
    }

    public FileDelimiter getFileDelimiter() {
        return fileDelimiter;
    }

    public void setFileDelimiter(FileDelimiter fileDelimiter) {
        this.fileDelimiter = fileDelimiter;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }

    public String getMd5checkSum() {
        return md5checkSum;
    }

    public void setMd5checkSum(String md5checkSum) {
        this.md5checkSum = md5checkSum;
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

    public Boolean getMissingValue() {
        return missingValue;
    }

    public void setMissingValue(Boolean missingValue) {
        this.missingValue = missingValue;
    }

}
