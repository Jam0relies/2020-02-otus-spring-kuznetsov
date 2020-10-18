CREATE TABLE if not exists genres
(
    id       BIGINT PRIMARY KEY auto_increment,
    mongo_id VARCHAR(255),
    name     VARCHAR(255)
);

CREATE TABLE if not exists authors
(
    id       BIGINT PRIMARY KEY auto_increment,
    mongo_id VARCHAR(255),
    name     VARCHAR(255)
);

CREATE TABLE if not exists books
(
    id       BIGINT PRIMARY KEY auto_increment,
    mongo_id VARCHAR(255),
    name     VARCHAR(255)
);

CREATE TABLE if not exists comments
(
    id        BIGINT PRIMARY KEY auto_increment,
    text      VARCHAR(255),
    timestamp timestamp,
    book_id   BIGINT references books (id) on delete cascade
);

CREATE TABLE if not exists book_author
(
    book_id   BIGINT,
    author_id BIGINT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) references books (id) on delete cascade,
    FOREIGN KEY (author_id) references authors (id) on delete cascade
);

CREATE TABLE if not exists book_genre
(
    book_id  BIGINT references books (id) on delete cascade,
    genre_id BIGINT references genres (id) on delete cascade,
    PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) references books (id) on delete cascade,
    FOREIGN KEY (genre_id) references genres (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS roles
(
    id        BIGINT PRIMARY KEY auto_increment,
    authority VARCHAR(255),
    mongo_id  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS users
(
    id                      BIGINT PRIMARY KEY auto_increment,
    mongo_id                VARCHAR(255),
    username                VARCHAR(255),
    password                VARCHAR(255),
    account_non_expired     boolean,
    account_non_locked      boolean,
    credentials_non_expired boolean,
    enabled                 boolean
)
