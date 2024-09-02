/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50742
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50742
 File Encoding         : 65001

 Date: 24/08/2023 20:55:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog_browse
-- ----------------------------
DROP TABLE IF EXISTS `blog_browse`;
CREATE TABLE `blog_browse`  (
  `browse_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '浏览的id',
  `browse_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览者的ip',
  `browse_location` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览者的位置',
  `browse_req_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求url',
  `browse_req_method` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法类型get post delete ...',
  `browse_req_param` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `browse_json_resp` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '响应内容',
  `browse_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '浏览时间',
  `browse_browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `browse_os` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browse_cookie` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存入cookie',
  PRIMARY KEY (`browse_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_browse
-- ----------------------------
INSERT INTO `blog_browse` VALUES ('046e34e624b4c5d3c055f91f8eba2c46', '127.0.0.1', NULL, '/blog/blogList/1', 'com.whai.blog.controller.pub.PubBlogController.getBlogList', '{}', NULL, '2023-06-23 14:34:29', NULL, 'Windows --- Chrome-114.0.0.0', NULL);
INSERT INTO `blog_browse` VALUES ('0a129a2b841d9605ffd673d5a3ef199e', '127.0.0.1', NULL, '/blog/getHomeBlogs', 'com.whai.blog.controller.pub.PubBlogController.getHomeBlogs', '{}', NULL, '2023-06-23 14:15:55', NULL, 'Windows --- Chrome-114.0.0.0', NULL);
INSERT INTO `blog_browse` VALUES ('28cbe67f5aa2f23fcdda48b69a357eb3', '127.0.0.1', NULL, '/blog/getHomeBlogs', 'com.whai.blog.controller.pub.PubBlogController.getHomeBlogs', '{}', NULL, '2023-06-23 14:16:06', NULL, 'Windows --- Chrome-114.0.0.0', NULL);
INSERT INTO `blog_browse` VALUES ('442d865ed2dff5db5a083d9a21472bda', '127.0.0.1', NULL, '/blog/blogList/1', 'com.whai.blog.controller.pub.PubBlogController.getBlogList', '{}', NULL, '2023-07-21 14:52:19', NULL, 'Windows --- Chrome-114.0.0.0', NULL);
INSERT INTO `blog_browse` VALUES ('48a578f9b3ccd6765fb13a48ea391c97', '127.0.0.1', NULL, '/blog/getHomeBlogs', 'com.whai.blog.controller.pub.PubBlogController.getHomeBlogs', '{}', NULL, '2023-06-23 14:15:24', NULL, 'Windows --- Chrome-114.0.0.0', NULL);
INSERT INTO `blog_browse` VALUES ('6342b8879d40544a18e238101ea5c37c', '127.0.0.1', NULL, '/blog/blogList/1', 'com.whai.blog.controller.pub.PubBlogController.getBlogList', '{}', NULL, '2023-06-23 14:15:50', NULL, 'Windows --- Chrome-114.0.0.0', NULL);
INSERT INTO `blog_browse` VALUES ('75e01a5d7fe916c753a9232aa140753d', '127.0.0.1', NULL, '/blog/getHomeBlogs', 'com.whai.blog.controller.pub.PubBlogController.getHomeBlogs', '{}', NULL, '2023-06-23 14:14:12', NULL, 'Windows --- Chrome-114.0.0.0', NULL);
INSERT INTO `blog_browse` VALUES ('7d9805b9cccee279bd03b22ebd11ffde', '127.0.0.1', NULL, '/blog/getHomeBlogs', 'com.whai.blog.controller.pub.PubBlogController.getHomeBlogs', '{}', NULL, '2023-06-23 14:15:59', NULL, 'Windows --- Chrome-114.0.0.0', NULL);
INSERT INTO `blog_browse` VALUES ('909b3f8f393a473be4983d180084dd06', '127.0.0.1', NULL, '/blog/getHomeBlogs', 'com.whai.blog.controller.pub.PubBlogController.getHomeBlogs', '{}', NULL, '2023-06-23 14:34:24', NULL, 'Windows --- Chrome-114.0.0.0', NULL);
INSERT INTO `blog_browse` VALUES ('92c1159f139779a2e7bda96f8b6bf692', '127.0.0.1', NULL, '/blog/getHomeBlogs', 'com.whai.blog.controller.pub.PubBlogController.getHomeBlogs', '{}', NULL, '2023-06-23 14:15:27', NULL, 'Windows --- Chrome-114.0.0.0', NULL);
INSERT INTO `blog_browse` VALUES ('a4628a2cb0b1f2c7521037d20ba38595', '127.0.0.1', NULL, '/blog/getHomeBlogs', 'com.whai.blog.controller.pub.PubBlogController.getHomeBlogs', '{}', NULL, '2023-06-23 14:14:43', NULL, 'Windows --- Chrome-114.0.0.0', NULL);
INSERT INTO `blog_browse` VALUES ('c2cda8c348b3582454f4af9142b8af9c', '127.0.0.1', NULL, '/blog/getHomeBlogs', 'com.whai.blog.controller.pub.PubBlogController.getHomeBlogs', '{}', NULL, '2023-07-21 14:52:14', NULL, 'Windows --- Chrome-114.0.0.0', NULL);

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment`  (
  `blog_comment_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blog_comment_session` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `blog_comment_ip` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `blog_comment_blog_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `blog_comment_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `blog_comment_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `blog_comment_content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `blog_comment_like` int(255) NULL DEFAULT 0,
  `blog_comment_delete` int(2) NULL DEFAULT 0,
  PRIMARY KEY (`blog_comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_comment
-- ----------------------------

-- ----------------------------
-- Table structure for blog_main
-- ----------------------------
DROP TABLE IF EXISTS `blog_main`;
CREATE TABLE `blog_main`  (
  `blog_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blog_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `blog_create_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者user_name',
  `blog_content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文本内容',
  `blog_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `blog_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储的md文件',
  `blog_tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'blog标签',
  `blog_views` int(255) UNSIGNED ZEROFILL NULL DEFAULT 000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000 COMMENT '浏览数量',
  `blog_last_change_time` datetime NULL DEFAULT NULL COMMENT '最后一次修改时间',
  `blog_like` int(20) NULL DEFAULT 0 COMMENT '喜欢这个博客',
  `blog_intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客简介',
  `blog_cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面图片位置地址',
  `blog_delete` int(2) NULL DEFAULT 0 COMMENT '逻辑删除 1为删除 0为正常',
  PRIMARY KEY (`blog_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_main
-- ----------------------------
INSERT INTO `blog_main` VALUES ('d71ea1bb5dfda6287401da279a152f24', '测试', NULL, '爱的速递撒1', '2023-06-23 14:15:16', NULL, '2', 000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000, NULL, 0, '1', '', 0);

-- ----------------------------
-- Table structure for blog_main_tag
-- ----------------------------
DROP TABLE IF EXISTS `blog_main_tag`;
CREATE TABLE `blog_main_tag`  (
  `blog_tag_id` int(11) NOT NULL,
  `blog_main_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`blog_tag_id`, `blog_main_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_main_tag
-- ----------------------------
INSERT INTO `blog_main_tag` VALUES (2, 'd71ea1bb5dfda6287401da279a152f24');

-- ----------------------------
-- Table structure for file_download
-- ----------------------------
DROP TABLE IF EXISTS `file_download`;
CREATE TABLE `file_download`  (
  `file_download_id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '下载操作的id',
  `file_download_ip` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下载的ip',
  `file_download_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下载者的地址',
  `file_download_file` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下载文件的id',
  `file_download_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下载时间',
  `file_download_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求url',
  `file_download_resp_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '响应情况',
  PRIMARY KEY (`file_download_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file_download
-- ----------------------------

-- ----------------------------
-- Table structure for file_main
-- ----------------------------
DROP TABLE IF EXISTS `file_main`;
CREATE TABLE `file_main`  (
  `file_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称',
  `file_upload_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'file上传时间',
  `file_upload_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传者',
  `file_upload_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传地点',
  `file_upload_ip` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传者ip',
  `file_like` int(20) UNSIGNED ZEROFILL NULL DEFAULT 00000000000000000000 COMMENT '喜欢这个文件',
  `file_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件标题',
  `file_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件上传位置',
  `file_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传文件类型',
  `file_system` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传文件属于什么系统',
  `file_remote_url` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '远程文件的url，如有必要',
  `file_info` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'file介绍信息',
  `file_other_intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他信息',
  `file_delete` int(2) NULL DEFAULT 0 COMMENT '0表示正常，1表示删除，逻辑删除',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file_main
-- ----------------------------

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (5);

-- ----------------------------
-- Table structure for home
-- ----------------------------
DROP TABLE IF EXISTS `home`;
CREATE TABLE `home`  (
  `home_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主页版次',
  `home_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `home_html` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用于配置一些页面',
  `home_status` int(2) NOT NULL DEFAULT 0 COMMENT '1表示激活',
  PRIMARY KEY (`home_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of home
-- ----------------------------
INSERT INTO `home` VALUES (1, '', '- :wave: Hi, I’m @whaibetter\n- :eyes: I am currently studying the postgraduate class of ***Computer Technology* in Zhejiang university of technology(ZJUT)** of China\n  -  I studied the undergraduate class of ***Data science and Big data technology* in Zhejiang university of science and technology(ZUST)** of China\n- :palm_tree: I’m currently learning java and big data\n- :two_hearts:\n- :bird: How to reach me: \n  - wechat:***whaihalo***\n  - :email: email: ***whaifree@163.com***\n\n---------\n\n- :wave:嗨，我是@whaibetter\n- :eye: 我目前就为在**浙江工业大学就读*计算机技术***专业硕士研究生\n  - 我本科毕业于**浙江科技学院*数据科学与大数据技术专业***\n- :palm_tree: 目前正在学习java和大数据\n- :two_hearts: 联系我?\n  - 微信:***whaihalo***\n  - :email::邮箱:***whaifree@163.com***\n\n\n<img src=\"http://whai.space/api/img/微信图片_20230420200626.jpg\"  height = \"400\" alt=\"图片名称\" align=center />\n\n<iframe src=\"//player.bilibili.com/player.html?aid=82959719&bvid=BV1wJ411p7hM&cid=141916896&page=1\" scrolling=\"no\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\" width=\"600px\" height= \"400px\"> </iframe>', 0);

-- ----------------------------
-- Table structure for img_main
-- ----------------------------
DROP TABLE IF EXISTS `img_main`;
CREATE TABLE `img_main`  (
  `img_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `img_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片标题',
  `img_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传图片的位置',
  `img_upload_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  `img_upload_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传者',
  `img_delete` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除',
  `img_tag_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'img的ltag类型',
  PRIMARY KEY (`img_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of img_main
-- ----------------------------

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `message_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `message_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `message_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `message_cookie` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cookie',
  `message_like` int(20) NULL DEFAULT 0 COMMENT '标记喜欢',
  `message_father_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父id',
  `message_delete` int(2) NULL DEFAULT 0 COMMENT '逻辑删除。1表示正常，0表示删除',
  `message_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留言内容',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('25b2fa56284d9a31bece5b9093b16966', '127.0.0.1', '2023-07-21 14:52:25', NULL, 0, NULL, 1, '1');
INSERT INTO `message` VALUES ('86dd50c2facd01c76820c197e32d913f', '127.0.0.1', '2023-07-21 15:09:43', NULL, 0, NULL, 0, '第三方方法');
INSERT INTO `message` VALUES ('8ead696fcdb3e43889c89a381dc5a5b9', '127.0.0.1', '2023-07-21 15:10:14', NULL, 0, NULL, 0, '第三方');

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('refreshMemoryScheduler', 'TASK_CLASS_NAME12', 'g1', '1-3 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('refreshMemoryScheduler', 'TASK_CLASS_NAME12', 'g1', NULL, 'com.whai.blog.utils.schedule.CustomJobExecution', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F504552544945537372001A636F6D2E776861692E626C6F672E6D6F64656C2E5379734A6F62000000000000000102000D4C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C0008637265617465427971007E00094C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C000C696E766F6B6554617267657471007E00094C00086A6F6247726F757071007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C00076A6F624E616D6571007E00094C000D6D697366697265506F6C69637971007E00094C000672656D61726B71007E00094C000673746174757371007E00094C0008757064617465427971007E00094C000A75706461746554696D6571007E000A78707400013174000561646D696E7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000018948EACDD87874000D312D33202A202A202A202A203F740037636F6D2E776861692E626C6F672E7574696C732E7363686564756C652E526566726573684D656D6F72794A6F622E65786563757465282974000267317372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000C7400026E317400013374000074000131740000707800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('refreshMemoryScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int(11) NULL DEFAULT NULL,
  `INT_PROP_2` int(11) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PRIORITY` int(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('refreshMemoryScheduler', 'TASK_CLASS_NAME12', 'g1', 'TASK_CLASS_NAME12', 'g1', NULL, 1689164101000, -1, 5, 'PAUSED', 'CRON', 1689164059000, 0, NULL, 2, '');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (12, 'n1', 'g1', 'com.whai.blog.utils.schedule.RefreshMemoryJob.execute()', '1-3 * * * * ?', '3', '1', '1', 'admin', '2023-07-12 15:04:55', '', NULL, '');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `opera_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `business_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '业务类型 crud',
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '方法名称 crud',
  `request_method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求方式 get post',
  `operator_type` int(1) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `oper_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '返回参数',
  `status` int(1) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`opera_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1582 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (1535, 'GET', 'SELECT', 'com.whai.blog.controller.pub.PubHomeController.getHome', 0, '获取该页面', '/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:14:12');
INSERT INTO `sys_oper_log` VALUES (1536, 'GET', 'SELECT', 'com.whai.blog.controller.pub.PubHomeController.getHome', 0, '获取该页面', '/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:14:43');
INSERT INTO `sys_oper_log` VALUES (1537, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:15:06');
INSERT INTO `sys_oper_log` VALUES (1538, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:15:06');
INSERT INTO `sys_oper_log` VALUES (1539, 'POST', 'ADD', 'com.whai.blog.controller.admin.BlogController.addBlog', 0, '新增blog', '/admin/blog/addBlog', '127.0.0.1', '', '97', NULL, 0, '', '2023-06-23 14:15:16');
INSERT INTO `sys_oper_log` VALUES (1540, 'GET', 'SELECT', 'com.whai.blog.controller.pub.PubHomeController.getHome', 0, '获取该页面', '/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:15:24');
INSERT INTO `sys_oper_log` VALUES (1541, 'GET', 'SELECT', 'com.whai.blog.controller.pub.PubHomeController.getHome', 0, '获取该页面', '/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:15:27');
INSERT INTO `sys_oper_log` VALUES (1542, 'GET', 'SELECT', 'com.whai.blog.controller.pub.PubHomeController.getHome', 0, '获取该页面', '/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:15:55');
INSERT INTO `sys_oper_log` VALUES (1543, 'GET', 'SELECT', 'com.whai.blog.controller.pub.PubHomeController.getHome', 0, '获取该页面', '/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:15:59');
INSERT INTO `sys_oper_log` VALUES (1544, 'GET', 'SELECT', 'com.whai.blog.controller.pub.PubHomeController.getHome', 0, '获取该页面', '/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:16:06');
INSERT INTO `sys_oper_log` VALUES (1545, 'GET', 'SELECT', 'com.whai.blog.controller.pub.PubHomeController.getHome', 0, '获取该页面', '/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:16:25');
INSERT INTO `sys_oper_log` VALUES (1546, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:16:34');
INSERT INTO `sys_oper_log` VALUES (1547, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:16:34');
INSERT INTO `sys_oper_log` VALUES (1548, 'GET', 'SELECT', 'com.whai.blog.controller.pub.PubHomeController.getHome', 0, '获取该页面', '/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:17:15');
INSERT INTO `sys_oper_log` VALUES (1549, 'GET', 'SELECT', 'com.whai.blog.controller.pub.PubHomeController.getHome', 0, '获取该页面', '/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:17:33');
INSERT INTO `sys_oper_log` VALUES (1550, 'GET', 'SELECT', 'com.whai.blog.controller.pub.PubHomeController.getHome', 0, '获取该页面', '/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-06-23 14:34:24');
INSERT INTO `sys_oper_log` VALUES (1551, 'POST', 'UPDATE', 'com.whai.blog.controller.admin.BlogController.updateBlog', 0, '更新blog', '/admin/blog/updateBlog', '127.0.0.1', '', '2', NULL, 0, '\r\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'WHERE blog_id=null  AND blog_delete=0\' at line 1\r\n### The error may exist in com/whai/blog/mapper/BlogMainMapper.java (best guess)\r\n### The error may involve com.whai.blog.mapper.BlogMainMapper.updateById-Inline\r\n### The error occurred while setting parameters\r\n### SQL: UPDATE blog_main    WHERE blog_id=?  AND blog_delete=0\r\n### Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'WHERE blog_id=null  AND blog_delete=0\' at line 1\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'WHERE blog_id=null  AND blog_delete=0\' at line 1', '2023-07-12 17:38:38');
INSERT INTO `sys_oper_log` VALUES (1552, 'POST', 'UPDATE', 'com.whai.blog.controller.admin.BlogController.updateBlog', 0, '更新blog', '/admin/blog/updateBlog', '127.0.0.1', '', '2', NULL, 0, '\r\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'WHERE blog_id=null  AND blog_delete=0\' at line 1\r\n### The error may exist in com/whai/blog/mapper/BlogMainMapper.java (best guess)\r\n### The error may involve com.whai.blog.mapper.BlogMainMapper.updateById-Inline\r\n### The error occurred while setting parameters\r\n### SQL: UPDATE blog_main    WHERE blog_id=?  AND blog_delete=0\r\n### Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'WHERE blog_id=null  AND blog_delete=0\' at line 1\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'WHERE blog_id=null  AND blog_delete=0\' at line 1', '2023-07-12 17:40:11');
INSERT INTO `sys_oper_log` VALUES (1553, 'POST', 'UPDATE', 'com.whai.blog.controller.admin.BlogController.updateBlog', 0, '更新blog', '/admin/blog/updateBlog', '127.0.0.1', '', '146', NULL, 0, '', '2023-07-12 17:41:05');
INSERT INTO `sys_oper_log` VALUES (1554, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-12 17:42:04');
INSERT INTO `sys_oper_log` VALUES (1555, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-12 17:42:04');
INSERT INTO `sys_oper_log` VALUES (1556, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-14 14:59:48');
INSERT INTO `sys_oper_log` VALUES (1557, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-14 14:59:48');
INSERT INTO `sys_oper_log` VALUES (1558, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-14 15:03:24');
INSERT INTO `sys_oper_log` VALUES (1559, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-14 15:03:24');
INSERT INTO `sys_oper_log` VALUES (1560, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-14 15:05:46');
INSERT INTO `sys_oper_log` VALUES (1561, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-14 15:05:47');
INSERT INTO `sys_oper_log` VALUES (1562, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-14 15:37:31');
INSERT INTO `sys_oper_log` VALUES (1563, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-14 15:37:32');
INSERT INTO `sys_oper_log` VALUES (1564, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-14 16:29:10');
INSERT INTO `sys_oper_log` VALUES (1565, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-14 16:29:10');
INSERT INTO `sys_oper_log` VALUES (1566, 'GET', 'SELECT', 'com.whai.blog.controller.pub.PubHomeController.getHome', 0, '获取该页面', '/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-21 14:52:14');
INSERT INTO `sys_oper_log` VALUES (1567, 'GET', 'SELECT', 'com.whai.blog.controller.pub.PubHomeController.getHome', 0, '获取该页面', '/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-21 14:52:30');
INSERT INTO `sys_oper_log` VALUES (1568, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-23 15:34:00');
INSERT INTO `sys_oper_log` VALUES (1569, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-23 15:34:01');
INSERT INTO `sys_oper_log` VALUES (1570, 'POST', 'UPDATE', 'com.whai.blog.controller.admin.UserController.updateUser', 0, '更新user', '/admin/user/updateUser', '127.0.0.1', '', '244', NULL, 0, '', '2023-07-23 15:34:10');
INSERT INTO `sys_oper_log` VALUES (1571, 'POST', 'UPDATE', 'com.whai.blog.controller.admin.UserController.updateUser', 0, '更新user', '/admin/user/updateUser', '127.0.0.1', '', '245', NULL, 0, '', '2023-07-23 15:34:14');
INSERT INTO `sys_oper_log` VALUES (1572, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-23 15:37:28');
INSERT INTO `sys_oper_log` VALUES (1573, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-23 15:37:28');
INSERT INTO `sys_oper_log` VALUES (1574, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-23 15:40:36');
INSERT INTO `sys_oper_log` VALUES (1575, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-23 15:40:36');
INSERT INTO `sys_oper_log` VALUES (1576, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-23 15:43:27');
INSERT INTO `sys_oper_log` VALUES (1577, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-07-23 15:43:27');
INSERT INTO `sys_oper_log` VALUES (1578, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-08-18 11:29:59');
INSERT INTO `sys_oper_log` VALUES (1579, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-08-18 11:29:59');
INSERT INTO `sys_oper_log` VALUES (1580, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-08-18 21:43:42');
INSERT INTO `sys_oper_log` VALUES (1581, 'GET', 'SELECT', 'com.whai.blog.controller.admin.HomeController.getHome', 0, '获取该页面', '/admin/home/getHomePage/1', '127.0.0.1', '', '2', NULL, 0, '', '2023-08-18 21:43:42');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_login_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登陆账号，登陆id',
  `user_nickname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `user_email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像路径',
  `user_password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号状态',
  `user_login_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登陆ip',
  `user_login_date` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登陆时间',
  `user_pwd_update_date` datetime NULL DEFAULT NULL COMMENT '最后一次修改密码的时间',
  `user_info_update_time` datetime NULL DEFAULT NULL COMMENT '用户更新时间',
  `user_skill` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '技能列表',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', 'whaihalo', 'whaifree@163.com', '15858128962', '\\api\\img\\msedge_ZhHLchdNJq.png', '$2a$10$TPiIo6No2Dtorr01Bap4leJtoU4PEJyv4NR6WZEmkLPrE/u1W3R6m', '1', '127.0.0.1', '2023-12-08 16:36:18', NULL, NULL, '[\"spring\",\"mysql\",\"vue\",\"thymeleaf\"]');

-- ----------------------------
-- Table structure for sys_user_online
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_online`;
CREATE TABLE `sys_user_online`  (
  `session_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_login_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_ipaddr` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登陆的ip',
  `login_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登陆地点',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器类型',
  `os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统类型',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在线状态',
  `session_start_time` datetime NULL DEFAULT NULL COMMENT 'session创建时间',
  `session_last_time` datetime NULL DEFAULT NULL COMMENT 'session最后一次的时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_online
-- ----------------------------

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tag_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tag_delete` int(2) NULL DEFAULT 0 COMMENT '逻辑删除，1表示被删除，0为正常标签',
  `tag_info` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '标签的介绍',
  `tag_parents_id` int(11) NULL DEFAULT NULL COMMENT '父标签id',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (2, '默认标签', '2023-04-01 17:30:22', 0, 'default', NULL);

-- ----------------------------
-- Table structure for tutorials
-- ----------------------------
DROP TABLE IF EXISTS `tutorials`;
CREATE TABLE `tutorials`  (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `published` bit(1) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tutorials
-- ----------------------------
INSERT INTO `tutorials` VALUES (1, 'de', b'0', 'title');
INSERT INTO `tutorials` VALUES (2, 'de', b'0', 'title');
INSERT INTO `tutorials` VALUES (3, 'de', b'0', 'title');
INSERT INTO `tutorials` VALUES (4, 'de', b'0', 'title');

SET FOREIGN_KEY_CHECKS = 1;
