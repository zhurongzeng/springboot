/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.28-log : Database - chu
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`chu` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `chu`;

/*Table structure for table `contact` */

DROP TABLE IF EXISTS `contact`;

CREATE TABLE `contact` (
  `id` bigint(50) NOT NULL,
  `firstName` varchar(30) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `phoneNumber` varchar(13) DEFAULT NULL,
  `emailAddress` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `contact` */

insert  into `contact`(`id`,`firstName`,`lastName`,`phoneNumber`,`emailAddress`) values (1515673304126,'s','s','s','s');
insert  into `contact`(`id`,`firstName`,`lastName`,`phoneNumber`,`emailAddress`) values (1515673321836,'sdf','s','s','123');
insert  into `contact`(`id`,`firstName`,`lastName`,`phoneNumber`,`emailAddress`) values (1515721914555,'dsf','sdf','sdf','sdf');

/*Table structure for table `t_book` */

DROP TABLE IF EXISTS `t_book`;

CREATE TABLE `t_book` (
  `id` bigint(50) NOT NULL AUTO_INCREMENT,
  `reader` varchar(50) DEFAULT NULL,
  `isbn` varchar(30) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1516022192689 DEFAULT CHARSET=utf8;

/*Data for the table `t_book` */

insert  into `t_book`(`id`,`reader`,`isbn`,`title`,`author`,`description`) values (1516022192682,'chu','2342342','Spring Boot in Action','Craig','Spring Boot in Action');
insert  into `t_book`(`id`,`reader`,`isbn`,`title`,`author`,`description`) values (1516022192683,'chu','2342342','Spring in Action','Craig','Spring in Action');
insert  into `t_book`(`id`,`reader`,`isbn`,`title`,`author`,`description`) values (1516022192684,'chu','123','123','123','123');
insert  into `t_book`(`id`,`reader`,`isbn`,`title`,`author`,`description`) values (1516022192685,'chu','123','123','123','123');
insert  into `t_book`(`id`,`reader`,`isbn`,`title`,`author`,`description`) values (1516022192686,'chu','123','123','123','123');
insert  into `t_book`(`id`,`reader`,`isbn`,`title`,`author`,`description`) values (1516022192687,'chu','123','123','123','123');
insert  into `t_book`(`id`,`reader`,`isbn`,`title`,`author`,`description`) values (1516022192688,'aaa',NULL,NULL,NULL,NULL);

/*Table structure for table `t_dictionary` */

DROP TABLE IF EXISTS `t_dictionary`;

CREATE TABLE `t_dictionary` (
  `id` varchar(50) NOT NULL COMMENT '字典ID',
  `code` varchar(20) DEFAULT NULL COMMENT '字典编码',
  `name` varchar(50) DEFAULT NULL COMMENT '字典名称',
  `type` varchar(2) DEFAULT NULL COMMENT '字典类别（1:字典项 2:字典值）',
  `parent_id` varchar(50) DEFAULT NULL COMMENT '上级ID',
  `status` varchar(2) DEFAULT NULL COMMENT '字典状态（off:禁用 on:启用）',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_dictionary` */

insert  into `t_dictionary`(`id`,`code`,`name`,`type`,`parent_id`,`status`,`remark`,`create_user`,`create_date`,`update_user`,`update_date`) values ('0','root','根节点','0',NULL,'on',NULL,'chu','2018-02-01 16:35:14','chu','2018-02-01 16:35:14');
insert  into `t_dictionary`(`id`,`code`,`name`,`type`,`parent_id`,`status`,`remark`,`create_user`,`create_date`,`update_user`,`update_date`) values ('1','1','男','2','cx8aARwdZDV0rl7M37W1RK','on',NULL,NULL,NULL,NULL,NULL);
insert  into `t_dictionary`(`id`,`code`,`name`,`type`,`parent_id`,`status`,`remark`,`create_user`,`create_date`,`update_user`,`update_date`) values ('2','2','女','2','cx8aARwdZDV0rl7M37W1RK','on',NULL,NULL,NULL,NULL,NULL);
insert  into `t_dictionary`(`id`,`code`,`name`,`type`,`parent_id`,`status`,`remark`,`create_user`,`create_date`,`update_user`,`update_date`) values ('cx8aARwdZDV0rl7M37W1RK','gender','性别','1','0','on',NULL,'chu','2018-02-01 16:00:21','chu','2018-02-01 16:00:21');
insert  into `t_dictionary`(`id`,`code`,`name`,`type`,`parent_id`,`status`,`remark`,`create_user`,`create_date`,`update_user`,`update_date`) values ('epLB3uBKd224gSK7vvixC9','yesno','是否','1','0','on',NULL,'chu','2018-02-01 16:35:14','chu','2018-02-01 16:35:14');

/*Table structure for table `t_menu` */

DROP TABLE IF EXISTS `t_menu`;

CREATE TABLE `t_menu` (
  `id` varchar(50) NOT NULL COMMENT 'ID',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `parent_id` varchar(50) DEFAULT NULL COMMENT '上级ID',
  `level_num` int(11) DEFAULT NULL COMMENT '菜单级别',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `path` varchar(100) DEFAULT NULL COMMENT '菜单路径',
  `status` varchar(5) DEFAULT NULL COMMENT '菜单状态（off:禁用 on:启用）',
  `order_num` int(11) DEFAULT NULL COMMENT '排序号',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_menu` */

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` varchar(50) NOT NULL COMMENT '角色ID',
  `name` varchar(50) NOT NULL COMMENT '角色名称 必须为ROLE_xx形式，不然security识别不出来',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`name`,`create_user`,`create_date`,`update_user`,`update_date`) values ('1','ROLE_READER',NULL,NULL,NULL,NULL);
insert  into `t_role`(`id`,`name`,`create_user`,`create_date`,`update_user`,`update_date`) values ('2','ROLE_ACTUATOR',NULL,NULL,NULL,NULL);
insert  into `t_role`(`id`,`name`,`create_user`,`create_date`,`update_user`,`update_date`) values ('3','ROLE_ADMIN',NULL,NULL,NULL,NULL);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` varchar(50) NOT NULL COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `fullname` varchar(50) NOT NULL COMMENT '客户姓名',
  `cert_type` varchar(20) DEFAULT NULL COMMENT '证件类型',
  `cert_id` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `birthday` varchar(20) DEFAULT NULL COMMENT '生日',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `mail_address` varchar(50) DEFAULT NULL COMMENT '邮箱地址',
  `address` varchar(100) DEFAULT NULL COMMENT '居住地址',
  `type` varchar(2) DEFAULT NULL COMMENT '用户类型',
  `level` varchar(2) DEFAULT NULL COMMENT '用户等级',
  `qq_no` varchar(20) DEFAULT NULL COMMENT 'QQ号码',
  `wx_no` varchar(20) DEFAULT NULL COMMENT '微信号码',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `biz_str1` varchar(50) DEFAULT NULL COMMENT '备用字段1',
  `biz_str2` varchar(50) DEFAULT NULL COMMENT '备用字段2',
  `biz_str3` varchar(50) DEFAULT NULL COMMENT '备用字段2',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`username`,`password`,`fullname`,`cert_type`,`cert_id`,`birthday`,`gender`,`phone`,`mail_address`,`address`,`type`,`level`,`qq_no`,`wx_no`,`remark`,`biz_str1`,`biz_str2`,`biz_str3`,`create_user`,`create_date`,`update_user`,`update_date`) values ('1','chu','$2a$10$ySzfir3U8/Wkm4jyBX0Uculmnxc2ufwiS158s9x.TsivbWLGy.4JC','Zachary Chu','','',NULL,NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `t_user`(`id`,`username`,`password`,`fullname`,`cert_type`,`cert_id`,`birthday`,`gender`,`phone`,`mail_address`,`address`,`type`,`level`,`qq_no`,`wx_no`,`remark`,`biz_str1`,`biz_str2`,`biz_str3`,`create_user`,`create_date`,`update_user`,`update_date`) values ('60','zhurongzeng','$2a$10$Kblu0AnfEc.sIuZbHmKb/udVP6q7K08qKZi/1BLA1zzZvsP02yzMK','朱荣增2','','',NULL,NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `user_id` varchar(50) DEFAULT NULL,
  `role_id` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`user_id`,`role_id`) values ('1','1');
insert  into `t_user_role`(`user_id`,`role_id`) values ('1','2');
insert  into `t_user_role`(`user_id`,`role_id`) values ('1','3');
insert  into `t_user_role`(`user_id`,`role_id`) values ('60','1');
insert  into `t_user_role`(`user_id`,`role_id`) values ('60','2');
insert  into `t_user_role`(`user_id`,`role_id`) values ('60','3');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
