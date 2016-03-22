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

import static org.junit.Assert.*;

import java.util.Date;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.entity.AnnnotationData;
import edu.pitt.dbmi.ccd.db.entity.Annnotation;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.entity.Upload;
import edu.pitt.dbmi.ccd.db.entity.Access;
import edu.pitt.dbmi.ccd.db.entity.Vocabulary;
import edu.pitt.dbmi.ccd.db.entity.Attribute;
import edu.pitt.dbmi.ccd.db.service.AnnnotationDataService;
import edu.pitt.dbmi.ccd.db.service.AnnnotationService;
import edu.pitt.dbmi.ccd.db.service.UserAccountService;
import edu.pitt.dbmi.ccd.db.service.UploadService;
import edu.pitt.dbmi.ccd.db.service.AccessService;
import edu.pitt.dbmi.ccd.db.service.VocabularyService;
import edu.pitt.dbmi.ccd.db.service.AttributeService;
import edu.pitt.dbmi.ccd.db.error.NotFoundException;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class AnnnotationDataServiceTest {

    @Autowired
    private AnnnotationDataService annotationDataService;
    @Autowired
    private AnnotationService annotationService
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private VocabularyService vocabularyService;
    @Autowired
    private AttributeService attributeService;

    private Annotation annotation;
    private AnnotationData data;

    @BeforeClass
    public void setupClass() {
        final Person person = new Person("Isaac", "Newton", "test@example.com", "~/.ccd_workspace");
        final UserAccount user = userAccountService.create(person, "test", "test", true, new Date()); 
        final Upload upload = uploadService.create(user, "Department Of Biomedical Informatics", "http://www.dbmi.pitt.edu")
        final Access access = accessService.findByName("PUBLIC");
        final Vocabulary vocab = vocabService.findByName("Plaintext");
        annotation = annotationService.create(new Annotation(user, upload, null, access, null, vocab);
    }

    @Before
    public void setup() {
        data = annotationDataService.create(annotation, 1L, "Test annotation");
        assertNotNull(data.getId());
    }

    @After
    public void cleanup() {
        final Long id = data.getId();
        annotationDataService.delete(data);
        try {
            data = groupService.findOne(id);
            fail("Data not deleted");
        } catch (NotFoundException ex) { }
    }

    @Test
    public void testCrud() {
        final Vocabulary vocab = vocabService.findByName(vocabName); 
        final Attribute attribute = vocab.getAttributes().stream().findFirst();
        final AnnnotationData data = new 
        // create
    }
}
