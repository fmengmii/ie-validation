-- MySQL dump 10.13  Distrib 5.6.5-m8, for osx10.6 (i386)
--
-- Host: localhost    Database: validator
-- ------------------------------------------------------
-- Server version	5.6.5-m8

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
-- Table structure for table `annotation`
--

DROP TABLE IF EXISTS `annotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation` (
  `id` int(11) NOT NULL,
  `document_namespace` varchar(500) NOT NULL,
  `document_table` varchar(500) NOT NULL,
  `document_id` bigint(20) NOT NULL,
  `document_name` varchar(100) DEFAULT NULL,
  `annotation_type` varchar(500) DEFAULT NULL,
  `start` int(11) DEFAULT NULL,
  `end` int(11) DEFAULT NULL,
  `value` text,
  `features` text,
  `provenance` varchar(500) DEFAULT NULL,
  `score` double DEFAULT '1',
  KEY `idx_annot_type` (`annotation_type`(255)),
  KEY `idx_annot_start` (`start`),
  KEY `idx_prov` (`provenance`(100)),
  KEY `idx_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `crf`
--

DROP TABLE IF EXISTS `crf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crf` (
  `crf_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `html_id` varchar(500) NOT NULL,
  `frame_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`crf_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `crf_element`
--

DROP TABLE IF EXISTS `crf_element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crf_element` (
  `crf_id` int(11) NOT NULL,
  `element_id` int(11) NOT NULL,
  PRIMARY KEY (`crf_id`,`element_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `crf_project`
--

DROP TABLE IF EXISTS `crf_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crf_project` (
  `crf_project_id` int(11) NOT NULL AUTO_INCREMENT,
  `crf_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`crf_project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `crf_section`
--

DROP TABLE IF EXISTS `crf_section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crf_section` (
  `section_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `crf_id` int(11) NOT NULL,
  `repeat` int(11) DEFAULT NULL,
  PRIMARY KEY (`section_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_type`
--

DROP TABLE IF EXISTS `data_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `document_status`
--

DROP TABLE IF EXISTS `document_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `document_status` (
  `document_namespace` varchar(500) NOT NULL,
  `document_table` varchar(500) NOT NULL,
  `document_id` bigint(20) NOT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`document_namespace`,`document_table`,`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documents` (
  `document_id` bigint(20) NOT NULL,
  `name` varchar(500) DEFAULT NULL,
  `doc_text` text,
  `date` datetime DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `mrn` int(11) DEFAULT NULL,
  PRIMARY KEY (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `element`
--

DROP TABLE IF EXISTS `element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `element` (
  `element_id` int(11) NOT NULL AUTO_INCREMENT,
  `display_name` varchar(500) DEFAULT NULL,
  `html_id` varchar(500) NOT NULL,
  `section_id` int(11) DEFAULT NULL,
  `repeat` int(11) DEFAULT NULL,
  `element_type` int(11) DEFAULT NULL,
  `data_type` int(11) DEFAULT NULL,
  `slot_id` int(11) DEFAULT NULL,
  `primary_key` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`element_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `element_type`
--

DROP TABLE IF EXISTS `element_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `element_type` (
  `element_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `element_type_name` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`element_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `element_value`
--

DROP TABLE IF EXISTS `element_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `element_value` (
  `element_id` int(11) NOT NULL,
  `value_id` int(11) NOT NULL,
  PRIMARY KEY (`element_id`,`value_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `filter`
--

DROP TABLE IF EXISTS `filter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `filter` (
  `profile_id` bigint(20) NOT NULL,
  `target_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `final`
--

DROP TABLE IF EXISTS `final`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `final` (
  `profile_id` bigint(20) DEFAULT NULL,
  `target_id` bigint(20) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `prec` double DEFAULT NULL,
  `valence` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `frame`
--

DROP TABLE IF EXISTS `frame`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `frame` (
  `frame_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`frame_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `frame_instance`
--

DROP TABLE IF EXISTS `frame_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `frame_instance` (
  `frame_instance_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `frame_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`frame_instance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `frame_instance_annotation`
--

DROP TABLE IF EXISTS `frame_instance_annotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `frame_instance_annotation` (
  `id` int(11) NOT NULL,
  `document_namespace` varchar(500) DEFAULT NULL,
  `document_table` varchar(500) DEFAULT NULL,
  `document_id` int(11) DEFAULT NULL,
  `annotation_type` varchar(500) DEFAULT NULL,
  `start` int(11) DEFAULT NULL,
  `end` int(11) DEFAULT NULL,
  `value` text,
  `features` text,
  `provenance` varchar(500) DEFAULT NULL,
  `score` double DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `frame_instance_data`
--

DROP TABLE IF EXISTS `frame_instance_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `frame_instance_data` (
  `frame_instance_id` int(11) NOT NULL,
  `slot_id` int(11) NOT NULL,
  `value` text,
  `section_slot_number` int(11) DEFAULT '0',
  `element_slot_number` int(11) DEFAULT '0',
  `document_namespace` varchar(500) DEFAULT NULL,
  `document_table` varchar(500) DEFAULT NULL,
  `document_id` int(11) DEFAULT NULL,
  `annotation_id` int(11) DEFAULT NULL,
  `provenance` varchar(500) DEFAULT NULL,
  `element_id` int(11) DEFAULT NULL,
  `v_scroll_pos` int(11) DEFAULT NULL,
  `scroll_height` int(11) DEFAULT NULL,
  `scroll_width` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `frame_instance_document`
--

DROP TABLE IF EXISTS `frame_instance_document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `frame_instance_document` (
  `frame_instance_id` int(11) DEFAULT NULL,
  `document_id` int(11) DEFAULT NULL,
  `document_table` varchar(500) DEFAULT NULL,
  `document_namespace` varchar(500) DEFAULT NULL,
  `document_key` varchar(500) DEFAULT NULL,
  `document_text_column` varchar(500) DEFAULT NULL,
  `document_name` varchar(500) DEFAULT NULL,
  `document_order` int(11) DEFAULT NULL,
  `document_features` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `frame_instance_document_history`
--

DROP TABLE IF EXISTS `frame_instance_document_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `frame_instance_document_history` (
  `frame_instance_id` int(11) DEFAULT NULL,
  `document_namespace` varchar(500) DEFAULT NULL,
  `document_table` varchar(500) DEFAULT NULL,
  `document_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `frame_instance_element_repeat`
--

DROP TABLE IF EXISTS `frame_instance_element_repeat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `frame_instance_element_repeat` (
  `frame_instance_id` int(11) NOT NULL,
  `element_id` int(11) DEFAULT NULL,
  `section_slot_num` int(11) DEFAULT NULL,
  `repeat_num` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `frame_instance_section_repeat`
--

DROP TABLE IF EXISTS `frame_instance_section_repeat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `frame_instance_section_repeat` (
  `frame_instance_id` int(11) NOT NULL,
  `section_id` int(11) DEFAULT NULL,
  `repeat_num` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `frame_instance_status`
--

DROP TABLE IF EXISTS `frame_instance_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `frame_instance_status` (
  `frame_instance_id` bigint(20) NOT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`frame_instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `frame_slot`
--

DROP TABLE IF EXISTS `frame_slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `frame_slot` (
  `frame_id` int(11) NOT NULL,
  `slot_id` int(11) NOT NULL,
  PRIMARY KEY (`frame_id`,`slot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `act` varchar(255) DEFAULT NULL,
  `html_id` varchar(255) DEFAULT NULL,
  `extra_information` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `index_lungrads`
--

DROP TABLE IF EXISTS `index_lungrads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `index_lungrads` (
  `profile_id` bigint(20) DEFAULT NULL,
  `target_id` bigint(20) DEFAULT NULL,
  `document_id` bigint(20) DEFAULT NULL,
  `start` bigint(20) DEFAULT NULL,
  `end` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `profile_id` int(11) NOT NULL AUTO_INCREMENT,
  `group` varchar(500) DEFAULT NULL,
  `profile` text,
  `name` varchar(500) DEFAULT NULL,
  `profile_type` int(11) DEFAULT NULL,
  `annotation_type` varchar(500) DEFAULT NULL,
  `score` double DEFAULT NULL,
  `true_pos` double DEFAULT NULL,
  `false_pos` double DEFAULT NULL,
  `rows` int(11) DEFAULT NULL,
  PRIMARY KEY (`profile_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_frame_instance`
--

DROP TABLE IF EXISTS `project_frame_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_frame_instance` (
  `project_id` int(11) NOT NULL,
  `frame_instance_id` int(11) NOT NULL,
  PRIMARY KEY (`project_id`,`frame_instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `provenance`
--

DROP TABLE IF EXISTS `provenance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provenance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `slot`
--

DROP TABLE IF EXISTS `slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slot` (
  `slot_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `annotation_type` varchar(100) DEFAULT NULL,
  `slot_type` int(11) NOT NULL,
  `cond` text,
  PRIMARY KEY (`slot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=250 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tablename_lookup`
--

DROP TABLE IF EXISTS `tablename_lookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tablename_lookup` (
  `annotation_type` varchar(500) DEFAULT NULL,
  `table_type` varchar(100) DEFAULT NULL,
  `table_name` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(500) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `frame_instance_id` int(11) DEFAULT NULL,
  `pw` text,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `validated_document`
--

DROP TABLE IF EXISTS `validated_document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `validated_document` (
  `document_id` bigint(20) NOT NULL,
  PRIMARY KEY (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `value`
--

DROP TABLE IF EXISTS `value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `value` (
  `value_id` int(11) NOT NULL AUTO_INCREMENT,
  `display_name` varchar(500) DEFAULT NULL,
  `slot_id` int(11) DEFAULT NULL,
  `html_id` varchar(500) NOT NULL,
  PRIMARY KEY (`value_id`)
) ENGINE=InnoDB AUTO_INCREMENT=250 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-02 19:59:32
