-- 创建数据库
CREATE DATABASE IF NOT EXISTS `vickze_auth` DEFAULT CHARACTER SET utf8mb4;

USE vickze_auth;

-- 系统
CREATE TABLE IF NOT EXISTS `system` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '名称',
  `key` varchar(32) NOT NULL COMMENT 'Key',
  `intercept_interface` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否开启接口拦截',
  `not_resource_login` tinyint(1) NOT NULL DEFAULT 0  COMMENT '是否开启接口拦截',
  `create_time` datetime COMMENT '创建时间',
  `update_time` datetime COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统';

-- 菜单
CREATE TABLE IF NOT EXISTS `menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `system_id` bigint NOT NULL COMMENT '系统ID',
  `parent_id` bigint COMMENT '父ID',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `create_time` datetime COMMENT '创建时间',
  `update_time` datetime COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单';

-- 菜单资源
CREATE TABLE IF NOT EXISTS `menu_resource` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `system_id` bigint NOT NULL COMMENT '系统ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `permission` varchar(256) NOT NULL COMMENT '权限标识',
  `create_time` datetime COMMENT '创建时间',
  `update_time` datetime COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单资源';

-- 菜单资源与接口对应关系
CREATE TABLE IF NOT EXISTS `menu_resource_interface` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `system_id` bigint NOT NULL COMMENT '系统ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `menu_resource_id` bigint NOT NULL COMMENT '菜单资源ID',
  `interface_uri` varchar(256) NOT NULL COMMENT '接口uri',
  `interface_method` varchar(8) NOT NULL COMMENT '接口方法',
  `create_time` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单资源与接口对应关系';

-- 角色
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime COMMENT '创建时间',
  `update_time` datetime COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';


-- 用户
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_time` datetime COMMENT '创建时间',
  `update_time` datetime COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- 用户与角色对应关系
