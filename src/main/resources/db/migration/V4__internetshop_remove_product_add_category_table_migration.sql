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

INSERT categories(category_name)
VALUES ('Category 6');

INSERT categories(category_name)
VALUES ('Category 7');

INSERT categories(category_name)
VALUES ('Category 8');

INSERT categories(category_name)
VALUES ('Category 9');

INSERT categories(category_name)
VALUES ('Category 10');
INSERT categories(category_name)
VALUES ('Category 11');

INSERT categories(category_name)
VALUES ('Category 12');

INSERT categories(category_name)
VALUES ('Category 13');

INSERT categories(category_name)
VALUES ('Category 14');

INSERT categories(category_name)
VALUES ('Category 15');
INSERT categories(category_name)
VALUES ('Category 16');

INSERT categories(category_name)
VALUES ('Category 17');

INSERT categories(category_name)
VALUES ('Category 18');

INSERT categories(category_name)
VALUES ('Category 19');

INSERT categories(category_name)
VALUES ('Category 20');