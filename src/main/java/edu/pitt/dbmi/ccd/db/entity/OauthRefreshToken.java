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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * Aug 3, 2016 12:29:49 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "OauthRefreshToken")
public class OauthRefreshToken implements Serializable {

    private static final long serialVersionUID = 3953469528885696569L;

    @Id
    @Column(name = "tokenId", unique = true, nullable = false)
    private String tokenId;

    @Column(name = "token", nullable = false)
    private byte[] token;

    @Column(name = "authentication", nullable = false)
    private byte[] authentication;

    public OauthRefreshToken() {
    }

    public OauthRefreshToken(String tokenId, byte[] token, byte[] authentication) {
        this.tokenId = tokenId;
        this.token = token;
        this.authentication = authentication;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

}
