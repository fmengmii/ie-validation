


CREATE TABLE SCHEMA."annotation" (
  "id" int NOT NULL,
  "document_namespace" varchar(500) NOT NULL,
  "document_table" varchar(500) NOT NULL,
  "document_id" bigint NOT NULL,
  "document_name" varchar(100) DEFAULT NULL,
  "annotation_type" varchar(500) DEFAULT NULL,
  "start" int DEFAULT NULL,
  "end" int DEFAULT NULL,
  "value" varchar(max),
  "features" varchar(max),
  "provenance" varchar(500) DEFAULT NULL,
  "score" float DEFAULT '1',
);


create index "idx_annot_type" on SCHEMA."annotation" ("annotation_type");
create index "idx_annot_start" on SCHEMA."annotation" ("start");
create index "idx_prov" on SCHEMA."annotation" ("provenance");
create index "idx_id" on SCHEMA."annotation" ("id");
create index "idx_docid_id" on SCHEMA."annotation" ("document_id", "id");
create index "idx_docid_start" on SCHEMA."annotation" ("document_id", "start");
create index "idx_docid_start_end_annottype" on SCHEMA."annotation"	("document_id", "start", "end", "annotation_type");
create index "idx_docnamespace"	on SCHEMA."annotation" ("document_namespace");
create index "idx_doctable" on SCHEMA."annotation" ("document_table");



  --
  -- Table structure for table "crf"
  --



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

  

  CREATE TABLE SCHEMA."crf_element" (
    "crf_id" int NOT NULL,
    "element_id" int NOT NULL,
    PRIMARY KEY ("crf_id","element_id")
  );

  --
  -- Table structure for table "crf_project"
  --



CREATE TABLE SCHEMA."crf_project" (
  "crf_project_id" int NOT NULL IDENTITY(1,1),
  "crf_id" int DEFAULT NULL,
  "project_id" int DEFAULT NULL,
  PRIMARY KEY ("crf_project_id")
);

--
-- Table structure for table "crf_section"
--



CREATE TABLE SCHEMA."crf_section" (
  "section_id" int NOT NULL IDENTITY(1,1),
  "name" varchar(500) DEFAULT NULL,
  "display_name" varchar(500) DEFAULT NULL,
  "crf_id" int NOT NULL,
  "repeat" int DEFAULT NULL,
  PRIMARY KEY ("section_id")
);

--
-- Table structure for table "data_type"
--



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



CREATE TABLE SCHEMA."element_value" (
  "element_id" int NOT NULL,
  "value_id" int NOT NULL,
  PRIMARY KEY ("element_id","value_id")
);

--
-- Table structure for table "frame"
--



CREATE TABLE SCHEMA."frame" (
  "frame_id" int NOT NULL IDENTITY(1,1),
  "name" varchar(500) DEFAULT NULL,
  PRIMARY KEY ("frame_id")
);

--
-- Table structure for table "frame_instance"
--



CREATE TABLE SCHEMA."frame_instance" (
  "frame_instance_id" int NOT NULL IDENTITY(1,1),
  "name" varchar(500) DEFAULT NULL,
  "frame_id" int DEFAULT NULL,
  PRIMARY KEY ("frame_instance_id")
);

--
-- Table structure for table "frame_instance_annotation"
--



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



  CREATE TABLE SCHEMA."frame_instance_data" (
    "frame_instance_id" int NOT NULL,
    "slot_id" int NOT NULL,
    "value" varchar(max),
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
  
  create index "idx_document_id" on SCHEMA."frame_instance_data" ("document_id");
  
  

  --
  -- Table structure for table "frame_instance_document"
  --



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
    "disabled" tinyint DEFAULT NULL,
    PRIMARY KEY ("frame_instance_id", "document_id")
  );
  
  create index "idx_document_id" on SCHEMA."frame_instance_document" ("document_id");


  

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



  CREATE TABLE SCHEMA."frame_instance_document_history" (
    "frame_instance_id" int DEFAULT NULL,
    "document_namespace" varchar(500) DEFAULT NULL,
    "document_table" varchar(500) DEFAULT NULL,
    "document_id" bigint DEFAULT NULL
  );

  --
  -- Table structure for table "frame_instance_element_repeat"
  --



  CREATE TABLE SCHEMA."frame_instance_element_repeat" (
    "frame_instance_id" int NOT NULL,
    "element_id" int DEFAULT NULL,
    "section_slot_num" int DEFAULT NULL,
    "repeat_num" int DEFAULT NULL
  );
  
  --
  -- Table structure for table "frame_instance_section_repeat"
  --
 
  

  CREATE TABLE SCHEMA."frame_instance_section_repeat" (
    "frame_instance_id" int NOT NULL,
    "section_id" int DEFAULT NULL,
    "repeat_num" int DEFAULT NULL
  );
  
  --
  -- Table structure for table "frame_instance_status"
  --



  CREATE TABLE SCHEMA."frame_instance_status" (
    "frame_instance_id" bigint NOT NULL,
    "status" int DEFAULT NULL,
    "user_id" int DEFAULT NULL,
    PRIMARY KEY ("frame_instance_id")
  );

  --
  -- Table structure for table "frame_slot"
  --



  CREATE TABLE SCHEMA."frame_slot" (
    "frame_id" int NOT NULL,
    "slot_id" int NOT NULL,
    PRIMARY KEY ("frame_id","slot_id")
  );
  
  --
  -- Table structure for table "history"
  --


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

  

  CREATE TABLE SCHEMA."project" (
    "project_id" int NOT NULL IDENTITY(1,1),
    "name" varchar(500) DEFAULT NULL,
    PRIMARY KEY ("project_id")
  );
  
  -- comment

  --
  -- Table structure for table "project_frame_instance"
  --



  CREATE TABLE SCHEMA."project_frame_instance" (
    "project_id" int NOT NULL,
    "frame_instance_id" int NOT NULL,
    PRIMARY KEY ("project_id","frame_instance_id")
  );

  create index "idx_frameinstanceid" on SCHEMA."project_frame_instance" ("frame_instance_id");
 
  
  --
  -- Table structure for table "provenance"
  --


