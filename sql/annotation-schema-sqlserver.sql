
DROP TABLE IF EXISTS SCHEMA."annotation";

CREATE TABLE SCHEMA."annotation" (
  "id" int NOT NULL,
  "document_namespace" varchar(500) NOT NULL,
  "document_table" varchar(500) NOT NULL,
  "document_id" bigint NOT NULL,
  "document_name" varchar(100) DEFAULT NULL,
  "annotation_type" varchar(500) DEFAULT NULL,
  "start" int DEFAULT NULL,
  "end" int DEFAULT NULL,
  "value" text,
  "features" text,
  "provenance" varchar(500) DEFAULT NULL,
  "score" float DEFAULT '1',
);


create index "idx_annot_type" on SCHEMA."annotation" ("annotation_type");
create index "idx_annot_start" on SCHEMA."annotation" ("start");
create index "idx_prov" on SCHEMA."annotation" ("provenance");
create index "idx_id" on SCHEMA."annotation" ("id");

  --
  -- Table structure for table "crf"
  --


DROP TABLE IF EXISTS SCHEMA."crf";
  CREATE TABLE SCHEMA."crf" (
    "crf_id" int NOT NULL IDENTITY(1,1),
    "name" varchar(100) NOT NULL,
    "html_id" varchar(500) NOT NULL,
    "frame_id" int DEFAULT NULL,
    PRIMARY KEY ("crf_id"),
  );

  --
  -- Table structure for table "crf_element"
  --

  
  DROP TABLE IF EXISTS SCHEMA."crf_element";
  CREATE TABLE SCHEMA."crf_element" (
    "crf_id" int NOT NULL,
    "element_id" int NOT NULL,
    PRIMARY KEY ("crf_id","element_id")
  );

  --
  -- Table structure for table "crf_project"
  --


DROP TABLE IF EXISTS SCHEMA."crf_project";
CREATE TABLE SCHEMA."crf_project" (
  "crf_project_id" int NOT NULL IDENTITY(1,1),
  "crf_id" int DEFAULT NULL,
  "project_id" int DEFAULT NULL,
  PRIMARY KEY ("crf_project_id")
);

--
-- Table structure for table "crf_section"
--


DROP TABLE IF EXISTS SCHEMA."crf_section";
CREATE TABLE SCHEMA."crf_section" (
  "section_id" int NOT NULL IDENTITY(1,1),
  "name" varchar(500) DEFAULT NULL,
  "crf_id" int NOT NULL,
  "repeat" int DEFAULT NULL,
  PRIMARY KEY ("section_id")
);

--
-- Table structure for table "data_type"
--


DROP TABLE IF EXISTS SCHEMA."data_type";
CREATE TABLE SCHEMA."data_type" (
  "id" int NOT NULL IDENTITY(1,1),
  "name" varchar(500) DEFAULT NULL,
  PRIMARY KEY ("id")
);

INSERT INTO SCHEMA."data_type" (name) VALUES ('number');
INSERT INTO SCHEMA."data_type" (name) VALUES ('string');
INSERT INTO SCHEMA."data_type" (name) VALUES ('date');
INSERT INTO SCHEMA."data_type" (name) VALUES ('categorical');


--
-- Table structure for table "document_status"
--


DROP TABLE IF EXISTS SCHEMA."document_status";
CREATE TABLE SCHEMA."document_status" (
  "document_namespace" varchar(500) NOT NULL,
  "document_table" varchar(500) NOT NULL,
  "document_id" bigint NOT NULL,
  "status" int DEFAULT '0',
  "user_id" int DEFAULT NULL,
  PRIMARY KEY ("document_namespace","document_table","document_id")
);


--
-- Table structure for table "element"
--


DROP TABLE IF EXISTS SCHEMA."element";
CREATE TABLE SCHEMA."element" (
  "element_id" int NOT NULL IDENTITY(1,1),
  "display_name" varchar(500) DEFAULT NULL,
  "html_id" varchar(500) NOT NULL,
  "section_id" int DEFAULT NULL,
  "repeat" int DEFAULT NULL,
  "element_type" int DEFAULT NULL,
  "data_type" int DEFAULT NULL,
  "slot_id" int DEFAULT NULL,
  "primary_key" tinyint DEFAULT NULL,
  PRIMARY KEY ("element_id")
);

--
-- Table structure for table "element_type"
--

