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

import static edu.pitt.dbmi.ccd.db.specification.AnnotationTargetSpecification.filterSpec;
import static edu.pitt.dbmi.ccd.db.specification.AnnotationTargetSpecification.searchSpec;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pitt.dbmi.ccd.db.entity.AnnotationTarget;
import edu.pitt.dbmi.ccd.db.repository.AnnotationTargetRepository;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AnnotationTargetService {

    @Autowired
    private AnnotationTargetRepository annotationTargetRepository;

    public AnnotationTarget save(AnnotationTarget annotationTarget) {
        return annotationTargetRepository.save(annotationTarget);
    }

    public List<AnnotationTarget> save(Set<AnnotationTarget> annotationTargets) {
        return annotationTargetRepository.save(annotationTargets);
    }

//    public AnnotationTarget findUpload(Long id) {
//        return annotationTargetRepository.findOne(id);
//    }

    public Optional<AnnotationTarget> findById(Long id) {
        return annotationTargetRepository.findById(id);
    }

    public Page<AnnotationTarget> filter(String username, String type, Pageable pageable) {
        return annotationTargetRepository.findAll(filterSpec(username, type), pageable);
    }

    public Page<AnnotationTarget> search(String username, String type, Set<String> matches, Set<String> nots, Pageable pageable) {
        return annotationTargetRepository.findAll(searchSpec(username, type, matches, nots), pageable);
    }

    public Page<AnnotationTarget> findAll(Pageable pageable) {
        return annotationTargetRepository.findAll(pageable);
    }

    public void delete(AnnotationTarget annotationTarget) {
        annotationTargetRepository.delete(annotationTarget);
    }

    public void delete(Set<AnnotationTarget> annotationTargets) {
        annotationTargetRepository.delete(annotationTargets);
    }
}
