package edu.pitt.dbmi.ccd.db.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
public class Annotation implements Serializable {

    private static final long serialVersionUID = 3156490985879126383L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @Basic(optional = false)
    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @Basic(optional = false)
    @Column(name = "redacted", nullable = false)
    private Boolean redacted = false;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userAccountId", nullable = false)
    private UserAccount user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "target", nullable = false)
    private File target;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "data", nullable = false)
    private AnnotationData data;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true)
    private Annotation parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<Annotation> children = new HashSet<>(0);

    public Annotation() { }

    public Annotation(final UserAccount user) {
        this.user = user;
    }

    @PrePersist
    protected void onCreate() {
        this.created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modified = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(final Integer version) {
        this.version = version;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }

    public Boolean getRedacted() {
        return redacted;
    }

    public void setRedacted(final Boolean redacted) {
        this.redacted = redacted;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(final UserAccount user) {
        this.user = user;
    }

    public AnnotationData getData() {
        return data;
    }

    public void setData(final AnnotationData data) {
        this.data = data;
    }

    public Annotation getParent() {
        return parent;
    }

    public void setParent(final Annotation annotation) {
        this.parent = annotation;
    }

    public Set<Annotation> getChildren() {
        return children;
    }

}
