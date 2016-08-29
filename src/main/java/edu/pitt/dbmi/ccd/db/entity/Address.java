package edu.pitt.dbmi.ccd.db.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Aug 23, 2016
 * Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name = "Address", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"url", "userAccountId"}),
    @UniqueConstraint(columnNames = {"title", "userAccountId"})}
)
public class Address implements Serializable {

    private static final long serialVersionUID = 5000597545700957709L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userAccountId", nullable = false)
    private UserAccount userAccount;

    @Column(name = "url", nullable = false, length = 2083)
    private String url;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    public Address() {
    }

    public Address(UserAccount userAccount, String url, String title) {
        this.userAccount = userAccount;
        this.url = url;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
