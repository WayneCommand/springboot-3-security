CREATE TABLE `user` (
                        `user_id` bigint(20) NOT NULL,
                        `user_name` varchar(255) NOT NULL COMMENT 'name',
                        `user_email` varchar(255) NOT NULL COMMENT 'email',
                        `password` varchar(255) NOT NULL COMMENT 'password',
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `modify_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                        PRIMARY KEY (`user_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE `role` (
                        `role_id` int NOT NULL AUTO_INCREMENT,
                        `role_name` varchar(255) NOT NULL COMMENT 'role name',
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `modify_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                        PRIMARY KEY (`role_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE `resource` (
                        `res_id` int NOT NULL AUTO_INCREMENT,
                        `res_name` varchar(255) NOT NULL COMMENT 'resource name',
                        `identifier` varchar(255) NOT NULL COMMENT 'resource identifier',
                        `url` varchar(255) NOT NULL COMMENT 'url',
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `modify_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                        PRIMARY KEY (`res_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE `user_role` (
                        `ref_id` int NOT NULL AUTO_INCREMENT,
                        `user_id` bigint(20) NOT NULL,
                        `role_id` int NOT NULL,
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        PRIMARY KEY (`ref_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE `role_resource` (
                        `ref_id` int NOT NULL AUTO_INCREMENT,
                        `role_id` int NOT NULL,
                        `res_id` int NOT NULL,
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        PRIMARY KEY (`ref_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
