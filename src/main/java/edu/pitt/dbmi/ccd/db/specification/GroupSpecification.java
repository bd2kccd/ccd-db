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

import edu.pitt.dbmi.ccd.db.entity.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public final class GroupSpecification {

    // columns
    private static final String NAME = "name";
    private static final String DESC = "description";

    private GroupSpecification() {
    }

    /**
     * find groups that contain search terms
     *
     * @param containing search terms
     * @param notContaining search terms
     * @return specification
     */
    public static Specification<Group> searchSpec(Set<String> containing, Set<String> notContaining) {
        return (root, query, cb) -> {
            return buildSearchSpec(root, cb, containing, notContaining);
        };
    }

    // build search predicates
    private static Predicate buildSearchSpec(Root<Group> root, CriteriaBuilder cb, Set<String> containing, Set<String> notContaining) {
        List<Predicate> predicates = new ArrayList<>(0);
        if (!isEmpty(containing)) {
            predicates.addAll(inNameOrDescription(root, cb, containing));
        }
        if (!isEmpty(notContaining)) {
            predicates.addAll(notInNameOrDescription(root, cb, notContaining));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    // build (name or description contains term) predicate for each search term
    private static List<Predicate> inNameOrDescription(Root<Group> root, CriteriaBuilder cb, Set<String> terms) {
        return terms.stream()
                .map(t -> containsLike(t))
                .map(t -> cb.or(nameContains(root, cb, t), descriptionContains(root, cb, t)))
                .collect(Collectors.toList());
    }

    // build (neither name or description contains term) predicate for each search term
    private static List<Predicate> notInNameOrDescription(Root<Group> root, CriteriaBuilder cb, Set<String> terms) {
        return terms.stream()
                .map(t -> containsLike(t))
                .map(t -> cb.not(cb.or(nameContains(root, cb, t), descriptionContains(root, cb, t))))
                .collect(Collectors.toList());
    }

    // build name contains term predicate
    private static Predicate nameContains(Root<Group> root, CriteriaBuilder cb, String term) {
        return cb.like(cb.lower(root.get(NAME)), term);
    }

    // build description contains term predicate
    private static Predicate descriptionContains(Root<Group> root, CriteriaBuilder cb, String term) {
        return cb.like(cb.lower(root.get(DESC)), term);
    }

    // wrap term in wildcards
    private static String containsLike(String term) {
        if (isEmpty(term)) {
            return "%";
        } else {
            return "%" + term.toLowerCase() + "%";
        }
    }

    public static final class SearchBuilder {
        private Set<String> contains;
        private Set<String> notContains;

        public SearchBuilder() {

        }

        public SearchBuilder containing(final Iterable<String> contains) {
            this.contains = StreamSupport.stream(contains.spliterator(), false).collect(Collectors.toSet());
            return this;
        }

        public SearchBuilder notContaining(final Iterable<String> notContains) {
            this.notContains = StreamSupport.stream(notContains.spliterator(), false).collect(Collectors.toSet());
            return this;
        }

        public Specification<Group> build() {
            return GroupSpecification.searchSpec(contains, notContains);
        }
    }
}
