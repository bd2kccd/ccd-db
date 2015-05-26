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
package edu.pitt.dbmi.ccd.db;

import java.util.List;

import edu.pitt.dbmi.ccd.db.entity.FileInfoDB;

/**
 *
 * May 18, 2015 1:54:38 PM
 *
 * @author Chirayu (Kong) Wongchokprasitti (chw20@pitt.edu)
 * 
 */
public class TestFileInfoUtility {

    public static String DOUBLE_LINE = "================================================================================";

    public static String SINGLE_LINE = "--------------------------------------------------------------------------------";

    public static void printFileInfoList(List<FileInfoDB> fileInfos, String title){
        System.out.println(DOUBLE_LINE);
        System.out.println(title);
        System.out.println(SINGLE_LINE);

        if(fileInfos == null){
        	System.out.println(fileInfos);
        }else if(fileInfos.isEmpty()){
        	System.out.println("FileInfo list is empty.");
        }else{
        	for(FileInfoDB fileInfo : fileInfos){
        		System.out.printf("ID: %d\n", fileInfo.getId());
                System.out.printf("File Name: %s\n", fileInfo.getFileName());
                System.out.printf("File Absolute Path: %s\n", fileInfo.getFileAbsolutePath());
                System.out.printf("Creation Time: %s\n", fileInfo.getCreationTime());
                System.out.printf("Last Access Time: %s\n", fileInfo.getLastAccessTime());
                System.out.printf("Last Modified Time: %s\n", fileInfo.getLastModifiedTime());
                System.out.printf("File Size: %d\n", fileInfo.getFileSize());
                System.out.printf("MD5 Checksum: %s\n", fileInfo.getMd5CheckSum());
                System.out.println();
        	}
        }
        
        System.out.println(DOUBLE_LINE);
    }
    
    public static void printFileInfo(FileInfoDB fileInfo, String title){
        System.out.println(DOUBLE_LINE);
        System.out.println(title);
        System.out.println(SINGLE_LINE);

        if(fileInfo == null){
        	System.out.println(fileInfo);
        }else{
    		System.out.printf("ID: %d\n", fileInfo.getId());
            System.out.printf("File Name: %s\n", fileInfo.getFileName());
            System.out.printf("File Absolute Path: %s\n", fileInfo.getFileAbsolutePath());
            System.out.printf("Creation Time: %s\n", fileInfo.getCreationTime());
            System.out.printf("Last Access Time: %s\n", fileInfo.getLastAccessTime());
            System.out.printf("Last Modified Time: %s\n", fileInfo.getLastModifiedTime());
            System.out.printf("File Size: %d\n", fileInfo.getFileSize());
            System.out.printf("MD5 Checksum: %s\n", fileInfo.getMd5CheckSum());
            System.out.println();
        }
        
        System.out.println(DOUBLE_LINE);
    }

}
