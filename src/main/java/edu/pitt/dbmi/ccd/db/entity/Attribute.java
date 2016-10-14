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
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"vocabulary_id", "level", "name"})
})
public class Attribute implements Serializable {

    private static final long serialVersionUID = 4437966176000119860L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_id", nullable = false)
    private Vocabulary vocabulary;

    @Column(unique = false, nullable = true)
    private String level;

    @NotBlank(message = "Name is required")
    @Column(unique = false, nullable = false)
    private String name;

    @Column(unique = false, nullable = true)
    private String requirementLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent", nullable = true)
    private Attribute parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<Attribute> children = new HashSet<>(0);

    public Attribute() {
    }

    public Attribute(Vocabulary vocab, String level, String name, String requirementLevel) {
        this.vocabulary = vocab;
        this.level = level;
        this.name = name;
        this.requirementLevel = requirementLevel;
    }

    public Attribute(Vocabulary vocab, Attribute parent, String level, String name, String requirementLevel) {
        this(vocab, level, name, requirementLevel);
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
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
}
