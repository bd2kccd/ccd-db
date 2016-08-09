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
package edu.pitt.dbmi.ccd.db.service;

import edu.pitt.dbmi.ccd.db.domain.EventTypeName;
import edu.pitt.dbmi.ccd.db.entity.EventType;
import edu.pitt.dbmi.ccd.db.repository.EventTypeRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * Aug 8, 2016 7:14:39 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
/**
 *
 * Aug 5, 2016 5:29:21 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class EventTypeService {

    private final EventTypeRepository eventTypeRepository;

    @Autowired
    public EventTypeService(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;

        List<EventType> eventTypes = eventTypeRepository.findAll();
        if (eventTypes.isEmpty()) {
            eventTypes.add(new EventType(EventTypeName.USER_ACTIVATION.name()));
            eventTypes.add(new EventType(EventTypeName.USER_LOGIN.name()));
            eventTypes.add(new EventType(EventTypeName.USER_LOGIN_FAILED.name()));
            eventTypes.add(new EventType(EventTypeName.USER_LOGOUT.name()));
            eventTypes.add(new EventType(EventTypeName.USER_REGISTRATION.name()));

            eventTypeRepository.save(eventTypes);
        }
    }

    @Cacheable("eventTypeByEventTypeName")
    public EventType findByEventTypeName(EventTypeName eventTypeName) {
        return eventTypeRepository.findByName(eventTypeName.name());
    }

}
