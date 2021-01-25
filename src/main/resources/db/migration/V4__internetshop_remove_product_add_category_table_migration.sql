alter table order_item
    drop foreign key orderItem_products_id_fk;

alter table order_item
    drop column product_id;

alter table order_item
    add column product_id varchar(255) not null after id;

drop table products;

create table categories
(
    id            int auto_increment primary key,
    category_name varchar(255) not null
);

INSERT categories(category_name)
VALUES ('Category 1');

INSERT categories(category_name)
VALUES ('Category 2');

INSERT categories(category_name)
VALUES ('Category 3');

INSERT categories(category_name)
VALUES ('Category 4');

INSERT categories(category_name)
VALUES ('Category 5');