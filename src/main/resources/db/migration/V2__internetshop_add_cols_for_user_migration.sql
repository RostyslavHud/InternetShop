ALTER TABLE users ADD COLUMN active BOOLEAN NOT NULL DEFAULT FALSE AFTER age;
ALTER TABLE users ADD COLUMN active_code varchar (255) AFTER active;
ALTER TABLE users ADD COLUMN registration_date timestamp after active_code;

INSERT products(product_name, price)
VALUES ('Onion', 5.25);
INSERT products(product_name, price)
VALUES ('Garlic', 3.22);
INSERT products(product_name, price)
VALUES ('Potato', 2.13);
INSERT products(product_name, price)
VALUES ('Cucumber', 6.66);
INSERT products(product_name, price)
VALUES ('Cabbage', 10.10);
INSERT products(product_name, price)
VALUES ('Tomato', 12.47);

INSERT users(user_name, user_password, role, first_name, last_name, email, age, active, active_code, registration_date)
VALUES ('admin','$2y$12$ZAzL1VIhR8aiqv4C2pBDOetkhlecz2hWYv2KKf3.f7aZyvQOX6Z1O', 'ADMIN', 'Ros', 'Hud', 'ros@gmail.com', 22, TRUE, '666', '2021-01-15 22:22:22');