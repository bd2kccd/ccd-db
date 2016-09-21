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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.AnnotationData;
import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.repository.AnnotationRepository;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class AnnotationDataServiceTest {

    @Autowired
    private AnnotationDataService annotationDataService;
    @Autowired
    private AnnotationRepository annotationRepository;
    @Autowired
    private AttributeService attributeService;

    @Test
    public void saveAndDelete() {
        // save
        final Annotation annotation = annotationRepository.findOne(1L);
        final Attribute attribute = attributeService.findById(1L).get();
        AnnotationData annotationData = new AnnotationData(annotation, attribute, "TEST ANNOTATION DATA", null);
        annotationData = annotationDataService.save(annotationData);
        assertNotNull(annotationData.getId());

        // delete
        annotationDataService.delete(annotationData);
        final Optional<AnnotationData> found = annotationDataService.findById(annotationData.getId());
        assertFalse(found.isPresent());
    }

    @Test
    public void findById() {
        final Optional<AnnotationData> annotationData = annotationDataService.findById(1L);
        assertTrue(annotationData.isPresent());
    }

    @Test
    public void findByAnnotation() {
        final Pageable pageable = new PageRequest(0, 2);
        final Annotation annotation = annotationRepository.findOne(4L);
        final Page<AnnotationData> annotationData = annotationDataService.findByAnnotation(annotation, pageable);
        assertEquals(2, annotationData.getTotalElements());
    }
}
