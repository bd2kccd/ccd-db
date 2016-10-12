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
package edu.pitt.dbmi.ccd.db.util;

import edu.pitt.dbmi.ccd.db.entity.Person;
import java.io.PrintStream;

/**
 *
 * Jul 23, 2015 2:51:58 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public class PersonPrint implements PrintPretty {

    private PersonPrint() {
    }

    public static void print(Person person, String title, PrintStream out) {
        out.println(DOUBLE_LINE);
        if (title != null && title.trim().length() > 0) {
            out.println(title);
            out.println(SINGLE_LINE);
        }
        if (person == null) {
            out.println(person);
        } else {
            out.printf("ID: %d\n", person.getId());
            out.printf("First Name: %s\n", person.getFirstName());
            out.printf("Last Name: %s\n", person.getLastName());
            out.printf("Email: %s\n", person.getEmail());
            out.printf("Workspace: %s\n", person.getWorkspace());
        }
        out.println(DOUBLE_LINE);
    }

}
