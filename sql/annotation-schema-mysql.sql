CREATE TABLE `annotation` (
  `id` int(11) NOT NULL,
  `document_namespace` varchar(500) DEFAULT NULL,
  `document_table` varchar(500) DEFAULT NULL,
  `document_id` bigint DEFAULT NULL,
  `annotation_type` varchar(500) DEFAULT NULL,
  `start` int(11) DEFAULT NULL,
  `end` int(11) DEFAULT NULL,
  `value` text,
  `features` text,
  `provenance` varchar(500) DEFAULT NULL,
  `score` double DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `crf` (
  `crf_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `html_id` varchar(500) NOT NULL,
  `frame_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`crf_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


CREATE TABLE `crf_element` (
  `crf_id` int(11) NOT NULL,
  `element_id` int(11) NOT NULL,
  PRIMARY KEY (`crf_id`,`element_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `crf_project` (
  `crf_project_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `crf_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `document_table` varchar(100) DEFAULT NULL,
  `document_id_column` varchar(100) DEFAULT NULL,
  `document_text_column` varchar(100) DEFAULT NULL,
  `entity_columns` varchar(500) DEFAULT NULL,
  `other_columns` varchar(500) DEFAULT NULL,
  `entity_types` varchar(500) DEFAULT NULL,
  `other_types` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`crf_project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


CREATE TABLE `crf_project_frame_instance` (
  `crf_project_id` int(11) NOT NULL,
  `frame_instance_id` int(11) NOT NULL,
  PRIMARY KEY (`crf_project_id`,`frame_instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `crf_section` (
  `section_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `crf_id` int(11) NOT NULL,
  `repeat` int(11) DEFAULT NULL,
  PRIMARY KEY (`section_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


CREATE TABLE `data_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

insert into data_type (name) values ('number');
insert into data_type (name) values ('string');
insert into data_type (name) values ('date');
insert into data_type (name) values ('categorical');



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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;


CREATE TABLE `element_type` (
  `element_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `element_type_name` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`element_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

insert into element_type (element_type_name) values ('text');
insert into element_type (element_type_name) values ('number');
insert into element_type (element_type_name) values ('radio');
insert into element_type (element_type_name) values ('checkbox');
insert into element_type (element_type_name) values ('date');
insert into element_type (element_type_name) values ('select');
insert into element_type (element_type_name) values ('textarea');



CREATE TABLE `element_value` (
  `element_id` int(11) NOT NULL,
  `value_id` int(11) NOT NULL,
  PRIMARY KEY (`element_id`,`value_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `frame` (
  `frame_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`frame_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


CREATE TABLE `frame_instance` (
  `frame_instance_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `frame_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`frame_instance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=463 DEFAULT CHARSET=utf8;


CREATE TABLE `frame_instance_annotation` (
  `id` int(11) NOT NULL,
  `document_namespace` varchar(500) DEFAULT NULL,
  `document_table` varchar(500) DEFAULT NULL,
  `document_id` bigint DEFAULT NULL,
  `annotation_type` varchar(500) DEFAULT NULL,
  `start` int(11) DEFAULT NULL,
  `end` int(11) DEFAULT NULL,
  `value` text,
  `features` text,
  `provenance` varchar(500) DEFAULT NULL,
  `score` double DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `frame_instance_data` (
  `frame_instance_id` int(11) NOT NULL,
  `slot_id` int(11) NOT NULL,
  `value` text,
  `section_slot_number` int(11) DEFAULT '0',
  `element_slot_number` int(11) DEFAULT '0',
  `document_namespace` varchar(500) DEFAULT NULL,
  `document_table` varchar(500) DEFAULT NULL,
  `document_id` bigint DEFAULT NULL,
  `annotation_id` int(11) DEFAULT NULL,
  `annotation_namespace` varchar(500) DEFAULT NULL,
  `element_id` int(11) DEFAULT NULL,
  `v_scroll_pos` int(11) DEFAULT NULL,
  `scroll_height` int(11) DEFAULT NULL,
  `scroll_width` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `frame_instance_document` (
  `frame_instance_id` int(11) DEFAULT NULL,
  `document_id` bigint DEFAULT NULL,
  `document_table` varchar(500) DEFAULT NULL,
  `document_namespace` varchar(500) DEFAULT NULL,
  `document_key` varchar(500) DEFAULT NULL,
  `document_text_column` varchar(500) DEFAULT NULL,
  `document_name` varchar(500) DEFAULT NULL,
  `document_order` int(11) DEFAULT NULL,
  `document_features` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `frame_instance_document_history` (
  `frame_instance_id` int(11) DEFAULT NULL,
  `document_namespace` varchar(500) DEFAULT NULL,
  `document_table` varchar(500) DEFAULT NULL,
  `document_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `frame_instance_element_repeat` (
  `frame_instance_id` int(11) NOT NULL,
  `element_id` int(11) DEFAULT NULL,
  `section_slot_num` int(11) DEFAULT NULL,
  `repeat_num` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `frame_instance_section_repeat` (
  `frame_instance_id` int(11) NOT NULL,
  `section_id` int(11) DEFAULT NULL,
  `repeat_num` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `frame_slot` (
  `frame_id` int(11) NOT NULL,
  `slot_id` int(11) NOT NULL,
  PRIMARY KEY (`frame_id`,`slot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `provenance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


CREATE TABLE `slot` (
  `slot_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `annotation_type` varchar(100) DEFAULT NULL,
  `slot_type` int(11) NOT NULL,
  `cond` text,
  PRIMARY KEY (`slot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;


CREATE TABLE `value` (
  `value_id` int(11) NOT NULL AUTO_INCREMENT,
  `display_name` varchar(500) DEFAULT NULL,
  `slot_id` int(11) DEFAULT NULL,
  `html_id` varchar(500) NOT NULL,
  PRIMARY KEY (`value_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
	`user_id` int(11) DEFAULT NULL,
	`user_name` varchar(500) DEFAULT NULL,
	`project_id` int(11) DEFAULT NULL,
	`frame_instance_id` int(11) DEFAULT NULL,
	`pw` text DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

