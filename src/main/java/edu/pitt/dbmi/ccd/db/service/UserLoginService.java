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

import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.UserLogin;
import edu.pitt.dbmi.ccd.db.repository.UserLoginRepository;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Jun 24, 2016 4:32:01 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class UserLoginService {

    private final UserLoginRepository userLoginRepository;

    @Autowired
    public UserLoginService(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    public UserLogin findByUserAccount(UserAccount userAccount) {
        return userLoginRepository.findByUserAccount(userAccount);
    }

    public UserLogin logUserSignIn(UserAccount userAccount, Long loginLocation) {
        Date loginDate = new Date(System.currentTimeMillis());

        UserLogin userLogin = userLoginRepository.findByUserAccount(userAccount);
        if (userLogin == null) {
            userLogin = new UserLogin(userAccount, loginDate, loginLocation, loginDate, loginLocation);
        } else {
            userLogin.setLastLoginDate(userLogin.getLoginDate());
            userLogin.setLastLoginLocation(userLogin.getLoginLocation());
            userLogin.setLoginDate(loginDate);
            userLogin.setLoginLocation(loginLocation);
        }

        return userLoginRepository.save(userLogin);
    }

}
