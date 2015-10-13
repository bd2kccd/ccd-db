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
import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
public class Annotation implements Serializable {

    private static final long serialVersionUID = 3156490985879126383L;

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @JsonIgnore
    @Version
    private Integer version;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="uploadId", nullable=true)
    private Upload target;

    @NotNull
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="userId", nullable=false)
    private UserAccount user;

    @NotNull
    @Column(nullable=false,
            columnDefinition="TINYINT(1) DEFAULT 0")
    private Boolean redacted = false;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="accessControl", nullable=false)
    private Access accessControl;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="groupId", nullable=true)
    private Group group;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(nullable=true)
    private Vocabulary vocab;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(nullable=true)
    private Annotation parent;

    @OneToMany(mappedBy="annotation", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<AnnotationData> data;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        modified = new Date();
    }

    public Annotation() { 
        data = new HashSet<>();
    }

    public Annotation(UserAccount user, Set<AnnotationData> data, Vocabulary vocab) {
        this.user = user;
        this.data = data;
        this.vocab = vocab;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }

    public Integer getVersion() {
        return version;
    }

    protected void setVersion(Integer version) {
        this.version = version;
    }

    public Upload getTarget() {
        return target;
    }

    public void setTarget(Upload target) {
        this.target = target;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Vocabulary getVocabulary() {
        return vocab;
    }

    public void setVocabulary(Vocabulary vocab) {
        this.vocab = vocab;
    }
}
