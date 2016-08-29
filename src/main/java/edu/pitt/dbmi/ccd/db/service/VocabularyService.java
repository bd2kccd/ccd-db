/*
 * Copyright (C) 2016 University of Pittsburgh.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package edu.pitt.dbmi.ccd.db.service;

import static edu.pitt.dbmi.ccd.db.specification.VocabularySpecification.searchSpec;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.entity.AttributeLevel;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;
import edu.pitt.dbmi.ccd.db.repository.VocabularyRepository;

/**
 *
 * Aug 3, 2016 4:30:57 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class VocabularyService {

    private final VocabularyRepository vocabularyRepository;

    @Autowired
    public VocabularyService(VocabularyRepository vocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;

        List<Vocabulary> vocabularies = vocabularyRepository.findAll();
        if (vocabularies.isEmpty()) {
            // Create Plaintext vocabulary
            Vocabulary plaintext = new Vocabulary("Plaintext", "Vocabulary with no required structure", null);
            plaintext.addAttribute(new Attribute(plaintext, "Text", true));
            vocabularies.add(plaintext);

            // Create HCLS vocabulary
            Vocabulary hcls = new Vocabulary("HCLS", "http://www.w3.org/2001/sw/hcls/notes/hcls-dataset/", "1");

            // Create HCLS attribute levels
            AttributeLevel summaryLevel = new AttributeLevel("Summary");
            AttributeLevel versionLevel = new AttributeLevel("Version");
            AttributeLevel distributionLevel = new AttributeLevel("Distribution");
            hcls.addAttributeLevel(summaryLevel);
            hcls.addAttributeLevel(versionLevel);
            hcls.addAttributeLevel(distributionLevel);

            // Create HCLS attributes
            Set<Attribute> attributes = Stream.of(
                    new Attribute(hcls, "Type", true, summaryLevel, null),
                    new Attribute(hcls, "Title", true, summaryLevel, null),
                    new Attribute(hcls, "Description", true, summaryLevel, null),
                    new Attribute(hcls, "Created By", true, summaryLevel, null),
                    new Attribute(hcls, "Created On", true, summaryLevel, null),
                    new Attribute(hcls, "Last Access Time", true, summaryLevel, null),
                    new Attribute(hcls, "Publisher", true, summaryLevel, null),
                    new Attribute(hcls, "License", false, summaryLevel, null),
                    new Attribute(hcls, "Identifier", false, versionLevel, null),
                    new Attribute(hcls, "Link", true, versionLevel, null),
                    new Attribute(hcls, "Data Source Provenance", false, versionLevel, null),
                    new Attribute(hcls, "Distribution", false, versionLevel, null),
                    new Attribute(hcls, "Issued", true, versionLevel, null),
                    new Attribute(hcls, "Download URI", true, versionLevel, null))
                .collect(Collectors.toSet());
            hcls.addAttributes(attributes);

            vocabularyRepository.save(vocabularies);
        }
    }

    public Vocabulary save(Vocabulary vocabulary) {
        return vocabularyRepository.save(vocabulary);
    }

    public Set<Vocabulary> save(Set<Vocabulary> vocabularies) {
        return new HashSet<>(vocabularyRepository.save(vocabularies));
    }

    public Optional<Vocabulary> findById(Long id) {
        return vocabularyRepository.findById(id);
    }

    public Optional<Vocabulary> findByName(String name) {
        return vocabularyRepository.findByName(name);
    }

    public Page<Vocabulary> search(Set<String> match, Set<String> negate, Pageable pageable) {
        return vocabularyRepository.findAll(searchSpec(match, negate), pageable);
    }

    public Page<Vocabulary> findAll(Pageable pageable) {
        return vocabularyRepository.findAll(pageable);
    }

    public List<Vocabulary> findAll() {
        return vocabularyRepository.findAll();
    }

    protected void delete(Vocabulary vocabulary) {
        vocabularyRepository.delete(vocabulary);
    }

    protected void delete(Set<Vocabulary> vocabularies) {
        vocabularyRepository.delete(vocabularies);
    }
}
