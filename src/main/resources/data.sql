INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User2', 'user2@yandex.ru', '{noop}password'),
       ('User3', 'user3@yandex.ru', '{noop}password');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4);

INSERT INTO RESTAURANT(name)
VALUES ('Pandas'),
       ('Millennium'),
       ('OutDateMenu');

INSERT INTO VOTE(restaurant_id, user_id)
VALUES (1, 1),
       (1, 2),
       (2, 3);

INSERT INTO VOTE(restaurant_id, user_id, date)
VALUES (2, 1, '2020-01-30'),
       (2, 2, '2020-01-30'),
       (2, 3, '2020-01-30'),
       (3, 1, '2020-01-25'),
       (3, 2, '2020-01-25'),
       (3, 3, '2020-01-25');

INSERT INTO MENU (name, price, restaurant_id)
VALUES ('Курица с ананасами', 2000, 1),
       ('Салат Оливье', 2500, 1),
       ('Бургер с говядиной', 5000, 2),
       ('Соус Невероятный', 500, 2),
       ('Menu Item 3', 500, 3);

INSERT INTO MENU (name, price, restaurant_id, date)
VALUES ('Курица с ананасами', 1500, 1, '2020-01-30'),
       ('Салат Оливье', 1500, 1, '2020-01-30'),
       ('Бургер с говядиной', 1500, 2, '2020-01-30'),
       ('Соус Невероятный', 1500, 2, '2020-01-30'),
       ('Menu Item 1', 1500, 3, '2020-01-25'),
       ('Menu Item 2', 1500, 3, '2020-01-25');