CREATE TABLE IF NOT EXISTS `user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `create_time` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色对应关系';

-- 角色与菜单资源对应关系
CREATE TABLE IF NOT EXISTS `role_menu_resource` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `system_id` bigint DEFAULT NULL COMMENT '系统ID',
  `menu_resource_id` bigint DEFAULT NULL COMMENT '菜单资源ID',
  `create_time` datetime COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单资源对应关系';


INSERT INTO `system`(`id`, `name`, `key`, `intercept_interface`, `not_resource_login`, `create_time`, `update_time`) VALUES (1, '权限控制中心', 'vickze-auth', 0, 0, now(), now());
INSERT INTO `system`(`id`, `name`, `key`, `intercept_interface`, `not_resource_login`, `create_time`, `update_time`) VALUES (2, '代码生成系统', 'vickze-generator', 0, 1, now(), now());


INSERT INTO `menu`(`id`, `system_id`, `parent_id`, `name`, `create_time`, `update_time`) VALUES (1, 1, NULL, '系统', now(), now());
INSERT INTO `menu`(`id`, `system_id`, `parent_id`, `name`, `create_time`, `update_time`) VALUES (2, 1, 1, '系统列表', now(), now());
INSERT INTO `menu`(`id`, `system_id`, `parent_id`, `name`, `create_time`, `update_time`) VALUES (3, 1, NULL, '菜单', now(), now());
INSERT INTO `menu`(`id`, `system_id`, `parent_id`, `name`, `create_time`, `update_time`) VALUES (4, 1, 3, '菜单列表', now(), now());
INSERT INTO `menu`(`id`, `system_id`, `parent_id`, `name`, `create_time`, `update_time`) VALUES (5, 1, NULL, '菜单资源', now(), now());
INSERT INTO `menu`(`id`, `system_id`, `parent_id`, `name`, `create_time`, `update_time`) VALUES (6, 1, 5, '菜单资源列表', now(), now());
INSERT INTO `menu`(`id`, `system_id`, `parent_id`, `name`, `create_time`, `update_time`) VALUES (7, 1, NULL, '角色', now(), now());
INSERT INTO `menu`(`id`, `system_id`, `parent_id`, `name`, `create_time`, `update_time`) VALUES (8, 1, 7, '角色列表', now(), now());
INSERT INTO `menu`(`id`, `system_id`, `parent_id`, `name`, `create_time`, `update_time`) VALUES (9, 1, NULL, '用户', now(), now());
INSERT INTO `menu`(`id`, `system_id`, `parent_id`, `name`, `create_time`, `update_time`) VALUES (10, 1, 9, '用户列表', now(), now());


INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (1, 1, 2, '查看', 'auth:system:view', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (2, 1, 2, '新增', 'auth:system:add', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (3, 1, 2, '详情', 'auth:system:profile', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (4, 1, 2, '修改', 'auth:system:edit', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (5, 1, 2, '复制', 'auth:system:duplicate', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (6, 1, 2, '删除', 'auth:system:delete', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (7, 1, 4, '查看', 'auth:menu:view', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (8, 1, 4, '新增', 'auth:menu:add', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (9, 1, 4, '详情', 'auth:menu:profile', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (10, 1, 4, '修改', 'auth:menu:edit', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (11, 1, 4, '复制', 'auth:menu:duplicate', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (12, 1, 4, '删除', 'auth:menu:delete', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (13, 1, 6, '查看', 'auth:menuResource:view', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (14, 1, 6, '新增', 'auth:menuResource:add', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (15, 1, 6, '详情', 'auth:menuResource:profile', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (16, 1, 6, '编辑', 'auth:menuResource:edit', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (17, 1, 6, '复制', 'auth:menuResource:duplicate', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (18, 1, 6, '删除', 'auth:menuResource:delete', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (19, 1, 8, '查看', 'auth:role:view', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (20, 1, 8, '新增', 'auth:role:add', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (21, 1, 8, '详情', 'auth:role:profile', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (22, 1, 8, '编辑', 'auth:role:edit', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (23, 1, 8, '复制', 'auth:role:duplicate', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (24, 1, 8, '删除', 'auth:role:delete', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (25, 1, 8, '分配权限', 'auth:role:assignPermission', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (26, 1, 10, '查看', 'auth:user:view', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (27, 1, 10, '新增', 'auth:user:add', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (28, 1, 10, '详情', 'auth:user:profile', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (29, 1, 10, '编辑', 'auth:user:edit', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (30, 1, 10, '复制', 'auth:user:duplicate', now(), now());
INSERT INTO `menu_resource`(`id`, `system_id`, `menu_id`, `name`, `permission`, `create_time`, `update_time`) VALUES (31, 1, 10, '删除', 'auth:user:delete', now(), now());


INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 2, 1, '/api/auth/system', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 2, 2, '/api/auth/system', 'POST', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 2, 3, '/api/auth/system/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 2, 4, '/api/auth/system/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 2, 4, '/api/auth/system', 'PUT', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 2, 5, '/api/auth/system/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 2, 5, '/api/auth/system', 'POST', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 2, 6, '/api/auth/system', 'DELETE', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 4, 8, '/api/auth/menu', 'POST', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 4, 9, '/api/auth/menu/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 4, 11, '/api/auth/menu/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 4, 11, '/api/auth/menu', 'POST', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 4, 10, '/api/auth/menu/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 4, 10, '/api/auth/menu', 'PUT', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 4, 12, '/api/auth/menu', 'DELETE', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 6, 14, '/api/auth/menuResource', 'POST', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 6, 15, '/api/auth/menuResource/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 6, 16, '/api/auth/menuResource/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 6, 16, '/api/auth/menuResource', 'PUT', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 6, 17, '/api/auth/menuResource/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 6, 17, '/api/auth/menuResource', 'POST', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 6, 18, '/api/auth/menuResource', 'DELETE', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 8, 19, '/api/auth/role', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 8, 21, '/api/auth/role/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 8, 22, '/api/auth/role/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 8, 22, '/api/auth/role', 'PUT', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 8, 23, '/api/auth/role/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 8, 23, '/api/auth/role', 'POST', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 8, 24, '/api/auth/role', 'DELETE', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 8, 20, '/api/auth/role', 'POST', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 8, 25, '/api/auth/system', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 8, 25, '/api/auth/menu', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 8, 25, '/api/auth/menuResource', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 8, 25, '/api/auth/role/assignMenuResource', 'POST', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 10, 26, '/api/auth/user', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 10, 27, '/api/auth/user', 'POST', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 10, 28, '/api/auth/user/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 10, 29, '/api/auth/user/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 10, 29, '/api/auth/user', 'PUT', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 10, 30, '/api/auth/user/{id}', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 10, 30, '/api/auth/user', 'POST', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 10, 31, '/api/auth/user', 'DELETE', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 6, 13, '/api/auth/system', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 6, 13, '/api/auth/menu/tree', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 6, 13, '/api/auth/menuResource', 'GET', now());
INSERT INTO `menu_resource_interface`(`system_id`, `menu_id`, `menu_resource_id`, `interface_uri`, `interface_method`, `create_time`) VALUES (1, 4, 7, '/api/auth/menu/tree', 'GET', now());


INSERT INTO `role`(`id`, `name`, `remark`, `create_time`, `update_time`) VALUES (1, '管理员', '管理员', now(), now());
INSERT INTO `role`(`id`, `name`, `remark`, `create_time`, `update_time`) VALUES (2, '系统管理', '权限控制中心-系统管理', now(), now());
INSERT INTO `role`(`id`, `name`, `remark`, `create_time`, `update_time`) VALUES (3, '菜单管理', '权限控制中心-菜单管理', now(), now());
INSERT INTO `role`(`id`, `name`, `remark`, `create_time`, `update_time`) VALUES (4, '菜单资源管理', '权限控制中心-菜单资源管理', now(), now());
INSERT INTO `role`(`id`, `name`, `remark`, `create_time`, `update_time`) VALUES (5, '角色管理', '权限控制中心-角色管理', now(), now());
INSERT INTO `role`(`id`, `name`, `remark`, `create_time`, `update_time`) VALUES (6, '用户管理', '权限控制中心-用户管理', now(), now());


INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 6, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 5, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 4, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 3, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 2, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 1, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 12, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 11, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 10, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 9, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 8, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 7, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 18, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 17, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 16, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 15, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 14, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 13, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 25, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 24, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 23, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 22, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 21, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 20, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 19, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 31, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 30, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 29, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 28, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 27, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (1, 1, 26, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (2, 1, 6, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (2, 1, 5, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (2, 1, 4, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (2, 1, 3, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (2, 1, 2, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (2, 1, 1, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (3, 1, 12, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (3, 1, 11, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (3, 1, 10, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (3, 1, 9, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (3, 1, 8, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (3, 1, 7, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (4, 1, 18, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (4, 1, 17, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (4, 1, 16, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (4, 1, 15, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (4, 1, 14, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (4, 1, 13, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (5, 1, 25, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (5, 1, 24, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (5, 1, 23, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (5, 1, 22, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (5, 1, 21, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (5, 1, 20, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (5, 1, 19, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (6, 1, 31, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (6, 1, 30, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (6, 1, 29, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (6, 1, 28, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (6, 1, 27, now());
INSERT INTO `role_menu_resource`(`role_id`, `system_id`, `menu_resource_id`, `create_time`) VALUES (6, 1, 26, now());

INSERT INTO `user`(`id`, `username`, `password`, `email`, `mobile`, `status`, `create_time`, `update_time`) VALUES (1, 'admin', '$2a$10$S3UCyx0ageXHZYXP8yrRO.DLDCYvPinfC4SKS6NYFuztcWW3hhUe.', NULL, NULL, 1, now(), now());
INSERT INTO `user`(`id`, `username`, `password`, `email`, `mobile`, `status`, `create_time`, `update_time`) VALUES (2, 'admin1', '$2a$10$7NOHdfgrqJPXqwuqlNLwSunZNWl1GnatNhZ9QkJoN09QFG9sqX2vi', NULL, NULL, 1, now(), now());
INSERT INTO `user`(`id`, `username`, `password`, `email`, `mobile`, `status`, `create_time`, `update_time`) VALUES (3, 'admin2', '$2a$10$K7j/LhUfTGTFC4aqRpy88OyzYEHI.kpltWfCJ127b3e2gD1qknJ.6', NULL, NULL, 1, now(), now());
INSERT INTO `user`(`id`, `username`, `password`, `email`, `mobile`, `status`, `create_time`, `update_time`) VALUES (4, 'admin3', '$2a$10$qFY9TKYX8Rjo3Siv8rphXuifNka2wn0TDXoOkIwQxwY98J9tHzfFK', NULL, NULL, 1, now(), now());
INSERT INTO `user`(`id`, `username`, `password`, `email`, `mobile`, `status`, `create_time`, `update_time`) VALUES (5, 'admin4', '$2a$10$w88zV0171nBKp22ZmHPP0u.azXb.A0fiNJ0j.2Wo/sQxnhjtOag.W', NULL, NULL, 1, now(), now());
INSERT INTO `user`(`id`, `username`, `password`, `email`, `mobile`, `status`, `create_time`, `update_time`) VALUES (6, 'admin5', '$2a$10$8knuTOJrBBSlOcWOivOdcunBIVYNDGghM9CCqhftQf9cVQUJy5NLq', NULL, NULL, 1, now(), now());


INSERT INTO `user_role`(`user_id`, `role_id`, `create_time`) VALUES (1, 1, now());
INSERT INTO `user_role`(`user_id`, `role_id`, `create_time`) VALUES (2, 6, now());
INSERT INTO `user_role`(`user_id`, `role_id`, `create_time`) VALUES (3, 3, now());
INSERT INTO `user_role`(`user_id`, `role_id`, `create_time`) VALUES (4, 4, now());
INSERT INTO `user_role`(`user_id`, `role_id`, `create_time`) VALUES (5, 5, now());
INSERT INTO `user_role`(`user_id`, `role_id`, `create_time`) VALUES (6, 6, now());

