package edu.pitt.dbmi.ccd.db.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "oauth_client_details")
public class ClientDetails implements Serializable {

    private static final long serialVersionUID = 3932864394592817342L;

    @Id
    private String clientId;

    @Column(nullable = true)
    private String resourceIds;

    @Column(nullable = true)
    private String clientSecret;

    @Column(nullable = true)
    private String scope;

    @Column(nullable = true)
    private String authorizedGrantTypes;

    @Column(nullable = true)
    private String webServerRedirectUri;

    @Column(nullable = true)
    private String authorities;

    @Column(nullable = true, columnDefinition = "INT(11)")
    private Integer accessTokenValidity;

    @Column(nullable = true, columnDefinition = "INT(11)")
    private Integer refreshTokenValidity;

    @Size(max = 4096)
    @Column(length = 4096, nullable = true)
    private String additionalInformation;

    @Column(name = "autoapprove", nullable = true)
    private String autoApproveScopes;

    public ClientDetails() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAutoApproveScopes() {
        return autoApproveScopes;
    }

    public void setAutoApproveScopes(String autoApproveScopes) {
        this.autoApproveScopes = autoApproveScopes;
    }

}
