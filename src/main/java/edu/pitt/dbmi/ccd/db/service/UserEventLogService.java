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
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.UserEventLog;
import edu.pitt.dbmi.ccd.db.repository.EventTypeRepository;
import edu.pitt.dbmi.ccd.db.repository.UserEventLogRepository;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * May 10, 2017 3:01:55 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class UserEventLogService {

    private final UserEventLogRepository userEventLogRepository;
    private final EventTypeRepository eventTypeRepository;

    @Autowired
    public UserEventLogService(UserEventLogRepository userEventLogRepository, EventTypeRepository eventTypeRepository) {
        this.userEventLogRepository = userEventLogRepository;
        this.eventTypeRepository = eventTypeRepository;
    }

    public UserEventLog logUserLogin(UserAccount userAccount) {
        return LogUserEvent(EventTypeService.USR_LOGIN_NAME, userAccount);
    }

    public UserEventLog logUserRegistration(UserAccount userAccount) {
        return LogUserEvent(EventTypeService.USR_REGSTR_NAME, userAccount);
    }

    public UserEventLog LogUserEvent(String userEventName, UserAccount userAccount) {
        EventType eventType = eventTypeRepository.findByName(userEventName);
        Date eventDate = new Date(System.currentTimeMillis());

        return userEventLogRepository.save(new UserEventLog(eventDate, eventType, userAccount));
    }

    public UserEventLogRepository getRepository() {
        return userEventLogRepository;
    }

}
