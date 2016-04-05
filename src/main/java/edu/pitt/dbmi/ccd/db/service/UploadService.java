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

import static edu.pitt.dbmi.ccd.db.specification.UploadSpecification.filterSpec;
import static edu.pitt.dbmi.ccd.db.specification.UploadSpecification.searchSpec;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.pitt.dbmi.ccd.db.entity.Upload;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.UploadRepository;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
@Transactional
public class UploadService {

    @Autowired
    private UploadRepository uploadRepository;

    public Upload save(Upload upload) {
        return uploadRepository.save(upload);
    }

    public List<Upload> save(Set<Upload> uploads) {
        return uploadRepository.save(uploads);
    }

//    public Upload findUpload(Long id) {
//        return uploadRepository.findOne(id);
//    }

    public Optional<Upload> findById(Long id) {
        return uploadRepository.findById(id);
    }

    public Page<Upload> filter(String username, String type, Pageable pageable) {
        return uploadRepository.findAll(filterSpec(username, type), pageable);
    }

    public Page<Upload> search(String username, String type, Set<String> matches, Set<String> nots, Pageable pageable) {
        return uploadRepository.findAll(searchSpec(username, type, matches, nots), pageable);
    }

    public Page<Upload> findAll(Pageable pageable) {
        return uploadRepository.findAll(pageable);
    }

    public void delete(Upload upload) {
        uploadRepository.delete(upload);
    }

    public void delete(Set<Upload> uploads) {
        uploadRepository.delete(uploads);
    }
}
