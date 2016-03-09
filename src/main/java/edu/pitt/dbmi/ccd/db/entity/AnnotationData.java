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
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames={"annotationId", "dataId"})
})
public class AnnotationData implements Serializable {

    private static final long serialVersionUID = 6905712225800779882L;

    @Id
    @GeneratedValue
    private Long id;

    // id relative to annotation
    @Column(unique=false, nullable=false)
    private Long dataId;

    @NotNull
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="annotationId", nullable=false)
    private Annotation annotation;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(nullable=true)
    private Attribute attribute;

    @Column(nullable=true)
    private String value;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(nullable=true)
    private AnnotationData parent;

    @OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
    @OrderBy("attribute")
    private Set<AnnotationData> children = new HashSet<>(0);

    public AnnotationData() { }

    public AnnotationData(Long dataId, Annotation annotation) {
        this.dataId = dataId;
        this.annotation = annotation;
    }

    public AnnotationData(Long dataId, Annotation annotation, Attribute attribute) {
        this(dataId, annotation);
        this.attribute = attribute;
    }

    public AnnotationData(Long dataId, Annotation annotation, String value) {
        this(dataId, annotation);
        this.value = value;
    }

    public AnnotationData(Long dataId, Annotation annotation, Attribute attribute, String value) {
        this(dataId, annotation, attribute);
        this.value = value;
    }

    public AnnotationData(Long dataId, Annotation annotation, Attribute attribute, AnnotationData parent) {
        this(dataId, annotation, attribute);
        this.parent = parent;
    }

    public AnnotationData(Long dataId, Annotation annotation, AnnotationData parent, Attribute attribute, String value) {
        this(dataId, annotation, attribute, value);
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
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

    public AnnotationData getParent() {
        return parent;
    }

    public void setParent(AnnotationData parent) {
        this.parent = parent;
    }

    public Set<AnnotationData> getChildren() {
        return children;
    }

    public boolean hasChild(AnnotationData child) {
        return children.contains(child);
    }

    public boolean hasChildren(Collection<AnnotationData> children) {
        return this.children.containsAll(children);
    }

    public void addChild(AnnotationData child) {
        children.add(child);
    }

    public void addChildren(AnnotationData... children) {
        for (AnnotationData d : children) {
            addChild(d);
        }
    }

    public void addChildren(Collection<AnnotationData> children) {
        this.children.addAll(children);
    }

    public void removeChild(AnnotationData child) {
        children.remove(child);
    }

    public void removeChildren(AnnotationData... children) {
        for (AnnotationData d : children) {
            removeChild(d);
        }
    }

    public void removeChildren(Collection<AnnotationData> children) {
        this.children.removeAll(children);
    }
}
