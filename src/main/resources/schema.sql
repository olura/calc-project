drop table if exists calculations cascade;
drop table if exists clients cascade;
drop table if exists prices cascade;
drop table if exists service_users cascade;

create table clients (
    id bigserial not null,
    creation_date timestamp(6),
    title varchar(255) unique,
    comment varchar(255),
    primary key (id)
);
create table service_users (
    id bigserial not null,
    username varchar(255) unique,
    password varchar(255),
    email varchar(255),
    role varchar(255) check (role in ('USER','ADMIN')),
    primary key (id)
);
create table prices (
    id bigserial not null,
    title varchar(255) not null,
    creation_date timestamp(6),
    lic_percent float(53) not null,
    work_percent float(53) not null,
    hour_cost float(53) not null,
    status boolean not null,
    primary key (id)
);
create table calculations (
    id bigserial not null,
    user_id bigint references service_users(id),
    client_id bigint references clients(id),
    price_id bigint references prices(id),
    creation_date timestamp(6),
    lic_cost float(53) not null,
    work_cost float(53) not null,
    hours integer not null,
    result_calculation float(53) not null,
    primary key (id)
);