package edu.pitt.dbmi.ccd.db.service;

import javax.transaction.Transactional;

import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.AnnotationData;
import edu.pitt.dbmi.ccd.db.repository.AnnotationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AnnotationDataService {

    @Autowired
    private AnnotationDataRepository repository;

    @Autowired
    private AttributeService attributeService;

    public AnnotationData save(AnnotationData data) {
        return repository.save(data);
    }

    public AnnotationData findById(Long id) {
        return repository.getOne(id);
    }

    public Page<AnnotationData> findByAnnotation(Annotation annotation, Pageable pageable) {
        return repository.findByAnnotation(annotation, pageable);
    }

    protected void delete(AnnotationData data) {
        repository.delete(data);
    }
}
