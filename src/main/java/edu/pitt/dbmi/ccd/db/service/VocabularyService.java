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
import java.util.Optional;
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
            long innerId = 1L;
            hcls.addAttributes(
                new Attribute(hcls, innerId++, "Summary", "Type", "Required"),
                new Attribute(hcls, innerId++, "Summary", "Title", "Required"),
                new Attribute(hcls, innerId++, "Summary", "Description", "Required"),
                new Attribute(hcls, innerId++, "Summary", "Created By", "Required"),
                new Attribute(hcls, innerId++, "Summary", "Created On", "Required"),
                new Attribute(hcls, innerId++, "Summary", "Last Access Time", "Required"),
                new Attribute(hcls, innerId++, "Summary", "Publisher", "Required"),
                new Attribute(hcls, innerId++, "Summary", "License", "Optional"),
                new Attribute(hcls, innerId++, "Version", "Identifier", "Optional"),
                new Attribute(hcls, innerId++, "Version", "Link", "Required"),
                new Attribute(hcls, innerId++, "Version", "Data Source Provenance", "Optional"),
                new Attribute(hcls, innerId++, "Version", "Distribution", "Optional"),
                new Attribute(hcls, innerId++, "Version", "Issued", "Required"),
                new Attribute(hcls, innerId, "Version", "Download URI", "Required")
            );

            vocabRepository.save(vocabs);
        }
    }

    public Vocabulary save(Vocabulary vocab) {
        return vocabRepository.save(vocab);
    }

    public Optional<Vocabulary> findOne(Long id) {
        return vocabRepository.findById(id);
    }

    public Optional<Vocabulary> findByName(String name) {
        return vocabRepository.findByName(name);
    }
    
    public Page<Vocabulary> findByNameStartsWith(String terms, Pageable pageable) {
        return vocabRepository.findByNameStartsWith(terms, pageable);
    }

    public Page<Vocabulary> findByNameContains(String terms, Pageable pageable) {
        return vocabRepository.findByNameContains(terms, pageable);
    }

    public Page<Vocabulary> findByDescriptionContains(String terms, Pageable pageable) {
        return vocabRepository.findByDescriptionContains(terms, pageable);
    }

    public Page<Vocabulary> findAll(Pageable pageable) {
        return vocabRepository.findAll(pageable);
    }
}
