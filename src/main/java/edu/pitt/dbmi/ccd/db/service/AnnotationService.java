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

import static edu.pitt.dbmi.ccd.db.specification.AnnotationSpecification.filterSpec;
import static edu.pitt.dbmi.ccd.db.specification.AnnotationSpecification.idSpec;
import static edu.pitt.dbmi.ccd.db.specification.AnnotationSpecification.parentSpec;

import javax.transaction.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.AnnotationRepository;

/**
 *
 * Aug 3, 2016 4:23:35 PM
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AnnotationService {

    private final AnnotationRepository annotationRepository;

    @Autowired
    public AnnotationService(AnnotationRepository annotationRepository) {
        this.annotationRepository = annotationRepository;
    }

    public Annotation save (Annotation annotation) {
        return annotationRepository.save(annotation);
    }

    public Set<Annotation> save(Set<Annotation> annotations) {
        return annotationRepository.save(annotations).stream().collect(Collectors.toSet());
    }

    public Optional<Annotation> findById(UserAccount requester, Long id) {
        Specification<Annotation> specification = idSpec(requester, id);
        return Optional.ofNullable(annotationRepository.findOne(specification));
    }

    public Page<Annotation> findByParent(UserAccount requester, Annotation parent, boolean showRedacted, Pageable pageable) {
        Specification<Annotation> specification = parentSpec(requester, parent.getId(), showRedacted);
        return annotationRepository.findAll(specification, pageable);
    }
//
//    public Page<Annotation> filter(UserAccount requester, Long user, Long group, Long target, Long vocabulary, Long attributeLevel, Long attribute, Boolean attributeRequired, Boolean showRedacted, Boolean parentless, Pageable pageable) {
//        Specification<Annotation> specification = filterSpec(requester, user, group)
//    }

    protected void delete(Annotation annotation) {
        annotationRepository.delete(annotation);
    }

    protected void delete(Set<Annotation> annotations) {
        annotationRepository.delete(annotations);
    }
}
