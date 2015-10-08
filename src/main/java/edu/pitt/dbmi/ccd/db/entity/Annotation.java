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
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Mark Silvis  (marksilvis@pitt.edu)
 */
@Entity
public class Annotation extends Versioned {

    private static final long serialVersionUID = 3156490985879126383L;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="uploadId", nullable=true)
    private Upload target;

    @NotNull
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="profileId", nullable=false)
    private Person user;

    @NotNull
    @Column(unique=false, nullable=false,
            columnDefinition="TINYINT(1) DEFAULT 0")
    private Boolean redacted = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable=false,
            columnDefinition = "ENUM('PRIVATE', 'GROUP', 'PUBLIC') DEFAULT 'PUBLIC'")
    private Access accessControl=Access.PUBLIC;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="groupId", nullable=true)
    private Group group;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(nullable=true)
    private Vocabulary vocab;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(unique=false, nullable=true)
    private Annotation parent;

    @OneToMany(mappedBy="annotation", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<AnnotationData> data;

    public Annotation() { 
        data = new HashSet<>();
    }

    public Annotation(Person user, Set<AnnotationData> data, Vocabulary vocab) {
        this.user = user;
        this.data = data;
        this.vocab = vocab;
    }

    public Optional<Upload> getTarget() {
        return Optional.ofNullable(target);
    }

    public void setTarget(Optional<Upload> target) {
        this.target = target.orElse(null);
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public boolean isRedacted() {
        return redacted;
    }

    public void redact() {
        redacted = true;
    }

    public Access getAccessControl() {
        return accessControl;
    }

    public void setAccessControl(Access accessControl) {
        this.accessControl = accessControl;
    }

    public Optional<Group> getGroup() {
        return Optional.ofNullable(group);
    }

    public void setGroup(Optional<Group> group) {
        this.group = group.orElse(null);
    }

    public Optional<Vocabulary> getVocabulary() {
        return Optional.ofNullable(vocab);
    }

    public void setVocabulary(Optional<Vocabulary> vocab) {
        this.vocab = vocab.orElse(null);
    }
}
