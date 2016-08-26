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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import edu.pitt.dbmi.ccd.db.entity.ShareGroup;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public final class ShareGroupSpecification {

    // columns
    private static final String NAME = "name";
    private static final String DESC = "description";

    private ShareGroupSpecification() { }

    /**
     * find groups that contain search terms
     * @param  matches search terms
     * @param  nots    negated search terms
     * @return specification
     */
    public static Specification<ShareGroup> searchSpec(Set<String> matches,
                                                       Set<String> nots) {
        return (root, query, cb) -> {
            return buildSearchSpec(root, cb, matches, nots);
        };
    }

    // build search predicates
    private static Predicate buildSearchSpec(Root<ShareGroup> root, CriteriaBuilder cb, Set<String> matches, Set<String> nots) {
        List<Predicate> predicates = new ArrayList<>(0);
        if (!isNullOrEmpty(matches)) {
            predicates.addAll(inNameOrDescription(root, cb, matches));
        }
        if (!isNullOrEmpty(nots)) {
            predicates.addAll(notInNameOrDescription(root, cb, nots));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    // build (name or description contains term) predicate for each search term
    private static List<Predicate> inNameOrDescription(Root<ShareGroup> root, CriteriaBuilder cb, Set<String> terms) {
        return terms.stream()
                    .map(t -> containsLike(t))
                    .map(t -> cb.or(nameContains(root, cb, t),
                                    descriptionContains(root, cb, t)))
                    .collect(Collectors.toList());
    }

    // build (neither name or description contains term) predicate for each search term
    private static List<Predicate> notInNameOrDescription(Root<ShareGroup> root, CriteriaBuilder cb, Set<String> terms) {
        return terms.stream()
                    .map(t -> containsLike(t))
                    .map(t -> cb.not(cb.or(nameContains(root, cb, t),
                                           descriptionContains(root, cb, t))))
                    .collect(Collectors.toList());
    }

    // build name contains term predicate
    private static Predicate nameContains(Root<ShareGroup> root, CriteriaBuilder cb, String term) {
        return cb.like(cb.lower(root.get(NAME)), term);
    }

    // build description contains term predicate
    private static Predicate descriptionContains(Root<ShareGroup> root, CriteriaBuilder cb, String term) {
        return cb.like(cb.lower(root.get(DESC)), term);
    }

    // wrap term in wildcards
    private static String containsLike(String term) {
        if (isNullOrEmpty(term)) {
            return "%";
        } else {
            return "%" + term.toLowerCase() + "%";
        }
    }
}