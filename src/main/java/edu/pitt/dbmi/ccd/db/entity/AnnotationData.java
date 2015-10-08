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

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.validation.constraints.Size;

/**
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Entity
public class AnnotationData extends Identified {

    private static final long serialVersionUID = 6905712225800779882L;

    @ManyToOne(fetch=FetchType.EAGER)
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
    private Set<AnnotationData> children;

    public AnnotationData() {
        children = new HashSet<>();
    }

    public AnnotationData(Annotation annotation) {
        this();
        this.annotation = annotation;
    }

    public AnnotationData(Annotation annotation, Attribute attribute) {
        this(annotation);
        this.attribute = attribute;
    }

    public AnnotationData(Annotation annotation, String value) {
        this(annotation);
        this.value = value;
    }

    public AnnotationData(Annotation annotation, Attribute attribute, String value) {
        this(annotation, attribute);
        this.value = value;
    }

    public AnnotationData(Annotation annotation, Attribute attribute, AnnotationData parent) {
        this(annotation, attribute);
        this.parent = parent;
    }

    public AnnotationData(Annotation annotation, Attribute attribute, String value, AnnotationData parent) {
        this(annotation, attribute, value);
        this.parent = parent;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Optional<Attribute> getAttribute() {
        return Optional.ofNullable(attribute);
    }

    public void setAttribute(Optional<Attribute> attribute) {
        this.attribute = attribute.orElse(null);
    }

    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    public void setValue(Optional<String> value) {
        this.value = value.orElse(null);
    }

    public Optional<AnnotationData> getParent() {
        return Optional.ofNullable(parent);
    }

    public void setParent(Optional<AnnotationData> parent) {
        this.parent = parent.orElse(null);
    }

    public Set<AnnotationData> getChildren() {
        return children;
    }
}
