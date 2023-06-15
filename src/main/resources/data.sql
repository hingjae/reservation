insert into user_account (login_id, password, name, phone_number) values ('honey', '$2a$10$F36MOa0H6ybdaiinlSB/QuC93xiXNB1KNjY3.Qh5En42KLLIX48Bq', '이재헌', '157-398-4256');
insert into user_account (login_id, password, name, phone_number) values ('user', '$2a$10$PVcPh4eLJ1fOqPAnJV3MAuMIf733hYOr8kyUb7LVDud3dXNSDMz0i', '고객', '157-398-4256');
insert into user_account (login_id, password, name, phone_number) values ('manager', '$2a$10$lgl3IqIuB3S4kOz2WB9X7.E9hwQgoyQZ3.vElBObqPRNdzr6CZ8gu', '매니저', '157-398-4256');

insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('user', '2023-06-21', '17:00', 'Golf', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('manager', '2023-06-29', '13:30', 'Grand Cherokee', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('user', '2023-06-25', '9:30', 'Arnage', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('manager', '2023-06-28', '16:30', 'Insight', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('manager', '2023-06-16', '11:30', 'Metro', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('honey', '2023-06-17', '9:30', 'SL-Class', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('user', '2023-06-22', '11:30', 'Galant', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('user', '2023-06-18', '12:30', 'Viper', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('honey', '2023-06-23', '14:00', 'Crown Victoria', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('user', '2023-06-23', '17:00', 'CR-Z', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('user', '2023-06-16', '15:30', '57', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('user', '2023-06-26', '14:30', '240SX', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('honey', '2023-06-25', '17:30', 'Sentra', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('honey', '2023-06-22', '15:30', 'Wrangler', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('manager', '2023-06-28', '11:30', '928', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('honey', '2023-06-16', '12:00', 'Lumina', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('manager', '2023-06-24', '9:30', 'Stealth', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('honey', '2023-06-26', '12:30', '745', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('honey', '2023-06-27', '12:30', 'IS-F', 'READY');
insert into reservation (user_account_id, local_date, local_time, memo, reservation_status) values ('manager', '2023-06-22', '16:00', 'Continental', 'READY');