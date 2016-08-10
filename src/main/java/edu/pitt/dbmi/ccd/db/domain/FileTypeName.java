/*
 * Copyright (C) 2016 University of Pittsburgh.
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
package edu.pitt.dbmi.ccd.db.domain;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Aug 10, 2016 12:20:04 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public enum FileTypeName {

    DATASET,
    ALGORITHM_RESULT,
    PRIOR_KNOWLEDGE,
    VARIABLE;

    private static final Map<String, FileTypeName> FILE_TYPE_ENUM_MAP = new HashMap<>();

    static {
        FILE_TYPE_ENUM_MAP.put(DATASET.name(), DATASET);
        FILE_TYPE_ENUM_MAP.put(ALGORITHM_RESULT.name(), ALGORITHM_RESULT);
        FILE_TYPE_ENUM_MAP.put(PRIOR_KNOWLEDGE.name(), PRIOR_KNOWLEDGE);
        FILE_TYPE_ENUM_MAP.put(VARIABLE.name(), VARIABLE);
    }

    public static FileTypeName getEnumByName(String name) {
        return FILE_TYPE_ENUM_MAP.get(name);
    }

}
