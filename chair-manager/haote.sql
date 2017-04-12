-- MySQL dump 10.13  Distrib 5.6.26, for Linux (x86_64)


DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '协议ID，自增ID',
  `phoneNumber` varchar(11) NOT NULL COMMENT '手机号码',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_phoneNumber` (`phoneNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `agreementrecharge`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `phoneNumber` varchar(11) NOT NULL COMMENT '手机号码',
  `amount` decimal(16,2) DEFAULT NULL COMMENT '用户累计充值金额',
  `totalDuration` int(11) DEFAULT NULL COMMENT '用户账户总时长（单位：分）',
  `usedDuration` int(11) DEFAULT NULL COMMENT '用户账户使用时长（单位：分）',
  `restDuration` int(11) DEFAULT NULL COMMENT '用户账户剩余时长（单位：分）',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_phoneNumber` (`phoneNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='用户账户表';
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '协议状态自增ID',
  `deviceNO` varchar(64) DEFAULT NULL COMMENT '设备编号',
  `deviceModel` varchar(64) DEFAULT NULL COMMENT '设备型号',
  `shopID` int(11) DEFAULT NULL COMMENT '店铺ID',
  `shopLocation` varchar(255) DEFAULT NULL COMMENT '设备所在位置',
  `shopName` varchar(255) DEFAULT NULL COMMENT '设备投放店铺名称',
  `proxyID` int(11) DEFAULT NULL COMMENT '设备投放店铺名称',
  `proxyName` varchar(255) DEFAULT NULL COMMENT '设备所属代理名称',
  `facrotyID` int(11) DEFAULT NULL COMMENT '厂家ID',
  `factoryName` varchar(255) DEFAULT NULL COMMENT '厂家名称',
  `status` int(2) DEFAULT 1 COMMENT '设备状态 1：有效（默认） 2：无效',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_deviceNO` (`deviceNO`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='按摩椅设备表';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `approvephoto`
--

DROP TABLE IF EXISTS `recharge_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recharge_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `packageName` varchar(32) DEFAULT NULL COMMENT '套餐名称',
  `rechargeAmount` decimal(16,2) NOT NULL COMMENT '充值金额',
  `rechargeDuration` int(11) NOT NULL COMMENT '充值时长（单位：分钟）',
  `status` int(2) DEFAULT 1 COMMENT '设备状态 1：有效（默认） 2：无效',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='充值套餐';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `billcharge`
--

DROP TABLE IF EXISTS `consume_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consume_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `packageName` varchar(32) DEFAULT NULL COMMENT '套餐名称',
  `consumedDuration` int(11) DEFAULT NULL COMMENT '消费时长',
  `status` int(2) DEFAULT 1 COMMENT '设备状态 1：有效（默认） 2：无效',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='消费套餐';
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `billset`
--

DROP TABLE IF EXISTS `recharge_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recharge_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账期自增ID',
  `batchNO` varchar(32) DEFAULT NULL COMMENT '充值批次号，唯一',
  `payOrderNO` varchar(64) DEFAULT NULL COMMENT '支付凭证编号',
  `phoneNumber` varchar(11) NOT NULL COMMENT '用户手机号',
  `rechargePackageID` int(11) DEFAULT NULL COMMENT '充值套餐ID',
  `rechargeAmount` decimal(16,2) DEFAULT NULL COMMENT '充值金额',
  `rechargeDuration` int(11) DEFAULT NULL COMMENT '充值时长',
  `rechargeTime` date DEFAULT NULL COMMENT '充值时间',
  `payAccount` varchar(64) NOT NULL COMMENT '支付账号',
  `payMethod` int(2) NOT NULL COMMENT '支付方式 1：微信',
  `payStatus` int(2) DEFAULT 1 COMMENT '支付状态 1待支付 2已支付',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='用户充值记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `breakdevicerulerecord`
--

DROP TABLE IF EXISTS `consumed_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consumed_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `phoneNumber` varchar(11) NOT NULL COMMENT '用户手机号',
  `consumedPackageID` int(11) NOT NULL COMMENT '消费套餐ID',
  `consumedDuration` int(11) NOT NULL COMMENT '消费时长',
  `factoryID` int(11) NOT NULL COMMENT '厂家ID',
  `factoryName` varchar(64)NOT NULL COMMENT '厂家名称',
  `proxyID` int(11) NOT NULL COMMENT '代理ID',
  `proxyName` varchar(64)NOT NULL COMMENT '代理名称',
  `shopID` int(11) NOT NULL COMMENT '商铺ID',
  `shopName` varchar(64)NOT NULL COMMENT '店铺名称',
  `shopLocation` varchar(255)NOT NULL COMMENT '店铺位置',
  `deviceID` int(11) NOT NULL COMMENT '设备ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='消费明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cardevicerel`
--

DROP TABLE IF EXISTS `factory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factory` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `factoryName` varchar(64) DEFAULT NULL COMMENT '厂家名称',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='厂家信息表（管理员表）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `consumerdetails`
--

DROP TABLE IF EXISTS `factory_proxy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `factory_proxy` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  -- `factoryID` int(11) DEFAULT NULL COMMENT '厂家ID，一个厂家多个代理',
  `proxyName` varchar(64) DEFAULT NULL COMMENT '代理名称',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='factory_proxy';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  -- `proxyID` int(11) DEFAULT NULL COMMENT '厂家代理ID，一个代理多个店铺',
  `shopName` varchar(64) DEFAULT NULL COMMENT '店铺名称',
  `shopLocation` varchar(255) DEFAULT NULL COMMENT '店铺位置',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdate` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='设备投放的店铺信息';
/*!40101 SET character_set_client = @saved_cs_client */;
