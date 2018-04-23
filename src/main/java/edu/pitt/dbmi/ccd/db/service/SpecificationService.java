package edu.pitt.dbmi.ccd.db.service;

import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;

import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.entity.Specification;
import edu.pitt.dbmi.ccd.db.repository.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class SpecificationService {

    private static final String DUPLICATE = "Specification already exists with name: %s";
    private static final String DUPLICATES = "Specifications already exist with names: ";

    private final SpecificationRepository repository;

    @Autowired
    public SpecificationService(SpecificationRepository repository) {
        this.repository = repository;

        // initialize
        final List<Specification> specifications = repository.findAll();
        if (specifications.isEmpty()) {
            Specification hcls = new Specification("HCLS", "http://www.w3.org/2001/sw/hcls/notes/hcls-dataset/");
            hcls.addAttributes(
                    new Attribute(hcls, "Type", "Summary", true),
                    new Attribute(hcls, "Title", "Summary", true),
                    new Attribute(hcls, "Description", "Summary", true),
                    new Attribute(hcls, "Created By", "Summary", true),
                    new Attribute(hcls, "Created On", "Summary", true),
                    new Attribute(hcls, "Last Access Time", "Summary", true),
                    new Attribute(hcls, "Publisher", "Summary", true),
                    new Attribute(hcls, "License", "Summary", false),
                    new Attribute(hcls, "Identifier", "Version", false),
                    new Attribute(hcls, "Link", "Version", true),
                    new Attribute(hcls, "Data Source Provenance", "Version", false),
                    new Attribute(hcls, "Distribution", "Version", false),
                    new Attribute(hcls, "Issued", "Version", true),
                    new Attribute(hcls, "Download URI", "Version", true)
            );

            Specification plaintext = new Specification("Plaintext", "Text with no required structure");
            plaintext.addAttribute(new Attribute(plaintext, "Text", null, true));

            specifications.add(hcls);
            specifications.add(plaintext);
        }
    }

    public Specification save(Specification spec) {
        return repository.save(spec);
    }

//    public List<Specification> saveAll(Set<Specification> specs) {
//        return repository.saveAll(specs);
//    }

    public Specification findById(Long id) {
        return repository.getOne(id);
    }

    public Specification findByName(String name) {
        return repository.findByName(name);
    }

    public Page<Specification> search(String value, Pageable pageable) {
        return repository.findByNameContainsOrDescriptionContainsOrderByName(value, pageable);
    }

    public Page<Specification> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    protected void delete(Specification spec) {
        repository.delete(spec);
    }
}
