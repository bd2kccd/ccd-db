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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Username constraint
 * 
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Documented
@Constraint(validatedBy=edu.pitt.dbmi.ccd.db.validation.Username.UsernameConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {

    String message() default "Valid characters: letters, numbers, dashes (-), and underscores (_)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class UsernameConstraintValidator implements ConstraintValidator<Username, String> {

        // May contain alphanumeric, dashes, and underscores
        // At least four characters
        private static final String USERNAME_PATTERN = "[a-zA-Z0-9\\-_]{4,}";
    
        @Override
        public void initialize(Username String) { }
    
        @Override
        public boolean isValid(String username, ConstraintValidatorContext ctx) {
            if (username == null) {
                return false;
            } else {
                return username.matches(USERNAME_PATTERN);
            }
        }
    }
}