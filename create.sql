create sequence user_sequence start 1 increment 1;

    create table users (
       id int8 not null,
        email TEXT not null,
        first_name TEXT not null,
        last_name TEXT not null,
        password TEXT not null,
        primary key (id)
    );

    alter table if exists users 
       add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);

CREATE TABLE users
(
    id         BIGINT NOT NULL,
    email      TEXT   NOT NULL,
    first_name TEXT   NOT NULL,
    last_name  TEXT   NOT NULL,
    password   TEXT   NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT user_email_unique UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT user_email_unique UNIQUE (email);

CREATE SEQUENCE IF NOT EXISTS address_sequence AS bigint START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE SEQUENCE IF NOT EXISTS user_sequence AS bigint START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE addresses
(
    id      BIGINT NOT NULL,
    city    TEXT,
    country TEXT,
    street  TEXT,
    CONSTRAINT addresses_pkey PRIMARY KEY (id)
);

CREATE TABLE airports
(
    airport_code TEXT NOT NULL,
    name         TEXT NOT NULL,
    address_id   BIGINT,
    CONSTRAINT airports_pkey PRIMARY KEY (airport_code)
);

CREATE TABLE flights
(
    flight_number    VARCHAR(255) NOT NULL,
    airline          TEXT         NOT NULL,
    business_price   FLOAT8,
    destination_code TEXT         NOT NULL,
    economic_price   FLOAT8,
    departure_time   TIMESTAMP WITHOUT TIME ZONE,
    landing_time     TIMESTAMP WITHOUT TIME ZONE,
    origin_code      TEXT         NOT NULL,
    CONSTRAINT flights_pkey PRIMARY KEY (flight_number)
);

ALTER TABLE airports
    ADD CONSTRAINT fk6aw756w63mwqndosc04xa56tc FOREIGN KEY (address_id) REFERENCES addresses (id) ON UPDATE NO ACTION ON DELETE NO ACTION;