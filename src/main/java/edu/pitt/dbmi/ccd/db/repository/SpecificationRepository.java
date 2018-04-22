package edu.pitt.dbmi.ccd.db.repository;

import java.util.Optional;

import edu.pitt.dbmi.ccd.db.entity.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Repository
@RepositoryRestResource(exported = false)
public interface SpecificationRepository extends JpaRepository<Specification, Long> {

    public Optional<Specification> findById(Long id);

    public Specification getOne(Long id);

    public Specification findByName(String name);

    public Page<Specification> findByNameContainsOrDescriptionContainsOrderByName(String value, Pageable pageable);

    public Page<Specification> findAll(Pageable pageable);
}
