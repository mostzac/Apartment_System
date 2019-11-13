insert into roles (name, allowed_resource, allowed_read, allowed_create, allowed_update, allowed_delete) values
('Admin', '/', 'Y', 'Y', 'Y', 'Y'),
('Manager', '/api/users,/api/apts,/api/apts,/api/packs', 'Y', 'Y', 'Y', 'N'),
('User', '/api/users/user,/api/packs/pack', 'Y', 'N', 'N', 'N')
;
commit;