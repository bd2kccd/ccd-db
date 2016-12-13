package edu.pitt.dbmi.ccd.db.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "oauth_client_token")
public class ClientToken implements Serializable {

    private static final long serialVersionUID = 7534882149225664328L;

    @Id
    private String authenticationId;

    @NotNull
    @Column(nullable = false)
    private String tokenId;

    @NotNull
    @Lob
    @Column(nullable = false)
    private byte[] token;

    @NotNull
    @Column(nullable = false)
    private String userName;

    @NotNull
    @Column(nullable = false)
    private String clientId;

    public ClientToken() {
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

}
