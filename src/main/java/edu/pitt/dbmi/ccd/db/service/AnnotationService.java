package edu.pitt.dbmi.ccd.db.service;

import static edu.pitt.dbmi.ccd.db.specification.AnnotationSearchSpecification.filterSpec;
import static edu.pitt.dbmi.ccd.db.specification.AnnotationSearchSpecification.idSpec;
import static edu.pitt.dbmi.ccd.db.specification.AnnotationSearchSpecification.parentSpec;
import static edu.pitt.dbmi.ccd.db.specification.AnnotationSearchSpecification.searchSpec;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;

import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.repository.AnnotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AnnotationService {

    @Autowired
    private AnnotationRepository repository;

    public Annotation save(Annotation annotation) {
        return repository.save(annotation);
    }

    public Annotation saveAndFlush(Annotation annotation) {
        return repository.saveAndFlush(annotation);
    }

    public Optional<Annotation> findById(Long user, Long id) {
        Specification<Annotation> spec = idSpec(user, id);
        return repository.findOne(spec);
    }

    public Page<Annotation> findByParent(Long user, Annotation annotation, boolean showRedacted, Pageable pageable) {
        Specification<Annotation> spec = parentSpec(user, annotation.getId(), showRedacted);
        return repository.findAll(spec, pageable);
    }

    public Page<Annotation> filter(
            Long user,
            Long target,
            String specification,
            String level,
            String name,
            Boolean required,
            Boolean redacted,
            Boolean isTop,
            Date createdBefore,
            Date createdAfter,
            Date modifiedBefore,
            Date modifiedAfter,
            Pageable pageable) {
        Specification<Annotation> spec = filterSpec(user, target, specification, level, name, required, redacted, isTop, createdBefore, createdAfter, modifiedBefore, modifiedAfter);
        return repository.findAll(spec, pageable);
    }

    public Page<Annotation> search(
            Long user,
            Long target,
            String specification,
            String level,
            String name,
            Boolean required,
            Boolean redacted,
            Boolean isTop,
            Date createdBefore,
            Date createdAfter,
            Date modifiedBefore,
            Date modifiedAfter,
            Set<String> matches,
            Set<String> nots,
            Pageable pageable) {
        Specification<Annotation> spec = searchSpec(user, target, specification, level, name, required, redacted, isTop, createdBefore, createdAfter, modifiedBefore, modifiedAfter, matches, nots);
        return repository.findAll(spec, pageable);
    }
}
