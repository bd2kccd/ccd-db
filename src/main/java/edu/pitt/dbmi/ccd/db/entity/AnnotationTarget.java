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
 * Aug 3, 2016 12:29:18 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "AnnotationTarget")
public class AnnotationTarget implements Serializable {

    private static final long serialVersionUID = -2275516486925004695L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fileId")
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userAccountId", nullable = false)
    private UserAccount userAccount;

    @Column(name = "url", length = 2083)
    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate", nullable = false, length = 19)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedDate", length = 19)
    private Date modifiedDate;

    @Column(name = "modifyCount", nullable = false)
    private int modifyCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "annotationTarget")
    private Set<Annotation> annotations = new HashSet<>(0);

    public AnnotationTarget() {
    }

    public AnnotationTarget(UserAccount userAccount, Date createdDate, int modifyCount) {
        this.userAccount = userAccount;
        this.createdDate = createdDate;
        this.modifyCount = modifyCount;
    }

    public AnnotationTarget(File file, UserAccount userAccount, String url, Date createdDate, Date modifiedDate, int modifyCount, Set<Annotation> annotations) {
        this.file = file;
        this.userAccount = userAccount;
        this.url = url;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.modifyCount = modifyCount;
        this.annotations = annotations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

}
