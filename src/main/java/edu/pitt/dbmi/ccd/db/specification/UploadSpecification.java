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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import edu.pitt.dbmi.ccd.db.entity.Upload;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public final class UploadSpecification {

    private static final String USER = "user";
    private static final String USERNAME = "username";
    private static final String TITLE = "title";
    private static final String ADDRESS = "address";
    private static final String FILE = "file";
    private static final String NAME = "name";
    private static final String URL = "url";

    private UploadSpecification() { }

    public static Specification<Upload> filterSpec(String username, String type) {
        return (root, query, cb) -> {
            return buildFilterSpec(root, cb, username, type);
        };
    }

    /**
     * find uploads that contain search terms
     * @param  username  user
     * @param  type      upload type (url or file)
     * @param  matches   contains terms
     * @param  nots      does not contain terms
     * @return           specification
     */
    public static Specification<Upload> searchSpec(String username,
                                                   String type,
                                                   Set<String> matches,
                                                   Set<String> nots) {
        return (root, query, cb) -> {
            return buildSearchSpec(root, cb, username, type, matches, nots);
        };
    }

    private static Predicate buildFilterSpec(Root<Upload> root, CriteriaBuilder cb, String username, String type) {
        List<Predicate> predicates = new ArrayList<>(0);
        if (!isNullOrEmpty(username)) {
            predicates.add(belongsToUser(root, cb, username));
        }
        if (!isNullOrEmpty(type)) {
            if (type.equalsIgnoreCase(FILE)) {
                predicates.add(isFile(root, cb));
            } else if (type.equalsIgnoreCase(URL)) {
                predicates.add(isURL(root, cb));
            }
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    // build search predicates
    private static Predicate buildSearchSpec(Root<Upload> root, CriteriaBuilder cb, String username, String type, Set<String> matches, Set<String> nots) {
        List<Predicate> predicates = new ArrayList<>(0);
        predicates.add(buildFilterSpec(root, cb, username, type));
        if (!isNullOrEmpty(matches)) {
            predicates.addAll(inTitleOrAddressOrName(root, cb, matches));
        }
        if (!isNullOrEmpty(nots)) {
            predicates.addAll(notInTitleOrAddressOrName(root, cb, nots));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    // belongs to user predicate
    private static Predicate belongsToUser(Root<Upload> root, CriteriaBuilder cb, String username) {
        return cb.like(cb.lower(root.get(USER).get(USERNAME)), username.toLowerCase());
    }

    // is file predicate
    private static Predicate isFile(Root<Upload> root, CriteriaBuilder cb) {
        return cb.isNull(root.get(ADDRESS));
    }

    // is url predicate
    private static Predicate isURL(Root<Upload> root, CriteriaBuilder cb) {
        return cb.isNotNull(root.get(ADDRESS));
    }

    // build (title or address or file name contains term) predicate for each search term
    private static List<Predicate> inTitleOrAddressOrName(Root<Upload> root, CriteriaBuilder cb, Set<String> terms) {
        return terms.stream()
                    .map(t -> containsLike(t))
                    .map(t -> cb.or(titleContains(root, cb, t),
                                    (root.get(ADDRESS) != null) ? addressContains(root, cb, t)
                                                                : fileNameContains(root, cb, t)))
                    .collect(Collectors.toList());
    }

    // build (neither name or description contains term) predicate for each search term
    private static List<Predicate> notInTitleOrAddressOrName(Root<Upload> root, CriteriaBuilder cb, Set<String> terms) {
        return terms.stream()
                    .map(t -> containsLike(t))
                    .map(t -> cb.not(cb.or(titleContains(root, cb, t),
                                           (root.get(ADDRESS) != null) ? addressContains(root, cb, t)
                                                                       : fileNameContains(root, cb, t))))
                    .collect(Collectors.toList());
    }

    // build title contains term predicate
    private static Predicate titleContains(Root<Upload> root, CriteriaBuilder cb, String term) {
        return cb.like(cb.lower(root.get(TITLE)), term);
    }

    // build address contains term predicate
    private static Predicate addressContains(Root<Upload> root, CriteriaBuilder cb, String term) {
        return cb.like(cb.lower(root.get(ADDRESS)), term);
    }

    // build file name contains term predicate
    private static Predicate fileNameContains(Root<Upload> root, CriteriaBuilder cb, String term) {
        return cb.like(cb.lower(root.get(FILE).get(NAME)), term);
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