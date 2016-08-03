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
import javax.persistence.UniqueConstraint;

/**
 *
 * Aug 3, 2016 12:29:26 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "Attribute", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "attributeLevelId", "vocabularyId"}))
public class Attribute implements Serializable {

    private static final long serialVersionUID = -276115590738102255L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentAttributeId", nullable = false)
    private Attribute attribute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attributeLevelId", nullable = false)
    private AttributeLevel attributeLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabularyId", nullable = false)
    private Vocabulary vocabulary;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "required", nullable = false)
    private boolean required;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attribute")
    private Set<AnnotationData> annotationData = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attribute")
    private Set<Attribute> attributes = new HashSet<>(0);

    public Attribute() {
    }

    public Attribute(Attribute attribute, AttributeLevel attributeLevel, Vocabulary vocabulary, String name, boolean required) {
        this.attribute = attribute;
        this.attributeLevel = attributeLevel;
        this.vocabulary = vocabulary;
        this.name = name;
        this.required = required;
    }

    public Attribute(Attribute attribute, AttributeLevel attributeLevel, Vocabulary vocabulary, String name, boolean required, Set<AnnotationData> annotationDatas, Set<Attribute> attributes) {
        this.attribute = attribute;
        this.attributeLevel = attributeLevel;
        this.vocabulary = vocabulary;
        this.name = name;
        this.required = required;
        this.annotationData = annotationDatas;
        this.attributes = attributes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public AttributeLevel getAttributeLevel() {
        return attributeLevel;
    }

    public void setAttributeLevel(AttributeLevel attributeLevel) {
        this.attributeLevel = attributeLevel;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Set<AnnotationData> getAnnotationData() {
        return annotationData;
    }

    public void setAnnotationData(Set<AnnotationData> annotationData) {
        this.annotationData = annotationData;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

}
