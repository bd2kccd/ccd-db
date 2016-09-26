/*
 * Copyright (C) 2016 University of Pittsburgh.
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
import javax.persistence.UniqueConstraint;

/**
 *
 * Aug 9, 2016 9:22:22 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "DataFile", uniqueConstraints = @UniqueConstraint(columnNames = "fileId"))
public class DataFile implements Serializable {

    private static final long serialVersionUID = 3907404589794031396L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fileId", unique = true, nullable = false)
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fileDelimiterId", nullable = false)
    private FileDelimiter fileDelimiter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variableTypeId", nullable = false)
    private VariableType variableType;

    @Column(name = "numOfRows")
    private Integer numOfRows;

    @Column(name = "numOfColumns")
    private Integer numOfColumns;

    public DataFile() {
    }

    public DataFile(File file, FileDelimiter fileDelimiter, VariableType variableType) {
        this.file = file;
        this.fileDelimiter = fileDelimiter;
        this.variableType = variableType;
    }

    public DataFile(File file, FileDelimiter fileDelimiter, VariableType variableType, Integer numOfRows, Integer numOfColumns) {
        this.file = file;
        this.fileDelimiter = fileDelimiter;
        this.variableType = variableType;
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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

}
