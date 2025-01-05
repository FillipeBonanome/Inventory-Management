create table users(

    id bigint not null auto_increment,
    login varchar(255) unique not null,
    name varchar(255) not null,
    email varchar(255) unique not null,
    cpf varchar(11) unique not null,
    password varchar(255) not null,

    primary key(id)

);