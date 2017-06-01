-- MySQL dump 10.16  Distrib 10.1.21-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: localhost
-- ------------------------------------------------------
-- Server version	10.1.21-MariaDB

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
-- Table structure for table `access`
--

DROP TABLE IF EXISTS `access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `access` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_i9ri07twt8pifah2927kw8vca` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `algorithm_run_log`
--

DROP TABLE IF EXISTS `algorithm_run_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `algorithm_run_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `algo_parameter` text NOT NULL,
  `data_file_summary` text NOT NULL,
  `submit_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `algorithm` varchar(64) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `annotation`
--

DROP TABLE IF EXISTS `annotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `redacted` bit(1) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `access_control` bigint(20) NOT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `target_id` bigint(20) NOT NULL,
  `user_account_id` bigint(20) NOT NULL,
  `vocabulary_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs4b4by304sbmamn6qs5i6vrmt` (`access_control`),
  KEY `FK1y9h92iq0qrw2yadmtmakp78e` (`group_id`),
  KEY `FKeevem0531ery9wxqh2chqel73` (`parent_id`),
  KEY `FK40i7hlc7ffuyi1wffgi8f1iwq` (`target_id`),
  KEY `FKr1rh2xf8gdegogsdgpbbgijhy` (`user_account_id`),
  KEY `FKe2ic87fdomv1ewxcxxhca8csv` (`vocabulary_id`),
  CONSTRAINT `FK1y9h92iq0qrw2yadmtmakp78e` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `FK3xq2f4xjwxiwy9alkteahn0k0` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `FK40i7hlc7ffuyi1wffgi8f1iwq` FOREIGN KEY (`target_id`) REFERENCES `annotation_target` (`id`),
  CONSTRAINT `FK5aphkaxsm3hrjc6f2gh6s7cc4` FOREIGN KEY (`vocabulary_id`) REFERENCES `vocabulary` (`id`),
  CONSTRAINT `FK7hwy1g5myfk7grmm2j7faqggd` FOREIGN KEY (`parent_id`) REFERENCES `annotation` (`id`),
  CONSTRAINT `FK884denh8ypoony2uvt1mj7lq1` FOREIGN KEY (`target_id`) REFERENCES `annotation_target` (`id`),
  CONSTRAINT `FKb3mdi2xxf8j13m5aw71ym0mea` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FKbrfrcxhos88op0ejuka8cnml0` FOREIGN KEY (`access_control`) REFERENCES `access` (`id`),
  CONSTRAINT `FKe2ic87fdomv1ewxcxxhca8csv` FOREIGN KEY (`vocabulary_id`) REFERENCES `vocabulary` (`id`),
  CONSTRAINT `FKeevem0531ery9wxqh2chqel73` FOREIGN KEY (`parent_id`) REFERENCES `annotation` (`id`),
  CONSTRAINT `FKr1rh2xf8gdegogsdgpbbgijhy` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FKs4b4by304sbmamn6qs5i6vrmt` FOREIGN KEY (`access_control`) REFERENCES `access` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `annotation_data`
--

DROP TABLE IF EXISTS `annotation_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `annotation_id` bigint(20) NOT NULL,
  `attribute_id` bigint(20) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmot5xx4bmk6wcqtl6v6y22q7g` (`annotation_id`),
  KEY `FKfqf14sbvddt3oelteros106sw` (`attribute_id`),
  KEY `FKg1o75oc8vwfm856uy13xrfewl` (`parent_id`),
  CONSTRAINT `FKfqf14sbvddt3oelteros106sw` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`id`),
  CONSTRAINT `FKg1o75oc8vwfm856uy13xrfewl` FOREIGN KEY (`parent_id`) REFERENCES `annotation_data` (`id`),
  CONSTRAINT `FKlanbkmouhoad9jejdvg6gdvn` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`id`),
  CONSTRAINT `FKlgu8e5h5vuwp6mjw4utcy19ls` FOREIGN KEY (`annotation_id`) REFERENCES `annotation` (`id`),
  CONSTRAINT `FKmot5xx4bmk6wcqtl6v6y22q7g` FOREIGN KEY (`annotation_id`) REFERENCES `annotation` (`id`),
  CONSTRAINT `FKpeinv3iong1ueh84tfgjbdnxa` FOREIGN KEY (`parent_id`) REFERENCES `annotation_data` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `annotation_target`