DROP TABLE IF EXISTS SCHEMA."element_type";
CREATE TABLE SCHEMA."element_type" (
  "element_type_id" int NOT NULL IDENTITY(1,1),
  "element_type_name" varchar(500) DEFAULT NULL,
  PRIMARY KEY ("element_type_id")
);

INSERT INTO SCHEMA."element_type" (element_type_name) VALUES ('text');
INSERT INTO SCHEMA."element_type" (element_type_name) VALUES ('number');
INSERT INTO SCHEMA."element_type" (element_type_name) VALUES ('radio');
INSERT INTO SCHEMA."element_type" (element_type_name) VALUES ('checkbox');
INSERT INTO SCHEMA."element_type" (element_type_name) VALUES ('date');
INSERT INTO SCHEMA."element_type" (element_type_name) VALUES ('select');
INSERT INTO SCHEMA."element_type" (element_type_name) VALUES ('textarea');

--
-- Table structure for table "element_value"
--


DROP TABLE IF EXISTS SCHEMA."element_value";
CREATE TABLE SCHEMA."element_value" (
  "element_id" int NOT NULL,
  "value_id" int NOT NULL,
  PRIMARY KEY ("element_id","value_id")
);

--
-- Table structure for table "frame"
--


DROP TABLE IF EXISTS SCHEMA."frame";
CREATE TABLE SCHEMA."frame" (
  "frame_id" int NOT NULL IDENTITY(1,1),
  "name" varchar(500) DEFAULT NULL,
  PRIMARY KEY ("frame_id")
);

--
-- Table structure for table "frame_instance"
--


DROP TABLE IF EXISTS SCHEMA."frame_instance";
CREATE TABLE SCHEMA."frame_instance" (
  "frame_instance_id" int NOT NULL IDENTITY(1,1),
  "name" varchar(500) DEFAULT NULL,
  "frame_id" int DEFAULT NULL,
  PRIMARY KEY ("frame_instance_id")
);

--
-- Table structure for table "frame_instance_annotation"
--


