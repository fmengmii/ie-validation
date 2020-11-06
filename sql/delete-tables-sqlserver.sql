-- users
delete from SCHEMA.user;
DBCC CHECKIDENT ("SCHEMA.user", RESEED, 0)

delete from SCHEMA.user_project;


-- crfs only

delete from SCHEMA.crf;
DBCC CHECKIDENT ("SCHEMA.crf", RESEED, 0)

delete from SCHEMA.crf_element;

delete from SCHEMA.crf_section;
DBCC CHECKIDENT ("SCHEMA.crf_section", RESEED, 0)

delete from SCHEMA.element;
DBCC CHECKIDENT ("SCHEMA.element", RESEED, 0)

delete from SCHEMA.element_value;

delete from SCHEMA.frame;
DBCC CHECKIDENT ("SCHEMA.frame", RESEED, 0)

delete from SCHEMA.frame_slot;

delete from SCHEMA.provenance;
DBCC CHECKIDENT ("SCHEMA.provenance", RESEED, 0)

delete from SCHEMA.slot;
DBCC CHECKIDENT ("SCHEMA.slot", RESEED, 0)

delete from SCHEMA.value;
DBCC CHECKIDENT ("SCHEMA.value", RESEED, 0)



-- project only
delete from SCHEMA.project;
DBCC CHECKIDENT ("SCHEMA.project", RESEED, 0)

delete from SCHEMA.project_preload;

delete from SCHEMA.crf_project;
DBCC CHECKIDENT ("SCHEMA.crf_project", RESEED, 0)

delete from SCHEMA.project_frame_instance;


delete from SCHEMA.frame_instance;
DBCC CHECKIDENT ("SCHEMA.frame_instance", RESEED, 0)

delete from SCHEMA.frame_instance_annotation;

delete from SCHEMA.frame_instance_document;

delete from SCHEMA.frame_instance_lock;




-- data only
delete from SCHEMA.frame_instance_data;

delete from SCHEMA.frame_instance_document_history;

delete from SCHEMA.frame_instance_element_repeat;

delete from SCHEMA.frame_instance_section_repeat;

delete from SCHEMA.annotation;

delete from SCHEMA.frame_instance_status;

delete from SCHEMA.document_status;



-- specific project
delete from SCHEMA.frame_instance where frame_instance_id in
(select a.frame_instance_id from SCHEMA.project_frame_instance a, SCHEMA.project b where b.name = ? and a.project_id = b.project_id);

delete from SCHEMA.frame_instance_document where frame_instance_id in
(select a.frame_instance_id from SCHEMA.project_frame_instance a, SCHEMA.project b where b.name = ? and a.project_id = b.project_id);

delete from SCHEMA.project_frame_instance where project_id in
(select a.project_id from SCHEMA.project a where a.name = ?);

delete from SCHEMA.crf_project where project_id in (select a.project_id from SCHEMA.project a where a.name = ?);

delete from SCHEMA.project_preload where project_id in (select a.project_id from SCHEMA.project a where a.name = ?);

delete from SCHEMA.project where name = ?;


