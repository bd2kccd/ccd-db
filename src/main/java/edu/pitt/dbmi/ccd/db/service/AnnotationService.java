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
import java.util.List;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.AnnotationData;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.AnnotationRepository;

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

    public Optional<Annotation> findOne(UserAccount requester, Long annoId) {
        return annotationRepository.findById(requester, annoId);
    }

    public Page<Annotation> findByUser(UserAccount requester, String username, Pageable pageable) {
        return (requester.getUsername() == username) ? annotationRepository.findByRequester(requester, pageable)
                                                     : annotationRepository.findByUser(requester, username, pageable);
    }

    public Page<Annotation> findByGroup(UserAccount requester, String groupName, Pageable pageable) {
        return annotationRepository.findByGroup(requester, groupName, pageable);
    }

    public Page<Annotation> findByUpload(UserAccount requester, Long uploadId, Pageable pageable) {
        return annotationRepository.findByUpload(requester, uploadId, pageable);
    }

    public Page<Annotation> findByUserAndGroup(UserAccount requester, String username, String groupName, Pageable pageable) {
        return annotationRepository.findByUserAndGroup(requester, username, groupName, pageable);
    }

    public Page<Annotation> findByUserAndUpload(UserAccount requester, String username, Long uploadId, Pageable pageable) {
        return annotationRepository.findByUserAndUpload(requester, username, uploadId, pageable);
    }

    public Page<Annotation> findByGroupAndUpload(UserAccount requester, String groupName, Long uploadId, Pageable pageable) {
        return annotationRepository.findByGroupAndUpload(requester, groupName, uploadId, pageable);
    }

    public Page<Annotation> findByUserAndGroupAndUpload(UserAccount requester, String username, String groupName, Long uploadId, Pageable pageable) {
        return null;
    }

    /**
     * Search for annotations matching non-null parameters
     * @param  requester                 requesting user (required)
     * @param  username                  username (nullable)
     * @param  group                     group name (nullable) 
     * @param  upload                    upload id (nullable)
     * @param  vocab                     vocab name (nullable)
     * @param  terms                     value contains (nullable)
     * @param  attributeLevel            attribute level (nullable)
     * @param  attributeName             attribute name (nullable)
     * @param  attributeRequirementLevel attribute requirement level (nullable)
     * @param  pageable                  page request
     * @return                           page of matches
     */
    public Page<Annotation> search(
            UserAccount requester,
            String username,
            String group,
            Long upload,
            String vocab,
            String terms,
            String attributeLevel,
            String attributeName,
            String attributeRequirementLevel,
            Pageable pageable) {
        return annotationRepository.search(requester, username, group, upload, vocab, terms, attributeLevel, attributeName, attributeRequirementLevel, pageable);
    }

    // public Page<Annotation> searchTest(
    //         UserAccount requester,
    //         String username,
    //         String group,
    //         Long upload,
    //         String vocab,
    //         List<String> terms,
    //         String attributeLevel,
    //         String attributeName,
    //         String attributeRequirementLevel,
    //         Pageable pageable) {
    //     return annotationRepository.searchTest(requester, username, group, upload, vocab, terms, attributeLevel, attributeName, attributeRequirementLevel, pageable);
    // }

    public Page<Annotation> findByVocab(UserAccount requester, String username, Pageable pageable) {
        return annotationRepository.findByVocab(requester, username, pageable);
    }

    public Page<Annotation> findByDataValue(UserAccount requester, String terms, Pageable pageable) {
        return annotationRepository.findByDataValue(requester, terms, pageable);
    }

    public Page<Annotation> findAllPublic(Pageable pageable) {
        return annotationRepository.findAllPublic(pageable);
    }

    public Page<Annotation> findAll(UserAccount requester, Pageable pageable) {
        return annotationRepository.findAll(requester, pageable);
    }
}
