-- MySQL dump 10.15  Distrib 10.0.21-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: ccd
-- ------------------------------------------------------
-- Server version	10.0.21-MariaDB

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
-- Table structure for table `DataFile`
--

DROP TABLE IF EXISTS `DataFile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DataFile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `absolutePath` varchar(255) NOT NULL,
  `creationTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastModifiedTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `fileSize` bigint(20) NOT NULL,
  `dataFileInfoId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dataFileInfoId` (`dataFileInfoId`),
  CONSTRAINT `DataFile_ibfk_1` FOREIGN KEY (`dataFileInfoId`) REFERENCES `DataFileInfo` (`id`)
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
-- Table structure for table `DataFileInfo`
--

DROP TABLE IF EXISTS `DataFileInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DataFileInfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `md5CheckSum` varchar(32) DEFAULT NULL,
  `numOfRows` int(11) DEFAULT NULL,
  `numOfColumns` int(11) DEFAULT NULL,
  `missingValue` tinyint(1) DEFAULT NULL,
  `variableTypeId` bigint(20) DEFAULT NULL,
  `fileDelimiterId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `variableTypeId` (`variableTypeId`),
  KEY `fileDelimiterId` (`fileDelimiterId`),
  CONSTRAINT `DataFileInfo_ibfk_1` FOREIGN KEY (`variableTypeId`) REFERENCES `VariableType` (`id`),
  CONSTRAINT `DataFileInfo_ibfk_2` FOREIGN KEY (`fileDelimiterId`) REFERENCES `FileDelimiter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DataFileInfo`
--

LOCK TABLES `DataFileInfo` WRITE;
/*!40000 ALTER TABLE `DataFileInfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `DataFileInfo` ENABLE KEYS */;
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
-- Table structure for table `JobQueueInfo`
--

DROP TABLE IF EXISTS `JobQueueInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JobQueueInfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `algorName` varchar(255) NOT NULL,
  `commands` text NOT NULL,
  `fileName` varchar(255) NOT NULL,
  `tmpDirectory` varchar(255) NOT NULL,
  `outputDirectory` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `addedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `JobQueueInfo`
--

LOCK TABLES `JobQueueInfo` WRITE;
/*!40000 ALTER TABLE `JobQueueInfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `JobQueueInfo` ENABLE KEYS */;
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
  `middleName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `workspace` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
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
-- Table structure for table `RolePermission`
--

DROP TABLE IF EXISTS `RolePermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RolePermission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RolePermission`
--

LOCK TABLES `RolePermission` WRITE;
/*!40000 ALTER TABLE `RolePermission` DISABLE KEYS */;
/*!40000 ALTER TABLE `RolePermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SecurityAnswer`
--

DROP TABLE IF EXISTS `SecurityAnswer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SecurityAnswer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) NOT NULL,
  `securityQuestionId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `securityQuestionId` (`securityQuestionId`),
  CONSTRAINT `SecurityAnswer_ibfk_1` FOREIGN KEY (`securityQuestionId`) REFERENCES `SecurityQuestion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SecurityAnswer`
--

LOCK TABLES `SecurityAnswer` WRITE;
/*!40000 ALTER TABLE `SecurityAnswer` DISABLE KEYS */;
/*!40000 ALTER TABLE `SecurityAnswer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SecurityQuestion`
--

DROP TABLE IF EXISTS `SecurityQuestion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SecurityQuestion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SecurityQuestion`
--

LOCK TABLES `SecurityQuestion` WRITE;
/*!40000 ALTER TABLE `SecurityQuestion` DISABLE KEYS */;
/*!40000 ALTER TABLE `SecurityQuestion` ENABLE KEYS */;
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
  `publicKey` varchar(592) DEFAULT NULL,
  `privateKey` varchar(448) DEFAULT NULL,
  `active` tinyint(1) NOT NULL,
  `accountId` varchar(255) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastLoginDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `personId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `personId` (`personId`),
  CONSTRAINT `UserAccount_ibfk_1` FOREIGN KEY (`personId`) REFERENCES `Person` (`id`)
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
-- Table structure for table `UserAccountDataFileRel`
--

