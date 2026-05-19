alter table members add column password_hash varchar(100);
update members set password_hash = '{noop}change-me' where password_hash is null;
alter table members alter column password_hash set not null;

alter table admin_users add column password_hash varchar(100);
update admin_users set password_hash = '{noop}change-me' where password_hash is null;
alter table admin_users alter column password_hash set not null;

