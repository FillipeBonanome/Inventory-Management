create table transactions(
    id bigint not null auto_increment primary key,
    product_id bigint not null,
    quantity int not null,
    transaction_date timestamp not null,
    transaction_type varchar(100) not null,

    constraint fk_product foreign key(product_id) references products(id)

);