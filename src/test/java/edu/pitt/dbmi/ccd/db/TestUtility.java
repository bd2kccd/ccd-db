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
package edu.pitt.dbmi.ccd.db;

import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import java.util.List;

/**
 *
 * May 13, 2015 12:42:07 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
public class TestUtility {

    public static String DOUBLE_LINE = "================================================================================";

    public static String SINGLE_LINE = "--------------------------------------------------------------------------------";

    public TestUtility() {
    }

    public static void printUserAccountList(List<UserAccount> userAccounts, String title) {
        System.out.println(DOUBLE_LINE);
        System.out.println(title);
        System.out.println(SINGLE_LINE);
        if (userAccounts == null) {
            System.out.println(userAccounts);
        } else if (userAccounts.isEmpty()) {
            System.out.println("UserAccount list is empty.");
        } else {
            for (UserAccount userAccount : userAccounts) {
                System.out.printf("ID: %d\n", userAccount.getId());
                System.out.printf("Username: %s\n", userAccount.getUsername());
                System.out.printf("Password: %s\n", userAccount.getPassword());
                System.out.printf("Active: %s\n", userAccount.isActive());
                System.out.printf("Creation Date: %s\n", userAccount.getCreatedDate());

                Person person = userAccount.getPerson();
                System.out.printf("\tID: %d\n", person.getId());
                System.out.printf("\tFirst Name: %s\n", person.getFirstName());
                System.out.printf("\tLast Name: %s\n", person.getLastName());
                System.out.printf("\tEmail: %s\n", person.getEmail());
                System.out.println();
            }
        }
        System.out.println(DOUBLE_LINE);
    }

    public static void printUserAccount(UserAccount userAccount, String title) {
        System.out.println(DOUBLE_LINE);
        System.out.println(title);
        System.out.println(SINGLE_LINE);
        if (userAccount == null) {
            System.out.println(userAccount);
        } else {
            System.out.printf("ID: %d\n", userAccount.getId());
            System.out.printf("Username: %s\n", userAccount.getUsername());
            System.out.printf("Password: %s\n", userAccount.getPassword());
            System.out.printf("Active: %s\n", userAccount.isActive());
            System.out.printf("Creation Date: %s\n", userAccount.getCreatedDate());

            Person person = userAccount.getPerson();
            System.out.printf("\tID: %d\n", person.getId());
            System.out.printf("\tFirst Name: %s\n", person.getFirstName());
            System.out.printf("\tLast Name: %s\n", person.getLastName());
            System.out.printf("\tEmail: %s\n", person.getEmail());
        }
        System.out.println(DOUBLE_LINE);
    }

    public static void printPersonList(List<Person> persons, String title) {
        System.out.println(DOUBLE_LINE);
        System.out.println(title);
        System.out.println(SINGLE_LINE);
        if (persons == null) {
            System.out.println(persons);
        } else if (persons.isEmpty()) {
            System.out.println("Person list is empty.");
        } else {
            for (Person person : persons) {
                System.out.printf("ID: %d\n", person.getId());
                System.out.printf("First Name: %s\n", person.getFirstName());
                System.out.printf("Last Name: %s\n", person.getLastName());
                System.out.printf("Email: %s\n", person.getEmail());
                System.out.println();
            }
        }
        System.out.println(DOUBLE_LINE);
    }

    public static void printPerson(Person person, String title) {
        System.out.println(DOUBLE_LINE);
        System.out.println(title);
        System.out.println(SINGLE_LINE);
        if (person == null) {
            System.out.println(person);
        } else {
            System.out.printf("ID: %d\n", person.getId());
            System.out.printf("First Name: %s\n", person.getFirstName());
            System.out.printf("Last Name: %s\n", person.getLastName());
            System.out.printf("Email: %s\n", person.getEmail());
        }
        System.out.println(DOUBLE_LINE);
    }

}
