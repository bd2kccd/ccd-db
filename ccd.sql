-- MySQL dump 10.15  Distrib 10.0.25-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: ccd
-- ------------------------------------------------------
-- Server version	10.0.25-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Annotation`
--

DROP TABLE IF EXISTS `Annotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Annotation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `redacted` tinyint(1) NOT NULL DEFAULT '0',
  `createddate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  `modifyCount` int(11) NOT NULL DEFAULT '0',
  `userAccountId` bigint(20) NOT NULL,
  `shareAccessId` bigint(20) NOT NULL,
  `shareGroupId` bigint(20) DEFAULT NULL,
  `vocabularyId` bigint(20) NOT NULL,
  `annotationTargetId` bigint(20) NOT NULL,
  `parentAnnotationId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userAccountId` (`userAccountId`),
  KEY `shareAccessId` (`shareAccessId`),
  KEY `shareGroupId` (`shareGroupId`),
  KEY `vocabularyId` (`vocabularyId`),
  KEY `annotationTargetId` (`annotationTargetId`),
  KEY `parentAnnotationId` (`parentAnnotationId`),
  CONSTRAINT `Annotation_ibfk_1` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`),
  CONSTRAINT `Annotation_ibfk_2` FOREIGN KEY (`shareAccessId`) REFERENCES `ShareAccess` (`id`),
  CONSTRAINT `Annotation_ibfk_3` FOREIGN KEY (`shareGroupId`) REFERENCES `ShareGroup` (`id`),
  CONSTRAINT `Annotation_ibfk_4` FOREIGN KEY (`vocabularyId`) REFERENCES `Vocabulary` (`id`),
  CONSTRAINT `Annotation_ibfk_5` FOREIGN KEY (`annotationTargetId`) REFERENCES `AnnotationTarget` (`id`),
  CONSTRAINT `Annotation_ibfk_6` FOREIGN KEY (`parentAnnotationId`) REFERENCES `Annotation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Annotation`
--

