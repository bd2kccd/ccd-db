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
package edu.pitt.dbmi.ccd.db.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * Jul 27, 2015 3:18:30 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Entity
public class SecurityAnswer implements Serializable {

    private static final long serialVersionUID = -3808999112651719528L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "securityQuestionId", nullable = false)
    private SecurityQuestion securityQuestion;

    @Column(name = "answer", nullable = false)
    private String answer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UserAccountSecurityAnswerRel", joinColumns = {
            @JoinColumn(name = "securityAnswerId", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "userAccountId", nullable = false, updatable = false)})
    private Set<UserAccount> userAccounts = new HashSet<>(0);

    public SecurityAnswer() {
    }

    public SecurityAnswer(SecurityQuestion securityQuestion, String answer) {
        this.securityQuestion = securityQuestion;
        this.answer = answer;
    }

    public SecurityAnswer(SecurityQuestion securityQuestion, String answer, Set<UserAccount> userAccounts) {
        this.securityQuestion = securityQuestion;
        this.answer = answer;
        this.userAccounts = userAccounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SecurityQuestion getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(SecurityQuestion securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Set<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(Set<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

}