DROP TABLE IF EXISTS SCHEMA."frame_instance_annotation";
CREATE TABLE SCHEMA."frame_instance_annotation" (
  "id" int NOT NULL,
  "document_namespace" varchar(500) DEFAULT NULL,
  "document_table" varchar(500) DEFAULT NULL,
  "document_id" bigint DEFAULT NULL,
  "annotation_type" varchar(500) DEFAULT NULL,
  "start" int DEFAULT NULL,
  "end" int DEFAULT NULL,
  "value" text,
  "features" text,
  "provenance" varchar(500) DEFAULT NULL,
  "score" float DEFAULT '1',
    PRIMARY KEY ("id")
  );
  
  --
  -- Table structure for table "frame_instance_data"
  --


  DROP TABLE IF EXISTS SCHEMA."frame_instance_data";
  CREATE TABLE SCHEMA."frame_instance_data" (
    "frame_instance_id" int NOT NULL,
    "slot_id" int NOT NULL,
    "value" text,
    "section_slot_number" int DEFAULT '0',
    "element_slot_number" int DEFAULT '0',
    "document_namespace" varchar(500) DEFAULT NULL,
    "document_table" varchar(500) DEFAULT NULL,
    "document_id" bigint DEFAULT NULL,
    "annotation_id" int DEFAULT NULL,
    "provenance" varchar(500) DEFAULT NULL,
    "element_id" int DEFAULT NULL,
    "v_scroll_pos" int DEFAULT NULL,
    "scroll_height" int DEFAULT NULL,
    "scroll_width" int DEFAULT NULL,
  );

  --
  -- Table structure for table "frame_instance_document"
  --


  DROP TABLE IF EXISTS SCHEMA."frame_instance_document";
  CREATE TABLE SCHEMA."frame_instance_document" (
    "frame_instance_id" int DEFAULT NULL,
    "document_id" bigint DEFAULT NULL,
    "document_table" varchar(500) DEFAULT NULL,
    "document_namespace" varchar(500) DEFAULT NULL,
    "document_key" varchar(500) DEFAULT NULL,
    "document_text_column" varchar(500) DEFAULT NULL,
    "document_name" varchar(500) DEFAULT NULL,
    "document_order" int DEFAULT NULL,
    "document_features" varchar(max) DEFAULT NULL,
    PRIMARY KEY ("frame_instance_id", "document_id")
  );

  
  DROP TABLE IF EXISTS SCHEMA."frame_instance_document_staging";
  CREATE TABLE SCHEMA."frame_instance_document_staging" (
    "frame_instance_id" int DEFAULT NULL,
    "document_id" bigint DEFAULT NULL,
    "document_table" varchar(500) DEFAULT NULL,
    "document_namespace" varchar(500) DEFAULT NULL,
    "document_key" varchar(500) DEFAULT NULL,
    "document_text_column" varchar(500) DEFAULT NULL,
    "document_name" varchar(500) DEFAULT NULL,
    "document_order" int DEFAULT NULL,
    "document_features" varchar(max) DEFAULT NULL,
    "etl_timestamp" datetime DEFAULT NULL,
    PRIMARY KEY ("frame_instance_id", "document_id")
  );
  
  
  --
  -- Table structure for table "frame_instance_document_history"
  --


  DROP TABLE IF EXISTS SCHEMA."frame_instance_document_history";
  CREATE TABLE SCHEMA."frame_instance_document_history" (
    "frame_instance_id" int DEFAULT NULL,
    "document_namespace" varchar(500) DEFAULT NULL,
    "document_table" varchar(500) DEFAULT NULL,
    "document_id" bigint DEFAULT NULL
  );

  --
  -- Table structure for table "frame_instance_element_repeat"
  --


  DROP TABLE IF EXISTS SCHEMA."frame_instance_element_repeat";
  CREATE TABLE SCHEMA."frame_instance_element_repeat" (
    "frame_instance_id" int NOT NULL,
    "element_id" int DEFAULT NULL,
    "section_slot_num" int DEFAULT NULL,
    "repeat_num" int DEFAULT NULL
  );
  
  --
  -- Table structure for table "frame_instance_section_repeat"
  --
 
  
  DROP TABLE IF EXISTS SCHEMA."frame_instance_section_repeat";
  CREATE TABLE SCHEMA."frame_instance_section_repeat" (
    "frame_instance_id" int NOT NULL,
    "section_id" int DEFAULT NULL,
    "repeat_num" int DEFAULT NULL
  );
  
  --
  -- Table structure for table "frame_instance_status"
  --


  DROP TABLE IF EXISTS SCHEMA."frame_instance_status";
  CREATE TABLE SCHEMA."frame_instance_status" (
    "frame_instance_id" bigint NOT NULL,
    "status" int DEFAULT NULL,
    "user_id" int DEFAULT NULL,
    PRIMARY KEY ("frame_instance_id")
  );

  --
  -- Table structure for table "frame_slot"
  --


  DROP TABLE IF EXISTS SCHEMA."frame_slot";
  CREATE TABLE SCHEMA."frame_slot" (
    "frame_id" int NOT NULL,
    "slot_id" int NOT NULL,
    PRIMARY KEY ("frame_id","slot_id")
  );
  
  --
  -- Table structure for table "history"
  --

  DROP TABLE IF EXISTS SCHEMA."history";
  CREATE TABLE SCHEMA."history" (
    "id" int NOT NULL IDENTITY(1,1),
    "act" varchar(255) DEFAULT NULL,
    "html_id" varchar(255) DEFAULT NULL,
    "extra_information" varchar(1000) DEFAULT NULL,
    PRIMARY KEY ("id")
  );

  --
  -- Table structure for table "project"
  --

  
DROP TABLE IF EXISTS SCHEMA."project";
  CREATE TABLE SCHEMA."project" (
    "project_id" int NOT NULL IDENTITY(1,1),
    "name" varchar(500) DEFAULT NULL,
    PRIMARY KEY ("project_id")
  );
  
  -- comment

  --
  -- Table structure for table "project_frame_instance"
  --


  DROP TABLE IF EXISTS SCHEMA."project_frame_instance";
  CREATE TABLE SCHEMA."project_frame_instance" (
    "project_id" int NOT NULL,
    "frame_instance_id" int NOT NULL,
    PRIMARY KEY ("project_id","frame_instance_id")
  );

  --
  -- Table structure for table "provenance"
  --

  DROP TABLE IF EXISTS SCHEMA."provenance";
CREATE TABLE SCHEMA."provenance" (
  "id" int NOT NULL IDENTITY(1,1),
  "name" varchar(500) DEFAULT NULL,
  "priority" int DEFAULT NULL,
  PRIMARY KEY ("id")
);

--
-- Table structure for table "slot"
--


