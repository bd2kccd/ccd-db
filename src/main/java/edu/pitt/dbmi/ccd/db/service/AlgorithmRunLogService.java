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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.pitt.dbmi.ccd.db.entity.Algorithm;
import edu.pitt.dbmi.ccd.db.entity.AlgorithmRunLog;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.AlgorithmRepository;
import edu.pitt.dbmi.ccd.db.repository.AlgorithmRunLogRepository;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * May 31, 2017 4:05:38 PM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class AlgorithmRunLogService {

    private final AlgorithmRunLogRepository algorithmRunLogRepository;
    private final AlgorithmRepository algorithmRepository;

    @Autowired
    public AlgorithmRunLogService(AlgorithmRunLogRepository algorithmRunLogRepository, AlgorithmRepository algorithmRepository) {
        this.algorithmRunLogRepository = algorithmRunLogRepository;
        this.algorithmRepository = algorithmRepository;
    }

    public AlgorithmRunLog logAlgorithmRun(String algorithmName, Map<String, String> parameters, Map<String, String> fileSummary, UserAccount userAccount) {
        return logAlgorithmRun(parameters, fileSummary, algorithmRepository.findByName(algorithmName), userAccount);
    }

    private AlgorithmRunLog logAlgorithmRun(Map<String, String> parameters, Map<String, String> fileSummary, Algorithm algorithm, UserAccount userAccount) {
        try {
            String algoParameter = new ObjectMapper().writeValueAsString(parameters);
            String dataFileSummary = new ObjectMapper().writeValueAsString(fileSummary);
            Date submitDate = new Date(System.currentTimeMillis());

            return algorithmRunLogRepository.save(new AlgorithmRunLog(algoParameter, dataFileSummary, submitDate, algorithm, userAccount));
        } catch (JsonProcessingException exception) {
            return null;
        }
    }

    public AlgorithmRunLogRepository getAlgorithmRunLogRepository() {
        return algorithmRunLogRepository;
    }

}
