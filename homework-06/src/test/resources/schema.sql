CREATE TABLE if not exists genres
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE if not exists authors
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE if not exists books
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE if not exists book_author
(
    book_id   BIGINT references books (id) on delete cascade,
    author_id BIGINT references authors (id) on delete cascade,
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE if not exists book_genre
(
    book_id  BIGINT references books (id) on delete cascade,
    genre_id BIGINT references genres (id) on delete cascade,
    PRIMARY KEY (book_id, genre_id)
);