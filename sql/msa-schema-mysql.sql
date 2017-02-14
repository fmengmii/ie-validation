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

