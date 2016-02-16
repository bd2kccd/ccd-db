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

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public class NotFoundException extends RuntimeException {

    private final String message;
    private final Object identifier;

    public NotFoundException(Class clazz) {
        super();
        this.message = String.format("%s not found", clazz.getSimpleName());
        this.identifier = null;
    }

    public NotFoundException(Class clazz, Object identifier) {
        super();
        this.message = String.format("%s not found", clazz.getSimpleName());
        this.identifier = identifier;
    }

    public String getMessage() {
        return message;
    }

    public Object getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        if (identifier == null) {
            return message;
        } else if (identifier instanceof Long) {
            return String.format("%s with id: %d", message, (Long) identifier);
        } else if (identifier instanceof String) {
            return String.format("%s with name: %s", message, (String) identifier);
        } else {
            return String.format("%s with identifier: %s", message, identifier);
        }
    }
}