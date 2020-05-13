/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : game

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 13/05/2020 09:26:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for make_up_bonus
-- ----------------------------
DROP TABLE IF EXISTS `make_up_bonus`;
CREATE TABLE `make_up_bonus`  (
  `orderId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `merchantId` bigint(20) NOT NULL COMMENT '商户id',
  `payType` tinyint(4) NOT NULL COMMENT '0是微信，1是支付宝',
  `makeUpFlag` bit(1) NOT NULL COMMENT '是否补偿',
  PRIMARY KEY (`orderId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of make_up_bonus
-- ----------------------------
INSERT INTO `make_up_bonus` VALUES ('12312321', 12312321, 21321321, 1, b'0');
INSERT INTO `make_up_bonus` VALUES ('123123211', 1231232111, 21321321, 1, b'0');
INSERT INTO `make_up_bonus` VALUES ('20200110110039870_390002_4824', 390002, 907001, 0, b'0');
INSERT INTO `make_up_bonus` VALUES ('20200110140131052_390003_1445', 390003, 907001, 0, b'0');
INSERT INTO `make_up_bonus` VALUES ('20200110140211560_390004_3810', 390004, 907001, 0, b'0');
INSERT INTO `make_up_bonus` VALUES ('20200110140243717_392002_0611', 392002, 907001, 0, b'0');
INSERT INTO `make_up_bonus` VALUES ('20200110164335619_396002_0732', 396002, 907001, 0, b'0');
INSERT INTO `make_up_bonus` VALUES ('20200110164438444_396003_6854', 396003, 907001, 0, b'0');
INSERT INTO `make_up_bonus` VALUES ('20200110164509144_398000_0960', 398000, 907001, 0, b'0');
INSERT INTO `make_up_bonus` VALUES ('20200110170020423_400000_9671', 400000, 907001, 0, b'0');
INSERT INTO `make_up_bonus` VALUES ('20200110173244068_402000_7350', 402000, 809001, 0, b'0');
INSERT INTO `make_up_bonus` VALUES ('20200110184927065_408003_1106', 408003, 917001, 0, b'0');
INSERT INTO `make_up_bonus` VALUES ('20200111164405909_412001_4366', 412001, 809001, 0, b'0');
INSERT INTO `make_up_bonus` VALUES ('20200111164554140_412002_5569', 412002, 809001, 1, b'0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` int(11) NOT NULL,
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'ww', '111');

SET FOREIGN_KEY_CHECKS = 1;