LOCK TABLES `Annotation` WRITE;
/*!40000 ALTER TABLE `Annotation` DISABLE KEYS */;
/*!40000 ALTER TABLE `Annotation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AnnotationData`
--

DROP TABLE IF EXISTS `AnnotationData`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AnnotationData` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modifiedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modifyCount` int(11) NOT NULL DEFAULT '0',
  `annotationId` bigint(20) NOT NULL,
  `attributeId` bigint(20) NOT NULL,
  `parentAnnotationDataId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `annotationId` (`annotationId`),
  KEY `attributeId` (`attributeId`),
  KEY `parentAnnotationDataId` (`parentAnnotationDataId`),
  CONSTRAINT `AnnotationData_ibfk_1` FOREIGN KEY (`attributeId`) REFERENCES `Attribute` (`id`),
  CONSTRAINT `AnnotationData_ibfk_2` FOREIGN KEY (`parentAnnotationDataId`) REFERENCES `AnnotationData` (`id`),
  CONSTRAINT `AnnotationData_ibfk_3` FOREIGN KEY (`annotationId`) REFERENCES `Annotation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AnnotationData`
--

LOCK TABLES `AnnotationData` WRITE;
/*!40000 ALTER TABLE `AnnotationData` DISABLE KEYS */;
/*!40000 ALTER TABLE `AnnotationData` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AnnotationTarget`
--

DROP TABLE IF EXISTS `AnnotationTarget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AnnotationTarget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(2083) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  `modifyCount` int(11) NOT NULL DEFAULT '0',
  `userAccountId` bigint(20) NOT NULL,
  `fileId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userAccountId` (`userAccountId`),
  KEY `fileId` (`fileId`),
  CONSTRAINT `AnnotationTarget_ibfk_1` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`),
  CONSTRAINT `AnnotationTarget_ibfk_2` FOREIGN KEY (`fileId`) REFERENCES `File` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AnnotationTarget`
--

LOCK TABLES `AnnotationTarget` WRITE;
/*!40000 ALTER TABLE `AnnotationTarget` DISABLE KEYS */;
/*!40000 ALTER TABLE `AnnotationTarget` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Attribute`
--

DROP TABLE IF EXISTS `Attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `required` tinyint(1) NOT NULL DEFAULT '0',
  `attributeLevelId` bigint(20) NOT NULL,
  `vocabularyId` bigint(20) NOT NULL,
  `parentAttributeId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`,`attributeLevelId`,`vocabularyId`),
  KEY `attributeLevelId` (`attributeLevelId`),
  KEY `vocabularyId` (`vocabularyId`),
  KEY `parentAttributeId` (`parentAttributeId`),
  CONSTRAINT `Attribute_ibfk_1` FOREIGN KEY (`attributeLevelId`) REFERENCES `AttributeLevel` (`id`),
  CONSTRAINT `Attribute_ibfk_2` FOREIGN KEY (`vocabularyId`) REFERENCES `Vocabulary` (`id`),
  CONSTRAINT `Attribute_ibfk_3` FOREIGN KEY (`parentAttributeId`) REFERENCES `Attribute` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Attribute`
--

LOCK TABLES `Attribute` WRITE;
/*!40000 ALTER TABLE `Attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `Attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AttributeLevel`
--

DROP TABLE IF EXISTS `AttributeLevel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AttributeLevel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(511) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AttributeLevel`
--

LOCK TABLES `AttributeLevel` WRITE;
/*!40000 ALTER TABLE `AttributeLevel` DISABLE KEYS */;
/*!40000 ALTER TABLE `AttributeLevel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DataFile`
--

DROP TABLE IF EXISTS `DataFile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DataFile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `numOfRows` int(11) DEFAULT NULL,
  `numOfColumns` int(11) DEFAULT NULL,
  `fileId` bigint(20) NOT NULL,
  `fileDelimiterId` bigint(20) NOT NULL,
  `variableTypeId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fileId` (`fileId`),
  KEY `fileDelimiterId` (`fileDelimiterId`),
  KEY `variableTypeId` (`variableTypeId`),
  CONSTRAINT `DataFile_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `File` (`id`),
  CONSTRAINT `DataFile_ibfk_2` FOREIGN KEY (`fileDelimiterId`) REFERENCES `FileDelimiter` (`id`),
  CONSTRAINT `DataFile_ibfk_3` FOREIGN KEY (`variableTypeId`) REFERENCES `VariableType` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DataFile`
--

LOCK TABLES `DataFile` WRITE;
/*!40000 ALTER TABLE `DataFile` DISABLE KEYS */;
/*!40000 ALTER TABLE `DataFile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `File`
--

DROP TABLE IF EXISTS `File`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `File` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `absolutePath` varchar(255) NOT NULL,
  `creationTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fileSize` bigint(20) NOT NULL,
  `md5CheckSum` varchar(32) DEFAULT NULL,
  `fileTypeId` bigint(20) DEFAULT NULL,
  `userAccountId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`,`userAccountId`),
  UNIQUE KEY `title` (`title`,`userAccountId`),
  KEY `fileTypeId` (`fileTypeId`),
  KEY `userAccountId` (`userAccountId`),
  CONSTRAINT `File_ibfk_1` FOREIGN KEY (`fileTypeId`) REFERENCES `FileType` (`id`),
  CONSTRAINT `File_ibfk_2` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `File`
--

LOCK TABLES `File` WRITE;
/*!40000 ALTER TABLE `File` DISABLE KEYS */;
/*!40000 ALTER TABLE `File` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FileDelimiter`
--

DROP TABLE IF EXISTS `FileDelimiter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FileDelimiter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL,
  `value` varchar(8) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FileDelimiter`
--

LOCK TABLES `FileDelimiter` WRITE;
/*!40000 ALTER TABLE `FileDelimiter` DISABLE KEYS */;
/*!40000 ALTER TABLE `FileDelimiter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FileType`
--

DROP TABLE IF EXISTS `FileType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FileType` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FileType`
--

LOCK TABLES `FileType` WRITE;
/*!40000 ALTER TABLE `FileType` DISABLE KEYS */;
/*!40000 ALTER TABLE `FileType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OauthAccessToken`
--

DROP TABLE IF EXISTS `OauthAccessToken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OauthAccessToken` (
  `authenticationId` varchar(255) NOT NULL,
  `tokenId` varchar(255) NOT NULL,
  `token` longblob NOT NULL,
  `userName` varchar(255) NOT NULL,
  `clientId` varchar(255) NOT NULL,
  `authentication` longblob NOT NULL,
  `refreshToken` varchar(255) NOT NULL,
  PRIMARY KEY (`authenticationId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OauthAccessToken`
--

LOCK TABLES `OauthAccessToken` WRITE;
/*!40000 ALTER TABLE `OauthAccessToken` DISABLE KEYS */;
/*!40000 ALTER TABLE `OauthAccessToken` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OauthRefreshToken`
--

DROP TABLE IF EXISTS `OauthRefreshToken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OauthRefreshToken` (
  `tokenId` varchar(255) NOT NULL,
  `token` longblob NOT NULL,
  `authentication` longblob NOT NULL,
  PRIMARY KEY (`tokenId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OauthRefreshToken`
--

LOCK TABLES `OauthRefreshToken` WRITE;
/*!40000 ALTER TABLE `OauthRefreshToken` DISABLE KEYS */;
/*!40000 ALTER TABLE `OauthRefreshToken` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Person`
--

DROP TABLE IF EXISTS `Person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) NOT NULL,
  `middleName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `workspace` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Person`
--

LOCK TABLES `Person` WRITE;
/*!40000 ALTER TABLE `Person` DISABLE KEYS */;
/*!40000 ALTER TABLE `Person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShareAccess`
--

DROP TABLE IF EXISTS `ShareAccess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ShareAccess` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(31) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShareAccess`
--

LOCK TABLES `ShareAccess` WRITE;
/*!40000 ALTER TABLE `ShareAccess` DISABLE KEYS */;
/*!40000 ALTER TABLE `ShareAccess` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShareGroup`
--

DROP TABLE IF EXISTS `ShareGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ShareGroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(127) NOT NULL,
  `description` varchar(511) NOT NULL,
  `userAccountId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `userAccountId` (`userAccountId`),
  CONSTRAINT `ShareGroup_ibfk_1` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShareGroup`
--

LOCK TABLES `ShareGroup` WRITE;
/*!40000 ALTER TABLE `ShareGroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `ShareGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShareGroupMembership`
--

DROP TABLE IF EXISTS `ShareGroupMembership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ShareGroupMembership` (
  `shareGroupId` bigint(20) NOT NULL,
  `userAccountId` bigint(20) NOT NULL,
  PRIMARY KEY (`shareGroupId`,`userAccountId`),
  KEY `shareGroupId` (`shareGroupId`),
  KEY `userAccountId` (`userAccountId`),
  CONSTRAINT `ShareGroupMembership_ibfk_1` FOREIGN KEY (`shareGroupId`) REFERENCES `ShareGroup` (`id`),
  CONSTRAINT `ShareGroupMembership_ibfk_2` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShareGroupMembership`
--

LOCK TABLES `ShareGroupMembership` WRITE;
/*!40000 ALTER TABLE `ShareGroupMembership` DISABLE KEYS */;
/*!40000 ALTER TABLE `ShareGroupMembership` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ShareGroupRequest`
--

DROP TABLE IF EXISTS `ShareGroupRequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ShareGroupRequest` (
  `shareGroupId` bigint(20) NOT NULL,
  `userAccountId` bigint(20) NOT NULL,
  PRIMARY KEY (`shareGroupId`,`userAccountId`),
  KEY `shareGroupId` (`shareGroupId`),
  KEY `userAccountId` (`userAccountId`),
  CONSTRAINT `ShareGroupRequest_ibfk_1` FOREIGN KEY (`shareGroupId`) REFERENCES `ShareGroup` (`id`),
  CONSTRAINT `ShareGroupRequest_ibfk_2` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ShareGroupRequest`
--

LOCK TABLES `ShareGroupRequest` WRITE;
/*!40000 ALTER TABLE `ShareGroupRequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `ShareGroupRequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserAccount`
--

DROP TABLE IF EXISTS `UserAccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserAccount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `disabled` tinyint(1) NOT NULL,
  `registrationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `registrationLocation` bigint(20) DEFAULT NULL,
  `account` varchar(255) NOT NULL,
  `activationKey` varchar(255) DEFAULT NULL,
  `userLoginId` bigint(20) NOT NULL,
  `userLoginAttemptId` bigint(20) NOT NULL,
  `personId` bigint(20) NOT NULL,
  `userRoleId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `userLoginId` (`userLoginId`),
  KEY `userLoginAttemptId` (`userLoginAttemptId`),
  KEY `personId` (`personId`),
  KEY `userRoleId` (`userRoleId`),
  CONSTRAINT `UserAccount_ibfk_1` FOREIGN KEY (`userLoginId`) REFERENCES `UserLogin` (`id`),
  CONSTRAINT `UserAccount_ibfk_2` FOREIGN KEY (`userLoginAttemptId`) REFERENCES `UserLoginAttempt` (`id`),
  CONSTRAINT `UserAccount_ibfk_3` FOREIGN KEY (`personId`) REFERENCES `Person` (`id`),
  CONSTRAINT `UserAccount_ibfk_4` FOREIGN KEY (`userRoleId`) REFERENCES `UserRole` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserAccount`
--

LOCK TABLES `UserAccount` WRITE;
/*!40000 ALTER TABLE `UserAccount` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserAccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserLogin`
--

DROP TABLE IF EXISTS `UserLogin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserLogin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `loginDate` timestamp NULL DEFAULT NULL,
  `loginLocation` bigint(20) DEFAULT NULL,
  `lastLoginDate` timestamp NULL DEFAULT NULL,
  `lastLoginLocation` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserLogin`
--

LOCK TABLES `UserLogin` WRITE;
/*!40000 ALTER TABLE `UserLogin` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserLogin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserLoginAttempt`
--

DROP TABLE IF EXISTS `UserLoginAttempt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserLoginAttempt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attemptDate` timestamp NULL DEFAULT NULL,
  `attemptLocation` bigint(20) DEFAULT NULL,
  `attemptCount` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserLoginAttempt`
--

LOCK TABLES `UserLoginAttempt` WRITE;
/*!40000 ALTER TABLE `UserLoginAttempt` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserLoginAttempt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserRole`
--

DROP TABLE IF EXISTS `UserRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserRole` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserRole`
--

LOCK TABLES `UserRole` WRITE;
/*!40000 ALTER TABLE `UserRole` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserRole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VariableFile`
--

DROP TABLE IF EXISTS `VariableFile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VariableFile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `numOfVars` int(11) DEFAULT NULL,
  `fileId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fileId` (`fileId`),
  CONSTRAINT `VariableFile_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `File` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VariableFile`
--

LOCK TABLES `VariableFile` WRITE;
/*!40000 ALTER TABLE `VariableFile` DISABLE KEYS */;
/*!40000 ALTER TABLE `VariableFile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VariableType`
--

DROP TABLE IF EXISTS `VariableType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VariableType` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VariableType`
--

LOCK TABLES `VariableType` WRITE;
/*!40000 ALTER TABLE `VariableType` DISABLE KEYS */;
/*!40000 ALTER TABLE `VariableType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Vocabulary`
--

DROP TABLE IF EXISTS `Vocabulary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Vocabulary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(127) NOT NULL,
  `description` varchar(511) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  `version` varchar(63) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Vocabulary`
--

LOCK TABLES `Vocabulary` WRITE;
/*!40000 ALTER TABLE `Vocabulary` DISABLE KEYS */;
/*!40000 ALTER TABLE `Vocabulary` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-02 16:50:40
