package edu.pitt.dbmi.ccd.db.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
public class Specification implements Serializable {

    private static final long serialVersionUID = 7981019296719637838L;

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

    @NotBlank(message = "Name is required")
    @Basic(optional = false)
    @Column(length = 255, unique = true, nullable = false)
    @Size(min = 4, max = 255, message = "Name must be between 4 and 255 characters")
    @NaturalId(mutable = true)
    private String name;

    @NotBlank(message = "Description is required")
    @Basic(optional = false)
    @Column(length = 500, nullable = false)
    @Size(max = 500, message = "Description must be no longer than 500 characters")
    private String description;

    @OneToMany(mappedBy = "specification", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Attribute> attributes = new HashSet<>(0);

    public Specification() {

    }

    public Specification(final String name, final String description) {
        this.name = name;
        this.description = description;
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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
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

    public Set<Attribute> getAttributes() {
        return this.attributes;
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public void addAttributes(Attribute... attributes) {
        for (Attribute a : attributes) {
            addAttribute(a);
        }
    }

    public void addAttributes(Collection<Attribute> attributes) {
        this.attributes.addAll(attributes);
    }
}
