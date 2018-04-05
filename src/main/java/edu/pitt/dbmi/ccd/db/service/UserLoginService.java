/*
 * Copyright (C) 2018 University of Pittsburgh.
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

import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.UserLogin;
import edu.pitt.dbmi.ccd.db.repository.UserLoginRepository;
import edu.pitt.dbmi.ccd.db.util.InetUtils;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Jan 15, 2018 3:24:27 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class UserLoginService {

    private final UserLoginRepository userLoginRepository;

    @Autowired
    public UserLoginService(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    /**
     * Log user's current login and get the last login.
     *
     * @param userAccount
     * @param ipAddress
     * @return user's last login
     */
    public UserLogin logUserLogin(UserAccount userAccount, String ipAddress) {
        UserLogin lastLogin = userLoginRepository
                .getLastUserLogin(userAccount);
        UserLogin currentLogin = userLoginRepository
                .save(new UserLogin(new Date(), InetUtils.getInetNTOA(ipAddress), userAccount));

        return (lastLogin == null) ? currentLogin : lastLogin;
    }

    public UserLoginRepository getRepository() {
        return userLoginRepository;
    }

}
