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

import edu.pitt.dbmi.ccd.db.entity.SecurityAnswer;
import edu.pitt.dbmi.ccd.db.entity.SecurityQuestion;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.SecurityAnswerRepository;
import edu.pitt.dbmi.ccd.db.repository.SecurityQuestionRepository;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Jul 29, 2015 9:39:37 AM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
@Transactional
public class SecurityAnswerService {

    private final SecurityAnswerRepository securityAnswerRepository;

    private final SecurityQuestionRepository securityQuestionRepository;

    @Autowired(required = true)
    public SecurityAnswerService(
            SecurityAnswerRepository securityAnswerRepository,
            SecurityQuestionRepository securityQuestionRepository) {
        this.securityAnswerRepository = securityAnswerRepository;
        this.securityQuestionRepository = securityQuestionRepository;
    }

    public List<SecurityAnswer> findByUserAccounts(Set<UserAccount> userAccounts) {
        return securityAnswerRepository.findByUserAccounts(userAccounts);
    }

    public SecurityAnswer saveSecurityAnswer(SecurityAnswer securityAnswer) {
        SecurityQuestion securityQuestion = securityQuestionRepository.save(securityAnswer.getSecurityQuestion());
        securityAnswer.setSecurityQuestion(securityQuestion);

        return securityAnswerRepository.save(securityAnswer);
    }

    public List<SecurityAnswer> saveSecurityAnswer(List<SecurityAnswer> securityAnswers) {
        return securityAnswerRepository.save(securityAnswers);
    }

    public void deleteSecurityAnswer(List<SecurityAnswer> securityAnswers) {
        securityAnswerRepository.delete(securityAnswers);
    }

}