DROP TABLE IF EXISTS `UserAccountDataFileRel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserAccountDataFileRel` (
  `userAccountId` bigint(20) NOT NULL,
  `dataFileId` bigint(20) NOT NULL,
  PRIMARY KEY (`userAccountId`,`dataFileId`),
  KEY `userAccountId` (`userAccountId`),
  KEY `dataFileId` (`dataFileId`),
  CONSTRAINT `UserAccountDataFileRel_ibfk_1` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`),
  CONSTRAINT `UserAccountDataFileRel_ibfk_2` FOREIGN KEY (`dataFileId`) REFERENCES `DataFile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserAccountDataFileRel`
--

LOCK TABLES `UserAccountDataFileRel` WRITE;
/*!40000 ALTER TABLE `UserAccountDataFileRel` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserAccountDataFileRel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserAccountJobQueueInfoRel`
--

DROP TABLE IF EXISTS `UserAccountJobQueueInfoRel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserAccountJobQueueInfoRel` (
  `userAccountId` bigint(20) NOT NULL,
  `jobQueueInfoId` bigint(20) NOT NULL,
  PRIMARY KEY (`userAccountId`,`jobQueueInfoId`),
  KEY `userAccountId` (`userAccountId`),
  KEY `jobQueueInfoId` (`jobQueueInfoId`),
  CONSTRAINT `UserAccountJobQueueInfoRel_ibfk_1` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`),
  CONSTRAINT `UserAccountJobQueueInfoRel_ibfk_2` FOREIGN KEY (`jobQueueInfoId`) REFERENCES `JobQueueInfo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserAccountJobQueueInfoRel`
--

LOCK TABLES `UserAccountJobQueueInfoRel` WRITE;
/*!40000 ALTER TABLE `UserAccountJobQueueInfoRel` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserAccountJobQueueInfoRel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserAccountSecurityAnswerRel`
--

DROP TABLE IF EXISTS `UserAccountSecurityAnswerRel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserAccountSecurityAnswerRel` (
  `userAccountId` bigint(20) NOT NULL,
  `securityAnswerId` bigint(20) NOT NULL,
  PRIMARY KEY (`userAccountId`,`securityAnswerId`),
  KEY `userAccountId` (`userAccountId`),
  KEY `securityAnswerId` (`securityAnswerId`),
  CONSTRAINT `UserAccountSecurityAnswerRel_ibfk_1` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`),
  CONSTRAINT `UserAccountSecurityAnswerRel_ibfk_2` FOREIGN KEY (`securityAnswerId`) REFERENCES `SecurityAnswer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserAccountSecurityAnswerRel`
--

LOCK TABLES `UserAccountSecurityAnswerRel` WRITE;
/*!40000 ALTER TABLE `UserAccountSecurityAnswerRel` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserAccountSecurityAnswerRel` ENABLE KEYS */;
UNLOCK TABLES;

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
  KEY `userAccountId` (`userAccountId`),
  KEY `userRoleId` (`userRoleId`),
  CONSTRAINT `UserAccountUserRoleRel_ibfk_1` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`),
  CONSTRAINT `UserAccountUserRoleRel_ibfk_2` FOREIGN KEY (`userRoleId`) REFERENCES `UserRole` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserAccountUserRoleRel`
--

LOCK TABLES `UserAccountUserRoleRel` WRITE;
/*!40000 ALTER TABLE `UserAccountUserRoleRel` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserAccountUserRoleRel` ENABLE KEYS */;
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
-- Table structure for table `UserRoleRolePermissionRel`
--

DROP TABLE IF EXISTS `UserRoleRolePermissionRel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserRoleRolePermissionRel` (
  `userRoleId` bigint(20) NOT NULL,
  `rolePermissionId` bigint(20) NOT NULL,
  PRIMARY KEY (`userRoleId`,`rolePermissionId`),
  KEY `userRoleId` (`userRoleId`),
  KEY `rolePermissionId` (`rolePermissionId`),
  CONSTRAINT `UserRoleRolePermissionRel_ibfk_1` FOREIGN KEY (`userRoleId`) REFERENCES `UserRole` (`id`),
  CONSTRAINT `UserRoleRolePermissionRel_ibfk_2` FOREIGN KEY (`rolePermissionId`) REFERENCES `RolePermission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserRoleRolePermissionRel`
--

LOCK TABLES `UserRoleRolePermissionRel` WRITE;
/*!40000 ALTER TABLE `UserRoleRolePermissionRel` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserRoleRolePermissionRel` ENABLE KEYS */;
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-06 11:42:54
