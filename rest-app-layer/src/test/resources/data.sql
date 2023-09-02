insert into transaction (transaction_id, user_name, description, transaction_date, transaction_amount, transaction_type) values(101, 'test', 'test_description_101', now() - 7 month, 3000.00, 'DEBIT');
insert into transaction (transaction_id, user_name, description, transaction_date, transaction_amount, transaction_type) values(102, 'test', 'test_description_102', now() - 7 month, 4000.00, 'DEBIT');
insert into transaction (transaction_id, user_name, description, transaction_date, transaction_amount, transaction_type) values(103, 'test', 'test_description_103', now() - 7 month, 7000.00, 'CREDIT');

insert into transaction (transaction_id, user_name, description, transaction_date, transaction_amount, transaction_type) values(104, 'test', 'test_description_104', now() - 3 month, 7000.00, 'CREDIT');
insert into transaction (transaction_id, user_name, description, transaction_date, transaction_amount, transaction_type) values(105, 'test', 'test_description_105', now() - 3 month, 1000.00, 'DEBIT');
insert into transaction (transaction_id, user_name, description, transaction_date, transaction_amount, transaction_type) values(106, 'test', 'test_description_106', now() - 3 month, 2000.00, 'DEBIT');

insert into transaction (transaction_id, user_name, description, transaction_date, transaction_amount, transaction_type) values(107, 'test', 'test_description_107', now() - 3 hour, 1000.00, 'DEBIT');
insert into transaction (transaction_id, user_name, description, transaction_date, transaction_amount, transaction_type) values(108, 'test', 'test_description_108', now() - 1 hour, 3000.00, 'DEBIT');
insert into transaction (transaction_id, user_name, description, transaction_date, transaction_amount, transaction_type) values(109, 'test', 'test_description_109', now(), 7000.00, 'CREDIT');