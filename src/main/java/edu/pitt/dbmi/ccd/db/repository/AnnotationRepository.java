package edu.pitt.dbmi.ccd.db.repository;

import java.util.Optional;

import edu.pitt.dbmi.ccd.db.entity.Annotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Repository
@RepositoryRestResource(exported = false)
public interface AnnotationRepository extends JpaRepository<Annotation, Long>, JpaSpecificationExecutor<Annotation> {

    public Optional<Annotation> findOne(Specification<Annotation> spec);

    /**
     * Find page of annotations by specification
     * @param spec
     * @param pageable
     * @return
     */
    public Page<Annotation> findAll(Specification<Annotation> spec, Pageable pageable);
}
