SET FOREIGN_KEY_CHECKS=0;

create database objectdb;
use  objectdb;

CREATE 
    TABLE `dbobject` 
    ( 
        `counter` INT not null AUTO_INCREMENT UNIQUE,
        `client_id` smallint NOT NULL default 0, 
        `type` varchar(5) NOT NULL, 
        `uuid` varchar(32) NOT NULL,
        `version` smallint default 0, 
        `name` varchar(255) default NULL, 
        `created_by` varchar(32) NOT NULL, 
        `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, 
        `modified_by` varchar(32), 
        `modified_on` timestamp null, 
        `status` smallint default 0, 
        `status_modified_on` timestamp null,
        PRIMARY KEY (`uuid`), 
        KEY `idx_dbobject_client_id` (`client_id`), 
        KEY `idx_dbobject_type` (`type`), 
        KEY `idx_dbobject_status` (`status`)
    ) 
    ENGINE= InnoDB DEFAULT CHARSET=utf8;
    
CREATE 
    TABLE `dbobject_metadata` 
    (
        `client_id` smallint NOT NULL default 0,
        `type` varchar(5),
        `uuid` varchar(32) NOT NULL, 
        `name` varchar(255) NOT NULL, 
        `value` mediumtext, 
        `created_by` varchar(32) NOT NULL, 
        `created_on` timestamp NOT NULL default CURRENT_TIMESTAMP, 
        `status` smallint default 0, 
        `status_modified_on` timestamp null,
        PRIMARY KEY (`uuid`,`name`),
        KEY `idx_dbobject_object_metadata_type` (`type`),
        KEY `idx_dbobject_object_metadata_client_id` (`client_id`)
    ) 
    ENGINE= InnoDB DEFAULT CHARSET=utf8;     
    
CREATE 
    TABLE `dbobject_relationship` 
    ( 
        `client_id` smallint NOT NULL default 0,
        `p_uuid` varchar(32) NOT NULL, 
        `c_uuid` varchar(32) NOT NULL, 
        `relationship_type` varchar(32) NOT NULL default '', 
        `sortorder` smallint default '0', 
		`created_by` varchar(32) NOT NULL,         
        `created_on` timestamp NOT NULL default CURRENT_TIMESTAMP, 
        `status` smallint default 0, 
        `status_modified_on` timestamp null,
        PRIMARY KEY (`p_uuid`,`c_uuid`,`relationship_type`), 
        KEY `idx_dbobject_relationship_client_id` (`client_id`),
        KEY `fk_dbobject_relationship_parent` (`p_uuid`), 
        KEY `fk_dbobject_relationship_child` (`c_uuid`), 
        KEY `idx_dbobject_relationship_type` (`relationship_type`) 
    ) 
    ENGINE= InnoDB DEFAULT CHARSET=utf8;         

CREATE 
    TABLE `dbobject_acl` 
    ( 
        `client_id` smallint NOT NULL default 0,
        `principal_uuid` varchar(32) NOT NULL, 
        `uuid` varchar(32) NOT NULL, 
        `permission` varchar(32) NOT NULL, 
        `created_by` varchar(32) NOT NULL, 
        `created_on` timestamp NOT NULL default CURRENT_TIMESTAMP,
        `status` smallint default 0, 
        `status_modified_on` timestamp null,
        PRIMARY KEY (`principal_uuid`,`uuid`), 
        KEY `idx_dbobject_acl_client_id` (`client_id`),
        KEY `idx_dbobject_acl_pricipal_object_permission` (`principal_uuid`, `uuid`,`permission`)
    ) 
    ENGINE= InnoDB DEFAULT CHARSET=utf8; 

CREATE 
    TABLE `dbobject_content_store` 
    ( 
        `client_id` smallint NOT NULL default 0,
        `uuid` varchar(32) NOT NULL, 
        `content_type` varchar(32) NOT NULL, 
        `uri` varchar(255) default NULL, 
        `data` longblob, 
        `created_by` varchar(32) NOT NULL, 
        `created_on` timestamp NOT NULL default CURRENT_TIMESTAMP, 
        `extension` varchar(32) default NULL, 
        `content_size` int default NULL, 
        `content_size_enc` int default NULL, 
        `checksum` varchar(32) default null, 
        `status` smallint default 0, 
        `status_modified_on` timestamp null,
        PRIMARY KEY (`uuid`,`content_type`), 
        KEY `idx_dbobject_content_store_client_id` (`client_id`)
    ) 
    ENGINE= InnoDB DEFAULT CHARSET=utf8; 


CREATE 
    TABLE `dbobject_relationship_metadata` 
    (
        `client_id` smallint NOT NULL default 0,
        `p_uuid` varchar(32) NOT NULL, 
        `c_uuid` varchar(32) NOT NULL, 
        `name` varchar(255) NOT NULL, 
        `value` mediumtext, 
        `created_by` varchar(32) NOT NULL default '', 
        `created_on_` timestamp NOT NULL default CURRENT_TIMESTAMP, 
        `relationship_type` varchar(32) NOT NULL default '0', 
        `status` smallint default 0, 
        `status_modified_on` timestamp null,
        PRIMARY KEY (`p_uuid`,`c_uuid`,`relationship_type`,`name`), 
        KEY `idx_dbobject_relationship_metadata_client_id` (`client_id`)
    ) 
    ENGINE= InnoDB DEFAULT CHARSET=utf8;
    
CREATE 
    TABLE `dbobject_metadata_json` 
    (
        `client_id` smallint NOT NULL default 0,
        `type` varchar(5),
        `uuid` varchar(32) NOT NULL, 
        `property_name` varchar(20) NOT NULL,
        `name` varchar(255) NOT NULL, 
        `value` mediumtext, 
        `created_by` varchar(32) NOT NULL, 
        `created_on` timestamp NOT NULL default CURRENT_TIMESTAMP, 
        `status` smallint default 0, 
        `status_modified_on` timestamp null,
        PRIMARY KEY (`uuid`,`name`),
        KEY `idx_dbobject_object_metadata_json_type` (`type`),
        KEY `idx_dbobject_object_metadata_json_client_id` (`client_id`)
    ) 
    ENGINE= InnoDB DEFAULT CHARSET=utf8; 
    
SET FOREIGN_KEY_CHECKS=1;

INSERT INTO `objectdb`.`dbobject` (`counter`, `client_id`, `type`, `uuid`, `version`, `name`, `created_by`, `created_on`, `modified_by`, `modified_on`, `status`) VALUES ('1', '0', 'USER1', 'USER1-SUPADMIN-FTC1', '0', 'ftcadmin', 'Macys', '2015-09-24 22:02:56', 'null', '2015-09-24 22:02:56', '0');    
INSERT INTO `objectdb`.`dbobject_metadata` (`client_id`, `type`, `uuid`, `name`, `value`, `created_by`, `created_on`, `status`, `status_modified_on`) VALUES ('0', 'USER1', 'USER1-SUPADMIN-FTC1', 'isSuperAdmin', 'true', 'MACYS', '2015-09-24 22:02:57', '0', '2015-09-24 22:02:57');
INSERT INTO `objectdb`.`dbobject_metadata` (`client_id`, `type`, `uuid`, `name`, `value`, `created_by`, `created_on`, `status`, `status_modified_on`) VALUES ('0', 'USER1', 'USER1-SUPADMIN-FTC1', 'password','fZG1Cw1FMeVZQCOcbNaAlRtLDaRA6A5ZVpXkkwIWcF5CkNRaahuKOGEcg2f8SeIP', 'MACYS', '2015-09-24 22:02:57', '0', '2015-09-24 22:02:57');
INSERT INTO `objectdb`.`dbobject_metadata` (`client_id`, `type`, `uuid`, `name`, `value`, `created_by`, `created_on`, `status`, `status_modified_on`) VALUES ('0', 'USER1', 'USER1-SUPADMIN-FTC1', 'userName','ftcadmin', 'MACYS', '2015-09-24 22:02:57', '0', '2015-09-24 22:02:57');

INSERT INTO `objectdb`.`dbobject` (`counter`, `client_id`, `type`, `uuid`, `version`, `name`, `created_by`, `created_on`) VALUES ('0', '0', 'RELSE', 'RELSE-57B91307-A8E6', '0', '15I', 'Macys', '2015-10-01 14:00:17');
INSERT INTO `objectdb`.`dbobject_metadata` (`client_id`, `type`, `uuid`, `name`, `value`, `created_by`, `created_on`, `status`) VALUES ('0', 'RELSE', 'RELSE-57B91307-A8E6', 'bcomDate', '9/10', '', '2015-10-01 14:00:17', '0');
INSERT INTO `objectdb`.`dbobject_metadata` (`client_id`, `type`, `uuid`, `name`, `value`, `created_by`, `created_on`, `status`) VALUES ('0', 'RELSE', 'RELSE-57B91307-A8E6', 'branchCutDate', '8/26', '', '2015-10-01 14:00:17', '0');
INSERT INTO `objectdb`.`dbobject_metadata` (`client_id`, `type`, `uuid`, `name`, `value`, `created_by`, `created_on`, `status`) VALUES ('0', 'RELSE', 'RELSE-57B91307-A8E6', 'branchFreezeDate', '9/10', '', '2015-10-01 14:00:17', '0');
INSERT INTO `objectdb`.`dbobject_metadata` (`client_id`, `type`, `uuid`, `name`, `value`, `created_by`, `created_on`, `status`) VALUES ('0', 'RELSE', 'RELSE-57B91307-A8E6', 'branchHardLockDate', '9/7', '', '2015-10-01 14:00:17', '0');
INSERT INTO `objectdb`.`dbobject_metadata` (`client_id`, `type`, `uuid`, `name`, `value`, `created_by`, `created_on`, `status`) VALUES ('0', 'RELSE', 'RELSE-57B91307-A8E6', 'branchProductionDate', '9/10', '', '2015-10-01 14:00:17', '0');
INSERT INTO `objectdb`.`dbobject_metadata` (`client_id`, `type`, `uuid`, `name`, `value`, `created_by`, `created_on`, `status`) VALUES ('0', 'RELSE', 'RELSE-57B91307-A8E6', 'mcomDate', '9/10', '', '2015-10-01 14:00:17', '0');


   

    