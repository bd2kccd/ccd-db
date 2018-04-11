-- MySQL dump 10.16  Distrib 10.2.14-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: ccd
-- ------------------------------------------------------
-- Server version	10.2.14-MariaDB

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
-- Table structure for table `AlgorithmType`
--

DROP TABLE IF EXISTS `AlgorithmType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AlgorithmType` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `shortName` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `shortName` (`shortName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DataDelimiter`
--

DROP TABLE IF EXISTS `DataDelimiter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DataDelimiter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `shortName` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `shortName` (`shortName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `creationTime` timestamp NOT NULL DEFAULT current_timestamp(),
  `fileSize` bigint(20) NOT NULL,
  `md5CheckSum` varchar(32) DEFAULT NULL,
  `userAccountId` bigint(20) NOT NULL,
  `fileFormatId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`,`userAccountId`),
  UNIQUE KEY `title` (`title`,`userAccountId`),
  KEY `userAccountId` (`userAccountId`),
  KEY `fileFormatId` (`fileFormatId`),
  CONSTRAINT `File_ibfk_1` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`),
  CONSTRAINT `File_ibfk_2` FOREIGN KEY (`fileFormatId`) REFERENCES `FileFormat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FileFormat`
--

DROP TABLE IF EXISTS `FileFormat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FileFormat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `shortName` varchar(32) NOT NULL,
  `fileTypeId` bigint(20) NOT NULL,
  `algorithmTypeId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `shortName` (`shortName`),
  UNIQUE KEY `fileTypeId` (`fileTypeId`,`algorithmTypeId`),
  KEY `algorithmTypeId` (`algorithmTypeId`),
  CONSTRAINT `FileFormat_ibfk_1` FOREIGN KEY (`fileTypeId`) REFERENCES `FileType` (`id`),
  CONSTRAINT `FileFormat_ibfk_2` FOREIGN KEY (`algorithmTypeId`) REFERENCES `AlgorithmType` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FileGroup`
--

DROP TABLE IF EXISTS `FileGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FileGroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `creationTime` timestamp NOT NULL DEFAULT current_timestamp(),
  `variableTypeId` bigint(20) NOT NULL,
  `userAccountId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`,`userAccountId`),
  KEY `fileTypeId` (`variableTypeId`),
  KEY `userAccountId` (`userAccountId`),
  CONSTRAINT `FileGroup_ibfk_1` FOREIGN KEY (`variableTypeId`) REFERENCES `VariableType` (`id`),
  CONSTRAINT `FileGroup_ibfk_2` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FileGroupFileRel`
--

