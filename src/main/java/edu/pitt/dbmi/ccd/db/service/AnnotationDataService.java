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

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.pitt.dbmi.ccd.db.entity.AnnotationData;
import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;
import edu.pitt.dbmi.ccd.db.repository.AnnotationDataRepository;
import edu.pitt.dbmi.ccd.db.service.AnnotationService;
import edu.pitt.dbmi.ccd.db.service.AttributeService;
import edu.pitt.dbmi.ccd.db.error.NotFoundException;
import edu.pitt.dbmi.ccd.db.error.VocabularyMismatchException;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AnnotationDataService {

    private final AnnotationDataRepository annotationDataRepository;
    private final AttributeService attributeService;

    @Autowired(required=true)
    public AnnotationDataService(AnnotationDataRepository annotationDataRepository, AttributeService attributeService) {
        this.annotationDataRepository = annotationDataRepository;
        this.attributeService = attributeService;
    }

    public AnnotationData create(Annotation annotation, Long attributeId, String value) {
        final Attribute attribute = attributeService.findOne(attributeId);
        final AnnotationData annoData = new AnnotationData(annotation, attribute, value);
        return save(annoData);
    }

    public AnnotationData create(Annotation annotation, AnnotationData parent, Long attributeId, String value) {
        final Attribute attribute = attributeService.findOne(attributeId);
        final AnnotationData annoData = new AnnotationData(annotation, parent, attribute, value);
        return save(annoData);
    }

    public AnnotationData patch(AnnotationData data, Long attributeId, String value) {
        final Attribute attribute = (attributeId != null) ? attributeService.findOne(attributeId) : null;
        if (attribute != null) {
            if (data.getAnnotation().getVocabulary() != attribute.getVocabulary()) {
                throw new VocabularyMismatchException(attribute, data.getAnnotation().getVocabulary());
            } else {
                data.setAttribute(attribute);
            }
        }
        if (value != null) {
            data.setValue(value);
        }
        return save(data);
    }

    public AnnotationData save(AnnotationData data) {
        return annotationDataRepository.save(data);
    }

    public AnnotationData findOne(Long id) {
        Optional<AnnotationData> annotationData = annotationDataRepository.findById(id);
        return annotationData.orElseThrow(() -> new NotFoundException("AnnotationData", "id", id));
    }

    public Page<AnnotationData> findByAnnotation(Annotation annotation, Pageable pageable) {
        return annotationDataRepository.findByAnnotation(annotation, pageable);
    }
}
