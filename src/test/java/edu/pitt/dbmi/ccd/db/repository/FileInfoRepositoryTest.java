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
package edu.pitt.dbmi.ccd.db.repository;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.TestFileInfoUtility;
import edu.pitt.dbmi.ccd.db.entity.FileInfo;
import edu.pitt.dbmi.ccd.db.entity.Person;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;

/**
 *
 * May 18, 2015 1:24:02 PM
 *
 * @author Chirayu (Kong) Wongchokprasitti (chw20@pitt.edu)
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CCDDatabaseApplication.class)
public class FileInfoRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PersonRepository personRepository;

	@Autowired
	private FileInfoRepository fileInfoRepository;
	
	@Test
    public void crudOperations() {
        System.out.println("CRUD Operations");
        
        // create person first
        Person person = new Person("John", "Doe", "john.doe@localhost", "/john.doe");
        personRepository.save(person);
        Assert.assertNotNull(person.getId());

        // create new user account
        String username = "jdoe";
        String password = "johnd";
        boolean active = true;
        Date createdDate = new Date(System.currentTimeMillis());
        UserAccount userAccount = new UserAccount(username, password, active, createdDate, person);
        userAccountRepository.save(userAccount);
        Assert.assertNotNull(userAccount.getId());

        String fileName = "ccd-graphviz.dot";
        String filePath = "/john.doe/ccd/workspace/data/";
        Date creationTime = new Date(System.currentTimeMillis());
        Date lastAccessTime = new Date(System.currentTimeMillis());
        Date lastModifiedTime = new Date(System.currentTimeMillis());
        Long fileSize = new Long(999999L);
        String md5CheckSum = "e10adc3949ba59abbe56e057f20f883e";
        
        // create
        FileInfo fileInfo = new FileInfo(fileName, filePath, creationTime, 
        		lastAccessTime, lastModifiedTime, fileSize, md5CheckSum, userAccount);        
        fileInfo = fileInfoRepository.save(fileInfo);
        Assert.assertNotNull(fileInfo.getId());
        TestFileInfoUtility.printFileInfo(fileInfo, "Create New FileInfo");
        
        fileName = "ccd-graphviz1.dot";
        fileInfo = new FileInfo(fileName, filePath, creationTime, 
        		lastAccessTime, lastModifiedTime, fileSize, md5CheckSum, userAccount);        
        fileInfo = fileInfoRepository.save(fileInfo);
        Assert.assertNotNull(fileInfo.getId());
        TestFileInfoUtility.printFileInfo(fileInfo, "Create Yet Another New FileInfo");
        
        // update
        filePath = "/john.doe/ccd/workspace/new-data/";
        fileInfo.setFilePath(filePath);
        fileInfo = fileInfoRepository.save(fileInfo);
        Assert.assertEquals(fileInfo.getFilePath(), filePath);
        TestFileInfoUtility.printFileInfo(fileInfo, "Update FileInfo");
        
        // read
        Long id = fileInfo.getId();
        fileInfo = fileInfoRepository.findOne(id);
        Assert.assertNotNull(fileInfo);
        TestFileInfoUtility.printFileInfo(fileInfo, "Find by ID");
        
        // delete
        id = fileInfo.getId();
        fileInfoRepository.delete(id);
        fileInfo = fileInfoRepository.findOne(id);
        Assert.assertNull(fileInfo);
        TestFileInfoUtility.printFileInfo(fileInfo, "Delete FileInfo");
	}
	
    /**
     * Test of findByUsername method, of class UserAccountRepository.
     */
    /*@Test
    public void testFindByFileName(){
        System.out.println("findByUsername");

        // create person first
        Person person = new Person("John", "Doe", "john.doe@localhost", "/john.doe");
        personRepository.save(person);
        Assert.assertNotNull(person.getId());

        // create new user account
        String username = "jdoe";
        String password = "johnd";
        boolean active = true;
        Date createdDate = new Date(System.currentTimeMillis());
        UserAccount userAccount = new UserAccount(username, password, active, createdDate, person);
        userAccountRepository.save(userAccount);
        Assert.assertNotNull(userAccount.getId());

        Long userAccountId = userAccount.getId();
        
        String fileName = "ccd-graphviz.dot";
        String filePath = "/john.doe/ccd/workspace/data/";
        Date creationTime = new Date(System.currentTimeMillis());
        Date lastAccessTime = new Date(System.currentTimeMillis());
        Date lastModifiedTime = new Date(System.currentTimeMillis());
        Long fileSize = new Long(999999L);
        String md5CheckSum = "e10adc3949ba59abbe56e057f20f883e";
        
        // create
        FileInfo fileInfo = new FileInfo(fileName, filePath, creationTime, 
        		lastAccessTime, lastModifiedTime, fileSize, md5CheckSum, userAccount);        
        fileInfo = fileInfoRepository.save(fileInfo);
        Assert.assertNotNull(fileInfo.getId());
        
        fileInfo = fileInfoRepository.findByFileName(fileName, userAccountId);
        Assert.assertNotNull(fileInfo);
        TestFileInfoUtility.printFileInfo(fileInfo, "Find by FileName");
    	
    }*/
	
}
