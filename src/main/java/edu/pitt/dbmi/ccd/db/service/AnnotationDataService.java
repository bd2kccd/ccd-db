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

    public AnnotationData create(Annotation annotation, Long parentId, Vocabulary vocabulary, Long attributeId, String value) {
        final AnnotationData parent = (parentId != null) ? findOne(parentId)
                                                         : null;
        final Attribute attribute = attributeService.findByVocabAndId(vocabulary, attributeId);
        final AnnotationData annoData = new AnnotationData(annotation, parent, attribute, value);
        return save(annoData);   
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
