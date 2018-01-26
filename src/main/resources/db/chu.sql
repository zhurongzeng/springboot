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

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` bigint(50) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT 'role必须为ROLE_xx形式，不然security识别不出来',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`name`) values (1,'ROLE_READER');
insert  into `t_role`(`id`,`name`) values (2,'ROLE_ACTUATOR');
insert  into `t_role`(`id`,`name`) values (3,'ROLE_ADMIN');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(50) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `fullname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`username`,`password`,`fullname`) values (1,'chu','$2a$10$ySzfir3U8/Wkm4jyBX0Uculmnxc2ufwiS158s9x.TsivbWLGy.4JC','Zachary Chu');

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `user_id` bigint(50) DEFAULT NULL,
  `role_id` bigint(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`user_id`,`role_id`) values (1,1);
insert  into `t_user_role`(`user_id`,`role_id`) values (1,2);
insert  into `t_user_role`(`user_id`,`role_id`) values (1,3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
