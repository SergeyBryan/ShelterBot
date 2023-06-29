--liquibase formatted sql

--changeset mr.rifleman:1

create table users
(
    id           bigserial primary key,
    name         text not null,
    chat_id      bigint not null unique,
    phone_num    text,
    address      text,
    trial_period timestamp,
    cat_id        bigint,
    dog_id        bigint
);

create table volunteer
(
    id       bigserial primary key,
    name     text not null,
    chat_id   bigint not null unique,
    phone_num text not null
);

create table cats_shelter
(
    id      bigserial primary key,
    name text
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
    name text
);

create table dogs
(
    id      bigserial primary key,
    name    text      not null,
    age     int       not null,
    shelter bigserial not null references dogs_shelter (id)
);

create table reports
(
    id            bigserial primary key,
    created_time  timestamp not null,
    pet_photo     text,
    text          text,
    user_owner_id bigserial not null references users (id),
    cat_id        bigint,
    dog_id        bigint,
    is_checked boolean not null default false
);

ALTER TABLE users
    ADD CONSTRAINT fk_user_cats
        FOREIGN KEY (cat_id)
            REFERENCES cats (id);
ALTER TABLE users
    ADD CONSTRAINT fk_user_dogs
        FOREIGN KEY (dog_id)
            REFERENCES dogs (id);
ALTER TABLE reports
    ADD CONSTRAINT fk_report_cats
        FOREIGN KEY (cat_id)
            REFERENCES cats (id);
ALTER TABLE reports
    ADD CONSTRAINT fk_report_dogs
        FOREIGN KEY (dog_id)
            REFERENCES dogs (id);