CREATE TABLE SCHEMA."provenance" (
  "id" int NOT NULL IDENTITY(1,1),
  "name" varchar(500) DEFAULT NULL,
  "priority" int DEFAULT NULL,
  PRIMARY KEY ("id")
);

--
-- Table structure for table "slot"
--



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



CREATE TABLE SCHEMA."value" (
  "value_id" int NOT NULL IDENTITY(1,1),
  "display_name" varchar(500) DEFAULT NULL,
  "slot_id" int DEFAULT NULL,
  "html_id" varchar(500) NOT NULL,
  PRIMARY KEY ("value_id")
);



CREATE TABLE SCHEMA."project_preload" (
  "project_id" int DEFAULT NULL,
  "value" varchar(500) DEFAULT NULl,
  "type" int DEFAULT NULL,
  "color" varchar(50) NULL
);



CREATE TABLE SCHEMA."frame_instance_lock" (
  "frame_instance_id" int NOT NULL,
  "username" varchar(50) NOT NULL,
  "created_at" datetime DEFAULT getdate(),
  PRIMARY KEY ("frame_instance_id")
);



CREATE TABLE SCHEMA."user_project" (
  "user_id" int DEFAULT NULL,
  "project_id" int DEFAULT NULL,
  "frame_instance_id" int DEFAULT NULL
);




CREATE TABLE SCHEMA."annotation_history" (
  "id" int NOT NULL,
  "document_namespace" varchar(500) NOT NULL,
  "document_table" varchar(500) NOT NULL,
  "document_id" bigint NOT NULL,
  "document_name" varchar(100) DEFAULT NULL,
  "annotation_type" varchar(500) DEFAULT NULL,
  "start" int DEFAULT NULL,
  "end" int DEFAULT NULL,
  "value" varchar(max),
  "features" varchar(max),
  "provenance" varchar(500) DEFAULT NULL,
  "score" float DEFAULT '1',
  "undo_num" int DEFAULT NULL,
  "undo_action" int DEFAULT NULL,
  "user_name" varchar(100) DEFAULT NULL
);

create index "idx_annot_type" on SCHEMA."annotation_history" ("annotation_type");
create index "idx_annot_start`" on SCHEMA."annotation_history" ("start");
create index "idx_prov" on SCHEMA."annotation_history" ("provenance");
create index "idx_id" on SCHEMA."annotation_history" ("id");
create index "idx_annot_doc_id" on SCHEMA."annotation_history" ("document_id");


CREATE TABLE SCHEMA."frame_instance_data_history" (
  "frame_instance_id" int NOT NULL,
  "slot_id" int NOT NULL,
  "value" varchar(max),
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
  "action" int DEFAULT NULL,
  "undo_num" int DEFAULT NULL,
  "user_name" varchar(100) DEFAULT NULL
);


CREATE TABLE SCHEMA."frame_instance_data_history2" (
  "frame_instance_id" int NOT NULL,
  "slot_id" int NOT NULL,
  "value" varchar(max),
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
  "action" int DEFAULT NULL,
  "undo_num" int DEFAULT NULL,
  "user_name" varchar(100) DEFAULT NULL
);



CREATE TABLE SCHEMA."frame_instance_order" (
  "project_id" int NOT NULL,
  "order_num" int NOT NULL,
  "frame_instance_id" int DEFAULT NULL,
  PRIMARY KEY ("order_num","project_id")
)
