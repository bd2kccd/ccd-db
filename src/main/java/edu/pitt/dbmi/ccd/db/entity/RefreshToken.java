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
@Table(name="oauth_refresh_token")
public class RefreshToken implements Serializable {
    
    private static final long serialVersionUID = 6671308861475893032L;

    @Id
    private String tokenId;

    @NotNull
    @Lob
    @Column(nullable=false)
    private byte[] token;

    @NotNull
    @Lob
    @Column(nullable=false)
    private byte[] authentication;
}