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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * Aug 5, 2016 4:31:22 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
@Table(name = "UserEventLog")
public class UserEventLog implements Serializable {

    private static final long serialVersionUID = 8727268026204666346L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eventTypeId", nullable = false)
    private EventType eventType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userAccountId", nullable = false)
    private UserAccount userAccount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "eventDate", nullable = false, length = 19)
    private Date eventDate;

    @Column(name = "eventLocation")
    private Long eventLocation;

    public UserEventLog() {
    }

    public UserEventLog(EventType eventType, UserAccount userAccount, Date eventDate) {
        this.eventType = eventType;
        this.userAccount = userAccount;
        this.eventDate = eventDate;
    }

    public UserEventLog(EventType eventType, UserAccount userAccount, Date eventDate, Long eventLocation) {
        this.eventType = eventType;
        this.userAccount = userAccount;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Long getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(Long eventLocation) {
        this.eventLocation = eventLocation;
    }

}
