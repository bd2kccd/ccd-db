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
  `absolutePath` varchar(255) NOT NULL,
  `creationTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fileSize` bigint(20) NOT NULL,
  `md5CheckSum` varchar(32) DEFAULT NULL,
  `fileTypeId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fileTypeId` (`fileTypeId`),
  CONSTRAINT `File_ibfk_1` FOREIGN KEY (`fileTypeId`) REFERENCES `FileType` (`id`)
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
-- Table structure for table `Person`
--

DROP TABLE IF EXISTS `Person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `middle_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
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
  `registration_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `registration_location` bigint(20) DEFAULT NULL,
  `account` varchar(255) NOT NULL,
  `activation_key` varchar(255) DEFAULT NULL,
  `user_login_id` bigint(20) NOT NULL,
  `user_login_attempt_id` bigint(20) NOT NULL,
  `person_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `user_login_id` (`user_login_id`),
  KEY `user_login_attempt_id` (`user_login_attempt_id`),
  KEY `person_id` (`person_id`),
  CONSTRAINT `UserAccount_ibfk_1` FOREIGN KEY (`user_login_id`) REFERENCES `UserLogin` (`id`),
  CONSTRAINT `UserAccount_ibfk_2` FOREIGN KEY (`user_login_attempt_id`) REFERENCES `UserLoginAttempt` (`id`),
  CONSTRAINT `UserAccount_ibfk_3` FOREIGN KEY (`person_id`) REFERENCES `Person` (`id`)
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
-- Table structure for table `UserAccountFileRel`
--

DROP TABLE IF EXISTS `UserAccountFileRel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserAccountFileRel` (
  `userAccountId` bigint(20) NOT NULL,
  `fileId` bigint(20) NOT NULL,
  PRIMARY KEY (`userAccountId`,`fileId`),
  KEY `userAccountId` (`userAccountId`),
  KEY `fileId` (`fileId`),
  CONSTRAINT `UserAccountFileRel_ibfk_1` FOREIGN KEY (`userAccountId`) REFERENCES `UserAccount` (`id`),
  CONSTRAINT `UserAccountFileRel_ibfk_2` FOREIGN KEY (`fileId`) REFERENCES `File` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserAccountFileRel`
--

LOCK TABLES `UserAccountFileRel` WRITE;
/*!40000 ALTER TABLE `UserAccountFileRel` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserAccountFileRel` ENABLE KEYS */;
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
-- Table structure for table `UserLogin`
--

DROP TABLE IF EXISTS `UserLogin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserLogin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_date` timestamp NULL DEFAULT NULL,
  `login_location` bigint(20) DEFAULT NULL,
  `last_login_date` timestamp NULL DEFAULT NULL,
  `last_login_location` bigint(20) DEFAULT NULL,
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
  `attempt_date` timestamp NULL DEFAULT NULL,
  `attempt_location` bigint(20) DEFAULT NULL,
  `attempt_count` tinyint(4) DEFAULT NULL,
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

-- Dump completed on 2016-07-11 14:11:24
