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

package edu.pitt.dbmi.ccd.db.error;

import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public final class VocabularyMismatchException extends RuntimeException {

    private final String MESSAGE = "Attribute %d must belong to annotation vocabulary %s";

    private final String message;

    /**
     * Constructor
     * @param  attribute  attribute
     * @param  vocabulary vocabulary
     * @return         VocabularyMismatchException
     */
    public VocabularyMismatchException(Attribute attribute, Vocabulary vocabulary) {
        super();
        this.message = String.format(MESSAGE, attribute.getId(), vocabulary.getName());
    }

    /**
     * Get message
     * @return message
     */
    @Override
    public String getMessage() {
        return message;
    }

}