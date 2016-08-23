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

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Aug 3, 2016 12:28:58 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "AnnotationData")
public class AnnotationData implements Serializable {

    private static final long serialVersionUID = -6554269784502436864L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annotationId", nullable = false)
    private Annotation annotation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentAnnotationDataId")
    private AnnotationData parentAnnotationData;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attributeId", nullable = false)
    private Attribute attribute;

    @Column(name = "value")
    private String value;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", nullable = false, length = 19)
    private Timestamp createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedDate", nullable = false, length = 19)
    private Timestamp modifiedDate;

    @Version
    @Column(name = "modifyCount", nullable = false)
    private int modifyCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nestedAnnotationData")
    private Set<AnnotationData> nestedAnnotationData = new HashSet<>(0);

    public AnnotationData() {
    }

    public AnnotationData(Annotation annotation, Attribute attribute) {
        this.annotation = annotation;
        this.attribute = attribute;
        this.value = null;
    }

    public AnnotationData(Annotation annotation, Attribute attribute, String value, AnnotationData parentAnnotationData) {
        this(annotation, attribute);
        this.value = value;
        this.parentAnnotationData = parentAnnotationData;
    }

    @PrePersist
    private void onCreate() {
        createdDate = new Timestamp((new Date()).getTime());
    }

    @PreUpdate
    private void onUpdate() {
        modifiedDate = new Timestamp((new Date()).getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public AnnotationData getParentAnnotationData() {
        return parentAnnotationData;
    }

    public void setParentAnnotationData(AnnotationData parentAnnotationData) {
        this.parentAnnotationData = parentAnnotationData;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getModifyCount() {
        return modifyCount;
    }

    public void setModifyCount(int modifyCount) {
        this.modifyCount = modifyCount;
    }

    public Set<AnnotationData> getNestedAnnotationData() {
        return nestedAnnotationData;
    }

    public void setNestedAnnotationData(Set<AnnotationData> nestedAnnotationData) {
        this.nestedAnnotationData = nestedAnnotationData;
    }
}
