DROP TABLE IF EXISTS genre;
CREATE TABLE genre(id BIGINT PRIMARY KEY, name VARCHAR(255));

DROP TABLE IF EXISTS author;
CREATE TABLE author(id BIGINT PRIMARY KEY, name VARCHAR(255));

DROP TABLE IF EXISTS book;
CREATE TABLE book(id BIGINT PRIMARY KEY, name VARCHAR(255));

DROP TABLE IF EXISTS book_author;
CREATE TABLE book_author(book_id BIGINT, author_id BIGINT, PRIMARY KEY (book_id, author_id));

DROP TABLE IF EXISTS book_genre;
CREATE TABLE book_genre(book_id BIGINT, genre_id BIGINT, PRIMARY KEY (book_id, genre_id));