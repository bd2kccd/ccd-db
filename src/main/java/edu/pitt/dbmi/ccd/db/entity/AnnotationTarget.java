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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Aug 3, 2016 12:29:18 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "AnnotationTarget")
public class AnnotationTarget implements Serializable {

    private static final long serialVersionUID = -8650768942525534955L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fileId")
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userAccountId", nullable = false)
    private UserAccount userAccount;

    @Column(name = "address")
    private Address address;

    @Column(name = "createdDate", nullable = false, length = 19)
    private Timestamp createdDate;

    @Column(name = "modifiedDate", length = 19)
    private Timestamp modifiedDate;

    @Version
    @Column(name = "modifyCount", nullable = false)
    private int modifyCount;

    @Transient
    private TargetType type;

    @Transient
    private String title;

    public AnnotationTarget() {
    }

    public AnnotationTarget(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public AnnotationTarget(UserAccount userAccount, File file, Address address) {
        this(userAccount);
        this.file = file;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public TargetType getType() {
        if (this.file != null && this.address == null) {
            return TargetType.FILE;
        } else if (this.address != null && this.file == null) {
            return TargetType.ADDRESS;
        } else {
            return TargetType.NOT_SET;
        }
    }

    public String getTitle() {
        String title = "";
        switch (getType()) {
            case FILE:
                title = this.file.getTitle();
                break;
            case ADDRESS:
                title = this.address.getTitle();
                break;
            case NOT_SET:
                break;
        }
        return title;
    }

    public enum TargetType {
        FILE, ADDRESS, NOT_SET;
    }
}
