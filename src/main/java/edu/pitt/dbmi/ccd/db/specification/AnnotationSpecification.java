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

package edu.pitt.dbmi.ccd.db.specification;

import static edu.pitt.dbmi.ccd.db.util.StringUtils.isNullOrEmpty;

import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.AnnotationData;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public final class AnnotationSpecification {

    public static final String USER = "user";
    public static final String GROUP = "group";
    public static final String ACCESS = "accessControl";
    public static final String TARGET = "target";
    public static final String VOCAB = "vocab";
    public static final String ANNO = "annotation";
    public static final String ATTRIB = "attribute";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String PUBLIC_ACCESS = "PUBLIC";
    public static final String GROUP_ACCESS = "GROUP";
    public static final String PRIVATE_ACCESS = "PRIVATE";
    public static final String VALUE = "value";
    public static final String LEVEL = "level";
    public static final String REQ_LEVEL = "requirementLevel";
    public static final String REDACTED = "redacted";

    private AnnotationSpecification() { }

    public static Specification<Annotation> authSpec(UserAccount requester) {
        return (root, query, cb) -> {
            return authFilter(root, cb, requester);
        };
    }

    public static Specification<Annotation> filterSpec(UserAccount requester,
                                                       String username,
                                                       String group,
                                                       Long upload,
                                                       String vocab,
                                                       String attributeLevel,
                                                       String attributeName,
                                                       String attributeReqLevel,
                                                       Boolean showRedacted) {
        return (root, query, cb) -> {
            return buildFilterSpec(root, query, cb, requester, username, group, upload, vocab, attributeLevel, attributeName, attributeReqLevel, showRedacted);
        };
    }

    public static Specification<Annotation> searchSpec(UserAccount requester,
                                                       String username,
                                                       String group,
                                                       Long upload,
                                                       String vocab,
                                                       String attributeLevel,
                                                       String attributeName,
                                                       String attributeReqLevel,
                                                       Boolean showRedacted,
                                                       Set<String> matches,
                                                       Set<String> nots) {
        return (root, query, cb) -> {
            return buildSearchSpec(root, query, cb, requester, username, group, upload, vocab, attributeLevel, attributeName, attributeReqLevel, showRedacted, matches, nots);
        };
    }
  
   private static Predicate buildFilterSpec(Root<Annotation> root,
                                               CriteriaQuery query,
                                               CriteriaBuilder cb,
                                               UserAccount requester,
                                               String username,
                                               String group,
                                               Long upload,
                                               String vocab,
                                               String attributeLevel,
                                               String attributeName,
                                               String attributeReqLevel,
                                               Boolean showRedacted) {
        List<Predicate> predicates = new ArrayList<>(0);
        Predicate authPredicate = authFilter(root, cb, requester);
        List<Predicate> filterPredicates = buildFilterPredicates(root, query, cb, username, group, upload, vocab, attributeLevel, attributeName, attributeReqLevel, showRedacted);
        
        predicates.add(authPredicate);
        predicates.addAll(filterPredicates);
        
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    private static Predicate buildSearchSpec(Root<Annotation> root,
                                               CriteriaQuery query,
                                               CriteriaBuilder cb,
                                               UserAccount requester,
                                               String username,
                                               String group,
                                               Long upload,
                                               String vocab,
                                               String attributeLevel,
                                               String attributeName,
                                               String attributeReqLevel,
                                               Boolean showRedacted,
                                               Set<String> matches,
                                               Set<String> nots) {
        List<Predicate> predicates = new ArrayList<>(0);
        Predicate authPredicate = authFilter(root, cb, requester);
        List<Predicate> filterPredicates = buildFilterPredicates(root, query, cb, username, group, upload, vocab, attributeName, attributeLevel, attributeReqLevel, showRedacted);
        List<Predicate> searchPredicates = buildSearchPredicates(root, query, cb, matches, nots);
        
        predicates.add(authPredicate);
        predicates.addAll(filterPredicates);
        predicates.addAll(searchPredicates);
        
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    /**
     * Requester access predicate
     * 
     * annotation is private AND belongs to user
     * OR annotation has public access
     * OR annotation has group access AND requester is in group
     */
    private static Predicate authFilter(Root<Annotation> root, CriteriaBuilder cb, UserAccount requester) {
        return cb.or(cb.like(root.get(ACCESS).get(NAME), PUBLIC_ACCESS),
                     cb.and(cb.like(root.get(ACCESS).get(NAME), PRIVATE_ACCESS),
                            cb.equal(root.get(USER), requester)),
                     cb.and(cb.like(root.get(ACCESS).get(NAME), GROUP_ACCESS),
                            root.get(GROUP).in(requester.getGroups())));
    }

    // build filter predicates of non-null parameters
    private static List<Predicate> buildFilterPredicates(Root<Annotation> root,
                                                         CriteriaQuery query,
                                                         CriteriaBuilder cb,
                                                         String username,
                                                         String group,
                                                         Long upload,
                                                         String vocab,
                                                         String attributeLevel,
                                                         String attributeName,
                                                         String attributeReqLevel,
                                                         Boolean showRedacted) {
        
        List<Predicate> predicates = new ArrayList<>(0);
        if (!isNullOrEmpty(username)) {
            predicates.add(belongsToUser(root, cb, username));
        }
        if (!isNullOrEmpty(group)) {
            predicates.add(belongsToGroup(root, cb, group));
        }
        if (!isNullOrEmpty(upload)) {
            predicates.add(targetsUpload(root, cb, upload));
        }
        if (!isNullOrEmpty(vocab)) {
            predicates.add(hasVocabulary(root, cb, vocab));
        }
        if (!isNullOrEmpty(attributeLevel)) {
            predicates.add(hasAttributeLevel(root, query, cb, attributeLevel));            
        }
        if (!isNullOrEmpty(attributeName)) {
            predicates.add(hasAttributeName(root, query, cb, attributeName));            
        }
        if (!isNullOrEmpty(attributeReqLevel)) {
            predicates.add(hasAttributeReqLevel(root, query, cb, attributeReqLevel));            
        }
        if (!showRedacted) {
            predicates.add(notRedacted(root, cb));
        }

        return predicates;
    }

    // build search predicates of non-null parameters
    private static List<Predicate> buildSearchPredicates(Root<Annotation> root,
                                                         CriteriaQuery query,
                                                         CriteriaBuilder cb,
                                                         Set<String> matches,
                                                         Set<String> nots) {
        
        List<Predicate> predicates = new ArrayList<>(0);
        if (!isNullOrEmpty(matches)) {
            predicates.addAll(containsTerms(root, query, cb, matches));
        }
        if (!isNullOrEmpty(nots)) {
            predicates.addAll(doesNotContainTerms(root, query, cb, nots));
        }
        return predicates;
    }


    // belong to user predicate
    private static Predicate belongsToUser(Root<Annotation> root, CriteriaBuilder cb, String username) {
        return cb.like(cb.lower(root.get(USER).get(USERNAME)), username.toLowerCase());
    }

    // belongs to group predicate
    private static Predicate belongsToGroup(Root<Annotation> root, CriteriaBuilder cb, String group) {
        return cb.like(cb.lower(root.get(GROUP).get(NAME)), group.toLowerCase());
    }

    // targets upload predicate
    private static Predicate targetsUpload(Root<Annotation> root, CriteriaBuilder cb, Long upload) {
        return cb.equal(root.get(TARGET).get(ID), upload);
    }

    // has vocabulary predicate
    private static Predicate hasVocabulary(Root<Annotation> root, CriteriaBuilder cb, String vocab) {
        return cb.like(cb.lower(root.get(VOCAB).get(NAME)), vocab.toLowerCase());
    }

    // contains terms predicate
    private static List<Predicate> containsTerms(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, Set<String> terms) {
        return terms.stream()
                    .map(t -> containsLike(t))
                    .map(t -> existsInData(root, query, cb, t))
                    .collect(Collectors.toList());
    }

    // term found in at least one AnnotationData value
    private static Predicate existsInData(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, String term) {
        Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(subquery.select(subroot)
                                 .where(cb.and(cb.equal(subroot.get(ANNO), root),
                                               cb.like(cb.lower(subroot.get(VALUE)), term))));
    }

    // does not contain terms predicate
    private static List<Predicate> doesNotContainTerms(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, Set<String> terms) {
        return terms.stream()
                    .map(t -> containsLike(t))
                    .map(t -> doesNotExistInData(root, query, cb, t))
                    .collect(Collectors.toList());
    }

    // term not found in any AnnotationData value
    private static Predicate doesNotExistInData(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, String term) {
        return cb.not(existsInData(root, query, cb, term));
    }

    // has attribute name
    private static Predicate hasAttributeName(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, String attributeName) {
        Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(subquery.select(subroot)
                                 .where(cb.and(cb.equal(subroot.get(ANNO), root),
                                               cb.like(cb.lower(subroot.get(ATTRIB).get(NAME)), attributeName.toLowerCase()))));
    }

    // has attribute level
    private static Predicate hasAttributeLevel(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, String attributeLevel) {
        Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(subquery.select(subroot)
                                 .where(cb.and(cb.equal(subroot.get(ANNO), root),
                                               cb.like(cb.lower(subroot.get(ATTRIB).get(LEVEL)), attributeLevel.toLowerCase()))));
    }

    // has attribute requirement name
    private static Predicate hasAttributeReqLevel(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, String attributeReqLevel) {
        Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(subquery.select(subroot)
                                 .where(cb.and(cb.equal(subroot.get(ANNO), root),
                                               cb.like(cb.lower(subroot.get(ATTRIB).get(REQ_LEVEL)), attributeReqLevel.toLowerCase()))));
    }

    // annotation is not redacted
    private static Predicate notRedacted(Root<Annotation> root, CriteriaBuilder cb) {
        return cb.equal(root.get(REDACTED), false);
    }

    private static String containsLike(String terms) {
        if (isNullOrEmpty(terms)) {
            return "%";
        } else {
            return "%" + terms.toLowerCase() + "%";
        }
    }
}