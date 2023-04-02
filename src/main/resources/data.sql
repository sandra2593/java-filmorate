-- словарь c MPA-рейтингами
-- TRUNCATE TABLE d_rating;
INSERT INTO d_rating VALUES (1, 'G');
INSERT INTO d_rating VALUES (2, 'PG');
INSERT INTO d_rating VALUES (3, 'PG-13');
INSERT INTO d_rating VALUES (4, 'R');
INSERT INTO d_rating VALUES (5, 'NC-17');

-- словарь с жанрами фильмов
-- TRUNCATE TABLE d_genre;
INSERT INTO d_genre VALUES (1, 'Комедия');
INSERT INTO d_genre VALUES (2, 'Драма');
INSERT INTO d_genre VALUES (3, 'Мультфильм');
INSERT INTO d_genre VALUES (4, 'Триллер');
INSERT INTO d_genre VALUES (5, 'Документальный');
INSERT INTO d_genre VALUES (6, 'Боевик');

-- словарь со статусами заявок в друзья
-- TRUNCATE TABLE d_friendship_status;
INSERT INTO d_friendship_status VALUES (1, 'Подтверждённая');
INSERT INTO d_friendship_status VALUES (2, 'Неподтверждённая');