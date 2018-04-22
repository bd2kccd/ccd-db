package edu.pitt.dbmi.ccd.db.service;

import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;

import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.entity.Specification;
import edu.pitt.dbmi.ccd.db.repository.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AttributeService {

    @Autowired
    private AttributeRepository repository;

    public Attribute findById(Long id) {
        return repository.getOne(id);
    }

    public Attribute save(Attribute attribute) {
        return repository.save(attribute);
    }

    public List<Attribute> saveAll(Set<Attribute> attributes) {
        return repository.saveAll(attributes);
    }

    public Page<Attribute> search(Specification specification, String name, String level, Boolean required, Pageable pageable) {
        return repository.search(specification.getId(), name, level, required, pageable);
    }

    public Page<Attribute> search(Long specId, String name, String level, Boolean required, Pageable pageable) {
        return repository.search(specId, name, level, required, pageable);
    }

    public Page<Attribute> searchNoParent(Specification specification, String name, String level, Boolean required, Pageable pageable) {
        return repository.searchNoParent(specification.getId(), name, level, required, pageable);
    }

    public Page<Attribute> searchNoParent(Long specId, String name, String level, Boolean required, Pageable pageable) {
        return repository.searchNoParent(specId, name, level, required, pageable);
    }

    public Page<Attribute> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    protected void delete(Attribute attribute) {
        repository.delete(attribute);
    }
}
