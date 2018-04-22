package edu.pitt.dbmi.ccd.db.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Size;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
public class AnnotationData implements Serializable {

    private static final long serialVersionUID = 6905712225800779882L;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Annotation annotation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Attribute attribute;

    @Column(length = 1000, nullable = false)
    @Size(max = 1000, message = "Value must be shorter than 1000 characters")
    private String value;


    public AnnotationData() { }

    public AnnotationData(final Annotation annotation, final Attribute attribute, final String value) {
        this.annotation = annotation;
        this.attribute = attribute;
        this.value = value;
    }

    @PrePersist
    private void onCreate() {
        this.created = new Date();
    }

    @PreUpdate
    private void onUpdate() {
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

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(final Annotation annotation) {
        this.annotation = annotation;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(final Attribute attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
