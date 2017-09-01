/*
Navicat MySQL Data Transfer

Source Server         : 180.97.90.76
Source Server Version : 50540
Source Host           : 180.97.90.76:3306
Source Database       : htcharger

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2017-02-13 10:37:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `menu_name` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '2016-10-27 15:08:28', '2016-10-27 15:45:08', null, null, '设备中心', '0');
INSERT INTO `menu` VALUES ('2', '2016-10-27 15:46:10', '2016-10-27 15:46:15', null, '@{/admin/chargers/}', '充电桩', '1');
INSERT INTO `menu` VALUES ('3', '2016-10-27 15:46:43', '2016-10-27 15:46:46', null, '@{/admin/stations/}', '充电站', '1');
INSERT INTO `menu` VALUES ('4', '2016-10-27 15:47:04', '2016-10-27 15:47:07', null, null, '充电桩设置', '1');
INSERT INTO `menu` VALUES ('5', '2016-10-27 15:47:35', '2016-10-27 15:47:37', null, '@{/admin/priceTemplates/}', '参数模板', '4');
INSERT INTO `menu` VALUES ('6', '2016-10-27 15:47:59', '2016-10-27 15:48:02', null, '@{/admin/paramSettings/}', '设备参数下发', '4');
INSERT INTO `menu` VALUES ('7', '2016-10-27 15:48:39', '2016-10-27 15:48:42', null, null, '会员中心', '0');
INSERT INTO `menu` VALUES ('8', '2016-10-27 15:49:12', '2016-10-27 15:49:09', null, '@{/admin/accounts/}', '会员管理', '7');
INSERT INTO `menu` VALUES ('9', '2016-10-27 15:50:01', '2016-10-27 15:50:04', null, '@{/admin/operators/', '运营商管理', '7');
INSERT INTO `menu` VALUES ('10', '2016-10-27 15:50:33', '2016-10-27 15:50:30', null, '@{/admin/cards/}', '充电卡管理', '7');
INSERT INTO `menu` VALUES ('11', '2016-10-27 15:50:59', '2016-10-27 15:50:57', null, '@{/admin/car/}', '车主认证', '7');
INSERT INTO `menu` VALUES ('12', '2016-10-27 15:51:16', '2016-10-27 15:51:14', null, null, '运营中心', '0');
INSERT INTO `menu` VALUES ('13', '2016-10-27 15:51:48', '2016-10-27 15:51:45', null, '@{/admin/orders/}', '订单管理', '12');
INSERT INTO `menu` VALUES ('14', '2016-10-27 15:52:01', '2016-10-27 15:51:59', null, '@{/admin/reviews/}', '评价管理', '12');
INSERT INTO `menu` VALUES ('15', '2016-10-27 15:52:34', '2016-10-27 15:52:31', null, null, '运维中心', '0');
INSERT INTO `menu` VALUES ('16', '2016-10-27 15:52:56', '2016-10-27 15:52:53', null, '@{/admin/events/}', '告警信息', '15');
INSERT INTO `menu` VALUES ('17', '2016-11-24 17:07:38', '2016-11-24 17:07:35', null, null, '客服中心', '0');
INSERT INTO `menu` VALUES ('18', '2016-10-31 18:39:52', '2016-10-31 18:39:55', null, null, '内容中心', '0');
INSERT INTO `menu` VALUES ('19', '2016-10-31 18:40:20', '2016-10-31 18:40:23', null, '@{/admin/contents/}', '内容管理', '18');
INSERT INTO `menu` VALUES ('21', '2016-11-03 15:49:40', '2016-11-03 15:49:37', null, '@{/admin/feedBacks/}', '客服记录', '17');
INSERT INTO `menu` VALUES ('22', '2016-11-22 14:52:28', '2016-11-22 14:52:25', null, '@{/admin/notify/}', '消息中心', '18');
INSERT INTO `menu` VALUES ('23', '2016-11-22 14:53:47', '2016-11-22 14:53:43', null, null, '分析中心', '0');
INSERT INTO `menu` VALUES ('24', '2016-11-22 14:54:24', '2016-11-22 14:54:22', null, '@{/admin/reports/userCount}', '用户增长分析', '23');
INSERT INTO `menu` VALUES ('25', '2016-11-22 14:54:52', '2016-11-22 14:54:49', null, '@{/admin/reports/chargingDataCount}', '充电消费分析', '23');
INSERT INTO `menu` VALUES ('26', '2016-11-22 14:55:17', '2016-11-22 14:55:14', null, '@{/admin/reports/eventDataCount}', '告警分类趋势', '23');
INSERT INTO `menu` VALUES ('27', '2016-11-22 14:55:40', '2016-11-22 14:55:38', null, '@{/admin/reports/recordDataCount}', '财务增长趋势', '23');
INSERT INTO `menu` VALUES ('28', '2016-11-22 14:56:04', '2016-11-22 14:56:00', null, null, '系统管理', '0');
INSERT INTO `menu` VALUES ('29', '2016-11-22 14:56:35', '2016-11-22 14:56:33', null, '@{/admin/managers/}', '用户管理', '28');
INSERT INTO `menu` VALUES ('33', '2016-11-22 15:00:05', '2016-11-22 15:00:02', null, '@{/admin/system/version/}', 'APP升级', '28');
INSERT INTO `menu` VALUES ('34', '2016-11-23 15:45:47', '2016-11-23 15:46:26', null, '/admin/eventCodes/', '故障码', '15');
INSERT INTO `menu` VALUES ('35', '2017-02-13 10:22:01', '2017-02-13 10:21:58', null, '@{/admin/reports/accountDataCount}', '会员消费分析', '23');
