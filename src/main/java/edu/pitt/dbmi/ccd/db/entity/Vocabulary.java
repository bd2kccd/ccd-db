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
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
public class Vocabulary implements Serializable {

    private static final long serialVersionUID = 7981019296719637838L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp created;

    private Timestamp modified;

    @Version
    private Integer version;

    @NotBlank
    @Size(min = 4, max = 255, message = "Name must be between 4 and 255 characters")
    @Column(length = 255, unique = true, nullable = false)
    @NaturalId(mutable = true)
    private String name;

    @NotBlank
    @Size(max = 500, message = "Description must be no longer than 500 characters")
    @Column(length = 500, nullable = false)
    private String description;

    @OneToMany(mappedBy = "vocabulary", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Attribute> attributes = new HashSet<>(0);

    public Vocabulary() {
    }

    public Vocabulary(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @PrePersist
    protected void onCreate() {
        created = new Timestamp((new Date()).getTime());
    }

    @PreUpdate
    protected void onUpdate() {
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

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public boolean hasAttribute(Attribute attribute) {
        return attributes.contains(attribute);
    }

    public boolean hasAttributes(Attribute... attributes) {
        return hasAttributes(Arrays.asList(attributes));
    }

    public boolean hasAttributes(Collection<Attribute> attributes) {
        return this.attributes.containsAll(attributes);
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public void addAttributes(Attribute... attributes) {
        for (Attribute a : attributes) {
            addAttribute(a);
        }
    }

    public void addAttributes(Collection<Attribute> attributes) {
        this.attributes.addAll(attributes);
    }

    public void removeAttribute(Attribute attribute) {
        attributes.remove(attribute);
    }

    public void removeAttributes(Attribute... attributes) {
        for (Attribute a : attributes) {
            removeAttribute(a);
        }
    }

    public void removeAttributes(Collection<Attribute> attributes) {
        this.attributes.removeAll(attributes);
    }

}
