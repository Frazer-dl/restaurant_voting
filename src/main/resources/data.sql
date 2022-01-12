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
       ('Millennium');

INSERT INTO VOTE(restaurant_id, user_id)
VALUES (1, 1),
       (1, 2),
       (2, 3);

INSERT INTO MENU (name, price, restaurant_id)
VALUES ('Курица с ананасами', 200, 1),
       ('Салат Оливье', 250, 1),
       ('Бургер с говядиной', 500, 2),
       ('Соус Невероятный', 50, 2)