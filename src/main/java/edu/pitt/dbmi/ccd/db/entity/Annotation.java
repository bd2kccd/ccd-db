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
import javax.validation.constraints.NotNull;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
public class Annotation implements Serializable {

    private static final long serialVersionUID = 3156490985879126383L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp created;

    private Timestamp modified;

    @Version
    private Integer version;

    @NotNull
    @Column(nullable=false)
    private Boolean redacted = false;

    @NotNull
    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="userAccountId", nullable=false)
    private UserAccount user;

    @NotNull
    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="accessControl", nullable=false)
    private Access accessControl;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="groupId", nullable=true)
    private Group group;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(nullable=false)
    private Vocabulary vocab;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="uploadId", nullable=false)
    private Upload target;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(nullable=true)
    private Annotation parent;

    @OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
    private Set<Annotation> children = new HashSet<>(0);

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="AnnotationUploadReferences", joinColumns = {
        @JoinColumn(name="annotationId", nullable=false)}, inverseJoinColumns = {
        @JoinColumn(name="uploadId", nullable=false)})
    private Set<Upload> references = new HashSet<>(0);

    @OneToMany(mappedBy="annotation", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<AnnotationData> data = new HashSet<>(0);

    @PrePersist
    private void onCreate() {
        created = new Timestamp((new Date()).getTime());
    }

    @PreUpdate
    private void onUpdate() {
        modified = new Timestamp((new Date()).getTime());
    }

    public Annotation() { }

    public Annotation(UserAccount user, Upload target, Annotation parent, Access accessControl, Group group, Vocabulary vocab) {
        this.user = user;
        this.target = target;
        this.parent = parent;
        this.accessControl = accessControl;
        this.group = group;
        this.vocab = vocab;
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

    public Boolean isRedacted() {
        return redacted;
    }

    public void redact() {
        redacted = true;
    }

    public Access getAccess() {
        return accessControl;
    }

    public void setAccess(Access accessControl) {
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

    public Annotation getParent() {
        return parent;
    }

    public Set<Annotation> getChildren() {
        return children;
    }

    public Set<Upload> getReferences() {
        return references;
    }

    public boolean hasReference(Upload ref) {
        return references.contains(ref);
    }

    public boolean hasReferences(Collection<Upload> ref) {
        return references.containsAll(ref);
    }

    public void addReference(Upload ref) {
        references.add(ref);
    }

    public void addReferences(Upload... refs) {
        addReferences(Arrays.asList(refs));
    }

    public void addReferences(Collection<Upload> refs) {
        references.addAll(refs);
    }

    public void removeReference(Upload ref) {
        references.remove(ref);
    }

    public void removeReferences(Upload... refs) {
        removeReferences(Arrays.asList(refs));
    }

    public void removeReferences(Collection<Upload> refs) {
        references.removeAll(refs);
    }

    public Set<AnnotationData> getData() {
        return data;
    }

    public boolean hasData(AnnotationData data) {
        return this.data.contains(data);
    }

    public boolean hasData(Collection<AnnotationData> data) {
        return this.data.containsAll(data);
    }

    public void addData(AnnotationData data) {
        this.data.add(data);
    }

    public void addData(AnnotationData... data) {
        addData(Arrays.asList(data));
    }

    public void addData(Collection<AnnotationData> data) {
        this.data.addAll(data);
    }

    public void removeData(AnnotationData data) {
        this.data.remove(data);
    }

    public void removeData(AnnotationData... data) {
        removeData(Arrays.asList(data));
    }

    public void removeData(Collection<AnnotationData> data) {
        this.data.removeAll(data);
    }
}
