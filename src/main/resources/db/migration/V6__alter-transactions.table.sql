alter table transactions add transaction_value double;
update transactions set transaction_value = 1.00;
alter table transactions modify transaction_value double not null;