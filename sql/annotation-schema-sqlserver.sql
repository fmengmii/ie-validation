CREATE TABLE validator.annotation (
  id int NOT NULL,
  document_namespace varchar(500) DEFAULT NULL,
  document_table varchar(500) DEFAULT NULL,
  document_id bigint DEFAULT NULL,
  annotation_type varchar(500) DEFAULT NULL,
  start int DEFAULT NULL,
  "end" int DEFAULT NULL,
  value text,
  features text,
  provenance varchar(500) DEFAULT NULL,
  score float DEFAULT 1,
  PRIMARY KEY (id)
)


CREATE TABLE validator.crf (
  crf_id int not null IDENTITY(1,1),
  name varchar(100) NOT NULL,
  html_id varchar(500) NOT NULL,
  frame_id int DEFAULT NULL,
  PRIMARY KEY (crf_id),
  CONSTRAINT AK_name UNIQUE(name)
)  


CREATE TABLE validator.crf_element (
  crf_id int NOT NULL,
  element_id int NOT NULL,
  PRIMARY KEY (crf_id,element_id)
) 



CREATE TABLE validator.crf_project (
  crf_project_id int NOT NULL IDENTITY(1,1),
  name varchar(500) DEFAULT NULL,
  crf_id int DEFAULT NULL,
  [document_table] [varchar](100) NULL,
  [document_id_column] [varchar](100) NULL,
  [document_text_column] [varchar](100) NULL,
  entity_columns varchar(500) NULL,
  other_columns varchar(500) NULL,
  entity_types varchar(500) NULL,
  other_tpyes varchar(500) NULL
  PRIMARY KEY (crf_project_id)
) 


CREATE TABLE validator.crf_project_frame_instance (
  crf_project_id int NOT NULL,
  frame_instance_id int NOT NULL,
  PRIMARY KEY (crf_project_id,frame_instance_id)
) 


CREATE TABLE validator.crf_section (
  section_id int NOT NULL IDENTITY(1,1),
  name varchar(500) DEFAULT NULL,
  crf_id int NOT NULL,
  repeat int DEFAULT NULL,
  PRIMARY KEY (section_id)
) 



CREATE TABLE validator.data_type (
  id int NOT NULL IDENTITY(1,1),
  name varchar(500) DEFAULT NULL,
  PRIMARY KEY (id)
) 



CREATE TABLE validator.element (
  element_id int NOT NULL IDENTITY(1,1),
  display_name varchar(500) DEFAULT NULL,
  html_id varchar(500) NOT NULL,
  section_id int DEFAULT NULL,
  repeat int DEFAULT NULL,
  element_type int DEFAULT NULL,
  data_type int DEFAULT NULL,
  slot_id int DEFAULT NULL,
  primary_key tinyint DEFAULT NULL,
  PRIMARY KEY (element_id)
)



CREATE TABLE validator.element_type (
  element_type_id int NOT NULL IDENTITY(1,1),
  element_type_name varchar(500) DEFAULT NULL,
  PRIMARY KEY (element_type_id)
) 



CREATE TABLE validator.element_value (
  element_id int NOT NULL,
  value_id int NOT NULL,
  PRIMARY KEY (element_id,value_id)
) 


CREATE TABLE validator.frame (
  frame_id int NOT NULL IDENTITY(1,1),
  name varchar(500) DEFAULT NULL,
  PRIMARY KEY (frame_id)
) 



CREATE TABLE validator.frame_instance (
  frame_instance_id int NOT NULL IDENTITY(1,1),
  name varchar(500) DEFAULT NULL,
  frame_id int DEFAULT NULL,
  PRIMARY KEY (frame_instance_id)
) 



CREATE TABLE validator.frame_instance_annotation (
  id int NOT NULL,
  document_namespace varchar(500) DEFAULT NULL,
  document_table varchar(500) DEFAULT NULL,
  document_id bigint DEFAULT NULL,
  annotation_type varchar(500) DEFAULT NULL,
  start int DEFAULT NULL,
  end int DEFAULT NULL,
  value text,
  features text,
  provenance varchar(500) DEFAULT NULL,
  score float DEFAULT '1',
  PRIMARY KEY (id)
) 


CREATE TABLE [validator].[frame_instance_data](
	[frame_instance_id] [int] NOT NULL,
	[slot_id] [int] NOT NULL,
	[value] [text] NULL,
	[section_slot_number] [int] NULL,
	[element_slot_number] [int] NULL,
	[document_namespace] [varchar](500) NULL,
	[document_table] [varchar](500) NULL,
	[annotation_id] [int] NULL,
	[annotation_namespace] [varchar](500) NULL,
	[element_id] [int] NULL,
	[v_scroll_pos] [int] NULL,
	[document_id] [bigint] NULL,
	[scroll_height] [int] NULL,
	[scroll_width] [int] NULL
)



