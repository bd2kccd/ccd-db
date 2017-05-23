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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

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

    private static final long serialVersionUID = 151820174223230277L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "quoteChar")
    private Character quoteChar;

    @Column(name = "missingValueMarker", length = 8)
    private String missingValueMarker;

    @Column(name = "commentMarker", length = 8)
    private String commentMarker;

    @Column(name = "numOfRows")
    private Integer numOfRows;

    @Column(name = "numOfColumns")
    private Integer numOfColumns;

    @JoinColumn(name = "fileId", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private File file;

    @JoinColumn(name = "fileDelimiterTypeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private FileDelimiterType fileDelimiterType;

    @JoinColumn(name = "fileVariableTypeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private FileVariableType fileVariableType;

    public TetradDataFile() {
    }

    public TetradDataFile(File file, FileDelimiterType fileDelimiterType, FileVariableType fileVariableType) {
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

    public Character getQuoteChar() {
        return quoteChar;
    }

    public void setQuoteChar(Character quoteChar) {
        this.quoteChar = quoteChar;
    }

    public String getMissingValueMarker() {
        return missingValueMarker;
    }

    public void setMissingValueMarker(String missingValueMarker) {
        this.missingValueMarker = missingValueMarker;
    }

    public String getCommentMarker() {
        return commentMarker;
    }

    public void setCommentMarker(String commentMarker) {
        this.commentMarker = commentMarker;
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
