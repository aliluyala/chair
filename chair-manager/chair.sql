/*
Navicat MySQL Data Transfer

Source Server         : 120.77.203.95
Source Server Version : 50621
Source Host           : 120.77.203.95:3306
Source Database       : chair

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2017-04-12 16:54:23
*/

USE chair;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for consumed_package
-- ----------------------------
DROP TABLE IF EXISTS `consumed_package`;
CREATE TABLE `consumed_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `package_name` varchar(32) DEFAULT NULL COMMENT '套餐名称',
  `consumed_duration` int(11) DEFAULT NULL COMMENT '消费时长',
  `status` int(2) DEFAULT '1' COMMENT '设备状态 1：有效（默认） 2：无效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消费套餐';

-- ----------------------------
-- Table structure for consumed_details
-- ----------------------------
DROP TABLE IF EXISTS `consumed_details`;
CREATE TABLE `consumed_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `open_id` varchar(128) DEFAULT NULL COMMENT '微信唯一标示',
  `phone_number` varchar(11) NOT NULL COMMENT '用户手机号',
  `consumed_package_id` int(11) NOT NULL COMMENT '消费套餐ID',
  `consumed_duration` int(11) NOT NULL COMMENT '消费时长',
  `factory_id` int(11) NOT NULL COMMENT '厂家ID',
  `factory_name` varchar(64) NOT NULL COMMENT '厂家名称',
  `proxy_id` int(11) NOT NULL COMMENT '代理ID',
  `proxy_name` varchar(64) NOT NULL COMMENT '代理名称',
  `shop_id` int(11) NOT NULL COMMENT '商铺ID',
  `shop_name` varchar(64) NOT NULL COMMENT '店铺名称',
  `shop_location` varchar(255) NOT NULL COMMENT '店铺位置',
  `device_id` int(11) NOT NULL COMMENT '设备ID',
  `consumed_time` datetime DEFAULT NULL COMMENT '消费时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='消费明细';



-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '协议状态自增ID',
  `device_no` varchar(64) DEFAULT NULL COMMENT '设备编号',
  `device_model` varchar(64) DEFAULT NULL COMMENT '设备型号',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺ID',
  `shop_location` varchar(255) DEFAULT NULL COMMENT '设备所在位置',
  `shop_name` varchar(255) DEFAULT NULL COMMENT '设备投放店铺名称',
  `proxy_id` int(11) DEFAULT NULL COMMENT '设备投放店铺名称',
  `proxy_name` varchar(255) DEFAULT NULL COMMENT '设备所属代理名称',
  `facroty_id` int(11) DEFAULT NULL COMMENT '厂家ID',
  `factory_name` varchar(255) DEFAULT NULL COMMENT '厂家名称',
  `status` int(2) DEFAULT '1' COMMENT '设备状态 1：有效（默认） 2：无效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_deviceNO` (`device_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按摩椅设备表';

-- ----------------------------
-- Table structure for factory
-- ----------------------------
DROP TABLE IF EXISTS `factory`;
CREATE TABLE `factory` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `factory_name` varchar(64) DEFAULT NULL COMMENT '厂家名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_updat` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='厂家信息表（管理员表）';

-- ----------------------------
-- Table structure for factory_proxy
-- ----------------------------
DROP TABLE IF EXISTS `factory_proxy`;
CREATE TABLE `factory_proxy` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `proxy_name` varchar(64) DEFAULT NULL COMMENT '代理名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='factory_proxy';

-- ----------------------------
-- Table structure for recharge_package
-- ----------------------------
DROP TABLE IF EXISTS `recharge_package`;
CREATE TABLE `recharge_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `package_name` varchar(32) DEFAULT NULL COMMENT '套餐名称',
  `recharge_amoun` decimal(16,2) NOT NULL COMMENT '充值金额',
  `recharge_duration` int(11) NOT NULL COMMENT '充值时长（单位：分钟）',
  `status` int(2) DEFAULT '1' COMMENT '设备状态 1：有效（默认） 2：无效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值套餐';

-- ----------------------------
-- Table structure for recharge_record
-- ----------------------------
DROP TABLE IF EXISTS `recharge_record`;
CREATE TABLE `recharge_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账期自增ID',
  `batch_no` varchar(32) DEFAULT NULL COMMENT '充值批次号，唯一',
  `transaction_id` varchar(128) DEFAULT NULL COMMENT '微信支付订单号',
  `open_id` varchar(128) DEFAULT NULL COMMENT '微信唯一标示',
  `phone_numbe` varchar(11) NOT NULL COMMENT '用户手机号',
  `recharge_package_id` int(11) DEFAULT NULL COMMENT '充值套餐ID',
  `recharge_amount` decimal(16,2) DEFAULT NULL COMMENT '充值金额',
  `recharge_duration` int(11) DEFAULT NULL COMMENT '充值时长',
  `recharge_time` date DEFAULT NULL COMMENT '充值时间',
  `pay_statu` int(2) DEFAULT '2' COMMENT '支付状态 1已支付 2未支付',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户充值记录表';



-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `shop_name` varchar(64) DEFAULT NULL COMMENT '店铺名称',
  `shop_location` varchar(255) DEFAULT NULL COMMENT '店铺位置',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备投放的店铺信息';

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `open_id` varchar(128) DEFAULT NULL COMMENT '微信唯一标示',
  `phone_number` varchar(11) NOT NULL COMMENT '手机号码',
  `amount` decimal(16,2) DEFAULT NULL COMMENT '用户累计充值金额',
  `total_duration` int(11) DEFAULT NULL COMMENT '用户账户总时长（单位：分）',
  `used_duration` int(11) DEFAULT NULL COMMENT '用户账户使用时长（单位：分）',
  `rest_duration` int(11) DEFAULT NULL COMMENT '用户账户剩余时长（单位：分）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_openID` (`open_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户账户表';


-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '协议ID，自增ID',
  `open_id` varchar(128) DEFAULT NULL COMMENT '微信唯一标示',
  `phone_number` varchar(11) NOT NULL COMMENT '手机号码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_openID` (`open_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='用户表';



DROP TABLE IF EXISTS `tempRedis`;
CREATE TABLE `tempRedis` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `key` varchar(32) DEFAULT NULL COMMENT 'key',
  `value` int(11) DEFAULT NULL COMMENT 'value',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='redis';
