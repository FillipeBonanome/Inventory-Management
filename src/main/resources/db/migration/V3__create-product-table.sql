create table products (

    id BIGINT AUTO_INCREMENT,
    name varchar(255) not null,
    quantity int not null,
    description TEXT,
    user_id BIGINT,
    constraint fk_user foreign key(user_id) references users(id),

    primary key(id)

);