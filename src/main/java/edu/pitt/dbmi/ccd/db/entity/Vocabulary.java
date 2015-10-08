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

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;

/**
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Entity
public class Vocabulary extends Versioned {

    private static final long serialVersionUID = 7981019296719637838L;

    @NotNull
    @Size(min=4, max=255)
    @Column(length=255, unique=true, nullable=false)
    @NaturalId(mutable=true)
    private String name;

    @NotNull
    @Column(unique=false, nullable=false,
            columnDefinition="TEXT")
    private String description;

    @OneToMany(mappedBy="vocab", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Attribute> attributes;

    public Vocabulary() { 
        attributes = new HashSet<>();
    }

    public Vocabulary(String name, String description) {
        this();
        this.name = name;
        this.description = description;
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

    public boolean hasAttributes(Collection<Attribute> attributes) {
        return this.attributes.containsAll(attributes);
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public void addAttributes(Attribute... attributes) {
        for (Attribute a: attributes) {
            this.attributes.add(a);
        }
    }

    public void addAttributes(Collection<Attribute> attributes) {
        this.attributes.addAll(attributes);
    }

    public void removeAttribute(Attribute attribute) {
        attributes.remove(attribute);
    }

    public void removeAttributes(Attribute... attributes) {
        for (Attribute a: attributes) {
            this.attributes.remove(a);
        }
    }

    public void removeAttributes(Collection<Attribute> attributes) {
        this.attributes.removeAll(attributes);
    }
}