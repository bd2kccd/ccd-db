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
 * Aug 3, 2016 12:29:42 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "OauthAccessToken")
public class OauthAccessToken implements Serializable {

    private static final long serialVersionUID = 3922847140816989795L;

    @Id
    @Column(name = "authenticationId", unique = true, nullable = false)
    private String authenticationId;

    @Column(name = "tokenId", nullable = false)
    private String tokenId;

    @Column(name = "token", nullable = false)
    private byte[] token;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "clientId", nullable = false)
    private String clientId;

    @Column(name = "authentication", nullable = false)
    private byte[] authentication;

    @Column(name = "refreshToken", nullable = false)
    private String refreshToken;

    public OauthAccessToken() {
    }

    public OauthAccessToken(String authenticationId, String tokenId, byte[] token, String userName, String clientId, byte[] authentication, String refreshToken) {
        this.authenticationId = authenticationId;
        this.tokenId = tokenId;
        this.token = token;
        this.userName = userName;
        this.clientId = clientId;
        this.authentication = authentication;
        this.refreshToken = refreshToken;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
