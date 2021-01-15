ALTER TABLE users ADD COLUMN active BOOLEAN NOT NULL DEFAULT FALSE AFTER age;
ALTER TABLE users ADD COLUMN active_code varchar (255) AFTER active;
ALTER TABLE users ADD COLUMN registration_date timestamp after active_code;
