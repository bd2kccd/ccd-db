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
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.pitt.dbmi.ccd.db.CCDDatabaseApplication;
import edu.pitt.dbmi.ccd.db.TestFileInfoUtility;
import edu.pitt.dbmi.ccd.db.entity.FileInfoDB;

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
        
        String fileName = "ccd-graphviz.dot";
        String fileAbsolutePath = "/john.doe/ccd/workspace/data/ccd-graphviz.dot";
        Date creationTime = new Date(System.currentTimeMillis());
        Date lastAccessTime = new Date(System.currentTimeMillis());
        Date lastModifiedTime = new Date(System.currentTimeMillis());
        Long fileSize = new Long(999999L);
        String md5CheckSum = "e10adc3949ba59abbe56e057f20f883e";
        
        // create
        FileInfoDB fileInfo = new FileInfoDB(fileName, fileAbsolutePath, creationTime, 
        		lastAccessTime, lastModifiedTime, fileSize, md5CheckSum);        
        fileInfo = fileInfoRepository.save(fileInfo);
        Assert.assertNotNull(fileInfo.getId());
        TestFileInfoUtility.printFileInfo(fileInfo, "Create New FileInfo");
        
        fileName = "ccd-graphviz1.dot";
        fileAbsolutePath = "/john.doe/ccd/workspace/data/ccd-graphviz1.dot";
        fileInfo = new FileInfoDB(fileName, fileAbsolutePath, creationTime, 
        		lastAccessTime, lastModifiedTime, fileSize, md5CheckSum);        
        fileInfo = fileInfoRepository.save(fileInfo);
        Assert.assertNotNull(fileInfo.getId());
        TestFileInfoUtility.printFileInfo(fileInfo, "Create Yet Another New FileInfo");
        
        // update
        fileAbsolutePath = "/john.doe/ccd/workspace/new-data/ccd-graphviz1.dot";
        fileInfo.setFileAbsolutePath(fileAbsolutePath);
        fileInfo = fileInfoRepository.save(fileInfo);
        Assert.assertEquals(fileInfo.getFileAbsolutePath(), fileAbsolutePath);
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
     * Test of findByFileName method, of class UserAccountRepository.
     */
    @Test
    public void testFindByFileName(){
        System.out.println("findByFileName");

        String fileName = "ccd-graphviz.dot";
        String fileAbsolutePath = "/john.doe/ccd/workspace/new-data/ccd-graphviz.dot";
        Date creationTime = new Date(System.currentTimeMillis());
        Date lastAccessTime = new Date(System.currentTimeMillis());
        Date lastModifiedTime = new Date(System.currentTimeMillis());
        Long fileSize = new Long(999999L);
        String md5CheckSum = "e10adc3949ba59abbe56e057f20f883e";
        
        // create
        FileInfoDB fileInfo = new FileInfoDB(fileName, fileAbsolutePath, creationTime, 
        		lastAccessTime, lastModifiedTime, fileSize, md5CheckSum);        
        fileInfo = fileInfoRepository.save(fileInfo);
        Assert.assertNotNull(fileInfo.getId());
        
        List<FileInfoDB> list = fileInfoRepository.findByFileName(fileName);
        for(FileInfoDB fInfo : list){
            Assert.assertNotNull(fInfo);
            TestFileInfoUtility.printFileInfo(fInfo, "Find by FileName");
        }
    	
    }
	
    /**
     * Test of findByFileAbsolutePath method, of class UserAccountRepository.
     */
    @Test
    public void testFindByFileAbsolutePath(){
        System.out.println("findByFileAbsolutePath");

        String fileAbsolutePath = "/john.doe/ccd/workspace/data/ccd-graphviz.dot";
        
        FileInfoDB fileInfo = fileInfoRepository.findByFileAbsolutePath(fileAbsolutePath);
        Assert.assertNotNull(fileInfo);
        TestFileInfoUtility.printFileInfo(fileInfo, "Find by File Absolute Path");
    	
    }
	
}
