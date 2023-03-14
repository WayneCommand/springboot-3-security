insert into sys_resource (res_id, res_name, identifier, url, created_at, modify_at)
values  (1, 'View project', 'project::view', '/projects', '2022-04-15 02:35:29', null),
        (2, 'Create project', 'project::create', '/project', '2022-04-15 02:36:03', null),
        (3, 'Update project', 'project::update', '/project', '2022-04-15 02:36:34', null),
        (4, 'Delete project', 'project::delete', '/project', '2022-04-15 02:36:54', null);

insert into sys_role (role_id, role_name, created_at, modify_at)
values  (1, 'guest', '2022-04-15 02:33:22', null),
        (2, 'developer', '2022-04-15 02:33:56', null),
        (3, 'maintanner', '2022-04-15 02:34:07', null);

insert into sys_role_resource (ref_id, role_id, res_id, created_at)
values  (1, 1, 1, '2022-04-15 08:13:05'),
        (2, 2, 1, '2022-04-15 08:13:05'),
        (3, 2, 2, '2022-04-15 08:13:05');

insert into sys_user (user_id, user_name, user_email, password, created_at, modify_at)
values  (1, 'shin', 'zhangxin@shin.com', '{bcrypt}$2a$10$B6uvRcuw4fEq5VTuFAJEWu4YNLyO6X2uLjy3rxOxxMPm7lIpPWJy2', '2022-04-15 02:00:16', '2022-04-15 02:11:36');

insert into sys_user_role (ref_id, user_id, role_id, created_at)
values  (1, 1, 1, '2022-04-15 08:12:30'),
        (2, 1, 2, '2022-04-15 08:12:37');

