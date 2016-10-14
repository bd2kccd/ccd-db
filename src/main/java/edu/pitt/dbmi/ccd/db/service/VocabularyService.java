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

import static edu.pitt.dbmi.ccd.db.specification.VocabularySpecification.searchSpec;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pitt.dbmi.ccd.db.entity.Vocabulary;
import edu.pitt.dbmi.ccd.db.repository.VocabularyRepository;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class VocabularyService {

    private static final String DUPLICATE = "Vocabulary already exists with name: %s";
    private static final String DUPLICATES = "Vocabularies already exist with names: ";

    private final VocabularyRepository vocabRepository;

    @Autowired(required = true)
    public VocabularyService(VocabularyRepository vocabRepository) {
        this.vocabRepository = vocabRepository;

//        List<Vocabulary> vocabs = vocabRepository.findAll();
//        if (vocabs.isEmpty()) {
//            Vocabulary hcls = new Vocabulary("HCLS", "http://www.w3.org/2001/sw/hcls/notes/hcls-dataset/");
//            hcls.addAttributes(
//                new Attribute(hcls, "Summary", "Type", "Required"),
//                new Attribute(hcls, "Summary", "Title", "Required"),
//                new Attribute(hcls, "Summary", "Description", "Required"),
//                new Attribute(hcls, "Summary", "Created By", "Required"),
//                new Attribute(hcls, "Summary", "Created On", "Required"),
//                new Attribute(hcls, "Summary", "Last Access Time", "Required"),
//                new Attribute(hcls, "Summary", "Publisher", "Required"),
//                new Attribute(hcls, "Summary", "License", "Optional"),
//                new Attribute(hcls, "Version", "Identifier", "Optional"),
//                new Attribute(hcls, "Version", "Link", "Required"),
//                new Attribute(hcls, "Version", "Data Source Provenance", "Optional"),
//                new Attribute(hcls, "Version", "Distribution", "Optional"),
//                new Attribute(hcls, "Version", "Issued", "Required"),
//                new Attribute(hcls, "Version", "Download URI", "Required")
//            );
//            vocabs.add(hcls);
//
//            Vocabulary plaintext = new Vocabulary("Plaintext", "Text with no required structure");
//            plaintext.addAttribute(new Attribute(plaintext, null, "Text", null));
//            vocabs.add(plaintext);
//
//            vocabRepository.save(vocabs);
//        }
    }

    public Vocabulary save(Vocabulary vocab) {
        return vocabRepository.save(vocab);
    }

    public List<Vocabulary> save(Set<Vocabulary> vocabs) {
        return vocabRepository.save(vocabs);
    }

    public Vocabulary findById(Long id) {
        return vocabRepository.findById(id);
    }

    public Vocabulary findByName(String name) {
        return vocabRepository.findByName(name);
    }

    public Page<Vocabulary> search(Set<String> matches, Set<String> nots, Pageable pageable) {
        return vocabRepository.findAll(searchSpec(matches, nots), pageable);
    }

    public Page<Vocabulary> findAll(Pageable pageable) {
        return vocabRepository.findAll(pageable);
    }

    protected void delete(Vocabulary vocabularies) {
        vocabRepository.delete(vocabularies);
    }

    protected void delete(Set<Vocabulary> vocabularies) {
        vocabRepository.delete(vocabularies);
    }
}
