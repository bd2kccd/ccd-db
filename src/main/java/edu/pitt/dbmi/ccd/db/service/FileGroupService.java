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

import edu.pitt.dbmi.ccd.db.repository.FileGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Jun 30, 2017 10:58:56 AM
 *
 * @author Kevin V. Bui (kvb2@pitt.edu)
 */
@Service
public class FileGroupService {

    private final FileGroupRepository fileGroupRepository;

    @Autowired
    public FileGroupService(FileGroupRepository fileGroupRepository) {
        this.fileGroupRepository = fileGroupRepository;
    }

    public FileGroupRepository getRepository() {
        return fileGroupRepository;
    }

}
