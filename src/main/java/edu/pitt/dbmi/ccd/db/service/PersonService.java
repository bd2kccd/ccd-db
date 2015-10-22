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
package edu.pitt.dbmi.ccd.db.service;

import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * Jul 24, 2015 1:31:14 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired(required = true)
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person findPerson(Long id) {
        return personRepository.findOne(id);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Person findPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public Page<Person> findPersonByDescription(String terms, Pageable pageable) {
        return personRepository.findByDescriptionContains(terms, pageable);
    }

}
