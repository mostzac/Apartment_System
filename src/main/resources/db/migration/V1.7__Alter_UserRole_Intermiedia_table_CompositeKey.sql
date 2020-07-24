TRUNCATE users_roles;

ALTER TABLE users_roles
    ADD CONSTRAINT users_roles_composite PRIMARY KEY ( user_id,role_id );

