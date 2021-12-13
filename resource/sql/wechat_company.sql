
CREATE TABLE `pe_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `department_id` bigint(18) NOT NULL DEFAULT '0' COMMENT '企业微信部门id',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint(18) NOT NULL DEFAULT '0' COMMENT '父部门id',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除 0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='部门表';

CREATE TABLE `pe_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` varchar(100) NOT NULL DEFAULT '' COMMENT '成员id',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '成员名称',
  `position` varchar(50) NOT NULL DEFAULT '' COMMENT '职位',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `gender` tinyint(2) NOT NULL DEFAULT '0' COMMENT '性别 0未知 1男 2女',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `thumb_avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像缩略图',
  `telephone` varchar(20) NOT NULL DEFAULT '' COMMENT '座机',
  `alias` varchar(50) NOT NULL DEFAULT '' COMMENT '别名',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '地址',
  `open_user_id` varchar(100) NOT NULL DEFAULT '' COMMENT '全局唯一   仅第三方应用可获取',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态 1已激活 2已禁用 4未激活 5退出企业',
  `qr_code` varchar(255) NOT NULL DEFAULT '' COMMENT '二维码',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除 0否 1是',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_email` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='成员表';

CREATE TABLE `pr_user_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` varchar(50) NOT NULL DEFAULT '' COMMENT '企业微信成员id',
  `department_id` bigint(18) NOT NULL DEFAULT '0' COMMENT '企业微信部门id',
  `is_main` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否为主要部门 0否 1是',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` tinyint(2) NOT NULL COMMENT '是否删除 0否',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_department_id` (`department_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='成员部门表';

CREATE TABLE `pe_user_external_attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` varchar(50) NOT NULL DEFAULT '' COMMENT '成员id',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '类型 0文本 1网页 2小程序',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '属性名称',
  `value` varchar(20) NOT NULL DEFAULT '' COMMENT '文本属性值',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '网页的url',
  `title` varchar(20) NOT NULL DEFAULT '' COMMENT '标题',
  `app_id` varchar(100) NOT NULL DEFAULT '' COMMENT '小程序appid',
  `page_path` varchar(255) NOT NULL DEFAULT '' COMMENT '小程序页面路径',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除 0否 1是',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成员对外属性表';

CREATE TABLE `pe_external_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `external_user_id` varchar(100) NOT NULL DEFAULT '' COMMENT '外部联系人id',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '外部联系人名称',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '类型 1微信 2企业微信',
  `gender` tinyint(2) NOT NULL DEFAULT '0' COMMENT '性别 0未知 1男 2女',
  `union_id` varchar(100) NOT NULL DEFAULT '' COMMENT '微信开放平台的身份标识',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_external_user_id` (`external_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='外部联系人表';

CREATE TABLE `pe_external_user_follow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` varchar(20) NOT NULL DEFAULT '' COMMENT '成员id',
  `external_user_id` varchar(50) NOT NULL DEFAULT '' COMMENT '外部联系人id',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `remark_corp_name` varchar(255) NOT NULL DEFAULT '' COMMENT '备注的企业名称',
  `remark_mobiles` varchar(255) NOT NULL DEFAULT '' COMMENT '备注的手机号',
  `add_way` tinyint(2) NOT NULL DEFAULT '0' COMMENT '添加方式',
  `add_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `oper_user_id` varchar(100) NOT NULL DEFAULT '' COMMENT '发起添加的用户id',
  `state` varchar(100) NOT NULL DEFAULT '' COMMENT '企业自定义的state',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除 0否 1是',
  `delete_by` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除方 1成员 2外部联系人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_external_user_id` (`external_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='外部联系人和成员关系表';