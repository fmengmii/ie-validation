<<<<<<< Updated upstream:sql/msa-schema-mysql.sql
=======
CREATE TABLE `project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `project_frame_instance` (
  `project_id` int(11) NOT NULL,
  `frame_instance_id` int(11) NOT NULL,
  PRIMARY KEY (`project_id`,`frame_instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `frame_instance_status` (
  `frame_instance_id` bigint(20) NOT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`frame_instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `document_status` (
  `document_namespace` varchar(500) NOT NULL,
  `document_table` varchar(500) NOT NULL,
  `document_id` bigint(20) NOT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`document_namespace`,`document_table`,`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tablename_lookup` (
  `annotation_type` varchar(500) DEFAULT NULL,
  `table_type` varchar(100) DEFAULT NULL,
  `table_name` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `act` varchar(255) DEFAULT NULL,
  `html_id` varchar(255) DEFAULT NULL,
  `extra_information` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;

CREATE TABLE `validated_document` (
  `document_id` bigint(20) NOT NULL,
  PRIMARY KEY (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

>>>>>>> Stashed changes:sql/annotation-schema-mysql-supplemental.sql
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



CREATE TABLE `index_lungrads` (
  `profile_id` bigint(20) DEFAULT NULL,
  `target_id` bigint(20) DEFAULT NULL,
  `document_id` bigint(20) DEFAULT NULL,
  `start` bigint(20) DEFAULT NULL,
  `end` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE `final` (
  `profile_id` bigint(20) DEFAULT NULL,
  `target_id` bigint(20) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `prec` double DEFAULT NULL,
  `valence` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
<<<<<<< Updated upstream:sql/msa-schema-mysql.sql

=======
>>>>>>> Stashed changes:sql/annotation-schema-mysql-supplemental.sql
