DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS films CASCADE;
DROP TABLE IF EXISTS film_genre CASCADE;
DROP TABLE IF EXISTS friendship CASCADE;
DROP TABLE IF EXISTS likes CASCADE;
DROP TABLE IF EXISTS d_genre CASCADE;
DROP TABLE IF EXISTS d_rating CASCADE;
DROP TABLE IF EXISTS d_friendship_status CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id       INT GENERATED BY DEFAULT AS IDENTITY,
    email    TEXT NOT NULL,
    login    TEXT NOT NULL,
    name     TEXT,
    birthday DATE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS films
(
    id            INT GENERATED BY DEFAULT AS IDENTITY,
    name          TEXT NOT NULL,
    description   TEXT NOT NULL,
    release_date  DATE NOT NULL,
    duration      INT  NOT NULL,
    rate        INT  NOT NULL DEFAULT 0,
    rating_id     INT  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS film_genre
(
    film_id  INT NOT NULL,
    genre_id INT NOT NULL,
    PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS friendship
(
    user1_id            INT NOT NULL,
    user2_id            INT NOT NULL,
    friendship_status_id INT NOT NULL,
    PRIMARY KEY (user1_id, user2_id)
);

CREATE TABLE IF NOT EXISTS likes
(
    user_id INT NOT NULL,
    film_id INT NOT NULL,
    PRIMARY KEY (user_id, film_id)
);

-- 3 словаря

CREATE TABLE IF NOT EXISTS d_genre
(
    id   INT  NOT NULL,
    name TEXT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS d_rating
(
    id   INT  NOT NULL,
    name TEXT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS d_friendship_status
(
    id   INT  NOT NULL,
    name TEXT NOT NULL,
    PRIMARY KEY (id)
);

-- создание внешних ключей

ALTER TABLE films DROP CONSTRAINT IF EXISTS fk_film_rating_id;
ALTER TABLE films ADD CONSTRAINT fk_film_rating_id FOREIGN KEY (rating_id) REFERENCES d_rating (id) ON DELETE CASCADE;

ALTER TABLE film_genre DROP CONSTRAINT IF EXISTS fk_film_genre_genre_id;
ALTER TABLE film_genre ADD CONSTRAINT fk_film_genre_genre_id FOREIGN KEY (genre_id) REFERENCES d_genre (id) ON DELETE CASCADE;

ALTER TABLE film_genre DROP CONSTRAINT IF EXISTS fk_genre_film_id;
ALTER TABLE film_genre ADD CONSTRAINT fk_genre_film_id FOREIGN KEY (film_id) REFERENCES films (id) ON DELETE CASCADE;

ALTER TABLE friendship DROP CONSTRAINT IF EXISTS fk_friendship_friendship_status_id;
ALTER TABLE friendship ADD CONSTRAINT fk_friendship_friendship_status_id FOREIGN KEY (friendship_status_id) REFERENCES d_friendship_status (id) ON DELETE CASCADE;

ALTER TABLE friendship DROP CONSTRAINT IF EXISTS fk_friendship_user1_id;
ALTER TABLE friendship ADD CONSTRAINT fk_friendship_user1_id FOREIGN KEY (user1_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE friendship DROP CONSTRAINT IF EXISTS fk_friendship_user2_id;
ALTER TABLE friendship ADD CONSTRAINT fk_friendship_user2_id FOREIGN KEY (user2_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE likes DROP CONSTRAINT IF EXISTS fk_likes_user_id;
ALTER TABLE likes ADD CONSTRAINT fk_likes_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE likes DROP CONSTRAINT IF EXISTS fk_likes_film_id;
ALTER TABLE likes ADD CONSTRAINT fk_likes_film_id FOREIGN KEY (film_id) REFERENCES films (id) ON DELETE CASCADE;