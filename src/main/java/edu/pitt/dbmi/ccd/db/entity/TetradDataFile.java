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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * May 10, 2017 2:34:12 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "TetradDataFile", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fileId"})})
@XmlRootElement
public class TetradDataFile implements Serializable {

    private static final long serialVersionUID = -2758636916004378451L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "hasHeader", nullable = false)
    private boolean hasHeader;

    @Column(name = "quoteChar")
    private Character quoteChar;

    @Column(name = "missingMarker", length = 8)
    private String missingMarker;

    @Column(name = "commentMarker", length = 8)
    private String commentMarker;

    @Column(name = "numOfLines")
    private Integer numOfLines;

    @Column(name = "numOfCols")
    private Integer numOfCols;

    @JoinColumn(name = "fileId", referencedColumnName = "id", unique = true, nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private File file;

    @JoinColumn(name = "dataDelimiterId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private DataDelimiter dataDelimiter;

    @JoinColumn(name = "variableTypeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private VariableType variableType;

    public TetradDataFile() {
    }

    public TetradDataFile(boolean hasHeader, File file, DataDelimiter dataDelimiter, VariableType variableType) {
        this.hasHeader = hasHeader;
        this.file = file;
        this.dataDelimiter = dataDelimiter;
        this.variableType = variableType;
    }

    public TetradDataFile(boolean hasHeader, Character quoteChar, String missingMarker, String commentMarker, Integer numOfLines, Integer numOfCols, File file, DataDelimiter dataDelimiter, VariableType variableType) {
        this.hasHeader = hasHeader;
        this.quoteChar = quoteChar;
        this.missingMarker = missingMarker;
        this.commentMarker = commentMarker;
        this.numOfLines = numOfLines;
        this.numOfCols = numOfCols;
        this.file = file;
        this.dataDelimiter = dataDelimiter;
        this.variableType = variableType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isHasHeader() {
        return hasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public Character getQuoteChar() {
        return quoteChar;
    }

    public void setQuoteChar(Character quoteChar) {
        this.quoteChar = quoteChar;
    }

    public String getMissingMarker() {
        return missingMarker;
    }

    public void setMissingMarker(String missingMarker) {
        this.missingMarker = missingMarker;
    }

    public String getCommentMarker() {
        return commentMarker;
    }

    public void setCommentMarker(String commentMarker) {
        this.commentMarker = commentMarker;
    }

    public Integer getNumOfLines() {
        return numOfLines;
    }

    public void setNumOfLines(Integer numOfLines) {
        this.numOfLines = numOfLines;
    }

    public Integer getNumOfCols() {
        return numOfCols;
    }

    public void setNumOfCols(Integer numOfCols) {
        this.numOfCols = numOfCols;
    }

    @JsonIgnore
    @XmlTransient
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public DataDelimiter getDataDelimiter() {
        return dataDelimiter;
    }

    public void setDataDelimiter(DataDelimiter dataDelimiter) {
        this.dataDelimiter = dataDelimiter;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }

}
