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
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
public class AnnotationData implements Serializable {

    private static final long serialVersionUID = 6905712225800779882L;

    @Id
    @GeneratedValue
    private Long id;

    private Timestamp created;

    private Timestamp modified;

    @Version
    private Integer version;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Annotation annotation;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Attribute attribute;

    @Column(nullable = true)
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private AnnotationData parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<AnnotationData> subData = new HashSet<>(0);

    public AnnotationData() {
    }

    public AnnotationData(Annotation annotation, Attribute attribute) {
        this.annotation = annotation;
        this.attribute = attribute;
    }

    public AnnotationData(Annotation annotation, Attribute attribute, String value) {
        this(annotation, attribute);
        this.value = value;
    }

    public AnnotationData(Annotation annotation, AnnotationData parent, Attribute attribute) {
        this(annotation, attribute);
        this.parent = parent;
    }

    public AnnotationData(Annotation annotation, AnnotationData parent, Attribute attribute, String value) {
        this(annotation, parent, attribute);
        this.value = value;
    }

    @PrePersist
    private void onCreate() {
        created = new Timestamp((new Date()).getTime());
    }

    @PreUpdate
    private void onUpdate() {
        modified = new Timestamp((new Date()).getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getModified() {
        return modified;
    }

    public Integer getVersion() {
        return version;
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

    public Set<AnnotationData> getSubData() {
        return subData;
    }

    public void setSubData(Set<AnnotationData> subData) {
        this.subData = subData;
    }

    public boolean hasSubData(AnnotationData sub) {
        return subData.contains(sub);
    }
}
