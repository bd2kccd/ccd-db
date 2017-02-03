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

import static edu.pitt.dbmi.ccd.db.specification.AnnotationSpecification.*;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.AnnotationRepository;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AnnotationService {

    @Autowired
    private AnnotationRepository annotationRepository;

    public Annotation save(Annotation annotation) {
        return annotationRepository.save(annotation);
    }

    public Annotation saveAndFlush(Annotation annotation) {
        return annotationRepository.saveAndFlush(annotation);
    }

    public Annotation findById(UserAccount requester, Long id) {
        Specification<Annotation> spec = idSpec(requester, id);
        return annotationRepository.findOne(spec);
    }

    public Page<Annotation> findByParent(UserAccount requester, Annotation annotation, boolean showRedacted, Pageable pageable) {
        Specification<Annotation> spec = parentSpec(requester, annotation.getId(), showRedacted);
        return annotationRepository.findAll(spec, pageable);
    }

//    public Page<Annotation> filter(
//            UserAccount requester,
//            String username,
//            String group,
//            Long target,
//            String vocab,
//            String attributeLevel,
//            String attributeName,
//            String attributeRequirementLevel,
//            Boolean showRedacted,
//            Boolean parentless,
//            Date createdBefore,
//            Date createdAfter,
//            Date modifiedBefore,
//            Date modifiedAfter,
//            Pageable pageable) {
//        Specification<Annotation> spec = filterSpec(requester, username, group, target, vocab, attributeLevel, attributeName, attributeRequirementLevel, showRedacted, parentless, createdBefore, createdAfter, modifiedBefore, modifiedAfter);
//        return annotationRepository.findAll(spec, pageable);
//    }
//
//    public Page<Annotation> search(
//            UserAccount requester,
//            String username,
//            String group,
//            Long target,
//            String vocab,
//            String attributeLevel,
//            String attributeName,
//            String attributeRequirementLevel,
//            Boolean showRedacted,
//            Boolean parentless,
//            Date createdBefore,
//            Date createdAfter,
//            Date modifiedBefore,
//            Date modifiedAfter,
//            Set<String> matches,
//            Set<String> nots,
//            Pageable pageable) {
//        Specification<Annotation> spec = searchSpec(requester, username, group, target, vocab, attributeLevel, attributeName, attributeRequirementLevel, showRedacted, parentless, createdBefore, createdAfter, modifiedBefore, modifiedAfter, matches, nots);
//        return annotationRepository.findAll(spec, pageable);
//    }

//    public Page<Annotation> filter(Specification<Annotation> specification, Pageable pageable) {
//        return annotationRepository.findAll(specification, pageable);
//    }

    public Page<Annotation> search(Specification<Annotation> specification, Pageable pageable) {
        return annotationRepository.findAll(specification, pageable);
    }

    public Page<Annotation> findAll(UserAccount requester, Pageable pageable) {
        Specification<Annotation> spec = authSpec(requester);
        return annotationRepository.findAll(spec, pageable);
    }

    protected void delete(Annotation annotation) {
        annotationRepository.delete(annotation);
    }
}
