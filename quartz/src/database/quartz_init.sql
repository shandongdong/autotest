/*
 * autotest 自动化测试平台 SQL脚本初始化
 * Version   0.0.1
 * Author    shandongdong@126.com
 * Date      2019-08-08
 
 ************  WARNING  ************   
 执行此脚本，会导致数据库所有数据初始化，初次执行，请慎重！！！！
*/


DROP DATABASE IF EXISTS autotest_quartz;
CREATE DATABASE IF NOT EXISTS autotest_quartz DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

USE autotest_quartz;

SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- step1: 存储每一个已配置的Job的详细信息
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`
(
    `sched_name`        varchar(120) NOT NULL,
    `job_name`          varchar(80)  NOT NULL,
    `job_group`         varchar(80)  NOT NULL,
    `description`       varchar(120) DEFAULT NULL,
    `job_class_name`    varchar(128) NOT NULL,
    `is_durable`        int(11)      NOT NULL,
    `is_nonconcurrent`  int(11)      NOT NULL,
    `is_update_data`    int(11)      NOT NULL,
    `requests_recovery` int(11)      NOT NULL,
    `job_data`          blob,
    PRIMARY KEY (`sched_name`, `job_name`, `job_group`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- step2: 存储已配置的 Trigger的信息
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`
(
    `sched_name`     varchar(120) NOT NULL,
    `trigger_name`   varchar(80)  NOT NULL,
    `trigger_group`  varchar(80)  NOT NULL,
    `job_name`       varchar(80)  NOT NULL,
    `job_group`      varchar(80)  NOT NULL,
    `description`    varchar(120) DEFAULT NULL,
    `next_fire_time` bigint(20)   DEFAULT NULL,
    `prev_fire_time` bigint(20)   DEFAULT NULL,
    `priority`       int(11)      DEFAULT NULL,
    `trigger_state`  varchar(16)  NOT NULL,
    `trigger_type`   varchar(8)   NOT NULL,
    `start_time`     bigint(20)   NOT NULL,
    `end_time`       bigint(20)   DEFAULT NULL,
    `calendar_name`  varchar(80)  DEFAULT NULL,
    `misfire_instr`  smallint(6)  DEFAULT NULL,
    `job_data`       blob,
    PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`),
    KEY `sched_name` (`sched_name`, `job_name`, `job_group`),
    CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- step3: 存储简单的 Trigger，包括重复次数，间隔，以及已触的次数
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`
(
    `sched_name`      varchar(120) NOT NULL,
    `trigger_name`    varchar(80)  NOT NULL,
    `trigger_group`   varchar(80)  NOT NULL,
    `repeat_count`    bigint(20)   NOT NULL,
    `repeat_interval` bigint(20)   NOT NULL,
    `times_triggered` bigint(20)   NOT NULL,
    PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`),
    CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- step4: 存储与已触发的Trigger相关的状态信息，以及相联Job的执行信息
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`
(
    `sched_name`        varchar(120) NOT NULL,
    `entry_id`          varchar(95)  NOT NULL,
    `trigger_name`      varchar(80)  NOT NULL,
    `trigger_group`     varchar(80)  NOT NULL,
    `instance_name`     varchar(80)  NOT NULL,
    `fired_time`        bigint(20)   NOT NULL,
    `sched_time`        bigint(20)   NOT NULL,
    `priority`          int(11)      NOT NULL,
    `state`             varchar(16)  NOT NULL,
    `job_name`          varchar(80) DEFAULT NULL,
    `job_group`         varchar(80) DEFAULT NULL,
    `is_nonconcurrent`  int(11)     DEFAULT NULL,
    `requests_recovery` int(11)     DEFAULT NULL,
    PRIMARY KEY (`sched_name`, `entry_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- step5: 存储Cron Trigger，包括Cron表达式和时区信息。
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`
(
    `sched_name`      varchar(120) NOT NULL,
    `trigger_name`    varchar(80)  NOT NULL,
    `trigger_group`   varchar(80)  NOT NULL,
    `cron_expression` varchar(120) NOT NULL,
    `time_zone_id`    varchar(80) DEFAULT NULL,
    PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`),
    CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- ----------------------------
-- step6：Trigger作为Blob类型存储(用于Quartz用户用JDBC创建他们自己定制的Trigger类型，JobStore 并不知道如何存储实例的时候)
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`
(
    `sched_name`    varchar(120) NOT NULL,
    `trigger_name`  varchar(80)  NOT NULL,
    `trigger_group` varchar(80)  NOT NULL,
    `blob_data`     blob,
    PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`),
    CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- step7: 以Blob类型存储Quartz的Calendar日历信息， quartz可配置一个日历来指定一个时间范围
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`
(
    `sched_name`    varchar(120) NOT NULL,
    `calendar_name` varchar(80)  NOT NULL,
    `calendar`      blob         NOT NULL,
    PRIMARY KEY (`calendar_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- ----------------------------
-- step8:存储已暂停的Trigger组的信息
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`
(
    `sched_name`    varchar(120) NOT NULL,
    `trigger_group` varchar(80)  NOT NULL,
    PRIMARY KEY (`sched_name`, `trigger_group`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- ----------------------------
-- step9：存储少量的有关 Scheduler的状态信息，和别的 Scheduler 实例(假如是用于一个集群中)
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`
(
    `sched_name`        varchar(120) NOT NULL,
    `instance_name`     varchar(80)  NOT NULL,
    `last_checkin_time` bigint(20)   NOT NULL,
    `checkin_interval`  bigint(20)   NOT NULL,
    PRIMARY KEY (`sched_name`, `instance_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- step10:存储程序的非观锁的信息(假如使用了悲观锁)
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`
(
    `sched_name` varchar(120) NOT NULL,
    `lock_name`  varchar(40)  NOT NULL,
    PRIMARY KEY (`sched_name`, `lock_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- step11: Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`
(
    `sched_name`    varchar(120) NOT NULL,
    `TRIGGER_NAME`  varchar(200) NOT NULL,
    `TRIGGER_GROUP` varchar(200) NOT NULL,
    `STR_PROP_1`    varchar(512)   DEFAULT NULL,
    `STR_PROP_2`    varchar(512)   DEFAULT NULL,
    `STR_PROP_3`    varchar(512)   DEFAULT NULL,
    `INT_PROP_1`    int(11)        DEFAULT NULL,
    `INT_PROP_2`    int(11)        DEFAULT NULL,
    `LONG_PROP_1`   bigint(20)     DEFAULT NULL,
    `LONG_PROP_2`   bigint(20)     DEFAULT NULL,
    `DEC_PROP_1`    decimal(13, 4) DEFAULT NULL,
    `DEC_PROP_2`    decimal(13, 4) DEFAULT NULL,
    `BOOL_PROP_1`   varchar(1)     DEFAULT NULL,
    `BOOL_PROP_2`   varchar(1)     DEFAULT NULL,
    PRIMARY KEY (`sched_name`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
    CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- step12:用户表
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`        int(11)      NOT NULL AUTO_INCREMENT,
    `user_name` varchar(40)  NOT NULL,
    `pass_word`  varchar(255) NOT NULL,
    `age`       int(4)       NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

INSERT INTO `user`
VALUES ('1', 'admin', '123456', '24');
