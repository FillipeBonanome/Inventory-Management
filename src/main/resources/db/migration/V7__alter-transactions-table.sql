alter table transactions add user_id bigint;
alter table transactions add constraint fk_user_id
foreign key (user_id) references users(id);