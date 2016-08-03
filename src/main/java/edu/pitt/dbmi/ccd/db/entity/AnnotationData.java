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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentAnnotationDataId", nullable = false)
    private AnnotationData parentAnnotationData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attributeId", nullable = false)
    private Attribute attribute;

    @Column(name = "value", nullable = false)
    private String value;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", nullable = false, length = 19)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedDate", nullable = false, length = 19)
    private Date modifiedDate;

    @Column(name = "modifyCount", nullable = false)
    private int modifyCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "annotationData")
    private Set<AnnotationData> annotationData = new HashSet<>(0);

    public AnnotationData() {
    }

    public AnnotationData(Annotation annotation, AnnotationData annotationData, Attribute attribute, String value, Date createdDate, Date modifiedDate, int modifyCount) {
        this.annotation = annotation;
        this.parentAnnotationData = annotationData;
        this.attribute = attribute;
        this.value = value;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.modifyCount = modifyCount;
    }

    public AnnotationData(Annotation annotation, AnnotationData annotationData, Attribute attribute, String value, Date createdDate, Date modifiedDate, int modifyCount, Set<AnnotationData> annotationDatas) {
        this.annotation = annotation;
        this.parentAnnotationData = annotationData;
        this.attribute = attribute;
        this.value = value;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.modifyCount = modifyCount;
        this.annotationData = annotationDatas;
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

    public int getModifyCount() {
        return modifyCount;
    }

    public void setModifyCount(int modifyCount) {
        this.modifyCount = modifyCount;
    }

    public Set<AnnotationData> getAnnotationData() {
        return annotationData;
    }

    public void setAnnotationData(Set<AnnotationData> annotationData) {
        this.annotationData = annotationData;
    }

}
