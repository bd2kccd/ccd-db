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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Aug 3, 2016 12:29:09 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "Annotation")
public class Annotation implements Serializable {

    private static final long serialVersionUID = -1314418549562720324L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentAnnotationId")
    private Annotation parentAnnotation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "annotationTargetId", nullable = false)
    private AnnotationTarget annotationTarget;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shareAccessId", nullable = false)
    private ShareAccess shareAccess;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shareGroupId")
    private ShareGroup shareGroup;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userAccountId", nullable = false)
    private UserAccount userAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vocabularyId", nullable = false)
    private Vocabulary vocabulary;

    @Column(name = "redacted", nullable = false)
    private boolean redacted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", nullable = false)
    private Timestamp createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedDate")
    private Timestamp modifiedDate;

    @Version
    @Column(name = "modifyCount", nullable = false)
    private int modifyCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentAnnotation")
    private Set<Annotation> childAnnotations = new HashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "annotation", cascade = CascadeType.ALL)
    private Set<AnnotationData> annotationData = new HashSet<>(0);

    public Annotation() {
    }

    public Annotation(AnnotationTarget annotationTarget, UserAccount userAccount, Vocabulary vocabulary, ShareAccess shareAccess, ShareGroup shareGroup) {
        this.annotationTarget = annotationTarget;
        this.userAccount = userAccount;
        this.vocabulary = vocabulary;
        this.shareAccess = shareAccess;
        this.shareGroup = shareGroup;
    }

    public Annotation(AnnotationTarget annotationTarget, UserAccount userAccount, Vocabulary vocabulary, ShareAccess shareAccess, ShareGroup shareGroup, Annotation parentAnnotation) {
        this(annotationTarget, userAccount, vocabulary, shareAccess, shareGroup);
        this.parentAnnotation = parentAnnotation;
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

    public Annotation getParentAnnotation() {
        return parentAnnotation;
    }

    public void setParentAnnotation(Annotation parentAnnotation) {
        this.parentAnnotation = parentAnnotation;
    }

    public AnnotationTarget getAnnotationTarget() {
        return annotationTarget;
    }

    public void setAnnotationTarget(AnnotationTarget annotationTarget) {
        this.annotationTarget = annotationTarget;
    }

    public ShareAccess getShareAccess() {
        return shareAccess;
    }

    public void setShareAccess(ShareAccess shareAccess) {
        this.shareAccess = shareAccess;
    }

    public ShareGroup getShareGroup() {
        return shareGroup;
    }

    public void setShareGroup(ShareGroup shareGroup) {
        this.shareGroup = shareGroup;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    public Boolean isRedacted() {
        return redacted;
    }

    public void setRedacted(Boolean redacted) {
        this.redacted = redacted;
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

    public int getModifyCount() {
        return modifyCount;
    }

    public void setModifyCount(int modifyCount) {
        this.modifyCount = modifyCount;
    }

    public Set<Annotation> getChildAnnotations() {
        return childAnnotations;
    }

    public void setChildAnnotations(Set<Annotation> childAnnotations) {
        this.childAnnotations = childAnnotations;
    }

    public Set<AnnotationData> getAnnotationData() {
        return annotationData;
    }

    public void setAnnotationData(Set<AnnotationData> annotationData) {
        this.annotationData = annotationData;
    }

}
