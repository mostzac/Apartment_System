insert into roles (name, allowed_resource, allowed_read, allowed_create, allowed_update, allowed_delete) values
('Admin', '/', 'Y', 'Y', 'Y', 'Y'),
('Manager', '/users,/apartments,/apts,/packages', 'Y', 'Y', 'Y', 'N'),
('User', '/user,/packages', 'Y', 'N', 'N', 'N')
;
commit;