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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import edu.pitt.dbmi.ccd.db.entity.AnnotationData;
import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.repository.AnnotationDataRepository;
import edu.pitt.dbmi.ccd.db.service.AnnotationService;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AnnotationDataService {

    private final AnnotationDataRepository annotationDataRepository;
    private final AnnotationService annotationService;

    @Autowired(required=true)
    public AnnotationDataService(AnnotationDataRepository annotationDataRepository, AnnotationService annotationService) {
        this.annotationDataRepository = annotationDataRepository;
        this.annotationService = annotationService;
    }

    public AnnotationData save(AnnotationData data) {
        return annotationDataRepository.save(data);
    }

    public AnnotationData findAnnotationData(Long id) {
        return annotationDataRepository.findOne(id);
    }

    public List<AnnotationData> findByAnnotation(Annotation annotation) {
        return annotationDataRepository.findByAnnotation(annotation);
    }
}
