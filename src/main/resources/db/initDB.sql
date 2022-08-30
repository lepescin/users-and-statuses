DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id               SERIAL PRIMARY KEY,
    email            VARCHAR NOT NULL,
    phone_number     VARCHAR NOT NULL,
    username         VARCHAR NOT NULL,
    first_name       VARCHAR NOT NULL,
    last_name        VARCHAR NOT NULL,
    status           VARCHAR NOT NULL,
    status_changed   TIMESTAMP
);


