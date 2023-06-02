--liquibase formatted sql

--changeset mr.rifleman:1

create table users
(
    id       bigserial primary key,
    name     text not null,
    chat_id   text not null unique,
    phone_num text not null,
    address  text
);

create table volunteer
(
    id       bigserial primary key,
    name     text not null,
    chat_id   text not null unique,
    phone_num text not null
);

create table cats_shelter
(
    id      bigserial primary key,
    cats bigserial not null
);

create table cats
(
    id      bigserial primary key,
    name    text not null,
    age     int  not null,
    shelter bigserial not null references cats_shelter(id)
);

create table dogs_shelter
(
    id      bigserial primary key,
    dogs bigserial not null
);

create table dogs
(
    id      bigserial primary key,
    name    text not null,
    age     int  not null,
    shelter bigserial not null references dogs_shelter(id)
);

ALTER TABLE cats_shelter
    ADD CONSTRAINT fk_cats_shelter
        FOREIGN KEY (cats)
            REFERENCES cats_shelter (id);

ALTER TABLE dogs_shelter
    ADD CONSTRAINT fk_dogs_shelter
        FOREIGN KEY (dogs)
            REFERENCES dogs_shelter (id);
