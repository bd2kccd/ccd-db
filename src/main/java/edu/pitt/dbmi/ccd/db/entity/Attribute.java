package edu.pitt.dbmi.ccd.db.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
public class Attribute implements Serializable {

    private static final long serialVersionUID = 4437966176000119860L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specification", nullable = false)
    private Specification specification;

    @NotBlank(message = "Name is required")
    @Basic(optional = false)
    @Column(length = 255, unique = true, nullable = false)
    @Size(min = 4, max = 255, message = "Name must be between 4 and 255 characters")
    @NaturalId(mutable = true)
    private String name;

    @Basic(optional = true)
    @Column(length = 255, unique = false, nullable = true)
    @Size(min = 4, max = 255, message = "Level must be between 4 and 255 characters")
    private String level;

    @Column(nullable = false)
    private Boolean required;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent", nullable = true)
    private Attribute parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<Attribute> children = new HashSet<>(0);

    public Attribute() { }

    public Attribute(final Specification specification, final String name, final String level, final Boolean required) {
        this.specification = specification;
        this.name = name;
        this.level = level;
        this.required = required;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(final Specification specification) {
        this.specification = specification;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(final String level) {
        this.level = level;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(final Boolean required) {
        this.required = required;
    }

    public Attribute getParent() {
        return parent;
    }

    public void setParent(final Attribute parent) {
        this.parent = parent;
    }

    public Set<Attribute> getChildren() {
        return children;
    }
}
