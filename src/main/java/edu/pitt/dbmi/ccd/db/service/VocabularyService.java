/*
 * Copyright (C) 2015 University of Pittsburgh.
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

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;
import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.repository.VocabularyRepository;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class VocabularyService {

    private final VocabularyRepository vocabRepository;

    @Autowired(required=true)
    public VocabularyService(VocabularyRepository vocabRepository) {
        this.vocabRepository = vocabRepository;

        List<Vocabulary> vocabs = vocabRepository.findAll();
        if (vocabs.isEmpty()) {
            Vocabulary hcls = new Vocabulary("HCLS", "http://www.w3.org/2001/sw/hcls/notes/hcls-dataset/");
            vocabs.add(hcls);
            
            Set<Attribute> hclsAttributes = new HashSet<>();
            hclsAttributes.add(new Attribute(hcls, "Summary", "Type", "Required"));
            hclsAttributes.add(new Attribute(hcls, "Summary", "Title", "Required"));
            hclsAttributes.add(new Attribute(hcls, "Summary", "Description", "Required"));
            hclsAttributes.add(new Attribute(hcls, "Summary", "Created By", "Required"));
            hclsAttributes.add(new Attribute(hcls, "Summary", "Created On", "Required"));
            hclsAttributes.add(new Attribute(hcls, "Summary", "Last Access Time", "Required"));
            hclsAttributes.add(new Attribute(hcls, "Summary", "Publisher", "Required"));
            hclsAttributes.add(new Attribute(hcls, "Summary", "License", "Optional"));
            hclsAttributes.add(new Attribute(hcls, "Version", "Identifier", "Optional"));
            hclsAttributes.add(new Attribute(hcls, "Version", "Link", "Required"));
            hclsAttributes.add(new Attribute(hcls, "Version", "Data Source Provenance", "Optional"));
            hclsAttributes.add(new Attribute(hcls, "Version", "Distribution", "Optional"));
            hclsAttributes.add(new Attribute(hcls, "Version", "Issued", "Required"));
            hclsAttributes.add(new Attribute(hcls, "Version", "Download URI", "Required"));
            hcls.addAttributes(hclsAttributes);

            vocabRepository.save(vocabs);
        }
    }

    public Vocabulary save(Vocabulary vocab) {
        return vocabRepository.save(vocab);
    }

    public Vocabulary findOne(Long id) {
        return vocabRepository.findOne(id);
    }

    public Page<Vocabulary> findAll(Pageable pageable) {
        return vocabRepository.findAll(pageable);
    }

    public Vocabulary findByName(String name) {
        return vocabRepository.findByName(name);
    }

    public Page<Vocabulary> searchNames(String terms, Pageable pageable) {
        return vocabRepository.findByNameContains(terms, pageable);
    }

    public Page<Vocabulary> searchDescriptions(String terms, Pageable pageable) {
        return vocabRepository.findByDescriptionContains(terms, pageable);
    }
}
