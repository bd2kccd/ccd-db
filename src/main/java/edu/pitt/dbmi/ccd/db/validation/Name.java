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

package edu.pitt.dbmi.ccd.db.validation;

import static edu.pitt.dbmi.ccd.db.util.StringUtils.isNullOrEmpty;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * Name constraint
 * 
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Documented
@Constraint(validatedBy=edu.pitt.dbmi.ccd.db.validation.Name.NameConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {

    String message() default "Must be at least 4 characters\nValid characters: letters, numbers, dash (-), and space";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class NameConstraintValidator implements ConstraintValidator<Name, String> {

        // Starts and ends with alphanumeric
        // May contain dashes and spaces
        // At least four characters
        private static final String NAME_PATTERN = "[a-zA-Z][a-zA-Z0-9\\- ]{2,}[a-zA-Z0-9]";
    
        @Override
        public void initialize(Name String) { }
    
        @Override
        public boolean isValid(String name, ConstraintValidatorContext ctx) {
            if (isNullOrEmpty(name)) {
                return false;
            } else {
                return name.matches(NAME_PATTERN);
            }
        }
    }
}