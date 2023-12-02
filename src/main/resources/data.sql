insert into service_users(username, password, email, role)
    values ('admin', '$2a$10$7OpFR2kzKhxvwe37Pi2KL.maJP8wfv8PO/.7sn56Cs8dvvkRwzFgW', 'admin@email.ru', 'ADMIN');
insert into service_users(username, password, email, role)
    values ('user', '$2a$10$2Pb36deyHJDMmdCRwzL9C.gbKo2s68SNER7JYttg5JgQ9VzjHQkOO', 'user@email.ru', 'USER');

insert into prices (title, lic_percent, work_percent, hour_cost, status, creation_date)
    values ('Тестовый прайс', 50, 100, 500, 'true', '2023-11-23 21:28:12');

insert into clients (title, comment, creation_date)
    values ('АО "Ромашка"','Тестовый юрик','2023-11-23 21:28:12');
insert into clients (title, comment, creation_date)
    values ('Иванов Иван','Тестовый физикик','2023-11-23 21:28:12');

insert into calculations
        (user_id, client_id, price_id, creation_date, lic_cost, work_cost, hours, result_calculation)
    values
        (1, 1, 1,'2023-11-22 21:28:12', 10, 20, 10, 5025);
insert into calculations
        (user_id, client_id, price_id, creation_date, lic_cost, work_cost, hours, result_calculation)
    values
        (1, 2, 1,'2023-11-23 21:28:12', 30, 20, 5, 2535);
insert into calculations
        (user_id, client_id, price_id, creation_date, lic_cost, work_cost, hours, result_calculation)
    values
        (2, 1, 1,'2023-11-24 21:28:12', 10, 20, 15, 7535);
