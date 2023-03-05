create table products
(
    id         bigserial,
    title      varchar(255) not null,
    cost       money        not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (id)
);

create table orders
(
    id          bigserial primary key,
    username    varchar(255) not null,
    total_price money   not null,
    address     varchar(255),
    phone       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp

);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    quantity          int    not null,
    price_per_product int    not null,
    price             money    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp

);



insert into products(title, cost)
values ('Milk', 80),
       ('Bread', 25),
       ('Cheese', 300),
       ('Cake', 259),
       ('Apple', 23),
       ('Tea', 67),
       ('Water', 5),
       ('Chocolate', 56),
       ('Nuts', 187),
       ('Lemon', 3),
       ('Banana', 7),
       ('Watermelon', 101),
       ('Potato', 32),
       ('Cucumber',4 )
       ;
