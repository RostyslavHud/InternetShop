create table languages
(
    id   int auto_increment primary key,
    language_name varchar(10) not null
);

INSERT
languages(language_name)
VALUES ('en');
INSERT
languages(language_name)
VALUES ('ru');

ALTER TABLE users
    ADD COLUMN language_id int NOT NULL DEFAULT 1 AFTER active;

ALTER table users add foreign key (language_id) references languages (id) on update cascade on delete cascade;