--

DROP TABLE IF EXISTS `annotation_target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation_target` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` longtext,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `file_id` bigint(20) DEFAULT NULL,
  `user_account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5hytknmeg31j8j489f6e10c5q` (`file_id`),
  KEY `FKp9l7v6r1n70lad6dos63mbu9` (`user_account_id`),
  CONSTRAINT `FK5hytknmeg31j8j489f6e10c5q` FOREIGN KEY (`file_id`) REFERENCES `data_file` (`id`),
  CONSTRAINT `FKl8xrncvaasts8j7pdil68gd4a` FOREIGN KEY (`file_id`) REFERENCES `data_file` (`id`),
  CONSTRAINT `FKp9l7v6r1n70lad6dos63mbu9` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FKpunef7srnfse8h9r4f05wc2hu` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `annotation_target_references`
--

DROP TABLE IF EXISTS `annotation_target_references`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation_target_references` (
  `annotation_id` bigint(20) NOT NULL,
  `target_id` bigint(20) NOT NULL,
  PRIMARY KEY (`annotation_id`,`target_id`),
  KEY `FK2xsx8gccnq5j548st8nrclht4` (`target_id`),
  CONSTRAINT `FK2xsx8gccnq5j548st8nrclht4` FOREIGN KEY (`target_id`) REFERENCES `annotation_target` (`id`),
  CONSTRAINT `FKa6eblpw5i1bp26kwivdk8hm6x` FOREIGN KEY (`annotation_id`) REFERENCES `annotation` (`id`),
  CONSTRAINT `FKaa1fy5rktvlvxn31d03t4r3jl` FOREIGN KEY (`target_id`) REFERENCES `annotation_target` (`id`),
  CONSTRAINT `FKgrlb0hkuadam1sfbbo3u8b97w` FOREIGN KEY (`annotation_id`) REFERENCES `annotation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `attribute`
--

DROP TABLE IF EXISTS `attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `level` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `requirement_level` varchar(255) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  `vocabulary_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKiryxtpxtp7od24fev3naal2f1` (`vocabulary_id`,`level`,`name`),
  UNIQUE KEY `UKe7xx27l7sww6bqh0srflqup5t` (`vocabulary_id`,`level`,`name`),
  KEY `FKl5lfwq9rcdl0939jiqnufh6em` (`parent`),
  CONSTRAINT `FK13e3fkhxh9ggxdk9h5xidr49b` FOREIGN KEY (`parent`) REFERENCES `attribute` (`id`),
  CONSTRAINT `FK1gnidyaix9e9pe56e64dtv1k` FOREIGN KEY (`vocabulary_id`) REFERENCES `vocabulary` (`id`),
  CONSTRAINT `FKl5lfwq9rcdl0939jiqnufh6em` FOREIGN KEY (`parent`) REFERENCES `attribute` (`id`),
  CONSTRAINT `FKxxinw7chwgsuu5vr1n84w52l` FOREIGN KEY (`vocabulary_id`) REFERENCES `vocabulary` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_file`
--

