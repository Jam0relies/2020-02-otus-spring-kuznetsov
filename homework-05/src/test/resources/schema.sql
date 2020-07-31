CREATE TABLE if not exists genre
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE if not exists author
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE if not exists book
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE if not exists book_author
(
    book_id   BIGINT references book (id) on delete cascade,
    author_id BIGINT references author (id) on delete cascade,
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE if not exists book_genre
(
    book_id  BIGINT references book (id) on delete cascade,
    genre_id BIGINT references genre (id) on delete cascade,
    PRIMARY KEY (book_id, genre_id)
);