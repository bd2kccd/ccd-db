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

import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.AnnotationData;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import static edu.pitt.dbmi.ccd.db.util.StringUtils.isNullOrEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

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
    public static final String PARENT = "parent";
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
    public static final String CREATED_DATE = "created";
    public static final String MODIFIED_DATE = "modified";

    private AnnotationSpecification() {
    }

    public static Specification<Annotation> idSpec(UserAccount requester, Long id) {
        return (root, query, cb) -> {
            return buildIdSpec(root, cb, requester, id);
        };
    }

    public static Specification<Annotation> parentSpec(UserAccount requester, Long id, boolean showRedacted) {
        return (root, query, cb) -> {
            return buildParentSpec(root, cb, requester, id, showRedacted);
        };
    }

    public static Specification<Annotation> authSpec(UserAccount requester) {
        return (root, query, cb) -> {
            return authFilter(root, cb, requester);
        };
    }

    public static Specification<Annotation> filterSpec(UserAccount requester,
            String username,
            String group,
            Long target,
            String vocab,
            String attributeLevel,
            String attributeName,
            String attributeReqLevel,
            Boolean showRedacted,
            Boolean parentless,
            Date createdBefore,
            Date createdAfter,
            Date modifiedBefore,
            Date modifiedAfter) {
        return (root, query, cb) -> {
            return buildFilterSpec(root, query, cb, requester, username, group, target, vocab, attributeLevel, attributeName, attributeReqLevel, showRedacted, parentless, createdBefore, createdAfter, modifiedBefore, modifiedAfter);
        };
    }

    public static Specification<Annotation> searchSpec(UserAccount requester,
            String username,
            String group,
            Long target,
            String vocab,
            String attributeLevel,
            String attributeName,
            String attributeReqLevel,
            Boolean showRedacted,
            Boolean parentless,
            Date createdBefore,
            Date createdAfter,
            Date modifiedBefore,
            Date modifiedAfter,
            Set<String> matches,
            Set<String> nots) {
        return (root, query, cb) -> {
            return buildSearchSpec(root, query, cb, requester, username, group, target, vocab, attributeLevel, attributeName, attributeReqLevel, showRedacted, parentless, createdBefore, createdAfter, modifiedBefore, modifiedAfter, matches, nots);
        };
    }

    private static Predicate buildIdSpec(Root<Annotation> root, CriteriaBuilder cb, UserAccount requester, Long id) {
        final Predicate authPredicate = authFilter(root, cb, requester);
        final Predicate idPredicate = cb.equal(root.get(ID), id);
        final Predicate[] predicates = new Predicate[]{authPredicate, idPredicate};
        return cb.and(predicates);
    }

    private static Predicate buildParentSpec(Root<Annotation> root, CriteriaBuilder cb, UserAccount requester, Long id, Boolean showRedacted) {
        final Predicate authPredicate = authFilter(root, cb, requester);
        final Predicate idPredicate = cb.equal(root.get(PARENT).get(ID), id);
        Predicate[] predicates;
        if (showRedacted) {
            predicates = new Predicate[]{authPredicate, idPredicate};
        } else {
            predicates = new Predicate[]{authPredicate, idPredicate, notRedacted(root, cb)};
        }
        return cb.and(predicates);
    }

    private static Predicate buildFilterSpec(Root<Annotation> root,
            CriteriaQuery query,
            CriteriaBuilder cb,
            UserAccount requester,
            String username,
            String group,
            Long target,
            String vocab,
            String attributeLevel,
            String attributeName,
            String attributeReqLevel,
            Boolean showRedacted,
            Boolean parentless,
            Date createdBefore,
            Date createdAfter,
            Date modifiedBefore,
            Date modifiedAfter) {
        final List<Predicate> predicates = new ArrayList<>(0);
        final Predicate authPredicate = authFilter(root, cb, requester);
        final List<Predicate> filterPredicates = buildFilterPredicates(root, query, cb, username, group, target, vocab, attributeLevel, attributeName, attributeReqLevel, showRedacted, parentless, createdBefore, createdAfter, modifiedBefore, modifiedAfter);

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
            Long target,
            String vocab,
            String attributeLevel,
            String attributeName,
            String attributeReqLevel,
            Boolean showRedacted,
            Boolean parentless,
            Date createdBefore,
            Date createdAfter,
            Date modifiedBefore,
            Date modifiedAfter,
            Set<String> matches,
            Set<String> nots) {
        final List<Predicate> predicates = new ArrayList<>(0);
        final Predicate authPredicate = authFilter(root, cb, requester);
        final List<Predicate> filterPredicates = buildFilterPredicates(root, query, cb, username, group, target, vocab, attributeName, attributeLevel, attributeReqLevel, showRedacted, parentless, createdBefore, createdAfter, modifiedBefore, modifiedAfter);
        final List<Predicate> searchPredicates = buildSearchPredicates(root, query, cb, matches, nots);

        predicates.add(authPredicate);
        predicates.addAll(filterPredicates);
        predicates.addAll(searchPredicates);

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    /**
     * Requester access predicate
     * <p>
     * annotation has public access OR annotation has private access AND
     * annotation belongs to requester OR annotation has group access AND
     * requester is in group
     */
    private static Predicate authFilter(Root<Annotation> root, CriteriaBuilder cb, UserAccount requester) {
        final List<Predicate> predicates = new ArrayList<>(0);

        // public access
        predicates.add(cb.like(root.get(ACCESS).get(NAME), PUBLIC_ACCESS));

        // private access AND belongs to requester
        predicates.add(cb.and(cb.like(root.get(ACCESS).get(NAME), PRIVATE_ACCESS),
                cb.equal(root.get(USER), requester)));

        // group access AND requester in group
        // criteriabuilder's in clause throws
        // sql error if collection has no elements
        if (requester.getGroups().size() > 0) {
            predicates.add(cb.and(cb.like(root.get(ACCESS).get(NAME), GROUP_ACCESS),
                    root.get(GROUP).in(requester.getGroups())));
        }
        return cb.or(predicates.toArray(new Predicate[predicates.size()]));
    }

    // build filter predicates of non-null parameters
    private static List<Predicate> buildFilterPredicates(Root<Annotation> root,
            CriteriaQuery query,
            CriteriaBuilder cb,
            String username,
            String group,
            Long target,
            String vocab,
            String attributeLevel,
            String attributeName,
            String attributeReqLevel,
            Boolean showRedacted,
            Boolean parentless,
            Date createdBefore,
            Date createdAfter,
            Date modifiedBefore,
            Date modifiedAfter) {

        final List<Predicate> predicates = new ArrayList<>(0);
        if (!isNullOrEmpty(username)) {
            predicates.add(belongsToUser(root, cb, username));
        }
        if (!isNullOrEmpty(group)) {
            predicates.add(belongsToGroup(root, cb, group));
        }
        if (!isNullOrEmpty(target)) {
            predicates.add(targetstarget(root, cb, target));
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
        if (parentless) {
            predicates.add(parentless(root, cb));
        }
        if (!isNullOrEmpty(createdBefore)) {
            predicates.add(createdBefore(root, cb, createdBefore));
        }
        if (!isNullOrEmpty(createdAfter)) {
            predicates.add(createdAfter(root, cb, createdAfter));
        }
        if (!isNullOrEmpty(modifiedBefore)) {
            predicates.add(modifiedBefore(root, cb, modifiedBefore));
        }
        if (!isNullOrEmpty(modifiedAfter)) {
            predicates.add(modifiedAfter(root, cb, modifiedAfter));
        }
        return predicates;
    }

    // build search predicates of non-null parameters
    private static List<Predicate> buildSearchPredicates(Root<Annotation> root,
            CriteriaQuery query,
            CriteriaBuilder cb,
            Set<String> matches,
            Set<String> nots) {

        final List<Predicate> predicates = new ArrayList<>(0);
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

    // targets target predicate
    private static Predicate targetstarget(Root<Annotation> root, CriteriaBuilder cb, Long target) {
        return cb.equal(root.get(TARGET).get(ID), target);
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
        final Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        final Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
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
        final Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        final Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(subquery.select(subroot)
                .where(cb.and(cb.equal(subroot.get(ANNO), root),
                        cb.like(cb.lower(subroot.get(ATTRIB).get(NAME)), attributeName.toLowerCase()))));
    }

    // has attribute level
    private static Predicate hasAttributeLevel(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, String attributeLevel) {
        final Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        final Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(subquery.select(subroot)
                .where(cb.and(cb.equal(subroot.get(ANNO), root),
                        cb.like(cb.lower(subroot.get(ATTRIB).get(LEVEL)), attributeLevel.toLowerCase()))));
    }

    // has attribute requirement name
    private static Predicate hasAttributeReqLevel(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, String attributeReqLevel) {
        final Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        final Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(subquery.select(subroot)
                .where(cb.and(cb.equal(subroot.get(ANNO), root),
                        cb.like(cb.lower(subroot.get(ATTRIB).get(REQ_LEVEL)), attributeReqLevel.toLowerCase()))));
    }

    // annotation is not redacted
    private static Predicate notRedacted(Root<Annotation> root, CriteriaBuilder cb) {
        return cb.equal(root.get(REDACTED), false);
    }

    // annotation has no parent
    private static Predicate parentless(Root<Annotation> root, CriteriaBuilder cb) {
        return cb.isNull(root.get(PARENT));
    }

    // annotation was created before date
    private static Predicate createdBefore(Root<Annotation> root, CriteriaBuilder cb, Date date) {
        return cb.lessThan(root.<Date>get(CREATED_DATE), date);
    }

    // annotation was created after date
    private static Predicate createdAfter(Root<Annotation> root, CriteriaBuilder cb, Date date) {
        return cb.greaterThan(root.<Date>get(CREATED_DATE), date);
    }

    // annotation was modified before date
    private static Predicate modifiedBefore(Root<Annotation> root, CriteriaBuilder cb, Date date) {
        return cb.lessThan(root.<Date>get(CREATED_DATE), date);
    }

    // annotation was modified after date
    private static Predicate modifiedAfter(Root<Annotation> root, CriteriaBuilder cb, Date date) {

        return cb.greaterThan(root.<Date>get(CREATED_DATE), date);
    }

    private static String containsLike(String terms) {
        if (isNullOrEmpty(terms)) {
            return "%";
        } else {
            return "%" + terms.toLowerCase() + "%";
        }
    }
}
