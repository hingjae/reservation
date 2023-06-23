insert into user_account (login_id, password, name, phone_number) values ('honey', '$2a$10$F36MOa0H6ybdaiinlSB/QuC93xiXNB1KNjY3.Qh5En42KLLIX48Bq', '이재헌', '157-398-4256');
insert into user_account (login_id, password, name, phone_number) values ('user', '$2a$10$PVcPh4eLJ1fOqPAnJV3MAuMIf733hYOr8kyUb7LVDud3dXNSDMz0i', '고객', '157-398-4256');
insert into user_account (login_id, password, name, phone_number) values ('manager', '$2a$10$lgl3IqIuB3S4kOz2WB9X7.E9hwQgoyQZ3.vElBObqPRNdzr6CZ8gu', '매니저', '157-398-4256');

insert into manager_account(name) values('manager1');
insert into manager_account(name) values('manager2');
insert into manager_account(name) values('manager3');

insert into reservation (user_account_id, manager_account_id, reservation_date, reservation_time, memo, reservation_status) values ('honey', 1, '2023-06-17', '9:00', '없음', 'COMP');
insert into reservation (user_account_id, manager_account_id, reservation_date, reservation_time, memo, reservation_status) values ('honey', 1, '2023-06-25', '9:00', '없음', 'READY');