DROP TABLE IF EXISTS `data_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `absolute_path` varchar(255) NOT NULL,
  `creation_time` datetime NOT NULL,
  `file_size` bigint(20) NOT NULL,
  `last_modified_time` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `data_file_info_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2dydftu088ia3o9methjobma7` (`data_file_info_id`),
  CONSTRAINT `FK7cpp1nci7gbuvmvtgsyhomk33` FOREIGN KEY (`data_file_info_id`) REFERENCES `data_file_info` (`id`),
  CONSTRAINT `FK_2dydftu088ia3o9methjobma7` FOREIGN KEY (`data_file_info_id`) REFERENCES `data_file_info` (`id`),
  CONSTRAINT `FKo7elyoef2pjpg56961sd5row` FOREIGN KEY (`data_file_info_id`) REFERENCES `data_file_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_file_info`
--

DROP TABLE IF EXISTS `data_file_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_file_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `md5check_sum` varchar(32) DEFAULT NULL,
  `missing_value` bit(1) DEFAULT NULL,
  `num_of_columns` int(11) DEFAULT NULL,
  `num_of_rows` int(11) DEFAULT NULL,
  `file_delimiter_id` bigint(20) DEFAULT NULL,
  `variable_type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8h5y84s2x6y7j93hdppd9gqym` (`file_delimiter_id`),
  KEY `FK_5786k6x3wkj4ilkmuvd81i1m4` (`variable_type_id`),
  CONSTRAINT `FK_5786k6x3wkj4ilkmuvd81i1m4` FOREIGN KEY (`variable_type_id`) REFERENCES `variable_type` (`id`),
  CONSTRAINT `FK_8h5y84s2x6y7j93hdppd9gqym` FOREIGN KEY (`file_delimiter_id`) REFERENCES `file_delimiter` (`id`),
  CONSTRAINT `FKamof6wsr21aoap2c7rmx37cb8` FOREIGN KEY (`variable_type_id`) REFERENCES `variable_type` (`id`),
  CONSTRAINT `FKcgamu10jkre36qhe1d1mlgw56` FOREIGN KEY (`variable_type_id`) REFERENCES `variable_type` (`id`),
  CONSTRAINT `FKfisrujwmo8y9wopsqtyavm9oe` FOREIGN KEY (`file_delimiter_id`) REFERENCES `file_delimiter` (`id`),
  CONSTRAINT `FKno9jvpnmu6ly8cmtiy9aapo2r` FOREIGN KEY (`file_delimiter_id`) REFERENCES `file_delimiter` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file_delimiter`
--

DROP TABLE IF EXISTS `file_delimiter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_delimiter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(16) NOT NULL,
  `value` varchar(8) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_membership`
--

DROP TABLE IF EXISTS `group_membership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_membership` (
  `group_id` bigint(20) NOT NULL,
  `user_account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`group_id`,`user_account_id`),
  KEY `FKiuw6bomtdbp27o1i9cf12ihal` (`user_account_id`),
  CONSTRAINT `FK12knve1s81p9bvamf27ydolvq` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FKiuw6bomtdbp27o1i9cf12ihal` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FKous4cdsopr1qhhiowr9qbrhrr` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `FKsc4ykyti548dy9kso0bd3hxf` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_moderation`
--

DROP TABLE IF EXISTS `group_moderation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_moderation` (
  `group_id` bigint(20) NOT NULL,
  `user_account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`group_id`,`user_account_id`),
  KEY `FK91wm2bpk34dq92tpfrt9mhtx7` (`user_account_id`),
  CONSTRAINT `FK91wm2bpk34dq92tpfrt9mhtx7` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FKfej00eff6o1m4a9e9jf9bqx6j` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `FKmsvgp4sklpg3nut7f3dsku5nv` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FKsys1x9ovlamb81hlsj27n1fbf` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_requests`
--

DROP TABLE IF EXISTS `group_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_requests` (
  `group_id` bigint(20) NOT NULL,
  `user_account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`group_id`,`user_account_id`),
  KEY `FK40yj50o0udhyyf3l89fa0h9sp` (`user_account_id`),
  CONSTRAINT `FK3udrg4njg5nsojhnuumis72d7` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK40yj50o0udhyyf3l89fa0h9sp` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK8s9bpf7y2rroo0eac14xsxjsi` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `FKinehq2ar2fympuj5uxl2lv0bj` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` longtext NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8mf0is8024pqmwjxgldfe54l7` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hpc_parameter`
--

DROP TABLE IF EXISTS `hpc_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hpc_parameter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parameter_key` varchar(255) NOT NULL,
  `parameter_value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hpc_parameter_job_queue_info_rel`
--

DROP TABLE IF EXISTS `hpc_parameter_job_queue_info_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hpc_parameter_job_queue_info_rel` (
  `job_queue_info_id` bigint(20) NOT NULL,
  `hpc_parameter_id` bigint(20) NOT NULL,
  PRIMARY KEY (`job_queue_info_id`,`hpc_parameter_id`),
  KEY `FKls7w1cq4xd637u4ms74rvg9l9` (`hpc_parameter_id`),
  CONSTRAINT `FKls7w1cq4xd637u4ms74rvg9l9` FOREIGN KEY (`hpc_parameter_id`) REFERENCES `hpc_parameter` (`id`),
  CONSTRAINT `FKs00obm77h5wigaeshx1nim53s` FOREIGN KEY (`job_queue_info_id`) REFERENCES `job_queue_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `job_queue_info`
