create table products
(
    id           int auto_increment primary key,
    product_name varchar(255) not null,
    price        double       not null
);

create table order_item
(
    id          int auto_increment primary key,
    product_id  int           not null,
    product_qty int default 1 not null,
    constraint orderItem_products_id_fk
    foreign key (product_id) references products (id)
);

create table users
(
    id            int auto_increment primary key,
    user_name     varchar(50)  not null,
    user_password varchar(255) not null,
    role          varchar(50)  not null,
    first_name    varchar(50)  not null,
    last_name     varchar(50)  null,
    email         varchar(255) not null,
    age           int          not null
);

create table orders
(
    id               int auto_increment primary key,
    order_number     int          not null,
    order_date       timestamp    not null,
    user_id          int          not null,
    shipping_address varchar(255) not null,
    description      varchar(255) null,
    order_status     varchar(50)  not null,
    updated          varchar(50)  null,
    constraint orders_users_id_fk
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
);

create table orders_order_items
(
    id            int auto_increment primary key,
    order_id      int not null,
    order_item_id int not null,
    constraint orders_orderItems_orderItem_id_fk
        foreign key (order_item_id) references order_item (id)
            on delete cascade,
    constraint orders_orderItems_orders_id_fk
        foreign key (order_id) references orders (id)
            on delete cascade
);