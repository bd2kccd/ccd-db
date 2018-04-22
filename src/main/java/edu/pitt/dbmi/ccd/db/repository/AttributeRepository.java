package edu.pitt.dbmi.ccd.db.repository;

import java.util.Optional;

import edu.pitt.dbmi.ccd.db.entity.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Repository
@RepositoryRestResource(exported = false)
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    Optional<Attribute> findById(Long id);

    Attribute getOne(Long id);

    @Query(value = "SELECT a FROM Attribute AS a " +
            "WHERE (:spec IS NULL or a.specification.id = :spec) " +
            "AND (:name IS NULL or a.name LIKE :name) " +
            "AND (:level is NULL or a.level LIKE :level) " +
            "AND (:required is NULL or a.required = :required)")
    Page<Attribute> search(
            @Param("spec") Long spec,
            @Param("name") String name,
            @Param("level") String level,
            @Param("required") Boolean isRequired,
            Pageable pageable);

    @Query(value = "SELECT a FROM Attribute AS a " +
            "WHERE (:spec IS NULL or a.specification.id = :spec) " +
            "AND (:name IS NULL or a.name LIKE :name) " +
            "AND (:level is NULL or a.level LIKE :level) " +
            "AND (:required is NULL or a.required = :required)" +
            "AND a.parent IS NULL")
    Page<Attribute> searchNoParent(
            @Param("spec") Long spec,
            @Param("name") String name,
            @Param("level") String level,
            @Param("required") Boolean isRequired,
            Pageable pageable);

    Page<Attribute> findAll(Pageable pageable);
}
