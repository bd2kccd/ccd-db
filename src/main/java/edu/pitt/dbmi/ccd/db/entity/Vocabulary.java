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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 *
 * Aug 3, 2016 12:31:18 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "Vocabulary", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Vocabulary implements Serializable {

    private static final long serialVersionUID = 1890971510927278914L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Version
    @Column(name = "version", nullable = false, length = 63)
    private String version;

    @Column(name = "name", unique = true, nullable = false, length = 127)
    private String name;

    @Column(name = "description", nullable = false, length = 511)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", nullable = false, length = 19)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedDate", length = 19)
    private Date modifiedDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vocabulary")
    private Set<Attribute> attributes = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vocabulary")
    private Set<Annotation> annotations = new HashSet<>(0);

    public Vocabulary() {
    }

    public Vocabulary(String name, String description, Date createdDate) {
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
    }

    public Vocabulary(String name, String description, Date createdDate, Date modifiedDate, Set<Attribute> attributes, Set<Annotation> annotations) {
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.attributes = attributes;
        this.annotations = annotations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

}
