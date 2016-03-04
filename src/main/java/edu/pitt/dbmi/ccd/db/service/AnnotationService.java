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

import static edu.pitt.dbmi.ccd.db.specification.AnnotationSpecification.filterSpec;
import static edu.pitt.dbmi.ccd.db.specification.AnnotationSpecification.searchSpec;

import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.AnnotationData;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.Group;
import edu.pitt.dbmi.ccd.db.repository.AnnotationRepository;
import edu.pitt.dbmi.ccd.db.error.NotFoundException;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AnnotationService {

    private final AnnotationRepository annotationRepository;

    @Autowired(required=true)
    public AnnotationService(AnnotationRepository annotationRepository) {
        this.annotationRepository = annotationRepository;
    }

    public Annotation save(Annotation annotation) {
        return annotationRepository.save(annotation);
    }

    public Annotation findById(UserAccount requester, Long id) {
        Optional<Annotation> annotation = annotationRepository.findById(requester, id);
        return annotation.orElseThrow(() -> new NotFoundException("Annotation", "id", id));
    }

    public Page<Annotation> findByUser(UserAccount requester, String username, Pageable pageable) {
        return annotationRepository.findByUser(requester, username, pageable);
    }

    public Page<Annotation> findByGroup(UserAccount requester, String groupName, Pageable pageable) {
        return annotationRepository.findByGroup(requester, groupName, pageable);
    }

    public Page<Annotation> findByUpload(UserAccount requester, Long uploadId, Pageable pageable) {
        return annotationRepository.findByUpload(requester, uploadId, pageable);
    }

    public Page<Annotation> findByVocab(UserAccount requester, String username, Pageable pageable) {
        return annotationRepository.findByVocab(requester, username, pageable);
    }

    public Page<Annotation> findByParent(UserAccount requester, Long id, Pageable pageable) {
      return annotationRepository.findByParent(requester, id, pageable);
    }

    public Page<Annotation> findAllPublic(Pageable pageable) {
        return annotationRepository.findAllPublic(pageable);
    }

    public Page<Annotation> filter(UserAccount requester,
                                   String username,
                                   String group,
                                   Long upload,
                                   String vocab,
                                   String attributeLevel,
                                   String attributeName,
                                   String attributeRequirementLevel,
                                   Boolean showRedacted,
                                   Pageable pageable) {
        Specification<Annotation> spec = filterSpec(requester, username, group, upload, vocab, attributeLevel, attributeName, attributeRequirementLevel, showRedacted);
        return annotationRepository.findAll(spec, pageable);
    }

    public Page<Annotation> search(UserAccount requester,
                                   String username,
                                   String group,
                                   Long upload,
                                   String vocab,
                                   String attributeLevel,
                                   String attributeName,
                                   String attributeRequirementLevel,
                                   Boolean showRedacted,
                                   Set<String> matches,
                                   Set<String> nots,
                                   Pageable pageable) {
        Specification<Annotation> spec = searchSpec(requester, username, group, upload, vocab, attributeLevel, attributeName, attributeRequirementLevel, showRedacted, matches, nots);
        return annotationRepository.findAll(spec, pageable);
    }

    public Page<Annotation> findAll(UserAccount requester, Pageable pageable) {
        return annotationRepository.findAll(requester, pageable);
    }
}
