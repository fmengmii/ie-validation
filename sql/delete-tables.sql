delete from crf;
alter table crf AUTO_INCREMENT = 1;

delete from crf_element;

delete from crf_section;
alter table crf_section AUTO_INCREMENT = 1;

delete from element;
alter table element AUTO_INCREMENT = 1;

delete from element_value;

delete from frame;
alter table frame AUTO_INCREMENT = 1;

delete from frame_section;

delete from frame_slot;

delete from provenance;
alter table provenance AUTO_INCREMENT = 1;

delete from slot;
alter table slot AUTO_INCREMENT = 1;

delete from `value`;
alter table `value` AUTO_INCREMENT = 1;



# project only
delete from project;
alter table project AUTO_INCREMENT = 1;

delete from project_preload;

delete from crf_project;
alter table crf_project AUTO_INCREMENT = 1;

delete from project_frame_instance;

delete from frame_instance;
alter table frame_instance AUTO_INCREMENT = 1;

delete from frame_instance_annotation;

delete from frame_instance_document;



# data only
delete from frame_instance_data;

delete from frame_instance_document_history;

delete from frame_instance_element_repeat;

delete from frame_instance_section_repeat;

delete from annotation;
alter table annotation AUTO_INCREMENT = 1;

delete from frame_instance_status;

delete from document_status;

