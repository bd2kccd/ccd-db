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
import edu.pitt.dbmi.ccd.db.entity.Upload;
import edu.pitt.dbmi.ccd.db.entity.Access;
import edu.pitt.dbmi.ccd.db.entity.Group;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;
import edu.pitt.dbmi.ccd.db.repository.AnnotationRepository;
import edu.pitt.dbmi.ccd.db.service.UploadService;
import edu.pitt.dbmi.ccd.db.service.AccessService;
import edu.pitt.dbmi.ccd.db.service.GroupService;
import edu.pitt.dbmi.ccd.db.service.VocabularyService;
import edu.pitt.dbmi.ccd.db.error.NotFoundException;
import edu.pitt.dbmi.ccd.db.error.AccessUpdateException;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class AnnotationService {

    private final AnnotationRepository annotationRepository;
    private final UploadService uploadService;
    private final AccessService accessService;
    private final GroupService groupService;
    private final VocabularyService vocabularyService;

    @Autowired(required=true)
    public AnnotationService(
            AnnotationRepository annotationRepository,
            UploadService uploadService,
            AccessService accessService,
            GroupService groupService,
            VocabularyService vocabularyService) {
        this.annotationRepository = annotationRepository;
        this.uploadService = uploadService;
        this.accessService = accessService;
        this.groupService = groupService;
        this.vocabularyService = vocabularyService;
    }

    public Annotation create(
            UserAccount user,
            Long targetId,
            Long parentId,
            String accessName,
            String groupName,
            String vocababularyName) {
        final Upload upload = uploadService.findOne(targetId);
        final Annotation anno = (parentId != null)
                              ? findById(user, parentId)
                              : null;
        final Access access = (groupName != null)
                            ? accessService.findByName(accessName)
                            : accessService.findByName("GROUP");
        final Group group = (groupName != null)
                          ? groupService.findByName(groupName)
                          : null;
        final Vocabulary vocab = vocabularyService.findByName(vocababularyName);
        final Annotation annotation = new Annotation(user, upload, anno, access, group, vocab);
        return save(annotation);
    }

    public Annotation update(Annotation annotation, String accessName, String groupName) {
        final Access access = accessService.findByName(accessName);
        final Group group = (groupName != null)
                          ? groupService.findByName(groupName)
                          : null;
        // no changes, don't update
        if (annotation.getAccess().getName().equalsIgnoreCase(access.getName()) && annotation.getGroup().getName().equalsIgnoreCase(group.getName())) {
            return annotation;
        // update just group
        } else if (annotation.getAccess().getName().equalsIgnoreCase(access.getName()) && access.getName().equalsIgnoreCase("GROUP") && !annotation.getGroup().getName().equalsIgnoreCase(group.getName()) {
            annotation.setGroup(group);
            return save(annotation);
        // update access from private to group and update group
        } else if (annotation.getAccess().getName().equalsIgnoreCase("PRIVATE") && access.getName().equalsIgnoreCase("GROUP")) {
            if (group == null) {
              throw new AccessUpdateException(true);
            } else {
              annotation.setAccess(access);
              annotation.setGroup(group);
              return save(annotation);
            }
        // update access from private to public
        } else if (annotation.getAccess().getName().equalsIgnoreCase("PRIVATE") && access.getName().equalsIgnoreCase("PUBLIC")) {
            annotation.setAccess(access);
            return save(annotation);
        // update access from group to public and remove group
        } else if (annotation.getAccess().getName().equalsIgnoreCase("GROUP") && access.getName().equalsIgnoreCase("PUBLIC")) {
            annotation.setAccess(access);
            annotation.setGroup(null);
            return save(annotation);          
        } else {
            throw new AccessUpdateException(annotation.getAccess(), access);
        }
    }

    public Annotation save(Annotation annotation) {
        return annotationRepository.save(annotation);
    }

    public Annotation saveAndFlush(Annotation annotation) {
      return annotationRepository.saveAndFlush(annotation);
    }

    public Annotation findOne(UserAccount requester, Long id) {
        Optional<Annotation> annotation = annotationRepository.findById(requester, id);
        return annotation.orElseThrow(() -> new NotFoundException("Annotation", "id", id));
    }

    public Page<Annotation> findByParent(UserAccount requester, Long id, Pageable pageable) {
      return annotationRepository.findByParent(requester, id, pageable);
    }

    public Page<Annotation> findAllPublic(Pageable pageable) {
        return annotationRepository.findAllPublic(pageable);
    }

    public Page<Annotation> filter(
            UserAccount requester,
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

    public Page<Annotation> search(
            UserAccount requester,
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
