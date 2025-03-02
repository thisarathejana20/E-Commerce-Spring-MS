create table if not exists category (
    id integer primary key,
    name varchar(255) not null,
    description varchar(255)
);

create table if not exists product (
    id integer primary key,
    name varchar(255),
    description varchar(255),
    available_quantity double precision not null,
    price numeric(38,2) not null,
    category_id integer not null,
    constraint fk_category foreign key (category_id) references category(id)
);

create sequence if not exists category_seq start with 1 increment by 50;
create sequence if not exists product_seq start with 1 increment by 50;