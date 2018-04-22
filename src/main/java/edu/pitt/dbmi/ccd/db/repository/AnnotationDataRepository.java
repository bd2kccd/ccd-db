package edu.pitt.dbmi.ccd.db.repository;

import java.util.Optional;

import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.AnnotationData;
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
public interface AnnotationDataRepository extends JpaRepository<AnnotationData, Long> {

    public Optional<AnnotationData> findById(Long id);

    public AnnotationData getOne(Long id);

    public Page<AnnotationData> findByAnnotation(Annotation annotation, Pageable pageable);
}
