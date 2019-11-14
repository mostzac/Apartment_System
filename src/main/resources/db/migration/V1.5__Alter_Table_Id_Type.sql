alter table users
alter column id type BIGINT,
alter column apartmentId type BIGINT;

alter table roles
alter column id type BIGINT;

alter table packages
alter column id type BIGINT,
alter column userId type BIGINT;

alter table apartments
alter column id type BIGINT;

alter table users_roles
alter column user_id type BIGINT,
alter column role_id type BIGINT;
