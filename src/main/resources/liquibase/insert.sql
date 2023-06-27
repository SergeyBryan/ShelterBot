--liquibase formatted sql

--changeset mr.rifleman:2
-- TRUNCATE TABLE users;
-- TRUNCATE TABLE dogs_shelter;
-- TRUNCATE TABLE cats_shelter;
-- TRUNCATE TABLE cats;
-- TRUNCATE TABLE dogs;
-- TRUNCATE TABLE volunteer;

insert into users (id, name, chat_id, phone_num, address, trial_period, cat_id, dog_id)
VALUES (1, 'test', 493667033, 'testphone_num', 'testadres', null, null, null );

insert into dogs_shelter (id, name)
VALUES (1, 'test');

insert into cats_shelter (id, name)
VALUES (1, 'test');

insert into cats (id, name, age, shelter)
VALUES (1, 'cattest', 1, 1);
insert into cats (id, name, age, shelter)
VALUES (2, 'cattest2', 2, 1);

insert into dogs (id, name, age, shelter)
VALUES (1, 'dogtest', 1, 1);
insert into dogs (id, name, age, shelter)
VALUES (2, 'dogtest2', 2, 1);

insert into volunteer (id, name, chat_id, phone_num)
VALUES (1, 'test', 493667033, 'testphone_num');

-- SELECT *
-- FROM reports
-- WHERE user_owner_id = 1
--   AND created_time::date = CURRENT_DATE;




