# users
delete from SCHEMA.`user`;
alter table SCHEMA.`user` AUTO_INCREMENT = 1;

delete from SCHEMA.user_project;



# crf and frames
delete from SCHEMA.crf;
alter table SCHEMA.crf AUTO_INCREMENT = 1;

delete from SCHEMA.crf_element;

delete from SCHEMA.crf_section;
alter table SCHEMA.crf_section AUTO_INCREMENT = 1;

delete from SCHEMA.element;
alter table SCHEMA.element AUTO_INCREMENT = 1;

delete from SCHEMA.element_value;

delete from SCHEMA.frame;
alter table SCHEMA.frame AUTO_INCREMENT = 1;

delete from SCHEMA.frame_slot;

delete from SCHEMA.provenance;
alter table SCHEMA.provenance AUTO_INCREMENT = 1;

delete from SCHEMA.slot;
alter table SCHEMA.slot AUTO_INCREMENT = 1;

delete from SCHEMA.`value`;
alter table SCHEMA.`value` AUTO_INCREMENT = 1;



# project only
delete from SCHEMA.project;
alter table SCHEMA.project AUTO_INCREMENT = 1;

delete from SCHEMA.project_preload;

delete from SCHEMA.crf_project;
alter table SCHEMA.crf_project AUTO_INCREMENT = 1;

delete from SCHEMA.project_frame_instance;

delete from SCHEMA.frame_instance;
alter table SCHEMA.frame_instance AUTO_INCREMENT = 1;

delete from SCHEMA.frame_instance_annotation;

delete from SCHEMA.frame_instance_document;

delete from SCHEMA.frame_instance_lock;



# data only
delete from SCHEMA.frame_instance_data;

delete from SCHEMA.frame_instance_data_history;

delete from SCHEMA.frame_instance_document_history;

delete from SCHEMA.frame_instance_element_repeat;

delete from SCHEMA.frame_instance_section_repeat;

delete from SCHEMA.annotation;

delete from SCHEMA.frame_instance_status;

delete from SCHEMA.document_status;


#  specific project
delete from SCHEMA.frame_instance where frame_instance_id in
(select a.frame_instance_id from SCHEMA.project_frame_instance a, SCHEMA.project b where b.name = ? and a.project_id = b.project_id);

delete from SCHEMA.frame_instance_document where frame_instance_id in
(select a.frame_instance_id from SCHEMA.project_frame_instance a, SCHEMA.project b where b.name = ? and a.project_id = b.project_id);

delete from SCHEMA.project_frame_instance where project_id in
(select a.project_id from SCHEMA.project a where a.name = ?);

delete from SCHEMA.crf_project where project_id in (select a.project_id from SCHEMA.project a where a.name = ?);

delete from SCHEMA.project_preload where project_id in (select a.project_id from SCHEMA.project a where a.name = ?);

delete from SCHEMA.project where name = ?;


