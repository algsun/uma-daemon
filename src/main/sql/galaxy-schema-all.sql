/*
SQLyog Ultimate v11.11 (32 bit)
MySQL - 5.5.29 : Database - galaxy_test
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `ACT_GE_BYTEARRAY` */

DROP TABLE IF EXISTS `ACT_GE_BYTEARRAY`;

CREATE TABLE `ACT_GE_BYTEARRAY` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `ACT_RE_DEPLOYMENT` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_GE_PROPERTY` */

DROP TABLE IF EXISTS `ACT_GE_PROPERTY`;

CREATE TABLE `ACT_GE_PROPERTY` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_HI_ACTINST` */

DROP TABLE IF EXISTS `ACT_HI_ACTINST`;

CREATE TABLE `ACT_HI_ACTINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_HI_ATTACHMENT` */

DROP TABLE IF EXISTS `ACT_HI_ATTACHMENT`;

CREATE TABLE `ACT_HI_ATTACHMENT` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_HI_COMMENT` */

DROP TABLE IF EXISTS `ACT_HI_COMMENT`;

CREATE TABLE `ACT_HI_COMMENT` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_HI_DETAIL` */

DROP TABLE IF EXISTS `ACT_HI_DETAIL`;

CREATE TABLE `ACT_HI_DETAIL` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_HI_PROCINST` */

DROP TABLE IF EXISTS `ACT_HI_PROCINST`;

CREATE TABLE `ACT_HI_PROCINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  UNIQUE KEY `ACT_UNIQ_HI_BUS_KEY` (`PROC_DEF_ID_`,`BUSINESS_KEY_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_HI_TASKINST` */

DROP TABLE IF EXISTS `ACT_HI_TASKINST`;

CREATE TABLE `ACT_HI_TASKINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `CLAIM_TIME_` datetime DEFAULT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime DEFAULT NULL,
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_HI_VARINST` */

DROP TABLE IF EXISTS `ACT_HI_VARINST`;

CREATE TABLE `ACT_HI_VARINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_ID_GROUP` */

DROP TABLE IF EXISTS `ACT_ID_GROUP`;

CREATE TABLE `ACT_ID_GROUP` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_ID_INFO` */

DROP TABLE IF EXISTS `ACT_ID_INFO`;

CREATE TABLE `ACT_ID_INFO` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_ID_MEMBERSHIP` */

DROP TABLE IF EXISTS `ACT_ID_MEMBERSHIP`;

CREATE TABLE `ACT_ID_MEMBERSHIP` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `ACT_ID_USER` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `ACT_ID_GROUP` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_ID_USER` */

DROP TABLE IF EXISTS `ACT_ID_USER`;

CREATE TABLE `ACT_ID_USER` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_RE_DEPLOYMENT` */

DROP TABLE IF EXISTS `ACT_RE_DEPLOYMENT`;

CREATE TABLE `ACT_RE_DEPLOYMENT` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOY_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_RE_MODEL` */

DROP TABLE IF EXISTS `ACT_RE_MODEL`;

CREATE TABLE `ACT_RE_MODEL` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp NULL DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `ACT_RE_DEPLOYMENT` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_RE_PROCDEF` */

DROP TABLE IF EXISTS `ACT_RE_PROCDEF`;

CREATE TABLE `ACT_RE_PROCDEF` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_RU_EVENT_SUBSCR` */

DROP TABLE IF EXISTS `ACT_RU_EVENT_SUBSCR`;

CREATE TABLE `ACT_RU_EVENT_SUBSCR` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_RU_EXECUTION` */

DROP TABLE IF EXISTS `ACT_RU_EXECUTION`;

CREATE TABLE `ACT_RU_EXECUTION` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_RU_BUS_KEY` (`PROC_DEF_ID_`,`BUSINESS_KEY_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_RU_IDENTITYLINK` */

DROP TABLE IF EXISTS `ACT_RU_IDENTITYLINK`;

CREATE TABLE `ACT_RU_IDENTITYLINK` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `ACT_RU_TASK` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_RU_JOB` */

DROP TABLE IF EXISTS `ACT_RU_JOB`;

CREATE TABLE `ACT_RU_JOB` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_RU_TASK` */

DROP TABLE IF EXISTS `ACT_RU_TASK`;

CREATE TABLE `ACT_RU_TASK` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DUE_DATE_` datetime DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ACT_RU_VARIABLE` */

DROP TABLE IF EXISTS `ACT_RU_VARIABLE`;

CREATE TABLE `ACT_RU_VARIABLE` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `h_car` */

DROP TABLE IF EXISTS `h_car`;

CREATE TABLE `h_car` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exhibitionId` int(11) DEFAULT NULL,
  `plateNumber` varchar(10) DEFAULT NULL COMMENT '车牌号',
  `director` varchar(20) DEFAULT NULL COMMENT '责任人',
  `directorPhone` varchar(11) DEFAULT NULL COMMENT '责任人联系方式',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_94` (`exhibitionId`),
  CONSTRAINT `FK_Reference_94` FOREIGN KEY (`exhibitionId`) REFERENCES `h_exhibition` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `h_config` */

DROP TABLE IF EXISTS `h_config`;

CREATE TABLE `h_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exhibitionId` int(11) DEFAULT NULL,
  `alarmRange` int(11) DEFAULT NULL COMMENT '报警范围',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_85` (`exhibitionId`),
  CONSTRAINT `FK_Reference_85` FOREIGN KEY (`exhibitionId`) REFERENCES `h_exhibition` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `h_device` */

DROP TABLE IF EXISTS `h_device`;

CREATE TABLE `h_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `carId` int(11) DEFAULT NULL,
  `deviceType` int(11) DEFAULT NULL COMMENT '1:摄像机设备。2：传感设备',
  `deviceId` varchar(22) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_92` (`carId`),
  CONSTRAINT `FK_Reference_92` FOREIGN KEY (`carId`) REFERENCES `h_car` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `h_exhibition` */

DROP TABLE IF EXISTS `h_exhibition`;

CREATE TABLE `h_exhibition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `startTime` datetime DEFAULT NULL COMMENT '外展记录创建时间',
  `endTime` datetime DEFAULT NULL COMMENT '结束外展时间',
  `outEventId` varchar(20) DEFAULT NULL COMMENT '出库事件记录ID',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_90` (`outEventId`),
  CONSTRAINT `FK_Reference_90` FOREIGN KEY (`outEventId`) REFERENCES `o_out_event` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `h_exhibition_state` */

DROP TABLE IF EXISTS `h_exhibition_state`;

CREATE TABLE `h_exhibition_state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exhibitionId` int(11) DEFAULT NULL COMMENT '外展ID',
  `state` int(11) DEFAULT NULL COMMENT '1.上车\n            2.下车',
  `changeTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_87` (`exhibitionId`),
  CONSTRAINT `FK_Reference_87` FOREIGN KEY (`exhibitionId`) REFERENCES `h_exhibition` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `h_packing_list` */

DROP TABLE IF EXISTS `h_packing_list`;

CREATE TABLE `h_packing_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence` int(11) DEFAULT NULL,
  `carId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_93` (`carId`),
  CONSTRAINT `FK_Reference_93` FOREIGN KEY (`carId`) REFERENCES `h_car` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `h_packing_relic` */

DROP TABLE IF EXISTS `h_packing_relic`;

CREATE TABLE `h_packing_relic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `packingId` int(11) DEFAULT NULL,
  `totalCode` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_84` (`packingId`),
  CONSTRAINT `FK_Reference_84` FOREIGN KEY (`packingId`) REFERENCES `h_packing_list` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `h_path` */

DROP TABLE IF EXISTS `h_path`;

CREATE TABLE `h_path` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exhibitionId` int(11) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `dataType` int(11) DEFAULT NULL COMMENT '1：起点\n            2：终点\n            3：中间点',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_88` (`exhibitionId`),
  CONSTRAINT `FK_Reference_88` FOREIGN KEY (`exhibitionId`) REFERENCES `h_exhibition` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线路预设点坐标';

/*Table structure for table `h_user` */

DROP TABLE IF EXISTS `h_user`;

CREATE TABLE `h_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exhibitionId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_86` (`exhibitionId`),
  CONSTRAINT `FK_Reference_86` FOREIGN KEY (`exhibitionId`) REFERENCES `h_exhibition` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `log_transfer` */

DROP TABLE IF EXISTS `log_transfer`;

CREATE TABLE `log_transfer` (
  `tableName` varchar(100) NOT NULL COMMENT '数据同步的表名或视图名',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '每次同步成功之后+1',
  `eventTime` datetime DEFAULT NULL COMMENT '系统后台同步事件发生的时间',
  PRIMARY KEY (`tableName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据同步状态表';

/*Table structure for table `m_avgdata` */

DROP TABLE IF EXISTS `m_avgdata`;

CREATE TABLE `m_avgdata` (
  `id` varchar(50) NOT NULL COMMENT '唯一标识uuid',
  `nodeid` varchar(20) NOT NULL COMMENT '产品入网唯一标识',
  `sensorPhysicalid` int(11) NOT NULL COMMENT '传感量标识',
  `maxValue` double DEFAULT NULL COMMENT '最大值',
  `maxTime` datetime DEFAULT NULL COMMENT '最大值时间',
  `minValue` double DEFAULT NULL COMMENT '最小值',
  `minTime` datetime DEFAULT NULL COMMENT '最小值时间',
  `avgValue` double DEFAULT NULL COMMENT '平均值',
  `waveValue` double DEFAULT NULL COMMENT '日波动值=最大值-最小值',
  `ms_date` date DEFAULT NULL COMMENT '日期值',
  `isupdate` int(11) DEFAULT '0' COMMENT '当数据因链路问题出现丢包，然后通过数据回补逻辑将基础数据补充完整后，统计表需要重新进行数据统计。（涉及到：均峰值、降雨量、日照、风向）',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='在线设备均峰值数据表';

/*Table structure for table `m_coefficient` */

DROP TABLE IF EXISTS `m_coefficient`;

CREATE TABLE `m_coefficient` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增唯一标识',
  `nodeid` varchar(20) NOT NULL COMMENT '节点号',
  `sensorPhysicalid` int(11) NOT NULL COMMENT '传感量标识',
  `paramName` varchar(10) NOT NULL COMMENT '例如：ax2+bx+c\n            a、b、c均为计算公式系数\n            取值范围：a~z的26个英文小写字母\n            ',
  `paramValue` varchar(20) NOT NULL COMMENT '节点计算公式自定义系数值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='自定义系数表';

/*Table structure for table `m_defaultcoefficient` */

DROP TABLE IF EXISTS `m_defaultcoefficient`;

CREATE TABLE `m_defaultcoefficient` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增唯一标识',
  `sensorPhysicalid` int(11) NOT NULL COMMENT '传感量标识',
  `paramName` varchar(10) NOT NULL COMMENT '计算公式系数',
  `defaultValue` varchar(20) NOT NULL COMMENT '默认系数值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=utf8 COMMENT='默认系数表';

/*Table structure for table `m_device_link_info` */

DROP TABLE IF EXISTS `m_device_link_info`;

CREATE TABLE `m_device_link_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nodeid` varchar(20) NOT NULL COMMENT '产品入网唯一标识',
  `nodeVersion` int(11) NOT NULL COMMENT '节点协议版本号',
  `isControl` int(11) NOT NULL COMMENT '0:可控 1:不可控',
  `parentIP` int(11) NOT NULL COMMENT '父节点IP号',
  `childIP` int(11) NOT NULL COMMENT '当前节点IP号',
  `feedbackIP` int(11) NOT NULL COMMENT '反馈地址IP号',
  `sequence` int(11) NOT NULL COMMENT '包序列号',
  `stamp` datetime NOT NULL COMMENT '时间戳',
  `emptyStamp` datetime DEFAULT NULL COMMENT '空数据时间戳',
  `interval_i` int(11) NOT NULL DEFAULT '600' COMMENT '工作周期',
  `rssi` int(11) NOT NULL COMMENT '接收信号强度',
  `lqi` int(11) NOT NULL COMMENT '连接质量参数',
  `lowvoltage` float NOT NULL DEFAULT '0' COMMENT '电压：-1、无电压值，其他的、电压值',
  `anomaly` int(11) NOT NULL DEFAULT '0' COMMENT '设备状态：-1、超时, 0、正常, 1、低电压, 2、掉电',
  `deviceMode` int(11) NOT NULL DEFAULT '0' COMMENT '0：正常模式 1：巡检模式',
  `remoteIp` varchar(15) NOT NULL DEFAULT '192.168.0.1' COMMENT '网关设备ip',
  `remotePort` int(11) NOT NULL DEFAULT '10000' COMMENT '网关数据监听端口',
  `sdCardState` int(1) NOT NULL DEFAULT '0' COMMENT 'SD卡状态：0未插卡或卡未插好 1卡已插好 2卡已写满',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `m_emptyrecord` */

DROP TABLE IF EXISTS `m_emptyrecord`;

CREATE TABLE `m_emptyrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `nodeId` varchar(30) NOT NULL COMMENT '设备编号',
  `stamp` datetime NOT NULL COMMENT '时间戳(小时)',
  `gatewaySuccess` int(11) NOT NULL DEFAULT '0' COMMENT '网关成功次数',
  `dataCacheSuccess` int(11) NOT NULL DEFAULT '0' COMMENT '缓存成功次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='空数据记录表';

/*Table structure for table `m_lowvolvalue` */

DROP TABLE IF EXISTS `m_lowvolvalue`;

CREATE TABLE `m_lowvolvalue` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `nodeid` varchar(20) NOT NULL COMMENT '产品入网唯一标识',
  `value` float NOT NULL COMMENT '阈值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nodeid_index` (`nodeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='低电阈值信息表';

/*Table structure for table `m_netinfo` */

DROP TABLE IF EXISTS `m_netinfo`;

CREATE TABLE `m_netinfo` (
  `id` varchar(50) NOT NULL COMMENT 'uuid',
  `radds` varchar(20) DEFAULT NULL COMMENT 'TCPClient通讯模式使用',
  `rport` int(5) DEFAULT '-1' COMMENT 'TCPClient通讯模式使用',
  `lport` int(5) DEFAULT '-1' COMMENT 'UDP通讯模式使用',
  `sport` varchar(20) DEFAULT NULL COMMENT '串口通讯使用',
  `brate` int(11) DEFAULT '-1' COMMENT '串口通讯使用',
  `model` int(11) NOT NULL DEFAULT '-1' COMMENT '1：串口\n            2：UDP\n            3：TCP',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '0：未连接\n            1：正在连接\n            2：已连接\n            3：正在断开连接',
  `siteId` varchar(50) DEFAULT NULL COMMENT '站点ID，如果是 v1.3 协议对应的siteId，v3协议忽略此字段。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主机与网关设备网络连接信息表';

/*Table structure for table `m_nodeinfo` */

DROP TABLE IF EXISTS `m_nodeinfo`;

CREATE TABLE `m_nodeinfo` (
  `nodeid` varchar(20) NOT NULL COMMENT '产品入网唯一标识',
  `nodeType` int(11) NOT NULL COMMENT '1：节点  2：中继  3:节点-主模块(可控) 4:节点-从模块(可控) 7：网关',
  `nodeName` varchar(50) DEFAULT NULL COMMENT '节点名称',
  `createTime` datetime NOT NULL COMMENT '节点创建时间或更新时间，与原add_time字段合并\n            （记录生成后不可修改）',
  `X` int(4) NOT NULL DEFAULT '0' COMMENT 'X轴坐标',
  `Y` int(4) NOT NULL DEFAULT '0' COMMENT 'Y轴坐标',
  `Z` int(4) NOT NULL DEFAULT '0' COMMENT 'Z轴坐标',
  `roomid` varchar(50) DEFAULT NULL COMMENT '监测区域(所属房间号) 默认为NULL，NULL或“”均表示未部署\n            ',
  `siteId` varchar(50) NOT NULL COMMENT '站点id',
  `deviceImage` varchar(100) DEFAULT NULL COMMENT '系统相对路径和名称',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据版本',
  `binding` int(2) DEFAULT '0' COMMENT '绑定状态：0 未绑定  1已绑定',
  `isActive` int(1) NOT NULL DEFAULT '1' COMMENT '设备状态：0 无效  1有效',
  `coordinateX` float NOT NULL DEFAULT '-1' COMMENT '在父区域平面图的x坐标',
  `coordinateY` float NOT NULL DEFAULT '-1' COMMENT '在父区域平面图的y坐标',
  PRIMARY KEY (`nodeid`),
  UNIQUE KEY `index_unique_nodeid` (`nodeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备信息表';

/*Table structure for table `m_nodeinfomemory` */

DROP TABLE IF EXISTS `m_nodeinfomemory`;

CREATE TABLE `m_nodeinfomemory` (
  `id` varchar(50) NOT NULL COMMENT 'uuid',
  `nodeid` varchar(20) NOT NULL COMMENT '产品入网唯一标识=接入点号（8位）+ip号（5位）',
  `nodeVersion` int(11) NOT NULL COMMENT '节点协议版本号',
  `isControl` int(11) NOT NULL COMMENT '0:可控 1:不可控',
  `parentIP` int(11) NOT NULL COMMENT '父节点IP号',
  `childIP` int(11) NOT NULL COMMENT '当前节点IP号',
  `feedbackIP` int(11) NOT NULL COMMENT '反馈地址IP号',
  `sequence` int(11) NOT NULL COMMENT '包序列号',
  `stamp` datetime NOT NULL COMMENT '时间戳',
  `emptyStamp` datetime DEFAULT NULL COMMENT '空数据时间戳',
  `interval_i` int(11) NOT NULL DEFAULT '600' COMMENT '工作周期',
  `rssi` int(11) NOT NULL COMMENT '接收信号强度',
  `lqi` int(11) NOT NULL COMMENT '连接质量参数',
  `lowvoltage` float NOT NULL DEFAULT '0' COMMENT '电压：-1、无电压值，其他的、电压值',
  `anomaly` int(11) NOT NULL DEFAULT '0' COMMENT '设备状态：-1、超时, 0、正常, 1、低电压, 2、掉电',
  `deviceMode` int(11) NOT NULL DEFAULT '0' COMMENT '0：正常模式 1：巡检模式',
  `remoteIp` varchar(15) NOT NULL DEFAULT '192.168.0.1' COMMENT '网关设备ip',
  `remotePort` int(11) NOT NULL DEFAULT '10000' COMMENT '网关数据监听端口',
  `sdCardState` int(1) NOT NULL DEFAULT '0' COMMENT 'SD卡状态：0未插卡或卡未插好 1卡已插好 2卡已写满',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_unique_nodeid` (`nodeid`),
  KEY `FK_设备信息和在线设备实时数据关系` (`nodeid`),
  CONSTRAINT `FK_设备信息和在线设备实时数据关系` FOREIGN KEY (`nodeid`) REFERENCES `m_nodeinfo` (`nodeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='在线设备实时状态表';

/*Table structure for table `m_nodesensor` */

DROP TABLE IF EXISTS `m_nodesensor`;

CREATE TABLE `m_nodesensor` (
  `id` varchar(50) NOT NULL COMMENT 'uuid',
  `nodeid` varchar(20) NOT NULL COMMENT '产品入网唯一标识',
  `sensorPhysicalid` int(11) NOT NULL COMMENT '传感量标识',
  `sensorPhysicalValue` varchar(30) NOT NULL COMMENT '传感量值',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '0：采样失败  0xFFFF为采样失败\n            1：采样正常\n            2：低于低阈值\n            3：超过高阈值\n            4：空数据（前台暂不处理）',
  `stamp` datetime NOT NULL COMMENT '数据采样时间戳（实时数据显示时采用一组数据中时间最大值）',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本',
  PRIMARY KEY (`id`),
  KEY `FK_nodeinfo_nodesensor` (`nodeid`),
  CONSTRAINT `FK_nodeinfo_nodesensor` FOREIGN KEY (`nodeid`) REFERENCES `m_nodeinfo` (`nodeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实时数据表';

/*Table structure for table `m_sensorinfo` */

DROP TABLE IF EXISTS `m_sensorinfo`;

CREATE TABLE `m_sensorinfo` (
  `id` int(11) NOT NULL COMMENT '流水号',
  `sensorPhysicalid` int(11) NOT NULL COMMENT '传感量标识',
  `en_name` varchar(20) NOT NULL COMMENT '传感量缩写',
  `cn_name` varchar(50) NOT NULL COMMENT '监测量中文名',
  `sensorPrecision` int(4) NOT NULL DEFAULT '2' COMMENT '传感量精度',
  `units` varchar(20) NOT NULL COMMENT '计量单位',
  `positions` int(4) NOT NULL DEFAULT '0' COMMENT '显示位',
  `isActive` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效 1：有效    0：无效',
  `showType` int(11) NOT NULL DEFAULT '0' COMMENT '0默认，1风向类；该字段用于呈现判断，风向类在实时数据、历史数据中需要展示为方向标识，而在图表中需要具体数值，考虑扩展性，加入此标识',
  `minValue` double NOT NULL DEFAULT '0' COMMENT '允许的最小值',
  `maxValue` double NOT NULL DEFAULT '0' COMMENT '允许的最大值',
  `rangeType` int(11) NOT NULL DEFAULT '0' COMMENT '无范围限制 0; 只有最小值限制 1; 只有最大值限制 2; 两个都有 3;',
  `signType` int(11) NOT NULL DEFAULT '0' COMMENT '原始值是否有符号。无符号 0; 有符号 1;',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='传感信息表';

/*Table structure for table `m_systemconfig` */

DROP TABLE IF EXISTS `m_systemconfig`;

CREATE TABLE `m_systemconfig` (
  `datetimeForAvg` time NOT NULL DEFAULT '01:30:00' COMMENT '执行均峰值时间',
  `intervalForClient` int(11) DEFAULT '30' COMMENT '客户端刷新实时数据的时间（秒）',
  `previousValue` double DEFAULT '-1' COMMENT '蒸发量上一包的值',
  `dvPlaceCodeIndex` int(11) DEFAULT '0' COMMENT '每添加一个摄像机，要用摄像机对应站点id+该索引作为摄像机编码，同时该索引需要更新+1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表';

/*Table structure for table `m_tbl_clone` */

DROP TABLE IF EXISTS `m_tbl_clone`;

CREATE TABLE `m_tbl_clone` (
  `id` varchar(50) NOT NULL COMMENT '唯一标识uuid',
  `nodeid` varchar(20) NOT NULL COMMENT '产品入网唯一标识',
  `sensorPhysicalid` int(11) NOT NULL COMMENT '传感量标识',
  `sensorPhysicalvalue` varchar(30) NOT NULL COMMENT '传感量值',
  `createtime` datetime NOT NULL COMMENT '收到数据包的时间',
  `state` int(11) NOT NULL COMMENT '传感量状态：0、采样失败，1、采样正常，2、低于低阈值，3、超过高阈值，4、空数据（前台暂不处理）',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节点克隆表';

/*Table structure for table `m_tbl_lxh_acc` */

DROP TABLE IF EXISTS `m_tbl_lxh_acc`;

CREATE TABLE `m_tbl_lxh_acc` (
  `id` varchar(50) NOT NULL COMMENT '唯一标识uuid',
  `nodeid` varchar(20) NOT NULL COMMENT '产品入网唯一标识',
  `lx_h` double DEFAULT '0' COMMENT '日照量',
  `ms_datetime` datetime DEFAULT NULL COMMENT '记录时间',
  `season` int(11) DEFAULT NULL COMMENT '季度',
  `isupdate` int(11) DEFAULT '0' COMMENT '是否修改',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='LXH光照日统计表';

/*Table structure for table `m_tbl_orders` */

DROP TABLE IF EXISTS `m_tbl_orders`;

CREATE TABLE `m_tbl_orders` (
  `orderId` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增唯一标识',
  `nodeid` varchar(50) NOT NULL COMMENT '设备标识',
  `datagramStamp` datetime DEFAULT NULL COMMENT '空数据回补的小时时间戳',
  `remoteAddress` varchar(15) NOT NULL COMMENT '远程ip',
  `remotePort` int(11) NOT NULL COMMENT '远程端口',
  `serialNum` varchar(15) NOT NULL DEFAULT '' COMMENT '包序列号',
  `submitTime` datetime DEFAULT NULL COMMENT '提交时间',
  `submitCount` int(11) DEFAULT '0' COMMENT '提交次数',
  `sendTime` datetime DEFAULT NULL COMMENT '发送时间',
  `currentState` int(11) DEFAULT '-1' COMMENT '命令交互设备返回状态码：[01成功/02失败/03送达成功/FF路径不通]',
  `isValid` int(11) DEFAULT '1' COMMENT '是否有效',
  `orderValue` varchar(100) DEFAULT NULL COMMENT '命令参数',
  `orderCode` int(11) DEFAULT NULL COMMENT '命令编号',
  `content` varchar(300) DEFAULT NULL COMMENT '下行命令包内容',
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='反控指令表';

/*Table structure for table `m_tbl_pt100_mapping` */

DROP TABLE IF EXISTS `m_tbl_pt100_mapping`;

CREATE TABLE `m_tbl_pt100_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `ohm` double DEFAULT NULL COMMENT '电阻值',
  `temp` double DEFAULT NULL COMMENT '温度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8 COMMENT='pt100字典表';

/*Table structure for table `m_tbl_rb_day_acc` */

DROP TABLE IF EXISTS `m_tbl_rb_day_acc`;

CREATE TABLE `m_tbl_rb_day_acc` (
  `id` varchar(50) NOT NULL COMMENT '唯一标识uuid',
  `nodeid` varchar(20) NOT NULL COMMENT '产品入网唯一标识',
  `rb` double DEFAULT '0' COMMENT '降雨量',
  `ms_date` date DEFAULT NULL COMMENT '记录日期',
  `isupdate` int(11) DEFAULT '0' COMMENT '是否修改',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日降水量 信息统计表';

/*Table structure for table `m_tbl_rb_hour_acc` */

DROP TABLE IF EXISTS `m_tbl_rb_hour_acc`;

CREATE TABLE `m_tbl_rb_hour_acc` (
  `id` varchar(50) NOT NULL COMMENT '唯一标识uuid',
  `nodeid` varchar(20) NOT NULL COMMENT '产品入网唯一标识',
  `rb` double DEFAULT '0' COMMENT '降雨量',
  `ms_datetime` datetime DEFAULT NULL COMMENT '记录时间',
  `isupdate` int(11) DEFAULT '0' COMMENT '是否修改',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小时降雨量表';

/*Table structure for table `m_tbl_sdcardrecord` */

DROP TABLE IF EXISTS `m_tbl_sdcardrecord`;

CREATE TABLE `m_tbl_sdcardrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标识',
  `nodeid` varchar(20) NOT NULL COMMENT '设备标识',
  `sTime` datetime DEFAULT NULL COMMENT '开始时间',
  `eTime` datetime DEFAULT NULL COMMENT '结束时间',
  `step` int(11) NOT NULL COMMENT 'SD卡操作步骤',
  `ms_timeStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SD卡操作记录表';

/*Table structure for table `m_threshold` */

DROP TABLE IF EXISTS `m_threshold`;

CREATE TABLE `m_threshold` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `zoneId` varchar(50) NOT NULL COMMENT '区域id ',
  `sensorPhysicalid` int(11) NOT NULL COMMENT '传感量标识',
  `maxValue` float DEFAULT NULL COMMENT '阈值上限',
  `minValue` float DEFAULT NULL COMMENT '阈值下限',
  `thresholdOptionId` int(11) DEFAULT NULL COMMENT '阈值配置',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_78` (`zoneId`),
  KEY `FK_Reference_80` (`sensorPhysicalid`),
  KEY `FK_Reference_81` (`thresholdOptionId`),
  CONSTRAINT `FK_Reference_78` FOREIGN KEY (`zoneId`) REFERENCES `t_zone` (`zoneId`),
  CONSTRAINT `FK_Reference_80` FOREIGN KEY (`sensorPhysicalid`) REFERENCES `m_sensorinfo` (`id`),
  CONSTRAINT `FK_Reference_81` FOREIGN KEY (`thresholdOptionId`) REFERENCES `m_threshold_option` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `m_threshold_option` */

DROP TABLE IF EXISTS `m_threshold_option`;

CREATE TABLE `m_threshold_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `zoneId` varchar(50) NOT NULL COMMENT '区域id',
  `beforeTime` varchar(20) DEFAULT NULL COMMENT '免打扰时段：时间点之前',
  `afterTime` varchar(20) DEFAULT NULL COMMENT '免打扰时段：时间点之后',
  `isNoDisturb` int(1) NOT NULL DEFAULT '0' COMMENT '是否设置免打扰时段：0  不设置 ， 1 设置',
  `notificationType` int(2) NOT NULL DEFAULT '1' COMMENT '通知类型：1 短信，2 邮件',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_77` (`zoneId`),
  CONSTRAINT `FK_Reference_77` FOREIGN KEY (`zoneId`) REFERENCES `t_zone` (`zoneId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='阈值配置信息表';

/*Table structure for table `m_threshold_user` */

DROP TABLE IF EXISTS `m_threshold_user`;

CREATE TABLE `m_threshold_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增唯一主键',
  `zoneId` varchar(50) NOT NULL COMMENT '区域id',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `thresholdOptionId` int(11) DEFAULT NULL COMMENT '阈值配置id',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_79` (`zoneId`),
  KEY `FK_Reference_82` (`thresholdOptionId`),
  KEY `FK_Reference_83` (`userId`),
  CONSTRAINT `FK_Reference_83` FOREIGN KEY (`userId`) REFERENCES `t_users` (`id`),
  CONSTRAINT `FK_Reference_79` FOREIGN KEY (`zoneId`) REFERENCES `t_zone` (`zoneId`),
  CONSTRAINT `FK_Reference_82` FOREIGN KEY (`thresholdOptionId`) REFERENCES `m_threshold_option` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='阈值通知用户表';

/*Table structure for table `m_windmark` */

DROP TABLE IF EXISTS `m_windmark`;

CREATE TABLE `m_windmark` (
  `windmark` varchar(10) DEFAULT NULL COMMENT '存放风向16个极坐标的角度值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='玫瑰风向标表';

/*Table structure for table `m_windrose` */

DROP TABLE IF EXISTS `m_windrose`;

CREATE TABLE `m_windrose` (
  `id` varchar(50) NOT NULL COMMENT '唯一标识uuid',
  `nodeid` varchar(20) NOT NULL COMMENT '产品入网唯一标识',
  `O_N` double DEFAULT '0' COMMENT '北风风向统计值',
  `S_N` double DEFAULT '0' COMMENT '北风风速平均值',
  `O_NNE` double DEFAULT '0' COMMENT '东北偏北风风向统计值',
  `S_NNE` double DEFAULT '0' COMMENT '东北偏北风风速平均值',
  `O_NE` double DEFAULT '0' COMMENT '东北风风向统计值',
  `S_NE` double DEFAULT '0' COMMENT '东北风风速平均值',
  `O_ENE` double DEFAULT '0' COMMENT '东北偏东风风向统计值',
  `S_ENE` double DEFAULT '0' COMMENT '东北偏东风风速平均值',
  `O_E` double DEFAULT '0' COMMENT '东风风向统计值',
  `S_E` double DEFAULT '0' COMMENT '东风风速平均值',
  `O_ESE` double DEFAULT '0' COMMENT '东南偏东风风向统计值',
  `S_ESE` double DEFAULT '0' COMMENT '东南偏东风风速平均值',
  `O_SE` double DEFAULT '0' COMMENT '东南风风向统计值',
  `S_SE` double DEFAULT '0' COMMENT '东南风风速平均值',
  `O_SSE` double DEFAULT '0' COMMENT '东南偏南风风向统计值',
  `S_SSE` double DEFAULT '0' COMMENT '东南偏南风风速平均值',
  `O_S` double DEFAULT '0' COMMENT '南风风向统计值',
  `S_S` double DEFAULT '0' COMMENT '南风风速平均值',
  `O_SSW` double DEFAULT '0' COMMENT '西南偏南风风向统计值',
  `S_SSW` double DEFAULT '0' COMMENT '西南偏南风风速平均值',
  `O_SW` double DEFAULT '0' COMMENT '西南风风向统计值',
  `S_SW` double DEFAULT '0' COMMENT '西南风风速平均值',
  `O_WSW` double DEFAULT '0' COMMENT '西南偏西风风向统计值',
  `S_WSW` double DEFAULT '0' COMMENT '西南偏西风风速平均值',
  `O_W` double DEFAULT '0' COMMENT '西风风向统计值',
  `S_W` double DEFAULT '0' COMMENT '西风风速平均值',
  `O_WNW` double DEFAULT '0' COMMENT '西北偏西风风向统计值',
  `S_WNW` double DEFAULT '0' COMMENT '西北偏西风风速平均值',
  `O_NW` double DEFAULT '0' COMMENT '西北风风向统计值',
  `S_NW` double DEFAULT '0' COMMENT '西北风风速平均值',
  `O_NNW` double DEFAULT '0' COMMENT '西北偏北风风向统计值',
  `S_NNW` double DEFAULT '0' COMMENT '西北偏北风风速平均值',
  `windcalm` double DEFAULT '0' COMMENT '风速小于0.2m/s时为静风',
  `sum` int(11) DEFAULT '0' COMMENT '当天风向总数',
  `ms_date` date DEFAULT NULL COMMENT '日期',
  `season` int(11) NOT NULL COMMENT '季度',
  `isupdate` int(11) DEFAULT '0' COMMENT '是否更新',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='风向玫瑰图数据表';

/*Table structure for table `o_accident` */

DROP TABLE IF EXISTS `o_accident`;

CREATE TABLE `o_accident` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `relicId` int(11) NOT NULL COMMENT '文物唯一标识',
  `accidentInfo` varchar(1000) NOT NULL COMMENT '事故详情',
  `accidentDate` date NOT NULL COMMENT '事故记录日期',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_41` (`relicId`),
  CONSTRAINT `FK_Reference_41` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='事故记录';

/*Table structure for table `o_appraisal` */

DROP TABLE IF EXISTS `o_appraisal`;

CREATE TABLE `o_appraisal` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `relicId` int(11) NOT NULL COMMENT '文物唯一标识',
  `expertOpinion` varchar(1000) NOT NULL COMMENT '鉴定意见',
  `examiner` varchar(50) NOT NULL COMMENT '鉴定组织/鉴定人',
  `appraisalDate` date NOT NULL COMMENT '鉴定日期',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_48` (`relicId`),
  CONSTRAINT `FK_Reference_48` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='鉴定记录';

/*Table structure for table `o_attachment` */

DROP TABLE IF EXISTS `o_attachment`;

CREATE TABLE `o_attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '附件id',
  `relicId` int(11) NOT NULL COMMENT '文物唯一标识',
  `attachmentDate` date NOT NULL COMMENT '附件上传日期',
  `path` varchar(100) NOT NULL COMMENT '附件路径',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_51` (`relicId`),
  CONSTRAINT `FK_Reference_51` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='文物挂接档案附件表';

/*Table structure for table `o_era` */

DROP TABLE IF EXISTS `o_era`;

CREATE TABLE `o_era` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '时代id',
  `name` varchar(20) NOT NULL COMMENT '时代名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='文物时代字典表';

/*Table structure for table `o_event_relic` */

DROP TABLE IF EXISTS `o_event_relic`;

CREATE TABLE `o_event_relic` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `relicId` int(11) DEFAULT NULL COMMENT '文物id',
  `eventId` varchar(20) DEFAULT NULL COMMENT '事件id，规则：站点编号+年月日数字+四位随机数',
  `outDate` date DEFAULT NULL COMMENT '出库时间',
  `inDate` date DEFAULT NULL COMMENT '入库时间',
  `state` int(11) DEFAULT '0' COMMENT '扫描状态 ： 0、待扫描 ，1、扫描到 ，2、未扫到',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_43` (`relicId`),
  KEY `FK_Reference_55` (`eventId`),
  CONSTRAINT `FK_Reference_43` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`),
  CONSTRAINT `FK_Reference_55` FOREIGN KEY (`eventId`) REFERENCES `o_out_event` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='出库事件文物表';

/*Table structure for table `o_event_zone` */

DROP TABLE IF EXISTS `o_event_zone`;

CREATE TABLE `o_event_zone` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id 编号',
  `eventId` varchar(20) NOT NULL COMMENT '事件id，规则：站点编号+年月日数字+四位随机数',
  `zoneId` varchar(50) DEFAULT NULL COMMENT '区域 可为null 为null 时 表示没有人负责的区域',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  `state` int(1) NOT NULL COMMENT '0 :未确认 1：确认通过  2 ：不通过',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_68` (`eventId`),
  KEY `FK_Reference_69` (`zoneId`),
  KEY `FK_Reference_70` (`userId`),
  CONSTRAINT `FK_Reference_68` FOREIGN KEY (`eventId`) REFERENCES `o_out_event` (`id`),
  CONSTRAINT `FK_Reference_69` FOREIGN KEY (`zoneId`) REFERENCES `t_zone` (`zoneId`),
  CONSTRAINT `FK_Reference_70` FOREIGN KEY (`userId`) REFERENCES `t_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='出库单 按区域确认表';

/*Table structure for table `o_event_zone_relic` */

DROP TABLE IF EXISTS `o_event_zone_relic`;

CREATE TABLE `o_event_zone_relic` (
  `eventZoneId` int(11) NOT NULL COMMENT '出库单按区域确认id',
  `relicId` int(11) NOT NULL COMMENT '文物id',
  PRIMARY KEY (`eventZoneId`,`relicId`),
  KEY `FK_Reference_72` (`relicId`),
  CONSTRAINT `FK_Reference_72` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`),
  CONSTRAINT `FK_Reference_71` FOREIGN KEY (`eventZoneId`) REFERENCES `o_event_zone` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='(出库文物按区域分单表)';

/*Table structure for table `o_historical_relic` */

DROP TABLE IF EXISTS `o_historical_relic`;

CREATE TABLE `o_historical_relic` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文物唯一标识',
  `totalCode` varchar(20) NOT NULL COMMENT '文物总登记号',
  `catalogCode` varchar(20) NOT NULL COMMENT '编目号',
  `typeCode` varchar(20) NOT NULL COMMENT '分类号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `era` int(11) NOT NULL COMMENT '时代',
  `count` int(11) DEFAULT '1' COMMENT '件数',
  `level` int(11) NOT NULL COMMENT '级别',
  `texture` int(11) NOT NULL COMMENT '文物质地',
  `zoneId` varchar(50) DEFAULT NULL COMMENT '区域uuid',
  `siteId` varchar(50) NOT NULL COMMENT '站点编号',
  `state` int(11) NOT NULL COMMENT '文物状态：0、在库 ;1、待出库；2、出库',
  `hasTag` int(11) NOT NULL DEFAULT '0' COMMENT '是否有电子标签：0：没有 1： 有',
  `is_canceled` int(1) NOT NULL DEFAULT '0' COMMENT '是否注销：0 未注销，1 注销',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_35` (`zoneId`),
  KEY `FK_Reference_37` (`texture`),
  KEY `FK_Reference_45` (`era`),
  KEY `FK_Reference_46` (`level`),
  KEY `FKB505B18E654F2A65` (`siteId`),
  CONSTRAINT `FKB505B18E654F2A65` FOREIGN KEY (`siteId`) REFERENCES `t_site` (`siteId`),
  CONSTRAINT `FK_Reference_35` FOREIGN KEY (`zoneId`) REFERENCES `t_zone` (`zoneId`),
  CONSTRAINT `FK_Reference_37` FOREIGN KEY (`texture`) REFERENCES `o_texture` (`id`),
  CONSTRAINT `FK_Reference_45` FOREIGN KEY (`era`) REFERENCES `o_era` (`id`),
  CONSTRAINT `FK_Reference_46` FOREIGN KEY (`level`) REFERENCES `o_level` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='文物基本信息表';

/*Table structure for table `o_inscription` */

DROP TABLE IF EXISTS `o_inscription`;

CREATE TABLE `o_inscription` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '铭文题跋id',
  `relicId` int(11) NOT NULL COMMENT '文物唯一标识',
  `info` varchar(1000) NOT NULL COMMENT '内容',
  `path` varchar(100) DEFAULT NULL COMMENT '图片路径',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_50` (`relicId`),
  CONSTRAINT `FK_Reference_50` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='铭文题跋';

/*Table structure for table `o_inventory` */

DROP TABLE IF EXISTS `o_inventory`;

CREATE TABLE `o_inventory` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '盘点id',
  `siteId` varchar(50) NOT NULL COMMENT '站点编号',
  `deadline` datetime DEFAULT NULL COMMENT '截止时间',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '盘点状态:0正在盘点，1已结束',
  `sumCount` int(11) DEFAULT NULL COMMENT '藏品总数',
  `sumNumber` int(11) DEFAULT NULL COMMENT '藏品总件数',
  `stockInCount` int(11) DEFAULT NULL COMMENT '在库数',
  `stockInNumber` int(11) DEFAULT NULL COMMENT '在库总件数',
  `stockOutCount` int(11) DEFAULT NULL COMMENT '出库数',
  `stockOutNumber` int(11) DEFAULT NULL COMMENT '出库总件数',
  `tagCount` int(11) DEFAULT NULL COMMENT '标签数',
  `tagNumber` int(11) DEFAULT NULL COMMENT '标签对应总件数',
  `scanCount` int(11) DEFAULT NULL COMMENT '扫描到总数',
  `scanNumber` int(11) DEFAULT NULL COMMENT '扫描对应总件数',
  `errorCount` int(11) DEFAULT NULL COMMENT '异常总数',
  `errorNumber` int(11) DEFAULT NULL COMMENT '异常对应总件数',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='盘点表';

/*Table structure for table `o_inventory_error` */

DROP TABLE IF EXISTS `o_inventory_error`;

CREATE TABLE `o_inventory_error` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `inventoryId` int(11) NOT NULL COMMENT '事件id',
  `relicId` int(11) NOT NULL COMMENT '藏品id',
  `sysState` int(1) NOT NULL COMMENT '出库时间',
  `scanState` int(1) NOT NULL COMMENT '扫描状态 ： 0、待扫描 ，1、扫描到 ，2、未扫到',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_52` (`inventoryId`),
  KEY `FK_Reference_58` (`relicId`),
  CONSTRAINT `FK_Reference_52` FOREIGN KEY (`inventoryId`) REFERENCES `o_inventory` (`id`),
  CONSTRAINT `FK_Reference_58` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='盘点异常表';

/*Table structure for table `o_inventory_out` */

DROP TABLE IF EXISTS `o_inventory_out`;

CREATE TABLE `o_inventory_out` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `inventoryId` int(11) NOT NULL COMMENT '事件id',
  `relicId` int(11) NOT NULL COMMENT '藏品id',
  `eventId` varchar(20) DEFAULT NULL COMMENT '事件id，规则：站点编号+年月日数字+四位随机数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_44` (`eventId`),
  KEY `FK_Reference_54` (`inventoryId`),
  KEY `FK_Reference_56` (`relicId`),
  CONSTRAINT `FK_Reference_44` FOREIGN KEY (`eventId`) REFERENCES `o_out_event` (`id`),
  CONSTRAINT `FK_Reference_54` FOREIGN KEY (`inventoryId`) REFERENCES `o_inventory` (`id`),
  CONSTRAINT `FK_Reference_56` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='盘点出库详情表';

/*Table structure for table `o_inventory_tag` */

DROP TABLE IF EXISTS `o_inventory_tag`;

CREATE TABLE `o_inventory_tag` (
  `relicId` int(11) NOT NULL COMMENT '藏品id',
  `inventoryId` int(11) NOT NULL COMMENT '事件id',
  PRIMARY KEY (`relicId`),
  KEY `FK_Reference_53` (`inventoryId`),
  CONSTRAINT `FK_Reference_53` FOREIGN KEY (`inventoryId`) REFERENCES `o_inventory` (`id`),
  CONSTRAINT `FK_Reference_57` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='盘点标签临时信息表';

/*Table structure for table `o_inventory_zone` */

DROP TABLE IF EXISTS `o_inventory_zone`;

CREATE TABLE `o_inventory_zone` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `statisticsType` int(2) NOT NULL COMMENT '统计类型：1、总数 2、在库 3、出库  4、标签 5、扫描 6、异常',
  `zoneId` varchar(50) DEFAULT NULL COMMENT '库房id',
  `inventoryId` int(11) NOT NULL COMMENT '盘点id',
  `countNo` int(11) NOT NULL COMMENT '数量',
  `sumNo` int(11) NOT NULL COMMENT '件数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_42` (`inventoryId`),
  KEY `FK_Reference_60` (`zoneId`),
  CONSTRAINT `FK_Reference_42` FOREIGN KEY (`inventoryId`) REFERENCES `o_inventory` (`id`),
  CONSTRAINT `FK_Reference_60` FOREIGN KEY (`zoneId`) REFERENCES `t_zone` (`zoneId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='按区域统计盘点信息表';

/*Table structure for table `o_level` */

DROP TABLE IF EXISTS `o_level`;

CREATE TABLE `o_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文物级别id',
  `name` varchar(20) NOT NULL COMMENT '文物级别名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='文物级别';

/*Table structure for table `o_out_event` */

DROP TABLE IF EXISTS `o_out_event`;

CREATE TABLE `o_out_event` (
  `id` varchar(20) NOT NULL COMMENT '事件id，规则：站点编号+年月日数字+四位随机数',
  `siteId` varchar(50) NOT NULL COMMENT '站点编号',
  `eventType` int(11) NOT NULL COMMENT '事件类型：1、外出借展，2、科研修复',
  `outBound` int(1) NOT NULL DEFAULT '0' COMMENT '是否出馆：0，否，1是',
  `useFor` varchar(50) NOT NULL COMMENT '提用目的',
  `beginDate` date NOT NULL COMMENT '开始日期',
  `endDate` date NOT NULL COMMENT '结束日期',
  `applicant` varchar(20) NOT NULL COMMENT '申请人或单位',
  `userId` int(11) NOT NULL COMMENT '代办人',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '出库单状态：0、申请中，1、出库，2、回库',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库事件表';

/*Table structure for table `o_out_event_attachment` */

DROP TABLE IF EXISTS `o_out_event_attachment`;

CREATE TABLE `o_out_event_attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `eventId` varchar(20) NOT NULL COMMENT '出库事件id',
  `userId` int(11) NOT NULL COMMENT '文档上传人',
  `path` varchar(50) NOT NULL COMMENT '文档存储路径',
  `date` datetime NOT NULL COMMENT '文档上传日期',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_75` (`eventId`),
  KEY `FK_Reference_76` (`userId`),
  CONSTRAINT `FK_Reference_75` FOREIGN KEY (`eventId`) REFERENCES `o_out_event` (`id`),
  CONSTRAINT `FK_Reference_76` FOREIGN KEY (`userId`) REFERENCES `t_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='出库事件文档记录表';

/*Table structure for table `o_photo` */

DROP TABLE IF EXISTS `o_photo`;

CREATE TABLE `o_photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '照片id',
  `relicId` int(11) NOT NULL COMMENT '文物唯一标识',
  `filmCode` varchar(50) DEFAULT NULL COMMENT '底片号',
  `photographer` varchar(20) DEFAULT NULL COMMENT '摄影师',
  `photoDate` date NOT NULL COMMENT '拍照日期',
  `ratio` varchar(50) DEFAULT NULL COMMENT '图片比例',
  `path` varchar(100) NOT NULL DEFAULT '' COMMENT '图片路径',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_47` (`relicId`),
  CONSTRAINT `FK_Reference_47` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='文物照片';

/*Table structure for table `o_property` */

DROP TABLE IF EXISTS `o_property`;

CREATE TABLE `o_property` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '属性id',
  `cnName` varchar(20) NOT NULL COMMENT '文物属性中文名',
  `enName` varchar(20) NOT NULL COMMENT '文物属性英文名',
  `belong` int(1) NOT NULL COMMENT '属于谁：0、藏品卡；1、档案；2、藏品卡加档案',
  `propertyType` int(11) NOT NULL DEFAULT '1' COMMENT '1、输入框 2、日期类型  3、文本域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='文物属性表';

/*Table structure for table `o_property_info` */

DROP TABLE IF EXISTS `o_property_info`;

CREATE TABLE `o_property_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `relicId` int(11) NOT NULL COMMENT '文物唯一标识',
  `propertyId` int(11) NOT NULL COMMENT '文物属性',
  `propertyVlaue` varchar(1000) DEFAULT NULL COMMENT '文物属性的值',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_36` (`relicId`),
  KEY `FK_Reference_40` (`propertyId`),
  CONSTRAINT `FK_Reference_36` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`),
  CONSTRAINT `FK_Reference_40` FOREIGN KEY (`propertyId`) REFERENCES `o_property` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='文物属性信息';

/*Table structure for table `o_restore` */

DROP TABLE IF EXISTS `o_restore`;

CREATE TABLE `o_restore` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `relicId` int(11) NOT NULL COMMENT '文物唯一标识',
  `restorers` varchar(50) NOT NULL COMMENT '承制单位/承制人',
  `restoreDate` date NOT NULL COMMENT '修复日期',
  `restoreInfo` varchar(1000) NOT NULL COMMENT '修复详情',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_4` (`relicId`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='文物记录信息';

/*Table structure for table `o_rove` */

DROP TABLE IF EXISTS `o_rove`;

CREATE TABLE `o_rove` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流传经历id',
  `relicId` int(11) DEFAULT NULL COMMENT '文物id',
  `roveInfo` varchar(1000) NOT NULL COMMENT '流传经历描述',
  `roveDate` date NOT NULL COMMENT '流传日期',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_59` (`relicId`),
  CONSTRAINT `FK_Reference_59` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='流传经历表';

/*Table structure for table `o_rubbing` */

DROP TABLE IF EXISTS `o_rubbing`;

CREATE TABLE `o_rubbing` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '拓片id',
  `relicId` int(11) NOT NULL COMMENT '文物唯一标识',
  `rubbingCode` varchar(50) DEFAULT NULL COMMENT '拓片号',
  `producer` varchar(20) DEFAULT NULL COMMENT '拓片人',
  `rubbingDate` date NOT NULL COMMENT '拓片日期',
  `ratio` varchar(20) DEFAULT NULL COMMENT '拓片比例',
  `path` varchar(100) NOT NULL COMMENT '拓片路径',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_34` (`relicId`),
  CONSTRAINT `FK_Reference_34` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='文物拓片';

/*Table structure for table `o_status_quo` */

DROP TABLE IF EXISTS `o_status_quo`;

CREATE TABLE `o_status_quo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '现状id',
  `relicId` int(11) NOT NULL COMMENT '文物唯一标识',
  `quoInfo` varchar(1000) NOT NULL COMMENT '现状描述',
  `quoDate` date NOT NULL COMMENT '现状日期',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_49` (`relicId`),
  CONSTRAINT `FK_Reference_49` FOREIGN KEY (`relicId`) REFERENCES `o_historical_relic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='文物现状';

/*Table structure for table `o_texture` */

DROP TABLE IF EXISTS `o_texture`;

CREATE TABLE `o_texture` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '质地id',
  `name` varchar(20) NOT NULL COMMENT '质地名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='文物质地';

/*Table structure for table `o_user_zone` */

DROP TABLE IF EXISTS `o_user_zone`;

CREATE TABLE `o_user_zone` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `userId` int(11) NOT NULL COMMENT '库房管理员（来自系统用户表中用户）',
  `zoneId` varchar(50) NOT NULL COMMENT '区域id ',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_66` (`userId`),
  KEY `FK_Reference_67` (`zoneId`),
  CONSTRAINT `FK_Reference_66` FOREIGN KEY (`userId`) REFERENCES `t_users` (`id`),
  CONSTRAINT `FK_Reference_67` FOREIGN KEY (`zoneId`) REFERENCES `t_zone` (`zoneId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='人员与区域关系';

/*Table structure for table `p_color_dictionary` */

DROP TABLE IF EXISTS `p_color_dictionary`;

CREATE TABLE `p_color_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `colorIndex` int(11) DEFAULT NULL COMMENT '索引',
  `colorRgb` int(11) DEFAULT NULL COMMENT '色轮的rgb',
  `colorType` int(11) DEFAULT NULL COMMENT '色轮类型：0彩色，1灰度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=392 DEFAULT CHARSET=utf8 COMMENT='色轮表';

/*Table structure for table `p_dv_place` */

DROP TABLE IF EXISTS `p_dv_place`;

CREATE TABLE `p_dv_place` (
  `id` varchar(22) NOT NULL COMMENT '唯一标识',
  `dvType` varchar(1) NOT NULL COMMENT '摄像机类型：1光学摄像机,2红外摄像机',
  `placeCode` varchar(255) DEFAULT NULL COMMENT '点位编码：站点id+“-”+摄像机索引（t_sysconfig维护）',
  `placeName` varchar(255) DEFAULT NULL COMMENT '点位名称',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `imageRealWidth` float DEFAULT NULL COMMENT '图片实际宽度（）',
  `imageWidth` int(11) DEFAULT NULL COMMENT '图片宽度（像素）',
  `imageHeight` int(11) DEFAULT NULL COMMENT '图片高度（像素）',
  `enable` int(11) DEFAULT NULL COMMENT '是否启用：0禁用，1启用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `dvIp` varchar(255) DEFAULT NULL COMMENT '摄像机ip',
  `ftpProfileId` varchar(22) DEFAULT NULL COMMENT 'ftp主键',
  `zoneId` varchar(36) DEFAULT NULL COMMENT '区域id',
  `realmap` varchar(50) DEFAULT NULL COMMENT '实景图',
  `coordinateX` float NOT NULL DEFAULT '-1' COMMENT '摄像机在区域平面图x轴坐标',
  `coordinateY` float NOT NULL DEFAULT '-1' COMMENT '摄像机在区域平面图y轴坐标',
  PRIMARY KEY (`id`),
  UNIQUE KEY `placeCode` (`placeCode`),
  KEY `FK8414C20920C20338` (`ftpProfileId`),
  KEY `FK8414C2097CCFA4A2` (`zoneId`),
  CONSTRAINT `FK8414C20920C20338` FOREIGN KEY (`ftpProfileId`) REFERENCES `p_ftp_profile` (`id`),
  CONSTRAINT `FK8414C2097CCFA4A2` FOREIGN KEY (`zoneId`) REFERENCES `t_zone` (`zoneId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='摄像机点位';

/*Table structure for table `p_dv_place_optics` */

DROP TABLE IF EXISTS `p_dv_place_optics`;

CREATE TABLE `p_dv_place_optics` (
  `dvPlaceId` varchar(22) NOT NULL COMMENT '点位公共信息ID',
  `dvPort` int(11) DEFAULT NULL COMMENT '摄像机端口',
  `ioIp` varchar(255) DEFAULT NULL COMMENT 'IOIp',
  `ioPort` int(11) DEFAULT NULL COMMENT 'IO端口',
  `lightOnTime` int(11) DEFAULT NULL COMMENT '拍照前开灯时间，单位：毫秒',
  `lightOffTime` int(11) DEFAULT NULL COMMENT '拍照后开灯时间，单位：毫秒',
  `photographTime` int(11) DEFAULT NULL COMMENT '拍照时间，单位：毫秒',
  `isIoOn` int(11) DEFAULT NULL COMMENT '是否外部控制(0、不控制 1、控制)',
  `isLightOn` int(11) DEFAULT NULL COMMENT '是否开灯(0、不开灯 1、开灯)',
  `ioLightRoute` int(1) DEFAULT '2' COMMENT '灯路数',
  `ioDvRoute` int(1) DEFAULT '1' COMMENT '摄像机路数',
  PRIMARY KEY (`dvPlaceId`),
  KEY `FK2D9215FC883EE0` (`dvPlaceId`),
  CONSTRAINT `FK2D9215FC883EE0` FOREIGN KEY (`dvPlaceId`) REFERENCES `p_dv_place` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='光学摄像机点位';

/*Table structure for table `p_ftp_profile` */

DROP TABLE IF EXISTS `p_ftp_profile`;

CREATE TABLE `p_ftp_profile` (
  `id` varchar(22) NOT NULL COMMENT '主键',
  `profile_name` varchar(255) DEFAULT NULL COMMENT '名称',
  `host` varchar(255) DEFAULT NULL COMMENT '地址',
  `port` int(11) DEFAULT NULL COMMENT '端口号',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `siteId` varchar(50) DEFAULT NULL COMMENT '站点id',
  PRIMARY KEY (`id`),
  KEY `FK90B4727D654F2A65` (`siteId`),
  CONSTRAINT `FK90B4727D654F2A65` FOREIGN KEY (`siteId`) REFERENCES `t_site` (`siteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='FTP 链接';

/*Table structure for table `p_identity_code` */

DROP TABLE IF EXISTS `p_identity_code`;

CREATE TABLE `p_identity_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  `first_x` int(11) DEFAULT NULL,
  `first_y` int(11) DEFAULT NULL,
  `second_x` int(11) DEFAULT NULL,
  `second_y` int(11) DEFAULT NULL,
  `third_x` int(11) DEFAULT NULL,
  `third_y` int(11) DEFAULT NULL,
  `picture_id` varchar(22) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK42CC0B3F4E9193BB` (`picture_id`),
  CONSTRAINT `FK42CC0B3F4E9193BB` FOREIGN KEY (`picture_id`) REFERENCES `p_pictures` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='二维码记录';

/*Table structure for table `p_infrared_mark_region` */

DROP TABLE IF EXISTS `p_infrared_mark_region`;

CREATE TABLE `p_infrared_mark_region` (
  `id` varchar(22) NOT NULL COMMENT 'id',
  `markRegionName` varchar(255) NOT NULL COMMENT '名称',
  `positionX` int(11) NOT NULL COMMENT 'X坐标',
  `positionY` int(11) NOT NULL COMMENT 'Y坐标',
  `regionWidth` int(11) NOT NULL COMMENT '宽度',
  `regionHeight` int(11) NOT NULL COMMENT '高度',
  `dvPlaceId` varchar(22) DEFAULT NULL COMMENT '摄像机点位id',
  PRIMARY KEY (`id`),
  KEY `FK94B9C697C30C9686` (`dvPlaceId`),
  CONSTRAINT `FK94B9C697C30C9686` FOREIGN KEY (`dvPlaceId`) REFERENCES `p_dv_place` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红外图片标记区域';

/*Table structure for table `p_infrared_picture_data` */

DROP TABLE IF EXISTS `p_infrared_picture_data`;

CREATE TABLE `p_infrared_picture_data` (
  `id` varchar(22) NOT NULL COMMENT 'id',
  `highTemperature` double NOT NULL COMMENT '高温',
  `lowTemperature` double NOT NULL COMMENT '低温',
  `averageTemperature` double DEFAULT NULL COMMENT '平均值',
  `picId` varchar(22) DEFAULT NULL COMMENT '图片id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `picId` (`picId`),
  KEY `FK93BA7E7AB2360BE4` (`picId`),
  CONSTRAINT `FK93BA7E7AB2360BE4` FOREIGN KEY (`picId`) REFERENCES `p_pictures` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红外图片值';

/*Table structure for table `p_infrared_region_data` */

DROP TABLE IF EXISTS `p_infrared_region_data`;

CREATE TABLE `p_infrared_region_data` (
  `id` varchar(22) NOT NULL COMMENT 'id',
  `highTemperature` double NOT NULL COMMENT '高温',
  `lowTemperature` double NOT NULL COMMENT '低温',
  `averageTemperature` double DEFAULT NULL COMMENT '平均温度',
  `picId` varchar(22) DEFAULT NULL COMMENT '图片id',
  `markRegionId` varchar(22) DEFAULT NULL COMMENT '标记区域id',
  PRIMARY KEY (`id`),
  KEY `FK66DE3406B2360BE4` (`picId`),
  KEY `FK66DE34066B74E4DD` (`markRegionId`),
  CONSTRAINT `FK66DE34066B74E4DD` FOREIGN KEY (`markRegionId`) REFERENCES `p_infrared_mark_region` (`id`),
  CONSTRAINT `FK66DE3406B2360BE4` FOREIGN KEY (`picId`) REFERENCES `p_pictures` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红外图片标记区域值';

/*Table structure for table `p_mark_segment` */

DROP TABLE IF EXISTS `p_mark_segment`;

CREATE TABLE `p_mark_segment` (
  `id` varchar(22) NOT NULL COMMENT '标记段id',
  `markType` int(11) NOT NULL COMMENT '标记段类型：1手动；2自动（二维码）',
  `markName` varchar(255) DEFAULT NULL COMMENT '标记段名称',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `cancelTime` datetime DEFAULT NULL COMMENT '注销时间',
  `cancelState` int(11) DEFAULT NULL COMMENT '注销状态：0未注销，1注销',
  `dvPlaceId` varchar(22) DEFAULT NULL COMMENT '摄像机点位ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `markName` (`markName`,`dvPlaceId`),
  KEY `FKC875CF30C30C9686` (`dvPlaceId`),
  CONSTRAINT `FKC875CF30C30C9686` FOREIGN KEY (`dvPlaceId`) REFERENCES `p_dv_place` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标记段';

/*Table structure for table `p_mark_segment_position` */

DROP TABLE IF EXISTS `p_mark_segment_position`;

CREATE TABLE `p_mark_segment_position` (
  `id` varchar(22) NOT NULL COMMENT 'id',
  `markType` int(11) NOT NULL COMMENT '标记段类型：1手动；2自动（二维码）',
  `positionX` int(11) DEFAULT NULL COMMENT '第一个点坐标x',
  `positionY` int(11) DEFAULT NULL COMMENT '第一个点坐标y',
  `positionX2` int(11) DEFAULT NULL COMMENT '第二个点坐标x',
  `positionY2` int(11) DEFAULT NULL COMMENT '第二个点坐标y',
  `picSaveTime` datetime DEFAULT NULL COMMENT '图片保存时间',
  `updateTime` datetime DEFAULT NULL COMMENT '最后修改时间',
  `markLength` float DEFAULT NULL COMMENT '两点长度(像素长度)',
  `lengthDelta` float DEFAULT NULL COMMENT '长度差值',
  `markId` varchar(22) DEFAULT NULL COMMENT '标记段ID',
  `picId` varchar(22) DEFAULT NULL COMMENT '图片ID',
  PRIMARY KEY (`id`),
  KEY `FK4D601158B2360BE4` (`picId`),
  KEY `FK4D60115883212A8F` (`markId`),
  CONSTRAINT `FK4D60115883212A8F` FOREIGN KEY (`markId`) REFERENCES `p_mark_segment` (`id`),
  CONSTRAINT `FK4D601158B2360BE4` FOREIGN KEY (`picId`) REFERENCES `p_pictures` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标记段坐标';

/*Table structure for table `p_mark_segment_qrcode` */

DROP TABLE IF EXISTS `p_mark_segment_qrcode`;

CREATE TABLE `p_mark_segment_qrcode` (
  `id` varchar(22) NOT NULL COMMENT '标记段ID',
  `textA` varchar(255) DEFAULT NULL COMMENT '二维码A',
  `textB` varchar(255) DEFAULT NULL COMMENT '二维码B',
  PRIMARY KEY (`id`),
  KEY `FKB0247B7D2B64D281` (`id`),
  CONSTRAINT `FKB0247B7D2B64D281` FOREIGN KEY (`id`) REFERENCES `p_mark_segment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='二维码标记段';

/*Table structure for table `p_mark_segment_qrcode_position` */

DROP TABLE IF EXISTS `p_mark_segment_qrcode_position`;

CREATE TABLE `p_mark_segment_qrcode_position` (
  `id` varchar(22) NOT NULL COMMENT '标记段坐标ID',
  `pointA_0_x` int(11) DEFAULT NULL COMMENT '二维码A点1x轴坐标',
  `pointA_0_y` int(11) DEFAULT NULL COMMENT '二维码A点1y轴坐标',
  `pointA_1_x` int(11) DEFAULT NULL COMMENT '二维码A点2x轴坐标',
  `pointA_1_y` int(11) DEFAULT NULL COMMENT '二维码A点2y轴坐标',
  `pointA_2_x` int(11) DEFAULT NULL COMMENT '二维码A点3x轴坐标',
  `pointA_2_y` int(11) DEFAULT NULL COMMENT '二维码A点3y轴坐标',
  `pointB_0_x` int(11) DEFAULT NULL COMMENT '二维码B点1x轴坐标',
  `pointB_0_y` int(11) DEFAULT NULL COMMENT '二维码B点1y轴坐标',
  `pointB_1_x` int(11) DEFAULT NULL COMMENT '二维码B点2x轴坐标',
  `pointB_1_y` int(11) DEFAULT NULL COMMENT '二维码B点2y轴坐标',
  `pointB_2_x` int(11) DEFAULT NULL COMMENT '二维码B点3x轴坐标',
  `pointB_2_y` int(11) DEFAULT NULL COMMENT '二维码B点3y轴坐标',
  PRIMARY KEY (`id`),
  KEY `FKFB89EBAB509C0D4A` (`id`),
  CONSTRAINT `FKFB89EBAB509C0D4A` FOREIGN KEY (`id`) REFERENCES `p_mark_segment_position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='二维码标记段坐标';

/*Table structure for table `p_photograph_time_interval` */

DROP TABLE IF EXISTS `p_photograph_time_interval`;

CREATE TABLE `p_photograph_time_interval` (
  `id` varchar(22) NOT NULL COMMENT '拍照计划id',
  `dayOfWeek` int(11) DEFAULT NULL COMMENT '是星期几:1星期一，2星期二，3星期三，4星期四，5星期五，6星期六，7星期天，8全部',
  `dvPlaceId` varchar(22) DEFAULT NULL COMMENT '摄像机点位公共信息表ID',
  `startTime` time DEFAULT NULL COMMENT '开始时间',
  `endTime` time DEFAULT NULL COMMENT '结束时间',
  `intervalTime` int(11) DEFAULT NULL COMMENT '间隔时间,单位：分钟',
  PRIMARY KEY (`id`),
  KEY `FKFC4ADF83C30C9686be6a9e43` (`dvPlaceId`),
  CONSTRAINT `FKFC4ADF83C30C9686be6a9e43` FOREIGN KEY (`dvPlaceId`) REFERENCES `p_dv_place` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='时间间隔拍照计划表';

/*Table structure for table `p_photograph_time_point` */

DROP TABLE IF EXISTS `p_photograph_time_point`;

CREATE TABLE `p_photograph_time_point` (
  `id` varchar(22) NOT NULL COMMENT '拍照计划编号',
  `dayOfWeek` int(11) DEFAULT NULL COMMENT '是星期几:1星期一，2星期二，3星期三，4星期四，5星期五，6星期六，7星期天，8全部',
  `dvPlaceId` varchar(22) DEFAULT NULL COMMENT '摄像机点位公共信息表ID',
  `timePoint` time DEFAULT NULL COMMENT '时间点',
  PRIMARY KEY (`id`),
  KEY `FKFC4ADF83C30C96868ef43cd2` (`dvPlaceId`),
  CONSTRAINT `FKFC4ADF83C30C96868ef43cd2` FOREIGN KEY (`dvPlaceId`) REFERENCES `p_dv_place` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='时间点拍照计划表';

/*Table structure for table `p_pictures` */

DROP TABLE IF EXISTS `p_pictures`;

CREATE TABLE `p_pictures` (
  `id` varchar(22) NOT NULL COMMENT '图片id',
  `pictureName` varchar(255) DEFAULT NULL COMMENT '图片名称',
  `picturePath` varchar(255) DEFAULT NULL COMMENT '图片路径',
  `picCreateTime` datetime DEFAULT NULL COMMENT '保存时间',
  `isAnalyzable` int(11) DEFAULT NULL COMMENT '是否可解析温度',
  `dvPlaceId` varchar(22) DEFAULT NULL COMMENT '摄像机公共信息ID',
  PRIMARY KEY (`id`),
  KEY `FKBA01F224C30C9686` (`dvPlaceId`),
  CONSTRAINT `FKBA01F224C30C9686` FOREIGN KEY (`dvPlaceId`) REFERENCES `p_dv_place` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片表';

/*Table structure for table `ph_check_item` */

DROP TABLE IF EXISTS `ph_check_item`;

CREATE TABLE `ph_check_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id 主键',
  `name` varchar(20) NOT NULL COMMENT '统计项名称',
  `subsystemId` int(11) NOT NULL COMMENT '该统计项隶属子系统',
  `checkUrl` varchar(50) NOT NULL COMMENT '该统计项检测url',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_73` (`subsystemId`),
  CONSTRAINT `FK_Reference_73` FOREIGN KEY (`subsystemId`) REFERENCES `t_subsystem` (`subsystemId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='健康指数统计项记录表';

/*Table structure for table `schema_version` */

DROP TABLE IF EXISTS `schema_version`;

CREATE TABLE `schema_version` (
  `version_rank` int(11) NOT NULL,
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`version`),
  KEY `schema_version_vr_idx` (`version_rank`),
  KEY `schema_version_ir_idx` (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_areacode_cn` */

DROP TABLE IF EXISTS `t_areacode_cn`;

CREATE TABLE `t_areacode_cn` (
  `areaCode` int(11) NOT NULL COMMENT '地区编码',
  `areaName` varchar(50) DEFAULT NULL COMMENT '地区名称',
  `parentCode` int(11) DEFAULT NULL COMMENT '父级编码',
  `isFilte` int(1) NOT NULL DEFAULT '0' COMMENT '是否过滤：0不过滤；1过滤',
  PRIMARY KEY (`areaCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_department` */

DROP TABLE IF EXISTS `t_department`;

CREATE TABLE `t_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id 主键',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `logicGroupId` int(11) DEFAULT NULL COMMENT '隶属逻辑站点',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `t_individuation` */

DROP TABLE IF EXISTS `t_individuation`;

CREATE TABLE `t_individuation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增流水号',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `uKey` varchar(20) DEFAULT NULL COMMENT '个性化设置的key',
  `uValue` longtext COMMENT '个性化设置的value',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_logicgroup` */

DROP TABLE IF EXISTS `t_logicgroup`;

CREATE TABLE `t_logicgroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'logicGroupId编号',
  `logicGroupName` varchar(50) NOT NULL COMMENT 'logicGroup名称',
  `siteId` varchar(50) DEFAULT NULL COMMENT '站点唯一标识',
  `parentLogicGroupId` int(11) DEFAULT NULL COMMENT '父级logicGroup',
  `orgCode` varchar(20) DEFAULT NULL COMMENT '机构代码',
  `orgAddress` varchar(100) DEFAULT NULL COMMENT '地址',
  `orgZipcode` varchar(10) DEFAULT NULL COMMENT '邮编',
  `orgWebsite` varchar(50) DEFAULT NULL COMMENT '网址',
  `orgTel` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `orgFax` varchar(20) DEFAULT NULL COMMENT '传真',
  `activeState` int(1) NOT NULL DEFAULT '1' COMMENT '1-未激活；2-待激活；3-已激活。',
  `logicGroupType` int(1) NOT NULL DEFAULT '1' COMMENT '1本实例创建；2别的tomcat实例同步上来的。',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本',
  PRIMARY KEY (`id`),
  KEY `FK42DD368C58ED2DA3` (`parentLogicGroupId`),
  KEY `FK42DD368C654F2A65` (`siteId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='t_logicGroup(站点组表)';

/*Table structure for table `t_posts` */

DROP TABLE IF EXISTS `t_posts`;

CREATE TABLE `t_posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '新闻ID',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `content` longtext COMMENT '内容',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `scope` int(11) DEFAULT NULL COMMENT '可见性: 1.公共 2.内部',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='新闻';

/*Table structure for table `t_role_privilege` */

DROP TABLE IF EXISTS `t_role_privilege`;

CREATE TABLE `t_role_privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增流水号',
  `roleId` int(11) NOT NULL COMMENT '角色id',
  `privilegeId` varchar(50) NOT NULL COMMENT '权限唯一标识',
  PRIMARY KEY (`id`),
  KEY `FKD480CD53D41E4AD2` (`privilegeId`),
  KEY `FKD480CD5363EADD03` (`roleId`),
  KEY `privilegeId` (`privilegeId`),
  CONSTRAINT `FKD480CD5363EADD03` FOREIGN KEY (`roleId`) REFERENCES `t_roles` (`id`),
  CONSTRAINT `FKD480CD53D41E4AD2` FOREIGN KEY (`privilegeId`) REFERENCES `t_system_privilege` (`privilegeId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Table structure for table `t_roles` */

DROP TABLE IF EXISTS `t_roles`;

CREATE TABLE `t_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增流水号',
  `roleName` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `isManager` int(11) DEFAULT NULL COMMENT '是否是管理员',
  `state` int(11) DEFAULT '0' COMMENT '状态. 1.访客',
  `logicGroupId` int(11) DEFAULT NULL COMMENT '角色归属logicGroup',
  PRIMARY KEY (`id`),
  KEY `FKA0E9CB32C18BD3F9` (`logicGroupId`),
  CONSTRAINT `FKA0E9CB32C18BD3F9` FOREIGN KEY (`logicGroupId`) REFERENCES `t_logicgroup` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `t_site` */

DROP TABLE IF EXISTS `t_site`;

CREATE TABLE `t_site` (
  `siteId` varchar(50) NOT NULL COMMENT '站点唯一标识',
  `siteName` varchar(50) DEFAULT NULL COMMENT '站点名称',
  `areaCode` int(11) DEFAULT NULL COMMENT '地区编码',
  `lngBaiDu` double DEFAULT NULL COMMENT '百度地图：经度',
  `latBaiDu` double DEFAULT NULL COMMENT '百度地图：纬度',
  PRIMARY KEY (`siteId`),
  KEY `FKCB62C032CB3B857B` (`areaCode`),
  CONSTRAINT `FKCB62C032CB3B857B` FOREIGN KEY (`areaCode`) REFERENCES `t_areacode_cn` (`areaCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_site_real` */

DROP TABLE IF EXISTS `t_site_real`;

CREATE TABLE `t_site_real` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '站点流水号',
  `name` varchar(50) DEFAULT NULL COMMENT '站点名称',
  `type` varchar(10) DEFAULT NULL COMMENT '站点类型',
  `city_or_province` varchar(20) DEFAULT NULL COMMENT '所属城市',
  `address` varchar(100) DEFAULT NULL COMMENT '具体地址',
  `zip_code` int(11) DEFAULT NULL COMMENT '邮政编码',
  `province_code` int(11) DEFAULT NULL COMMENT '省级编码（包括直辖市）',
  `city_code` int(11) DEFAULT NULL COMMENT '市级编码',
  `region_code` int(11) DEFAULT NULL COMMENT '区县编码',
  `relics_site_code` int(11) DEFAULT NULL COMMENT '站点编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2899 DEFAULT CHARSET=utf8;

/*Table structure for table `t_siteinfo` */

DROP TABLE IF EXISTS `t_siteinfo`;

CREATE TABLE `t_siteinfo` (
  `siteId` varchar(50) NOT NULL,
  `siteName` varchar(100) NOT NULL,
  `planAddress` varchar(1000) DEFAULT NULL,
  `isCurrentSite` int(11) NOT NULL DEFAULT '0' COMMENT '0:默认值，非当前站点\n            1:是当前站点',
  PRIMARY KEY (`siteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站点平面图，主要移动要塞用';

/*Table structure for table `t_sitemap` */

DROP TABLE IF EXISTS `t_sitemap`;

CREATE TABLE `t_sitemap` (
  `siteId` varchar(50) NOT NULL COMMENT '站点唯一标识',
  `targetHost` varchar(50) DEFAULT NULL COMMENT '目标主机地址(IP或域名)',
  `siteIp` varchar(50) NOT NULL COMMENT '站点IP',
  `isActive` int(1) NOT NULL DEFAULT '1' COMMENT '网络状态-标识其是否加入网络:0未加入、1加入。\n            ',
  `isCurrentSite` int(1) NOT NULL DEFAULT '0' COMMENT '是否当前站点：0不是，1是。',
  PRIMARY KEY (`siteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站点信息映射表';

/*Table structure for table `t_subscribe` */

DROP TABLE IF EXISTS `t_subscribe`;

CREATE TABLE `t_subscribe` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `siteId` varchar(50) NOT NULL COMMENT '站点id',
  `subscribeType` int(1) NOT NULL COMMENT '订阅类型：1 周报表',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_65` (`userId`),
  CONSTRAINT `FK_Reference_65` FOREIGN KEY (`userId`) REFERENCES `t_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用来记录用户订阅信息';

/*Table structure for table `t_subsystem` */

DROP TABLE IF EXISTS `t_subsystem`;

CREATE TABLE `t_subsystem` (
  `subsystemId` int(1) NOT NULL COMMENT '业务系统id',
  `subsystemName` varchar(50) NOT NULL COMMENT '业务系统名称',
  `subsystemCode` varchar(50) NOT NULL COMMENT '业务系统代号',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `enable` int(1) DEFAULT NULL COMMENT '是否启用：0否，1是',
  PRIMARY KEY (`subsystemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务系统字典表';

/*Table structure for table `t_sys_logs` */

DROP TABLE IF EXISTS `t_sys_logs`;

CREATE TABLE `t_sys_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志自增ID',
  `userName` varchar(50) NOT NULL COMMENT '用户名称',
  `userEmail` varchar(50) NOT NULL COMMENT '用户的邮箱',
  `logicGroupId` int(11) DEFAULT NULL COMMENT 'logicGroupId',
  `logicGroupName` varchar(50) DEFAULT NULL COMMENT 'logicGroupName 操作功能名称',
  `subsystemId` int(11) NOT NULL COMMENT '业务子系统',
  `logName` varchar(50) NOT NULL COMMENT '功能名称',
  `logContent` varchar(100) NOT NULL COMMENT '操作内容',
  `logTime` datetime NOT NULL COMMENT '操作时间',
  `logState` int(1) NOT NULL COMMENT '状态:0失败；1成功。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='日志表';

/*Table structure for table `t_system_privilege` */

DROP TABLE IF EXISTS `t_system_privilege`;

CREATE TABLE `t_system_privilege` (
  `subsystemId` int(11) NOT NULL DEFAULT '1' COMMENT '所属业务系统标识',
  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '分组序列',
  `privilegeId` varchar(50) NOT NULL COMMENT '权限ID',
  `parentPrivilegeId` varchar(50) DEFAULT NULL COMMENT '父级权限标识',
  `privilegeName_CN` varchar(50) NOT NULL COMMENT '权限名称',
  `url` varchar(200) DEFAULT NULL COMMENT 'URL',
  `state` int(11) NOT NULL DEFAULT '2' COMMENT '权限状态：1必选；2可选；4超级管理员独占；8普通用户拥有；16超级管理员拥有；32站点管理员拥有；64访客拥有。这里存储的是二进制复合权限的int数值',
  `isNavigation` int(1) NOT NULL DEFAULT '0' COMMENT '是否属于导航权栏限：0否，1是',
  PRIMARY KEY (`privilegeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_task` */

DROP TABLE IF EXISTS `t_task`;

CREATE TABLE `t_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id 主键',
  `taskTitle` varchar(50) NOT NULL COMMENT '任务标题',
  `taskInfo` varchar(300) DEFAULT NULL COMMENT '任务描述',
  `releaseDate` datetime NOT NULL COMMENT '任务发布时间',
  `endDate` datetime DEFAULT NULL COMMENT '任务截至时间',
  `releaser` int(11) NOT NULL COMMENT '发布人',
  `completeStatus` int(3) DEFAULT NULL COMMENT '任务完成度',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '任务状态：0 未结束 1 已结束',
  `siteId` varchar(50) NOT NULL COMMENT '站点编号',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_61` (`releaser`),
  CONSTRAINT `FK_Reference_61` FOREIGN KEY (`releaser`) REFERENCES `t_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='任务表';

/*Table structure for table `t_task_designee` */

DROP TABLE IF EXISTS `t_task_designee`;

CREATE TABLE `t_task_designee` (
  `taskId` int(11) NOT NULL COMMENT '任务id',
  `designee` int(11) NOT NULL COMMENT '指派人',
  KEY `FK_Reference_74` (`taskId`),
  KEY `FK_user_designee` (`designee`),
  CONSTRAINT `FK_user_designee` FOREIGN KEY (`designee`) REFERENCES `t_users` (`id`),
  CONSTRAINT `FK_Reference_74` FOREIGN KEY (`taskId`) REFERENCES `t_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务指派人记录表';

/*Table structure for table `t_task_record` */

DROP TABLE IF EXISTS `t_task_record`;

CREATE TABLE `t_task_record` (
  `id` int(1) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `taskId` int(11) NOT NULL COMMENT '任务编号',
  `replier` int(11) NOT NULL COMMENT '回复人',
  `recordInfo` varchar(300) DEFAULT NULL COMMENT '回复记录描述',
  `recordDate` datetime NOT NULL COMMENT '回复记录日期',
  `endDate` datetime DEFAULT NULL COMMENT '截止日期',
  `completeStatus` int(3) DEFAULT NULL COMMENT '完成度',
  `state` int(1) DEFAULT '0' COMMENT '任务状态 : 0 未结束 1 结束',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_63` (`taskId`),
  KEY `FK_Reference_64` (`replier`),
  CONSTRAINT `FK_Reference_63` FOREIGN KEY (`taskId`) REFERENCES `t_task` (`id`),
  CONSTRAINT `FK_Reference_64` FOREIGN KEY (`replier`) REFERENCES `t_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='任务回复记录表';

/*Table structure for table `t_user_logicgroup` */

DROP TABLE IF EXISTS `t_user_logicgroup`;

CREATE TABLE `t_user_logicgroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增列',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `logicGroupId` int(11) NOT NULL COMMENT 'logicGroupId',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_7` (`userId`),
  KEY `FK_Reference_8` (`logicGroupId`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`userId`) REFERENCES `t_users` (`id`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`logicGroupId`) REFERENCES `t_logicgroup` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增流水号',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `roleId` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  KEY `FK331DEE5F63EADD03` (`roleId`),
  KEY `FK331DEE5F6940326D` (`userId`),
  CONSTRAINT `FK331DEE5F63EADD03` FOREIGN KEY (`roleId`) REFERENCES `t_roles` (`id`),
  CONSTRAINT `FK331DEE5F6940326D` FOREIGN KEY (`userId`) REFERENCES `t_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Table structure for table `t_users` */

DROP TABLE IF EXISTS `t_users`;

CREATE TABLE `t_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户自增ID',
  `logicGroupId` int(11) DEFAULT NULL COMMENT '用户归属logicGroup',
  `userPassword` varchar(60) NOT NULL COMMENT '用户密码',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `userEmail` varchar(50) NOT NULL COMMENT 'Email',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机',
  `isDisable` int(1) NOT NULL DEFAULT '0' COMMENT '用户状态：0可用；1禁用。',
  `sex` int(1) DEFAULT NULL COMMENT '性别：1女；2男。',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `token` varchar(50) DEFAULT NULL COMMENT '发邮件认证唯一标识码',
  `isActive` int(11) DEFAULT '0' COMMENT '是否激活：0未激活；1已激活。',
  `photo` varchar(50) DEFAULT NULL COMMENT '用户头像图片文件名',
  `departmentId` int(11) DEFAULT NULL COMMENT '用户隶属部门编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userEmail` (`userEmail`),
  KEY `FKA115CA7DC18BD3F9` (`logicGroupId`),
  KEY `t_users_department` (`departmentId`),
  CONSTRAINT `t_users_department` FOREIGN KEY (`departmentId`) REFERENCES `t_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 COMMENT='用户明细表';

/*Table structure for table `t_zone` */

DROP TABLE IF EXISTS `t_zone`;

CREATE TABLE `t_zone` (
  `zoneId` varchar(50) NOT NULL COMMENT '监测点uuid',
  `siteId` varchar(50) NOT NULL COMMENT '区域所属站点id',
  `parentId` varchar(50) DEFAULT NULL COMMENT '父id为null的表明是顶级区域',
  `zoneName` varchar(50) DEFAULT NULL COMMENT '监测区域平面部署图， 主要用于设备在平面图进行部署',
  `planImage` varchar(500) DEFAULT NULL COMMENT '平面地图',
  `dataVersion` bigint(20) NOT NULL DEFAULT '0' COMMENT '数据同步版本',
  `coordinateX` float NOT NULL DEFAULT '-1' COMMENT '在父区域平面图的x坐标',
  `coordinateY` float NOT NULL DEFAULT '-1' COMMENT '在父区域平面图的y坐标',
  PRIMARY KEY (`zoneId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域';

/*Table structure for table `u_action_census` */

DROP TABLE IF EXISTS `u_action_census`;

CREATE TABLE `u_action_census` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '人员ID',
  `actionTempletId` int(11) NOT NULL COMMENT '规则ID',
  `goActionTempId` int(11) NOT NULL COMMENT '往规则ID',
  `backActionTempId` int(11) NOT NULL COMMENT '返规则ID',
  `goTime` bigint(14) NOT NULL COMMENT '进入时间(毫秒数)',
  `backTime` bigint(14) NOT NULL COMMENT '返回时间(毫秒数)',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_29` (`actionTempletId`),
  KEY `FK_Reference_goTemp` (`goActionTempId`),
  KEY `FK_Reference_backTemp` (`backActionTempId`),
  CONSTRAINT `FK_Reference_29` FOREIGN KEY (`actionTempletId`) REFERENCES `u_action_templet` (`id`),
  CONSTRAINT `FK_Reference_backTemp` FOREIGN KEY (`backActionTempId`) REFERENCES `u_action_templet` (`id`),
  CONSTRAINT `FK_Reference_goTemp` FOREIGN KEY (`goActionTempId`) REFERENCES `u_action_templet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='往返记录表';

/*Table structure for table `u_action_exciter` */

DROP TABLE IF EXISTS `u_action_exciter`;

CREATE TABLE `u_action_exciter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actionTempletId` int(11) NOT NULL COMMENT '规则ID',
  `deviceId` int(11) NOT NULL COMMENT '设备ID',
  `sequence` int(11) NOT NULL COMMENT '序号',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_15` (`deviceId`),
  KEY `FK_Reference_3` (`actionTempletId`),
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`deviceId`) REFERENCES `u_device` (`id`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`actionTempletId`) REFERENCES `u_action_templet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行为规则激发器关系表';

/*Table structure for table `u_action_templet` */

DROP TABLE IF EXISTS `u_action_templet`;

CREATE TABLE `u_action_templet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) NOT NULL COMMENT '只有往、返规则拥有相关ID,关联往返规则',
  `name` varchar(50) NOT NULL COMMENT '规则名称',
  `type` int(1) NOT NULL COMMENT '规则类型：1、单程，2、往返，3、往，4、返',
  `enable` int(1) NOT NULL DEFAULT '1' COMMENT '规则状态：1、启用，2、停用',
  `siteId` varchar(50) NOT NULL COMMENT '站点id',
  `zoneId` varchar(50) DEFAULT NULL COMMENT '区域id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='行为规则表';

/*Table structure for table `u_analyse_state` */

DROP TABLE IF EXISTS `u_analyse_state`;

CREATE TABLE `u_analyse_state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '人员ID',
  `u_location_time` bigint(14) NOT NULL COMMENT '位置信息分析时间(毫秒数)',
  `u_action_time` bigint(14) DEFAULT NULL COMMENT '行为记录分析时间(毫秒数)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='统计分析状态表';

/*Table structure for table `u_device` */

DROP TABLE IF EXISTS `u_device`;

CREATE TABLE `u_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(1) NOT NULL COMMENT '1,读卡器；2,激发器；3,电子卡',
  `deviceId` varchar(10) DEFAULT NULL COMMENT '读卡器ID',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `sn` varchar(10) NOT NULL COMMENT '序号',
  `ip` varchar(15) DEFAULT NULL COMMENT 'IP地址',
  `port` int(5) DEFAULT NULL COMMENT '端口号',
  `voltage` float DEFAULT NULL COMMENT '电子卡电量（V）',
  `version` varchar(20) DEFAULT NULL COMMENT '读卡器版本',
  `enable` int(1) NOT NULL DEFAULT '1' COMMENT '是否启用：0-停用；1-启用',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '工作状态：0-异常；1-正常',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '后一次工作最时间,实时更新',
  `siteId` varchar(50) NOT NULL COMMENT '站点id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn` (`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员管理设备表';

/*Table structure for table `u_location` */

DROP TABLE IF EXISTS `u_location`;

CREATE TABLE `u_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '人员ID',
  `deviceId` int(11) NOT NULL COMMENT '设备ID',
  `currentTime` bigint(14) NOT NULL COMMENT '记录时间(毫秒数)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_U_LOCATION_USERID` (`userId`,`currentTime`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`userId`) REFERENCES `t_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='位置记录表';

/*Table structure for table `u_port_site_mapping` */

DROP TABLE IF EXISTS `u_port_site_mapping`;

CREATE TABLE `u_port_site_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `port` int(11) NOT NULL COMMENT '服务监听端口',
  `siteId` varchar(50) NOT NULL COMMENT '站点id',
  `isFilterExciter` int(1) NOT NULL DEFAULT '0' COMMENT '是否过滤激发器 0 不过滤，1过滤',
  PRIMARY KEY (`id`),
  UNIQUE KEY `port` (`port`),
  UNIQUE KEY `siteId` (`siteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `u_realtime_location` */

DROP TABLE IF EXISTS `u_realtime_location`;

CREATE TABLE `u_realtime_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '人员ID',
  `deviceId` int(11) NOT NULL COMMENT '设备ID',
  `occurrenceTime` bigint(14) NOT NULL COMMENT '记录时间(毫秒数)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实时位置表';

/*Table structure for table `u_user_action` */

DROP TABLE IF EXISTS `u_user_action`;

CREATE TABLE `u_user_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '人员ID',
  `actionTempletId` int(11) NOT NULL COMMENT '规则ID',
  `type` int(1) NOT NULL COMMENT '1.单程\n  3.往\n  4.返',
  `occurrenceTime` bigint(14) NOT NULL COMMENT '发生时间(毫秒数)',
  `checkFlag` int(1) NOT NULL DEFAULT '0' COMMENT '是否被二次匹配过：单程时该标识无意义，默认值0；往返未匹配时默认为0，往返匹配上时为1，往返超时24小时未匹配上时为2',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_28` (`actionTempletId`),
  CONSTRAINT `FK_Reference_28` FOREIGN KEY (`actionTempletId`) REFERENCES `u_action_templet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='行为记录表';

/*Table structure for table `u_user_card` */

DROP TABLE IF EXISTS `u_user_card`;

CREATE TABLE `u_user_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户ID',
  `sn` varchar(10) NOT NULL COMMENT '电子卡序号',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_1` (`userId`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`userId`) REFERENCES `t_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='发卡记录表';

/*Table structure for table `u_zone_exciter` */

DROP TABLE IF EXISTS `u_zone_exciter`;

CREATE TABLE `u_zone_exciter` (
  `zoneId` varchar(50) NOT NULL COMMENT '区域ID',
  `deviceId` int(11) NOT NULL COMMENT '设备ID',
  PRIMARY KEY (`deviceId`),
  KEY `FK_Reference_30` (`zoneId`),
  CONSTRAINT `FK_Reference_30` FOREIGN KEY (`zoneId`) REFERENCES `t_zone` (`zoneId`),
  CONSTRAINT `FK_Reference_31` FOREIGN KEY (`deviceId`) REFERENCES `u_device` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域激发器关系表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
