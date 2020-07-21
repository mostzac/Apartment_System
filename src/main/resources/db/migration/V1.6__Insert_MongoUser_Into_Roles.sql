insert into roles (name, allowed_resource, allowed_read, allowed_create, allowed_update, allowed_delete) values
('MongoUser', '/api/mongo', 'Y', 'Y', 'Y', 'Y')
;
commit;