CREATE TABLE validator.frame_instance_document (
  frame_instance_id int DEFAULT NULL,
  document_id bigint DEFAULT NULL,
  document_table varchar(500) DEFAULT NULL,
  document_namespace varchar(500) DEFAULT NULL,
  document_key varchar(500) DEFAULT NULL,
  document_text_column varchar(500) DEFAULT NULL,
  document_name varchar(500) DEFAULT NULL,
  document_order int DEFAULT NULL,
  document_features text DEFAULT NULL
) 


CREATE TABLE [validator].[frame_instance_document_history](
	[frame_instance_id] [int] NOT NULL,
	[document_namespace] [varchar](500) NULL,
	[document_table] [varchar](500) NULL,
	[document_id] [bigint] NULL
)



CREATE TABLE validator.frame_instance_section_repeat (
  frame_instance_id int NOT NULL,
  section_id int DEFAULT NULL,
  repeat_num int DEFAULT NULL
) 


CREATE TABLE [validator].[frame_instance_element_repeat](
	[frame_instance_id] [int] NOT NULL,
	[element_id] [int] NULL,
	[repeat_num] [int] NULL,
	section_slot_num int
)


CREATE TABLE validator.frame_slot (
  frame_id int NOT NULL,
  slot_id int NOT NULL,
  PRIMARY KEY (frame_id,slot_id)
) 


CREATE TABLE validator.provenance (
  id int NOT NULL IDENTITY(1,1),
  name varchar(500) DEFAULT NULL,
  priority int DEFAULT NULL,
  PRIMARY KEY (id)
) 



CREATE TABLE validator.slot (
  slot_id int NOT NULL IDENTITY(1,1),
  name varchar(500) DEFAULT NULL,
  annotation_type varchar(100) DEFAULT NULL,
  slot_type int NOT NULL,
  cond text,
  PRIMARY KEY (slot_id)
) 



CREATE TABLE validator.value (
  value_id int NOT NULL IDENTITY(1,1),
  display_name varchar(500) DEFAULT NULL,
  slot_id int DEFAULT NULL,
  html_id varchar(500) NOT NULL,
  PRIMARY KEY (value_id)
) 


CREATE TABLE [validator].[user](
	[user_name] [varchar](500) NULL,
	[project_id] [int] NULL,
	[frame_instance_id] [int] NULL,
	[user_id] [int] IDENTITY(1,1) NOT NULL,
	[pw] [varchar](500) NULL
	PRIMARY KEY (user_id)
)




--only delete annotation when necessary
delete from validator.annotation;
dbcc checkident('validator.crf', reseed, 0);


--clear CRF information
delete from validator.crf;
dbcc checkident('validator.crf', reseed, 0);
delete from validator.crf_element;
dbcc checkident('validator.crf_element', reseed, 0);
delete from validator.crf_project;
dbcc checkident('validator.crf_project', reseed, 0);
delete from validator.crf_project_frame_instance;
dbcc checkident('validator.crf_project_frame_instance', reseed, 0);
delete from validator.crf_section;
dbcc checkident('validator.crf_section', reseed, 0);
delete from validator.element;
dbcc checkident('validator.element', reseed, 0);
delete from validator.element_value;
dbcc checkident('validator.element_value', reseed, 0);
delete from validator.frame;
dbcc checkident('validator.frame', reseed, 0);
delete from validator.frame_slot;
dbcc checkident('validator.frame_slot', reseed, 0);
delete from validator.provenance;
dbcc checkident('validator.provenance', reseed, 0);
delete from validator.slot;
dbcc checkident('validator.slot', reseed, 0);
delete from validator.value;
dbcc checkident('validator.value', reseed, 0);


--clear project information
delete from validator.frame_instance;
dbcc checkident('validator.frame_instance', reseed, 0);
delete from validator.frame_instance_annotation;
dbcc checkident('validator.frame_instance_annotation', reseed, 0);
delete from validator.frame_instance_data;
dbcc checkident('validator.frame_instance_data', reseed, 0);
delete from validator.frame_instance_document;
dbcc checkident('validator.frame_instance_document', reseed, 0);
delete from validator.frame_instance_document_history;
dbcc checkident('validator.frame_instance_document', reseed, 0);
delete from validator.frame_instance_element_repeat;
dbcc checkident('validator.frame_instance_section_repeat', reseed, 0);
delete from validator.frame_instance_section_repeat;
dbcc checkident('validator.frame_instance_section_repeat', reseed, 0);
