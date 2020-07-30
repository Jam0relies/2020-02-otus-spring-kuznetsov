insert into genre (id, name)
values (1, 'Classic');

insert into author (id, name)
values (1, 'William Shakespeare');

insert into book (id, name)
VALUES (1, 'Hamlet');

insert into book_genre (book_id, genre_id)
VALUES (1, 1);

insert into book_author (book_id, author_id)
VALUES (1, 1)