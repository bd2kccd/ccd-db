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
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentAnnotationId")
    private Annotation parentAnnotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annotationTargetId", nullable = false)
    private AnnotationTarget annotationTarget;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shareAccessId", nullable = false)
    private ShareAccess shareAccess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shareGroupId")
    private ShareGroup shareGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userAccountId", nullable = false)
    private UserAccount userAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabularyId", nullable = false)
    private Vocabulary vocabulary;

    @Column(name = "redacted", nullable = false)
    private boolean redacted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createddate", nullable = false, length = 19)
    private Date createddate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedDate", length = 19)
    private Date modifiedDate;

    @Column(name = "modifyCount", nullable = false)
    private int modifyCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentAnnotation")
    private Set<Annotation> annotations = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "annotation")
    private Set<AnnotationData> annotationData = new HashSet<>(0);

    public Annotation() {
    }

    public Annotation(AnnotationTarget annotationTarget, ShareAccess shareAccess, UserAccount userAccount, Vocabulary vocabulary, boolean redacted, Date createddate, int modifyCount) {
        this.annotationTarget = annotationTarget;
        this.shareAccess = shareAccess;
        this.userAccount = userAccount;
        this.vocabulary = vocabulary;
        this.redacted = redacted;
        this.createddate = createddate;
        this.modifyCount = modifyCount;
    }

    public Annotation(Annotation parentAnnotation, AnnotationTarget annotationTarget, ShareAccess shareAccess, ShareGroup shareGroup, UserAccount userAccount, Vocabulary vocabulary, boolean redacted, Date createddate, Date modifiedDate, int modifyCount, Set<Annotation> annotations, Set<AnnotationData> annotationDatas) {
        this.parentAnnotation = parentAnnotation;
        this.annotationTarget = annotationTarget;
        this.shareAccess = shareAccess;
        this.shareGroup = shareGroup;
        this.userAccount = userAccount;
        this.vocabulary = vocabulary;
        this.redacted = redacted;
        this.createddate = createddate;
        this.modifiedDate = modifiedDate;
        this.modifyCount = modifyCount;
        this.annotations = annotations;
        this.annotationData = annotationDatas;
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

    public boolean isRedacted() {
        return redacted;
    }

    public void setRedacted(boolean redacted) {
        this.redacted = redacted;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getModifyCount() {
        return modifyCount;
    }

    public void setModifyCount(int modifyCount) {
        this.modifyCount = modifyCount;
    }

    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
    }

    public Set<AnnotationData> getAnnotationData() {
        return annotationData;
    }

    public void setAnnotationData(Set<AnnotationData> annotationData) {
        this.annotationData = annotationData;
    }

}
