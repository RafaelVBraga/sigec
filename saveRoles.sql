insert into authorities(authority) values('ROLE_USER');
insert into authorities(authority) values('ROLE_ADMIN');
insert into authorities(authority) values('ROLE_DEVELOPER');

insert into users(username, password,account_non_expired,account_non_locked,credentials_non_expired, enabled)
values('Developer','$2a$10$5pSp5h2MMzibtAYCO52MROYG6PzfGlFbDkgTpgqFVR.8SNUmp53Ca',true,true,true,true);

insert into users(username, password,account_non_expired,account_non_locked,credentials_non_expired, enabled)
values('Admin','$2a$10$CPXeIsGUdzmfccu9Dl/IzupZzbgDAvcEVO/UZMD/2VFjBSQebHkky',true,true,true,true);

insert into users(username, password,account_non_expired,account_non_locked,credentials_non_expired, enabled)
values('User','$2a$10$uJPVcNP.10Qg56ZGJH1S1uQFJAPa.B28NVgBC4A4nh8V9GU44zNtG',true,true,true,true);

insert into users_authorities(users_id,authorities_id) values (7,7);
insert into users_authorities(users_id,authorities_id) values (7,8);
insert into users_authorities(users_id,authorities_id) values (7,9);
insert into users_authorities(users_id,authorities_id) values (8,7);
insert into users_authorities(users_id,authorities_id) values (8,8);
insert into users_authorities(users_id,authorities_id) values (9,7);