DROP TABLE IF EXISTS `FileGroupFileRel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FileGroupFileRel` (
  `fileGroupId` bigint(20) NOT NULL,
  `fileId` bigint(20) NOT NULL,
  PRIMARY KEY (`fileGroupId`,`fileId`),
  KEY `fileId` (`fileId`),
  CONSTRAINT `FileGroupFileRel_ibfk_1` FOREIGN KEY (`fileGroupId`) REFERENCES `FileGroup` (`id`),
  CONSTRAINT `FileGroupFileRel_ibfk_2` FOREIGN KEY (`fileId`) REFERENCES `File` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FileType`
--

DROP TABLE IF EXISTS `FileType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FileType` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `shortName` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `shortName` (`shortName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `JobInfo`
--

DROP TABLE IF EXISTS `JobInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JobInfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `algoParam` text DEFAULT NULL,
  `datasetId` bigint(20) NOT NULL,
  `singleDataset` tinyint(1) NOT NULL DEFAULT 0,
  `creationTime` timestamp NOT NULL DEFAULT current_timestamp(),
  `startTime` timestamp NULL DEFAULT NULL,
  `endTime` timestamp NULL DEFAULT NULL,
  `jobStatusId` bigint(20) NOT NULL,
  `jobLocationId` bigint(20) NOT NULL,
  `algorithmTypeId` bigint(20) NOT NULL,
  `userAccountId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `algorithmTypeId` (`algorithmTypeId`),
  KEY `jobLocationId` (`jobLocationId`),
  KEY `jobStatusId` (`jobStatusId`),
  KEY `userAccountId` (`userAccountId`),
  CONSTRAINT `JobInfo_ibfk_1` FOREIGN KEY (`algorithmTypeId`) REFERENCES `AlgorithmType` (`id`),
  CONSTRAINT `JobInfo_ibfk_2` FOREIGN KEY (`jobLocationId`) REFERENCES `JobLocation` (`id`),
  CONSTRAINT `JobInfo_ibfk_3` FOREIGN KEY (`jobStatusId`) REFERENCES `JobStatus` (`id`),
  CONSTRAINT `JobInfo_ibfk_4` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `JobLocation`
--

DROP TABLE IF EXISTS `JobLocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JobLocation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `shortName` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `shortName` (`shortName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `JobQueue`
--

DROP TABLE IF EXISTS `JobQueue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JobQueue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `jobInfoId` bigint(20) NOT NULL,
  `userAccountId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `jobInfoId` (`jobInfoId`),
  KEY `userAccountId` (`userAccountId`),
  CONSTRAINT `JobQueue_ibfk_1` FOREIGN KEY (`jobInfoId`) REFERENCES `JobInfo` (`id`),
  CONSTRAINT `JobQueue_ibfk_2` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `JobStatus`
--

DROP TABLE IF EXISTS `JobStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JobStatus` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `shortName` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `shortName` (`shortName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TetradDataFile`
--

DROP TABLE IF EXISTS `TetradDataFile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TetradDataFile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hasHeader` tinyint(1) NOT NULL DEFAULT 0,
  `quoteChar` char(1) DEFAULT NULL,
  `missingMarker` varchar(8) DEFAULT NULL,
  `commentMarker` varchar(8) DEFAULT NULL,
  `numOfCases` int(11) DEFAULT NULL,
  `numOfVars` int(11) DEFAULT NULL,
  `fileId` bigint(20) NOT NULL,
  `dataDelimiterId` bigint(20) NOT NULL,
  `variableTypeId` bigint(20) NOT NULL,
  `userAccountId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fileId` (`fileId`),
  KEY `dataDelimiterId` (`dataDelimiterId`),
  KEY `variableTypeId` (`variableTypeId`),
  KEY `userAccountId` (`userAccountId`),
  CONSTRAINT `TetradDataFile_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `File` (`id`),
  CONSTRAINT `TetradDataFile_ibfk_2` FOREIGN KEY (`dataDelimiterId`) REFERENCES `DataDelimiter` (`id`),
  CONSTRAINT `TetradDataFile_ibfk_3` FOREIGN KEY (`variableTypeId`) REFERENCES `VariableType` (`id`),
  CONSTRAINT `TetradDataFile_ibfk_4` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TetradVariableFile`
--

DROP TABLE IF EXISTS `TetradVariableFile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TetradVariableFile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `numOfVars` int(11) DEFAULT NULL,
  `fileId` bigint(20) NOT NULL,
  `userAccountId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fileId` (`fileId`),
  KEY `userAccountId` (`userAccountId`),
  CONSTRAINT `TetradVariableFile_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `File` (`id`),
  CONSTRAINT `TetradVariableFile_ibfk_2` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `account` varchar(255) NOT NULL,
  `activated` tinyint(1) NOT NULL DEFAULT 0,
  `disabled` tinyint(1) NOT NULL DEFAULT 0,
  `actionKey` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `UserAccountUserRoleRel`
--

DROP TABLE IF EXISTS `UserAccountUserRoleRel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserAccountUserRoleRel` (
  `userAccountId` bigint(20) NOT NULL,
  `userRoleId` bigint(20) NOT NULL,
  PRIMARY KEY (`userAccountId`,`userRoleId`),
  KEY `userRoleId` (`userRoleId`),
  CONSTRAINT `UserAccountUserRoleRel_ibfk_1` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`) ON DELETE CASCADE,
  CONSTRAINT `UserAccountUserRoleRel_ibfk_2` FOREIGN KEY (`userRoleId`) REFERENCES `UserRole` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `UserInformation`
--

DROP TABLE IF EXISTS `UserInformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserInformation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) DEFAULT NULL,
  `middleName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `userAccountId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userAccountId` (`userAccountId`),
  CONSTRAINT `UserInformation_ibfk_1` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `UserLogin`
--

DROP TABLE IF EXISTS `UserLogin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserLogin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `loginDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `loginLocation` bigint(20) DEFAULT NULL,
  `userAccountId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userAccountId` (`userAccountId`),
  CONSTRAINT `UserLogin_ibfk_1` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `UserRegistration`
--

DROP TABLE IF EXISTS `UserRegistration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserRegistration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `registrationDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `registrationLocation` bigint(20) DEFAULT NULL,
  `userAccountId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userAccountId` (`userAccountId`),
  CONSTRAINT `UserRegistration_ibfk_1` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `UserRole`
--

DROP TABLE IF EXISTS `UserRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserRole` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `shortName` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `shortName` (`shortName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `VariableType`
--

DROP TABLE IF EXISTS `VariableType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VariableType` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `shortName` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `shortName` (`shortName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-11 17:23:22
