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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "version", length = 50)
    private String version;

    @Column(name = "name", unique = true, nullable = false, length = 25)
    private String name;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "createdDate", nullable = false, length = 19)
    private Timestamp createdDate;

    @Column(name = "modifiedDate", length = 19)
    private Timestamp modifiedDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "VocabularyAttributeLevels", joinColumns = {
        @JoinColumn(name = "vocabularyId", nullable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "attributeLevelId", nullable = false, updatable = false)})
    private Set<AttributeLevel> attributeLevels = new HashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "vocabulary")
    private Set<Attribute> attributes = new HashSet<>(0);

    public Vocabulary() {
    }

    public Vocabulary(String name, String description, String version) {
        this.name = name;
        this.description = description;
        this.version = version;
    }

    public Vocabulary(String name, String description, String version, Set<AttributeLevel> attributeLevels) {
        this(name, description, version);
        this.attributeLevels = attributeLevels;
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

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public boolean hasAttribute(Attribute attribute) {
        return this.attributes.contains(attribute);
    }

    public boolean hasAttributes(Set<Attribute> attributes) {
        return this.attributes.containsAll(attributes);
    }

    public boolean addAttribute(Attribute attribute) {
        return this.attributes.add(attribute);
    }

    public boolean addAttributes(Set<Attribute> attributes) {
        return this.attributes.addAll(attributes);
    }

    public boolean removeAttribute(Attribute attribute) {
        return this.attributes.remove(attribute);
    }

    public boolean removeAttributes(Set<Attribute> attributes) {
        return this.attributes.removeAll(attributes);
    }

    public Set<AttributeLevel> getAttributeLevels() {
        return attributeLevels;
    }

    public void setAttributeLevels(Set<AttributeLevel> attributeLevels) {
        this.attributeLevels = attributeLevels;
    }

    public boolean hasAttributeLevel(AttributeLevel attributeLevel) {
        return this.attributeLevels.contains(attributeLevel);
    }

    public boolean hasAttributeLevels(Set<AttributeLevel> attributeLevels) {
        return this.attributeLevels.containsAll(attributeLevels);
    }

    public boolean addAttributeLevel(AttributeLevel attributeLevel) {
        return this.attributeLevels.add(attributeLevel);
    }

    public boolean addAttributeLevels(Set<AttributeLevel> attributeLevels) {
        return this.attributeLevels.addAll(attributeLevels);
    }
    public boolean removeAttributeLevel(AttributeLevel attributeLevel) {
        return this.attributeLevels.remove(attributeLevel);
    }

    public boolean removeAttributeLevels(Set<AttributeLevel> attributeLevels) {
        return this.attributeLevels.removeAll(attributeLevels);
    }
}
