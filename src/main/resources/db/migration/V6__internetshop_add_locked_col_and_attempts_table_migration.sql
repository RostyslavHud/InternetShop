ALTER TABLE users
    ADD COLUMN account_non_locked boolean NOT NULL DEFAULT 1 AFTER active;

create table user_attempts
(
    id            int auto_increment primary key,
    username      varchar(50) not null,
    attempts      int         not null,
    last_modified timestamp   not null
);