/*
MySQL Data Transfer

Source Server         : localhost
Source Host           : localhost:3306
Source Database       : bootMustr

Date: 2018-01-10 13:21:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- 部门表
-- ----------------------------
DROP TABLE IF EXISTS `TDept`;
CREATE TABLE `TDept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `orderx` int(11) DEFAULT NULL COMMENT '排序',
  `status` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='部门管理';

INSERT INTO `TDept` VALUES ('1', '0', '研发部', '1', '0');
INSERT INTO `TDept` VALUES ('2', '1', '研发一部', '1', '0');
INSERT INTO `TDept` VALUES ('3', '1', '研发二部', '2', '0');
INSERT INTO `TDept` VALUES ('4', '0', '财务部', '2', '0');
INSERT INTO `TDept` VALUES ('5', '4', '财务一部', '1', '0');
INSERT INTO `TDept` VALUES ('6', '0', '产品部', '3', '0');
INSERT INTO `TDept` VALUES ('7', '6', '产品一部', '1', '0');
INSERT INTO `TDept` VALUES ('8', '0', '测试部', '5', '0');
INSERT INTO `TDept` VALUES ('9', '8', '测试一部', '1', '0');
INSERT INTO `TDept` VALUES ('10', '8', '测试二部', '2', '0');


-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `TUser`;
CREATE TABLE TUser(
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `username` varchar(100) NOT NULL COMMENT '用户名',
 `password` varchar(256) NOT NULL COMMENT '密码',
 `name` varchar(50) COMMENT '用户姓名',
 `sex` tinyint(1) COMMENT '性别 1男 0女',
 `dept_id` bigint(20) COMMENT '部门id',
 `status` tinyint(4) DEFAULT '0' COMMENT '状态：0正常，-1禁用',
 `email` varchar(100) COMMENT '邮箱',
 `mobile` varchar(30) COMMENT '手机',
 `birth` datetime COMMENT '出生日期',
 `address` varchar(128) COMMENT '现居住地',
 `creator` bigint(20) COMMENT '创建者',
 `createTime` datetime COMMENT '创建时间',
 `lastModifiedTime` datetime COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='用户表';

insert into `TUser` VALUES ('1','admin','$2a$10$E2k1SlgFC7pXO53YVjyOMOpHHB4rtDT8NVI.ytQlxgTgh/htkWR6q','超级管理员','1',null,'0','harry_mu@163.com','158-8888-8888','2000-01-01','深圳',null,null,null);
insert into `TUser` VALUES ('2','test','$2a$10$kgYBfb6OBPgxswrMDJ2drOY65h6M3q/rSozhxr2.bHM7HzmeKnRQu','测试','1',null,'0','harry_mu@163.com','158-8888-8888','2000-01-01','深圳',null,null,null);
insert into `TUser` VALUES ('3','deptmanager','$2a$10$wwyekmVrNSxGmFU3ej3GVe.8KuOZmTMk9z/cOI6v6WQuLsKdYU2iO','部门管理员','1',null,'0','harry_mu@163.com','158-8888-8888','2000-01-01','深圳',null,null,null);
insert into `TUser` VALUES ('4','usermanager','$2a$10$PHBeUhPVQdoj7SF9toMnnOVGnulOJiAbDae.n6WBJHh7Ym5UsFIgm','用户管理员','1',null,'0','harry_mu@163.com','158-8888-8888','2000-01-01','深圳',null,null,null);
insert into `TUser` VALUES ('5','user3','$2a$10$kgYBfb6OBPgxswrMDJ2drOY65h6M3q/rSozhxr2.bHM7HzmeKnRQu','德玛西亚之力','1','2','0','harry_mu@163.com','158-8888-8888','2000-01-01','深圳',null,null,null);
insert into `TUser` VALUES ('6','user4','$2a$10$kgYBfb6OBPgxswrMDJ2drOY65h6M3q/rSozhxr2.bHM7HzmeKnRQu','赵信','1','3','0','harry_mu@163.com','158-8888-8888','2000-01-01','深圳',null,null,null);
insert into `TUser` VALUES ('7','user5','$2a$10$kgYBfb6OBPgxswrMDJ2drOY65h6M3q/rSozhxr2.bHM7HzmeKnRQu','阿卡丽','0','5','0','harry_mu@163.com','158-8888-8888','2000-01-01','深圳',null,null,null);
insert into `TUser` VALUES ('8','user6','$2a$10$kgYBfb6OBPgxswrMDJ2drOY65h6M3q/rSozhxr2.bHM7HzmeKnRQu','寒冰射手','0','7','0','harry_mu@163.com','158-8888-8888','2000-01-01','深圳',null,null,null);
insert into `TUser` VALUES ('9','user7','$2a$10$kgYBfb6OBPgxswrMDJ2drOY65h6M3q/rSozhxr2.bHM7HzmeKnRQu','无极剑圣','1','9','0','harry_mu@163.com','158-8888-8888','2000-01-01','深圳',null,null,null);
insert into `TUser` VALUES ('10','user8','$2a$10$kgYBfb6OBPgxswrMDJ2drOY65h6M3q/rSozhxr2.bHM7HzmeKnRQu','蛮族之王','1','10','0','harry_mu@163.com','158-8888-8888','2000-01-01','深圳',null,null,null);
insert into `TUser` VALUES ('11','user9','$2a$10$kgYBfb6OBPgxswrMDJ2drOY65h6M3q/rSozhxr2.bHM7HzmeKnRQu','德玛西亚皇子','1','2','0','harry_mu@163.com','158-8888-8888','2000-01-01','深圳',null,null,null);
insert into `TUser` VALUES ('12','user10','$2a$10$kgYBfb6OBPgxswrMDJ2drOY65h6M3q/rSozhxr2.bHM7HzmeKnRQu','武器大师','1','3','0','harry_mu@163.com','158-8888-8888','2000-01-01','深圳',null,null,null);

-- ----------------------------
-- 角色表
-- ----------------------------
DROP TABLE IF EXISTS `TRole`;
CREATE TABLE TRole(
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COMMENT '角色名',
  `sign` varchar(50) COMMENT '角色标识',
  `remark` varchar(256) COMMENT '备注',
  `creator` bigint(20) COMMENT '创建者',
  `createTime` datetime COMMENT '创建时间',
  `lastModifiedTime` datetime COMMENT '最后修改时间',
  PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色表';

insert into `TRole` values ('1','超级管理员','admin','最高权限','1','2018-09-17 22:32:52','2018-09-17 22:32:52');
insert into `TRole` values ('2','普通用户','user','普通用户，拥有一般权利','1','2018-09-17 22:32:52','2018-09-17 22:32:52');
insert into `TRole` values ('3','测试用户','test','测试用户','1','2018-09-17 22:32:52','2018-09-17 22:32:52');
insert into `TRole` values ('4','部门管理员','manageDept','部门管理员，管理部门','1','2018-09-17 22:32:52','2018-09-17 22:32:52');
insert into `TRole` values ('5','用户管理员','manageUser','用户管理员，管理用户','1','2018-09-17 22:32:52','2018-09-17 22:32:52');

-- ----------------------------
-- 角色_用户表
-- ----------------------------
DROP TABLE IF EXISTS `TUser_Role`;
CREATE TABLE TUser_Role(
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) COMMENT '用户id',
  `role_id` bigint(20) COMMENT '角色id',
  PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色与用户的对应关系表';

insert into `TUser_Role` VALUES ('1','1','1');
insert into `TUser_Role` VALUES ('2','2','3');
insert into `TUser_Role` VALUES ('3','3','4');
insert into `TUser_Role` VALUES ('4','4','5');

-- ----------------------------
-- 权限_用户表
-- ----------------------------
DROP TABLE IF EXISTS `TUser_Permissions`;
CREATE TABLE TUser_Permissions(
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) COMMENT '用户id',
  `permissions` varchar(100) COMMENT '权限名',
  `targetKey` bigint(20) COMMENT '对象key',
  `targetType` tinyint(4) COMMENT '对象类型',
  PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限与用户的对应关系表';

-- ----------------------------
-- 权限_角色表
-- ----------------------------
DROP TABLE IF EXISTS `TRole_Permissions`;
CREATE TABLE TRole_Permissions(
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) COMMENT '角色id',
  `permissions` varchar(100) COMMENT '权限名',
  PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色与权限的对应关系表';

insert into `TRole_Permissions` values ('1','1','p/all');
insert into `TRole_Permissions` values ('2','3','p/test');
insert into `TRole_Permissions` values ('3','4','p/all/manageDept');
insert into `TRole_Permissions` values ('4','5','p/all/manageUser');


-- ----------------------------
-- 日志表
-- ----------------------------
DROP TABLE IF EXISTS `TLog`;
CREATE TABLE TLog(
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `time` int(11) DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';


-- ----------------------------
-- 项目
-- ----------------------------
DROP TABLE IF EXISTS `TProject`;
CREATE TABLE `TProject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级项目id，一级为0',
  `name` varchar(50) DEFAULT NULL COMMENT '项目名称',
  `webhook` varchar(128) COMMENT '发送提醒消息的webhook地址',
  `status` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='项目';


-- ----------------------------
-- 文件
-- ----------------------------
DROP TABLE IF EXISTS `TFile`;
CREATE TABLE `TFile` (
  `id` bigint(20) NOT NULL,
  `name` varchar(128) NOT NULL COMMENT '文件名称',
  `size` bigint NOT NULL COMMENT '文件大小',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `content_type` varchar(128) NOT NULL COMMENT '文件格式',
  `suffix` varchar(32) NOT NULL COMMENT '文件后缀名',
  `bucket` varchar(32) NOT NULL  COMMENT '桶',
  `object_name` varchar(128) NOT NULL COMMENT '对象名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目';


-- ----------------------------
-- 文档
-- ----------------------------
DROP TABLE IF EXISTS `TDocument`;
CREATE TABLE `TDocument` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT '所属项目id',
  `file_id` bigint(20) NOT NULL COMMENT '文件id',
  `convert_file_id` bigint(20) DEFAULT NULL COMMENT '转换后的文件id',
  `name` varchar(128) NOT NULL COMMENT '文件名称',
  `size` bigint NOT NULL COMMENT '文件大小',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `creator_id` bigint DEFAULT NULL COMMENT '上传者',
  `create_time` datetime NOT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '0：待转换,1:正常,2转换失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='项目';
