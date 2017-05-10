/*
 * Copyright (C) 2017 University of Pittsburgh.
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

import edu.pitt.dbmi.ccd.db.entity.EventType;
import edu.pitt.dbmi.ccd.db.repository.EventTypeRepository;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * May 10, 2017 2:54:59 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class EventTypeService {

    public static final String USER_LOGIN_NAME = "User Login";
    public static final String USER_REGISTRATION_NAME = "User Registration";

    private final EventTypeRepository eventTypeRepository;

    @Autowired
    public EventTypeService(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;

        List<EventType> eventTypes = eventTypeRepository.findAll();
        if (eventTypes.isEmpty()) {
            eventTypeRepository.save(Arrays.asList(
                    new EventType(USER_LOGIN_NAME),
                    new EventType(USER_REGISTRATION_NAME)));
        }

    }

    public EventTypeRepository getEventTypeRepository() {
        return eventTypeRepository;
    }

}
