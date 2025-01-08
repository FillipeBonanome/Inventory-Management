alter table products add price double;
update products set price = 1.00;
alter table products modify price double not null;