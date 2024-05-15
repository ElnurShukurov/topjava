DELETE
FROM user_role;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2024-05-03 12:00:00', 'Breakfast', 400, 100000),
       ('2024-05-03 18:00:00', 'Dinner', 600, 100000),
       ('2024-05-03 08:00:00', 'Breakfast', 500, 100001),
       ('2024-05-03 14:00:00', 'Lunch', 700, 100001);