--

DROP TABLE IF EXISTS `job_queue_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job_queue_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `added_time` datetime NOT NULL,
  `algor_name` varchar(255) NOT NULL,
  `commands` longtext NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `output_directory` varchar(255) NOT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `tmp_directory` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=172 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_access_token`
--

DROP TABLE IF EXISTS `oauth_access_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_access_token` (
  `authentication_id` varchar(255) NOT NULL,
  `authentication` longblob NOT NULL,
  `client_id` varchar(255) NOT NULL,
  `refresh_token` varchar(255) NOT NULL,
  `token` longblob NOT NULL,
  `token_id` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_client_details`
--

DROP TABLE IF EXISTS `oauth_client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `additional_information` longtext,
  `authorities` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_client_token`
--

DROP TABLE IF EXISTS `oauth_client_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_client_token` (
  `authentication_id` varchar(255) NOT NULL,
  `client_id` varchar(255) NOT NULL,
  `token` longblob NOT NULL,
  `token_id` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_code`
--

DROP TABLE IF EXISTS `oauth_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_code` (
  `id` int(11) NOT NULL,
  `authentication` longblob,
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_refresh_token`
--

DROP TABLE IF EXISTS `oauth_refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(255) NOT NULL,
  `authentication` longblob NOT NULL,
  `token` longblob NOT NULL,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `workspace` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fwmwi44u55bo4rvwsv0cln012` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_answer`
--

DROP TABLE IF EXISTS `security_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `security_answer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) NOT NULL,
  `security_question_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bbyqis9yossit5dsetsjrus9h` (`security_question_id`),
  CONSTRAINT `FK482px3ssoy4xx5spku09ilw6g` FOREIGN KEY (`security_question_id`) REFERENCES `security_question` (`id`),
  CONSTRAINT `FK_bbyqis9yossit5dsetsjrus9h` FOREIGN KEY (`security_question_id`) REFERENCES `security_question` (`id`),
  CONSTRAINT `FKsj66ipd0qcy3n5ipxtcmg6s4n` FOREIGN KEY (`security_question_id`) REFERENCES `security_question` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `security_question`
--

DROP TABLE IF EXISTS `security_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `security_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(255) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_login_date` datetime DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `private_key` longtext,
  `public_key` longtext,
  `username` varchar(255) NOT NULL,
  `person_id` bigint(20) NOT NULL,
  `activation_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`),
  KEY `FK_fqfn7sv6xx80uqwsmnal0tipj` (`person_id`),
  CONSTRAINT `FK9cxlqfb4vfj6538slq1qxns4h` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  CONSTRAINT `FK_fqfn7sv6xx80uqwsmnal0tipj` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  CONSTRAINT `FKk0g4j0im93123k70l6c3cf0qd` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_account_data_file_rel`
--

DROP TABLE IF EXISTS `user_account_data_file_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_data_file_rel` (
  `data_file_id` bigint(20) NOT NULL,
  `user_account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_account_id`,`data_file_id`),
  KEY `FK_r1tl1yq9ki07ud755fcuamx5e` (`data_file_id`),
  CONSTRAINT `FK2d8vch8093x9iwi7hrkml8qj6` FOREIGN KEY (`data_file_id`) REFERENCES `data_file` (`id`),
  CONSTRAINT `FK5juecfmhyccmti70fmnv9a3mn` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK64fujs02t8ejsit9x8xpr9qvr` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_hwqveygcmuswlbyjdt0vr2s0g` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_r1tl1yq9ki07ud755fcuamx5e` FOREIGN KEY (`data_file_id`) REFERENCES `data_file` (`id`),
  CONSTRAINT `FKfx137y43kqp4wsnfnwf4qw69w` FOREIGN KEY (`data_file_id`) REFERENCES `data_file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_account_job_queue_info_rel`
--

DROP TABLE IF EXISTS `user_account_job_queue_info_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_job_queue_info_rel` (
  `job_queue_info_id` bigint(20) NOT NULL,
  `user_account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_account_id`,`job_queue_info_id`),
  KEY `FK_s1mvwws3a418t7vqys6ssp2wd` (`job_queue_info_id`),
  CONSTRAINT `FK3lj4p82tg37f3gb60eisrtyai` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK5mdlmkmgg44rw5sn2gxqsfwkd` FOREIGN KEY (`job_queue_info_id`) REFERENCES `job_queue_info` (`id`),
  CONSTRAINT `FK_43ig88gg8un0fr2hj8e61db8f` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_s1mvwws3a418t7vqys6ssp2wd` FOREIGN KEY (`job_queue_info_id`) REFERENCES `job_queue_info` (`id`),
  CONSTRAINT `FKkj4jcrv3dpnr4xnxqf31gke5p` FOREIGN KEY (`job_queue_info_id`) REFERENCES `job_queue_info` (`id`),
  CONSTRAINT `FKr6jkjs5cyvcs58fvlkqhkesjm` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_account_security_answer_rel`
--

DROP TABLE IF EXISTS `user_account_security_answer_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_security_answer_rel` (
  `security_answer_id` bigint(20) NOT NULL,
  `user_account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_account_id`,`security_answer_id`),
  KEY `FK_m8nsa9d7q06br6j6wo9aua5en` (`security_answer_id`),
  CONSTRAINT `FK21x52yydij0htm4i7usfmap6w` FOREIGN KEY (`security_answer_id`) REFERENCES `security_answer` (`id`),
  CONSTRAINT `FK8hitcpaqhn65n0knuvykcmm55` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_97sjkenl8kap42mpwb7irtvrd` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_m8nsa9d7q06br6j6wo9aua5en` FOREIGN KEY (`security_answer_id`) REFERENCES `security_answer` (`id`),
  CONSTRAINT `FKe5w5iumph4g4rn2yn3d9vrp3m` FOREIGN KEY (`security_answer_id`) REFERENCES `security_answer` (`id`),
  CONSTRAINT `FKq06i69k6pmfs28oa94js0yqys` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_account_user_role_rel`
--

DROP TABLE IF EXISTS `user_account_user_role_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_user_role_rel` (
  `user_role_id` bigint(20) NOT NULL,
  `user_account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_account_id`,`user_role_id`),
  KEY `FK_ekal95wjiy0vo3oxgbtqxbil8` (`user_role_id`),
  CONSTRAINT `FK3pnv8t6omww5p20aft8en2d9j` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`id`),
  CONSTRAINT `FK_ekal95wjiy0vo3oxgbtqxbil8` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`id`),
  CONSTRAINT `FK_gd32yxj7i2v2x33ers880ow91` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FKkiq6221axiynoc060trg5759` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FKo67553d5kq825vuhtqgj9bnfh` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FKtbv12te6qh9fyxilkq5n41kid` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role_role_permission_rel`
--

DROP TABLE IF EXISTS `user_role_role_permission_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role_role_permission_rel` (
  `user_role_id` bigint(20) NOT NULL,
  `role_permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_permission_id`,`user_role_id`),
  KEY `FK_pttd5226iumddrnuiy7re78o` (`user_role_id`),
  CONSTRAINT `FK8d407pweghl1ayu0cvm5hgare` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`id`),
  CONSTRAINT `FK_lkjcutj9iwjus01c6ktv2xs7o` FOREIGN KEY (`role_permission_id`) REFERENCES `role_permission` (`id`),
  CONSTRAINT `FK_pttd5226iumddrnuiy7re78o` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`id`),
  CONSTRAINT `FKhsh46qbwwwuylqh05hl87e74u` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`id`),
  CONSTRAINT `FKhyf6yf30nu062dqarr0jargiy` FOREIGN KEY (`role_permission_id`) REFERENCES `role_permission` (`id`),
  CONSTRAINT `FKlk36a3mum2tpj1b92ts5bp451` FOREIGN KEY (`role_permission_id`) REFERENCES `role_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `variable_type`
--

DROP TABLE IF EXISTS `variable_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `variable_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vocabulary`
--

DROP TABLE IF EXISTS `vocabulary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vocabulary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `description` longtext NOT NULL,
  `modified` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_co0f3gvf5v59bqepck6bemw9r` (`name`),
  UNIQUE KEY `UK_himrbo0p807qmr2se0b67sepj` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-01 15:33:11
