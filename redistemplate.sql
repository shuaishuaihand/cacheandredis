/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : redistemplate

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2019-07-25 15:12:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(11) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1851', '张三');
INSERT INTO `user` VALUES ('1852', '李四');
INSERT INTO `user` VALUES ('1854', '李小萌');

-- ----------------------------
-- Table structure for usertypecount
-- ----------------------------
DROP TABLE IF EXISTS `usertypecount`;
CREATE TABLE `usertypecount` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `num` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usertypecount
-- ----------------------------
INSERT INTO `usertypecount` VALUES ('1', '儿童', '100');
INSERT INTO `usertypecount` VALUES ('2', '青年', '200');
INSERT INTO `usertypecount` VALUES ('3', '中年', '300');
INSERT INTO `usertypecount` VALUES ('4', '老年', '200');
