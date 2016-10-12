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
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public class Annotation implements Serializable {

    private static final long serialVersionUID = 3156490985879126383L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp created;

    private Timestamp modified;

    @Version
    private Integer version;

    @Column(nullable = false)
    private Boolean redacted = false;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userAccountId", nullable = false)
    private UserAccount user;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "accessControl", nullable = false)
    private Access AccessControl;
}
