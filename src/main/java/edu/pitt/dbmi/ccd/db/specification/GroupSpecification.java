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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import edu.pitt.dbmi.ccd.db.entity.Group;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public final class GroupSpecification {

    // columns
    private static final String NAME = "name";
    private static final String DESC = "description";

    private GroupSpecification() { }

    /**
     * find groups that contain search terms
     * @param  terms search terms
     * @return       specification
     */
    public static Specification<Group> searchNamesAndDescriptions(Set<String> terms) {
        return (root, query, cb) -> {
            return cb.and(inNameOrDescription(root, cb, terms));
        };
    }

    // build (name or description contains term) predicate for each search term
    private static Predicate[] inNameOrDescription(Root<Group> root, CriteriaBuilder cb, Set<String> terms) {
        return terms.stream()
                    .map(t -> containsLike(t))
                    .map(t -> cb.or(nameContains(root, cb, t),
                                    descriptionContains(root, cb, t)))
                    .toArray(Predicate[]::new);
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
        if (isNullOrEmpty(term)) {
            return "%";
        } else {
            return "%" + term.toLowerCase() + "%";
        }
    }
}