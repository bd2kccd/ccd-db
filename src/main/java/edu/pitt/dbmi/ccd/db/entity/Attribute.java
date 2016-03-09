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
import java.util.Arrays;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Entity
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames={"vocabId", "level", "name"}),
    @UniqueConstraint(columnNames={"vocabId", "id"})
})
public class Attribute implements Serializable {

    private static final long serialVersionUID = 4437966176000119860L;

    @Id
    @GeneratedValue
    private Long attributeId;

    // id relative to vocabulary
    @Column(unique=false, nullable=false)
    private Long id;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="vocabId", nullable=false)
    private Vocabulary vocab;

    @Column(unique=false, nullable=true)
    private String level;

    @NotBlank
    @Column(unique=false, nullable=false)
    private String name;

    @Column(unique=false, nullable=true)
    private String requirementLevel;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="parent", nullable=true)
    private Attribute parent;

    @OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
    @OrderBy
    private Set<Attribute> children = new HashSet<>(0);

    public Attribute() { }

    protected Attribute(String level, String name, String requirementLevel) {
        this.level = level;
        this.name = name;
        this.requirementLevel = requirementLevel;        
    }

    public Attribute(Vocabulary vocab, Long id, String level, String name, String requirementLevel) {
        this(level, name, requirementLevel);
        this.id = id;
        this.vocab = vocab;
    }

    public Attribute(Vocabulary vocab, Long id, String level, String name, String requirementLevel, Attribute parent) {
        this(vocab, id, level, name, requirementLevel);
        this.parent = parent;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vocabulary getVocabulary() {
        return vocab;
    }

    public void setVocabulary(Vocabulary vocab) {
        this.vocab = vocab;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequirementLevel() {
        return requirementLevel;
    }

    public void setRequirementLevel(String requirementLevel) {
        this.requirementLevel = requirementLevel;
    }

    public Attribute getParent() {
        return parent;
    }

    public void setParent(Attribute parent) {
        this.parent = parent;
    }

    public Set<Attribute> getChildren() {
        return children;
    }

    public boolean hasChild(Attribute child) {
        return children.contains(child);
    }

    public boolean hasChildren(Attribute... children) {
        return hasChildren(Arrays.asList(children));
    }

    public boolean hasChildren(Collection<Attribute> children) {
        return this.children.containsAll(children);
    }

    public void addChild(Attribute child) {
        children.add(child);
    }

    public void addChildren(Attribute... children) {
        for (Attribute c : children) {
            addChild(c);
        }
    }

    public void addChildren(Collection<Attribute> children) {
        this.children.addAll(children);
    }

    public void removeChild(Attribute child) {
        children.remove(child);
    }

    public void removeChildren(Attribute... children) {
        for (Attribute c : children) {
            removeChild(c);
        }
    }

    public void removeChildren(Collection<Attribute> children) {
        this.children.removeAll(children);
    }
}