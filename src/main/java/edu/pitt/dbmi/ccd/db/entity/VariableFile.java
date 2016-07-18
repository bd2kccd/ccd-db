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
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * Jul 18, 2016 2:36:28 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
public class VariableFile implements Serializable {

    private static final long serialVersionUID = -2848985981108384222L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "numOfVars")
    private Integer numOfVars;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fileId", nullable = false)
    private File file;

    public VariableFile() {
    }

    public VariableFile(Long id, File file) {
        this.id = id;
        this.file = file;
    }

    public VariableFile(Long id, Integer numOfVars, File file) {
        this.id = id;
        this.numOfVars = numOfVars;
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumOfVars() {
        return numOfVars;
    }

    public void setNumOfVars(Integer numOfVars) {
        this.numOfVars = numOfVars;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
