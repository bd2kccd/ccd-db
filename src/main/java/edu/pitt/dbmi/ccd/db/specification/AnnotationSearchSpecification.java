package edu.pitt.dbmi.ccd.db.specification;

import static org.springframework.util.StringUtils.isEmpty;

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
import javax.validation.constraints.NotNull;

import edu.pitt.dbmi.ccd.db.entity.Annotation;
import edu.pitt.dbmi.ccd.db.entity.AnnotationData;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public class AnnotationSearchSpecification {

    public static final String TARGET = "target";
    public static final String SPECIFICATION = "specification";
    public static final String ANNOTATION = "annotation";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PARENT = "parent";
    public static final String ATTRIBUTE = "attribute";
    public static final String VALUE = "value";
    public static final String LEVEL = "level";
    public static final String REQUIRED = "required";
    public static final String REDACTED = "redacted";
    public static final String CREATED = "created";
    public static final String MODIFIED = "modified";

    private AnnotationSearchSpecification() {
    }

    public static Specification<Annotation> authSpec(@NotNull Long user) {
        return (root, query, cb) -> {
            return authFilter(root, cb, user);
        };
    }

    public static Specification<Annotation> idSpec(@NotNull Long user, Long id) {
        return (root, query, cb) -> {
            return buildIdSpec(root, cb, user, id);
        };
    }

    public static Specification<Annotation> parentSpec(@NotNull Long user, Long id, Boolean redacted) {
        return (root, query, cb) -> {
            return buildParentSpec(root, cb, user, id, redacted);
        };
    }

    public static Specification<Annotation> filterSpec(
            @NotNull Long user,
            Long target,
            String specification,
            String level,
            String name,
            Boolean required,
            Boolean redacted,
            Boolean isTop,
            Date createdBefore,
            Date createdAfter,
            Date modifiedBefore,
            Date modifiedAfter) {
        return (root, query, cb) -> {
            return buildFilterSpec(
                    root, query, cb, user, target, specification, level, name, required, redacted, isTop, createdBefore, createdAfter, modifiedBefore, modifiedAfter);
        };
    }

    public static Specification<Annotation> searchSpec(
            @NotNull Long user,
            Long target,
            String specification,
            String level,
            String name,
            Boolean required,
            Boolean redacted,
            Boolean isTop,
            Date createdBefore,
            Date createdAfter,
            Date modifiedBefore,
            Date modifiedAfter,
            Set<String> matches,
            Set<String> nots) {
        return (root, query, cb) -> {
            return buildSearchSpec(
                    root, query, cb, user, target, specification, level, name, required, redacted, isTop, createdBefore, createdAfter, modifiedBefore, modifiedAfter, matches, nots);
        };
    }

    private static Predicate authFilter(Root<Annotation> root, CriteriaBuilder cb, Long user) {
        return cb.equal(root.get(ID), user);
    }

    private static Predicate buildIdSpec(Root<Annotation> root, CriteriaBuilder cb, Long user, Long id) {
        final Predicate authPredicate = authFilter(root, cb, user);
        final Predicate idPredicate = cb.equal(root.get(ID), id);
        return cb.and(
                new Predicate[]{
                        authPredicate,
                        idPredicate
                }
        );
    }

    private static Predicate buildParentSpec(Root<Annotation> root, CriteriaBuilder cb, Long user, Long id, Boolean redacted) {
        final Predicate authPredicate = authFilter(root, cb, user);
        final Predicate idPredicate = cb.equal(root.get(ID), id);
        final Predicate[] predicates;
        if (!redacted) {
            predicates = new Predicate[]{authPredicate, idPredicate, hideRedacted(root, cb)};
        } else {
            predicates = new Predicate[]{authPredicate, idPredicate};
        }
        return cb.and(predicates);
    }

    private static Predicate buildFilterSpec(
            Root<Annotation> root,
            CriteriaQuery query,
            CriteriaBuilder cb,
            Long user,
            Long target,
            String specification,
            String level,
            String name,
            Boolean required,
            Boolean redacted,
            Boolean isTop,
            Date createdBefore,
            Date createdAfter,
            Date modifiedBefore,
            Date modifiedAfter) {
        final List<Predicate> predicates = new ArrayList<>();
        final Predicate authPredicate = authFilter(root, cb, user);
        final List<Predicate> filterPredicates = buildFilterPredicates(
                root, query, cb, target, specification, level, name, required, redacted, isTop, createdBefore, createdAfter, modifiedBefore, modifiedAfter);
        predicates.add(authPredicate);
        predicates.addAll(filterPredicates);
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    private static Predicate buildSearchSpec(
            Root<Annotation> root,
            CriteriaQuery query,
            CriteriaBuilder cb,
            Long user,
            Long target,
            String specification,
            String level,
            String name,
            Boolean required,
            Boolean redacted,
            Boolean isTop,
            Date createdBefore,
            Date createdAfter,
            Date modifiedBefore,
            Date modifiedAfter,
            Set<String> matches,
            Set<String> not) {
        final List<Predicate> predicates = new ArrayList<>();
        final Predicate authPredicate = authFilter(root, cb, user);
        final List<Predicate> filterPredicates = buildFilterPredicates(
                root, query, cb, target, specification, level, name, required, redacted, isTop, createdBefore, createdAfter, modifiedBefore, modifiedAfter);
        final List<Predicate> searchPredicates = buildSearchPredicates(root, query, cb, matches, not);
        predicates.add(authPredicate);
        predicates.addAll(filterPredicates);
        predicates.addAll(searchPredicates);
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    private static List<Predicate> buildFilterPredicates(
            Root<Annotation> root,
            CriteriaQuery query,
            CriteriaBuilder cb,
            Long target,
            String specification,
            String level,
            String name,
            Boolean required,
            Boolean redacted,
            Boolean isTop,
            Date createdBefore,
            Date createdAfter,
            Date modifiedBefore,
            Date modifiedAfter) {
        final List<Predicate> predicates = new ArrayList<>();
        if (!isEmpty(target)) {
            predicates.add(hasTarget(root, cb, target));
        }
        if (!isEmpty(specification)) {
            predicates.add(hasSpecification(root, cb, specification));
        }
        if (!isEmpty(level)) {
            predicates.add(hasLevel(root, query, cb, level));
        }
        if (!isEmpty(name)) {
            predicates.add(hasName(root, query, cb, name));
        }
        if (!isEmpty(required)) {
            predicates.add(isRequired(root, query, cb, required));
        }
        if (!redacted) {
            predicates.add(hideRedacted(root, cb));
        }
        if (isTop) {
            predicates.add(isTop(root, cb));
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

    public static List<Predicate> buildSearchPredicates(
            Root<Annotation> root,
            CriteriaQuery query,
            CriteriaBuilder cb,
            Set<String> matches,
            Set<String> nots) {
        final List<Predicate> predicates = new ArrayList<>();
        if (!isEmpty(matches)) {
            predicates.addAll(containsTerms(root, query, cb, matches));
        }
        if (!isEmpty(nots)) {
            predicates.addAll(doesNotContainTerms(root, query, cb, nots));
        }
        return predicates;
    }

    private static Predicate hasTarget(Root<Annotation> root, CriteriaBuilder cb, Long target) {
        return cb.equal(root.get(TARGET), target);
    }

    private static Predicate hasSpecification(Root<Annotation> root, CriteriaBuilder cb, String specification) {
        return cb.like(cb.lower(root.get(SPECIFICATION).get(NAME)), specification.toLowerCase());
    }

    private static Predicate hasLevel(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, String level) {
        final Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        final Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(
                subquery.select(subroot)
                    .where(cb.and(cb.equal(subroot.get(ANNOTATION), root),
                        cb.like(cb.lower(subroot.get(ATTRIBUTE).get(LEVEL)), level.toLowerCase()))));
    }

    private static Predicate hasName(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, String name) {
        final Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        final Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(
                subquery.select(subroot)
                        .where(cb.and(cb.equal(subroot.get(ANNOTATION), root),
                                cb.like(cb.lower(subroot.get(ATTRIBUTE).get(NAME)), name.toLowerCase()))));
    }

    private static Predicate isRequired(Root<Annotation> root, CriteriaQuery query, CriteriaBuilder cb, Boolean required) {
        final Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        final Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(
                subquery.select(subroot)
                        .where(cb.and(cb.equal(subroot.get(ANNOTATION), root),
                                cb.equal(subroot.get(ATTRIBUTE).get(REQUIRED), required))));
    }

    private static Predicate hideRedacted(Root<Annotation> root, CriteriaBuilder cb) {
        return cb.equal(root.get(REDACTED), false);
    }

    private static Predicate isTop(Root<Annotation> root, CriteriaBuilder cb) {
        return cb.isNull(root.get(PARENT));
    }

    private static Predicate createdBefore(Root<Annotation> root, CriteriaBuilder cb, Date date) {
        return cb.lessThan(root.<Date>get(CREATED), date);
    }

    private static Predicate createdAfter(Root<Annotation> root, CriteriaBuilder cb, Date date) {
        return cb.greaterThan(root.<Date>get(CREATED), date);
    }

    private static Predicate modifiedBefore(Root<Annotation> root, CriteriaBuilder cb, Date date) {
        return cb.lessThan(root.<Date>get(MODIFIED), date);
    }

    private static Predicate modifiedAfter(Root<Annotation> root, CriteriaBuilder cb, Date date) {
        return cb.greaterThan(root.<Date>get(MODIFIED), date);
    }

    private static List<Predicate> containsTerms(
            Root<Annotation> root,
            CriteriaQuery query,
            CriteriaBuilder cb,
            Set<String> terms) {
        return terms.stream()
                .map(t -> containsLike(t))
                .map(t -> existsInData(root, query, cb, t))
                .collect(Collectors.toList());

    }

    private static List<Predicate> doesNotContainTerms(
            Root<Annotation> root,
            CriteriaQuery query,
            CriteriaBuilder cb,
            Set<String> terms) {
        return terms.stream()
                .map(t -> containsLike(t))
                .map(t -> doesNotExistInData(root, query, cb, t))
                .collect(Collectors.toList());
    }

    private static Predicate existsInData(
            Root<Annotation> root,
            CriteriaQuery query,
            CriteriaBuilder cb,
            String term) {
        final Subquery<AnnotationData> subquery = query.subquery(AnnotationData.class);
        final Root<AnnotationData> subroot = subquery.from(AnnotationData.class);
        return cb.exists(subquery.select(subroot)
        .where(cb.and(cb.equal(subroot.get(ANNOTATION), root),
                cb.like(cb.lower(subroot.get(VALUE)), term))));
    }

    private static Predicate doesNotExistInData(
            Root<Annotation> root,
            CriteriaQuery query,
            CriteriaBuilder cb,
            String term) {
        return cb.not(existsInData(root, query, cb, term));
    }

    private static String containsLike(String terms) {
        if (isEmpty(terms)) {
            return "%";
        } else {
            return "%" + terms.toLowerCase() +"%";
        }
    }
}
