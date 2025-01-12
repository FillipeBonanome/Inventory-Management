alter table products add min_quantity int;
update products set min_quantity = 1;
alter table products modify min_quantity int not null;