DROP TABLE IF EXISTS SCHEMA."slot";
CREATE TABLE SCHEMA."slot" (
  "slot_id" int NOT NULL IDENTITY(1,1),
  "name" varchar(500) DEFAULT NULL,
  "annotation_type" varchar(100) DEFAULT NULL,
  "slot_type" int NOT NULL,
  "cond" text,
  PRIMARY KEY ("slot_id")
);

--
-- Table structure for table "tablename_lookup"
--


DROP TABLE IF EXISTS SCHEMA."tablename_lookup";
CREATE TABLE SCHEMA."tablename_lookup" (
  "annotation_type" varchar(500) DEFAULT NULL,
  "table_type" varchar(100) DEFAULT NULL,
  "table_name" varchar(500) DEFAULT NULL
);

--
-- Table structure for table "user"
--

DROP TABLE IF EXISTS SCHEMA."user";
CREATE TABLE SCHEMA."user" (
  "user_id" int NOT NULL IDENTITY(1,1),
  "user_name" varchar(500) DEFAULT NULL,
  "project_id" int DEFAULT NULL,
  "frame_instance_id" int DEFAULT NULL,
  "pw" text,
  PRIMARY KEY ("user_id")
);

--
-- Table structure for table "value"
--


DROP TABLE IF EXISTS SCHEMA."value";
CREATE TABLE SCHEMA."value" (
  "value_id" int NOT NULL IDENTITY(1,1),
  "display_name" varchar(500) DEFAULT NULL,
  "slot_id" int DEFAULT NULL,
  "html_id" varchar(500) NOT NULL,
  PRIMARY KEY ("value_id")
);


DROP TABLE IF EXISTS SCHEMA."project_preload";
CREATE TABLE SCHEMA."project_preload" (
  "project_id" int DEFAULT NULL,
  "annotation_type" varchar(500) DEFAULT NULL
);


DROP TABLE IF EXISTS SCHEMA."frame_instance_lock";
CREATE TABLE SCHEMA."frame_instance_lock" (
  "frame_instance_id" int NOT NULL,
  "username" varchar(50) NOT NULL,
  "created_at" datetime DEFAULT getdate(),
  PRIMARY KEY ("frame_instance_id")
);


DROP TABLE IF EXISTS SCHEMA."user_project";
CREATE TABLE SCHEMA."user_project" (
  "user_id" int DEFAULT NULL,
  "project_id" int DEFAULT NULL,
  "frame_instance_id" int DEFAULT NULL
);



DROP TABLE IF EXISTS SCHEMA."annotation_history";
CREATE TABLE SCHEMA."annotation_history" (
  "id" int(11) NOT NULL,
  "document_namespace" varchar(500) NOT NULL,
  "document_table" varchar(500) NOT NULL,
  "document_id" bigint(20) NOT NULL,
  "document_name" varchar(100) DEFAULT NULL,
  "annotation_type" varchar(500) DEFAULT NULL,
  "start" int(11) DEFAULT NULL,
  "end" int(11) DEFAULT NULL,
  "value" varchar(max),
  "features" varchar(max),
  "provenance" varchar(500) DEFAULT NULL,
  "score" double DEFAULT '1',
  "undo_num" int(11) DEFAULT NULL,
  "undo_action" int(11) DEFAULT NULL,
  "user_name" varchar(100) DEFAULT NULL
);


DROP TABLE IF EXISTS SCHEMA."frame_instance_data_history";
CREATE TABLE SCHEMA."frame_instance_data_history" (
  "frame_instance_id" int(11) NOT NULL,
  "slot_id" int(11) NOT NULL,
  "value" varchar(max),
  "section_slot_number" int(11) DEFAULT '0',
  "element_slot_number" int(11) DEFAULT '0',
  "document_namespace" varchar(500) DEFAULT NULL,
  "document_table" varchar(500) DEFAULT NULL,
  "document_id" bigint(20) DEFAULT NULL,
  "annotation_id" int(11) DEFAULT NULL,
  "provenance" varchar(500) DEFAULT NULL,
  "element_id" int(11) DEFAULT NULL,
  "v_scroll_pos" int(11) DEFAULT NULL,
  "scroll_height" int(11) DEFAULT NULL,
  "scroll_width" int(11) DEFAULT NULL,
  "action" int(11) DEFAULT NULL,
  "undo_num" int(11) DEFAULT NULL,
  "user_name" varchar(100) DEFAULT NULL
);


