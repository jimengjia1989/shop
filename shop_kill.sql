
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table for item_kill
-- ----------------------------
DROP TABLE IF EXISTS `item_kill`;
CREATE TABLE `item_kill` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `total` int(11) DEFAULT NULL COMMENT '可被秒杀的总数',
  `start_time` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  `is_active` tinyint(11) DEFAULT '1' COMMENT '是否有效（1=是；0=否）',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='待秒杀商品表';

-- ----------------------------
-- Data of item_kill
-- ----------------------------
INSERT INTO `item_kill` VALUES ('1','100', '2020-06-26 21:59:07', '2019-07-23 21:59:11', '1', '2020-06-26 21:59:07');

-- ----------------------------
-- Table  for item_kill_success
-- ----------------------------
DROP TABLE IF EXISTS `item_kill_success`;
CREATE TABLE `item_kill_success` (
  `code` varchar(50) NOT NULL COMMENT '秒杀成功生成的订单编号',
  `kill_id` int(11) DEFAULT NULL COMMENT '秒杀id',
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户id',
  `status` tinyint(4) DEFAULT '-1' COMMENT '秒杀结果: -1无效  0成功(未付款)  1已付款',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功订单表';
