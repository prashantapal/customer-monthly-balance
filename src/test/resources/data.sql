insert into transaction (transaction_id, description, transaction_date, transaction_amount, transaction_type) values(100, 'test_description_100', now() - 3 hour, 1000.00, 'DEBIT');
insert into transaction (transaction_id, description, transaction_date, transaction_amount, transaction_type) values(101, 'test_description_101', now() - 1 hour, 3000.00, 'DEBIT');
insert into transaction (transaction_id, description, transaction_date, transaction_amount, transaction_type) values(102, 'test_description_102', now(), 7000.00, 'CREDIT');

insert into transaction (transaction_id, description, transaction_date, transaction_amount, transaction_type) values(103, 'test_description_103', now() - 3 month, 7000.00, 'CREDIT');
insert into transaction (transaction_id, description, transaction_date, transaction_amount, transaction_type) values(104, 'test_description_104', now() - 3 month, 1000.00, 'DEBIT');
insert into transaction (transaction_id, description, transaction_date, transaction_amount, transaction_type) values(105, 'test_description_105', now() - 3 month, 2000.00, 'DEBIT');


insert into transaction (transaction_id, description, transaction_date, transaction_amount, transaction_type) values(106, 'test_description_106', now() - 7 month, 3000.00, 'DEBIT');
insert into transaction (transaction_id, description, transaction_date, transaction_amount, transaction_type) values(107, 'test_description_107', now() - 7 month, 4000.00, 'DEBIT');
insert into transaction (transaction_id, description, transaction_date, transaction_amount, transaction_type) values(108, 'test_description_108', now() - 7 month, 7000.00, 'CREDIT');