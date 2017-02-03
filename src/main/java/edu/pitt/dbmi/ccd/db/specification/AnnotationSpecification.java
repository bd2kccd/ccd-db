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

import static org.springframework.util.StringUtils.isEmpty;

import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.validation.constraints.Null;

import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.AnnotationData;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public final class AnnotationSpecification {

    private static final String USER = "user";
    private static final String GROUP = "group";
    private static final String ACCESS = "accessControl";
    private static final String TARGET = "target";
    private static final String VOCABULARY = "vocabulary";
    private static final String ANNOTATION = "annotation";
    private static final String ATTRIBRIBUTE = "attribute";
    private static final String PARENT = "parent";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ACCOUNTID = "accountId";
    private static final String PUBLIC_ACCESS = "PUBLIC";
    private static final String GROUP_ACCESS = "GROUP";
    private static final String PRIVATE_ACCESS = "PRIVATE";
    private static final String VALUE = "value";
    private static final String LEVEL = "level";
    private static final String REQ_LEVEL = "requirementLevel";
    private static final String REDACTED = "redacted";
    private static final String CREATED_DATE = "created";
    private static final String MODIFIED_DATE = "modified";
    private static final String DATE_RANGE_ERROR = "After date must be before Before date";

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
                                                       String userAccountId,
                                                       Long groupId,
                                                       Long targetId,
                                                       Long vocabularyId,
                                                       Long attributeId,
                                                       String attributeLevel,
                                                       String attributeName,
                                                       String attributeReqLevel,
                                                       Boolean showRedacted,
                                                       Boolean topLevel,
                                                       Date createdBefore,
                                                       Date createdAfter,
                                                       Date modifiedBefore,
                                                       Date modifiedAfter) {
        return (root, query, cb) -> {
            return buildFilterSpec(root, query, cb, requester, userAccountId, groupId, targetId, vocabularyId, attributeId, attributeLevel, attributeName, attributeReqLevel, showRedacted, topLevel, createdBefore, createdAfter, modifiedBefore, modifiedAfter);
        };
    }

    public static Specification<Annotation> searchSpec(UserAccount requester,
                                                       String userAccountId,
                                                       Long groupId,
                                                       Long targetId,
                                                       Long vocabularyId,
                                                       Long attributeId,
                                                       String attributeLevel,
                                                       String attributeName,
                                                       String attributeReqLevel,
                                                       Boolean showRedacted,
                                                       Boolean topLevel,
                                                       Date createdBefore,
                                                       Date createdAfter,
                                                       Date modifiedBefore,
                                                       Date modifiedAfter,
                                                       Set<String> matches,
                                                       Set<String> nots) {
        return (root, query, cb) -> {
//            return buildSearchSpec(root, query, cb, requester, username, group, target, vocab, attributeLevel, attributeName, attributeReqLevel, showRedacted, parentless, createdBefore, createdAfter, modifiedBefore, modifiedAfter, matches, nots);
            return buildSearchSpec(root, query, cb, requester, userAccountId, groupId, targetId, vocabularyId, attributeId, attributeLevel, attributeName, attributeReqLevel, showRedacted, topLevel, createdBefore, createdAfter, modifiedBefore, modifiedAfter, matches, nots);
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
                                             String userAccountId,
                                             Long groupId,
                                             Long targetId,
                                             Long vocabularyId,
                                             Long attributeId,
                                             String attributeLevel,
                                             String attributeName,
                                             String attributeReqLevel,
                                             Boolean showRedacted,
                                             Boolean topLevel,
                                             Date createdBefore,
                                             Date createdAfter,
                                             Date modifiedBefore,
                                             Date modifiedAfter) {
        final List<Predicate> predicates = new ArrayList<>(0);
        final Predicate authPredicate = authFilter(root, cb, requester);
        final List<Predicate> filterPredicates = buildFilterPredicates(root, query, cb, userAccountId, groupId, targetId, vocabularyId, attributeId, attributeLevel, attributeName, attributeReqLevel, showRedacted, topLevel, createdBefore, createdAfter, modifiedBefore, modifiedAfter);

        predicates.add(authPredicate);
        predicates.addAll(filterPredicates);

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    private static Predicate buildSearchSpec(Root<Annotation> root,
                                             CriteriaQuery query,
                                             CriteriaBuilder cb,
                                             UserAccount requester,
                                             String userAccountId,
                                             Long groupId,
                                             Long targetId,
                                             Long vocabularyId,
                                             Long attributeId,
                                             String attributeLevel,
                                             String attributeName,
                                             String attributeReqLevel,
                                             Boolean showRedacted,
                                             Boolean topLevel,
                                             Date createdBefore,
                                             Date createdAfter,
                                             Date modifiedBefore,
                                             Date modifiedAfter,
                                             Set<String> matches,
                                             Set<String> nots) {
        final List<Predicate> predicates = new ArrayList<>(0);
        final Predicate authPredicate = authFilter(root, cb, requester);
        final List<Predicate> filterPredicates = buildFilterPredicates(root, query, cb, userAccountId, groupId, targetId, vocabularyId, attributeId, attributeLevel, attributeName, attributeReqLevel, showRedacted, topLevel, createdBefore, createdAfter, modifiedBefore, modifiedAfter);
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

        // Annotation has public access
        predicates.add(cb.like(root.get(ACCESS).get(NAME), PUBLIC_ACCESS));

        // Annotation has private access AND belongs to requester
        predicates.add(cb.and(cb.like(root.get(ACCESS).get(NAME), PRIVATE_ACCESS),
                cb.equal(root.get(USER), requester)));

        // Annotation has group access AND requester is in annotation's group
        // criteriabuilder's in clause throws sql error if collection has no elements
        // TODO: Look into if this is SQL error is fixed
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
                                                         String userAccountId,
                                                         Long groupId,
                                                         Long targetId,
                                                         Long vocabularyId,
                                                         Long attributeId,
                                                         String attributeLevel,
                                                         String attributeName,
                                                         String attributeReqLevel,
                                                         Boolean showRedacted,
                                                         Boolean topLevel,
                                                         Date createdBefore,
                                                         Date createdAfter,
                                                         Date modifiedBefore,
                                                         Date modifiedAfter) {

        final List<Predicate> predicates = new ArrayList<>(0);
        if (!isEmpty(userAccountId)) {
            predicates.add(belongsToUser(root, cb, userAccountId));
        }
        if (!isEmpty(groupId)) {
            predicates.add(belongsToGroup(root, cb, groupId));
        }
        if (!isEmpty(targetId)) {
            predicates.add(hasTarget(root, cb, targetId));
        }
        if (!isEmpty(vocabularyId)) {
            predicates.add(hasVocabulary(root, cb, vocabularyId));
        }
        if (!isEmpty(attributeId)) {
            predicates.add(hasAttribute(root, query, cb, attributeId));
        }
        if (!isEmpty(attributeLevel)) {
            predicates.add(hasAttributeLevel(root, query, cb, attributeLevel));
        }
        if (!isEmpty(attributeName)) {
            predicates.add(hasAttributeName(root, query, cb, attributeName));
        }
        if (!isEmpty(attributeReqLevel)) {
            predicates.add(hasAttributeReqLevel(root, query, cb, attributeReqLevel));
        }
        if (!isEmpty(showRedacted)) {
            predicates.add(notRedacted(root, cb));
        }
        if (!isEmpty(topLevel)) {
            predicates.add(topLevel(root, cb));
        }
        if (!isEmpty(createdBefore)) {
            predicates.add(createdBefore(root, cb, createdBefore));
        }
        if (!isEmpty(createdAfter)) {
            predicates.add(createdAfter(root, cb, createdAfter));
        }
        if (!isEmpty(modifiedBefore)) {
            predicates.add(modifiedBefore(root, cb, modifiedBefore));
        }
        if (!isEmpty(modifiedAfter)) {
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
        if (!isEmpty(matches)) {
            predicates.addAll(containsTerms(root, query, cb, matches));
        }
        if (!isEmpty(nots)) {
            predicates.addAll(doesNotContainTerms(root, query, cb, nots));
        }
        return predicates;
    }

    // belong to user predicate
    private static Predicate belongsToUser(Root<Annotation> root, CriteriaBuilder cb, String userAccountId) {
        return cb.equal(root.get(USER).get(ACCOUNTID), userAccountId);
//        return cb.like(cb.lower(root.get(USER).get(USERNAME)), username.toLowerCase());
    }

    // belongs to group predicate
    private static Predicate belongsToGroup(Root<Annotation> root, CriteriaBuilder cb, Long groupId) {
        return cb.equal(root.get(GROUP).get(ID), groupId);
//        return cb.like(cb.lower(root.get(GROUP).get(NAME)), group.toLowerCase());
    }

    // has target predicate
    private static Predicate hasTarget(Root<Annotation> root, CriteriaBuilder cb, Long targetId) {
        return cb.equal(root.get(TARGET).get(ID), targetId);
//        return cb.equal(root.get(TARGET).get(ID), target);
    }

    // has vocabulary predicate
    private static Predicate hasVocabulary(Root<Annotation> root, CriteriaBuilder cb, Long vocabularyId) {
        return cb.equal(root.get(VOCABULARY).get(ID), vocabularyId);
//        return cb.like(cb.lower(root.get(VOCAB).get(NAME)), vocab.toLowerCase());
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
                .where(cb.and(cb.equal(subroot.get(ANNOTATION), root),
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

    // has attribute
    private static Predicate hasAttribute(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, Long attributeId) {
        final Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        final Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(subquery.select(subroot)
                .where(cb.and(cb.equal(subroot.get(ANNOTATION), root),
                        cb.equal(subroot.get(ATTRIBRIBUTE).get(ID), attributeId))));
    }

    // has attribute name
    private static Predicate hasAttributeName(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, String attributeName) {
        final Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        final Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(subquery.select(subroot)
                .where(cb.and(cb.equal(subroot.get(ANNOTATION), root),
                        cb.equal(cb.lower(subroot.get(ATTRIBRIBUTE).get(NAME)), attributeName.toLowerCase()))));
    }

    // has attribute level
    private static Predicate hasAttributeLevel(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, String attributeLevel) {
        final Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        final Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(subquery.select(subroot)
                .where(cb.and(cb.equal(subroot.get(ANNOTATION), root),
                        cb.like(cb.lower(subroot.get(ATTRIBRIBUTE).get(LEVEL)), attributeLevel.toLowerCase()))));
    }

    // has attribute requirement name
    private static Predicate hasAttributeReqLevel(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, String attributeReqLevel) {
        final Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        final Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(subquery.select(subroot)
                .where(cb.and(cb.equal(subroot.get(ANNOTATION), root),
                        cb.like(cb.lower(subroot.get(ATTRIBRIBUTE).get(REQ_LEVEL)), attributeReqLevel.toLowerCase()))));
    }

    // annotation is not redacted
    private static Predicate notRedacted(Root<Annotation> root, CriteriaBuilder cb) {
        return cb.equal(root.get(REDACTED), false);
    }

    // annotation is top-level (annotations target directly)
    private static Predicate topLevel(Root<Annotation> root, CriteriaBuilder cb) {
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
        return cb.lessThan(root.<Date>get(MODIFIED_DATE), date);
    }

    // annotation was modified after date
    private static Predicate modifiedAfter(Root<Annotation> root, CriteriaBuilder cb, Date date) {

        return cb.greaterThan(root.<Date>get(MODIFIED_DATE), date);
    }

    private static String containsLike(String terms) {
        if (isEmpty(terms)) {
            return "%";
        } else {
            return "%" + terms.toLowerCase() + "%";
        }
    }

    public static final class SearchBuilder {
        private final UserAccount requester;
        private String userAccountId;
        private Long groupId;
        private Long targetId;
        private Long vocabularyId;
        private Long attributeId;
        private String attributeLevel;
        private String attributeName;
        private String attributeRequirementLevel;
        private Boolean showTopLevel;
        private Boolean showRedacted;
        private Date createdBefore;
        private Date createdAfter;
        private Date modifiedBefore;
        private Date modifiedAfter;
        private Set<String> contains;
        private Set<String> notContains;

        public SearchBuilder(final UserAccount requester) {
            this.requester = requester;
        }

        public SearchBuilder user(final String userAccountId) {
            this.userAccountId = userAccountId;
            return this;
        }

        public SearchBuilder group(final Long groupId) {
            this.groupId = groupId;
            return this;
        }

        public SearchBuilder target(final Long targetId) {
            this.targetId = targetId;
            return this;
        }

        public SearchBuilder vocabulary(final Long vocabularyId) {
            this.vocabularyId = vocabularyId;
            return this;
        }

        public SearchBuilder attribute(final Long attributeId) {
            this.attributeId = attributeId;
            return this;
        }

        public SearchBuilder attributeLevel(final String attributeLevel) {
            this.attributeLevel = attributeLevel;
            return this;
        }

        public SearchBuilder attributeName(final String attributeName) {
            this.attributeName = attributeName;
            return this;
        }

        public SearchBuilder attributeRequirementLevel(final String attributeRequirementLevel) {
            this.attributeRequirementLevel = attributeRequirementLevel;
            return this;
        }

        public SearchBuilder topLevelOnly() {
            this.showTopLevel = true;
            return this;
        }

        public SearchBuilder showRedacted() {
            this.showRedacted = true;
            return this;
        }

        public SearchBuilder createdRange(final Date createdBefore, final Date createdAfter) throws IllegalArgumentException {
            this.createdBefore = createdBefore;
            this.createdAfter = createdAfter;
            if (createdBefore != null && createdAfter != null && (createdBefore.before(createdAfter) || createdBefore.equals(createdAfter))) {
                throw new IllegalArgumentException(DATE_RANGE_ERROR);
            }
            return this;
        }

        public SearchBuilder modifiedRange(final Date modifiedBefore, final Date modifiedAfter) throws IllegalArgumentException {
            this.modifiedBefore = modifiedBefore;
            this.modifiedAfter = modifiedAfter;
            if (modifiedBefore != null && modifiedAfter != null && (modifiedBefore.before(modifiedAfter) || modifiedBefore.equals(modifiedAfter))) {
                throw new IllegalArgumentException(DATE_RANGE_ERROR);
            }
            return this;
        }

        public SearchBuilder containing(Iterable<String> contains) {
            if (contains instanceof Set) {
                this.contains = (Set<String>) contains;
            } else {
                this.contains = StreamSupport.stream(contains.spliterator(), false).collect(Collectors.toSet());
            }
            return this;
        }

        public SearchBuilder notContaining(Iterable<String> notContains) {
            if (notContains instanceof Set) {
                this.notContains = (Set<String>) notContains;
            } else {
                this.notContains = StreamSupport.stream(notContains.spliterator(), false).collect(Collectors.toSet());
            }
            return this;
        }

        public Specification<Annotation> build() {
            return AnnotationSpecification.searchSpec(requester, userAccountId, groupId, targetId, vocabularyId, attributeId, attributeLevel, attributeName, attributeRequirementLevel, showTopLevel, showRedacted, createdBefore, createdAfter, modifiedBefore, modifiedAfter, contains, notContains);
        }
    }
}
