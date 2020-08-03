insert into genres (id, name)
values (1, 'Classic');

insert into authors (id, name)
values (1, 'William Shakespeare');

insert into books (id, name)
VALUES (1, 'Hamlet');

insert into book_genre (book_id, genre_id)
VALUES (1, 1);

insert into book_author (book_id, author_id)
VALUES (1, 1);

insert into books (id, name)
values (2, 'Structure and Interpretation of Computer Programs');
insert into authors (id, name)
VALUES (2, 'Harold Abelson');
insert into authors (id, name)
VALUES (3, 'Gerald Jay Sussman');
insert into authors (id, name)
VALUES (4, 'Julie Sussman');
insert into book_author (book_id, author_id)
VALUES (2, 2);
insert into book_author (book_id, author_id)
VALUES (2, 3);
insert into book_author (book_id, author_id)
VALUES (2, 4);
insert into genres (id, name)
VALUES (2, 'Religion');
insert into book_genre (book_id, genre_id)
VALUES (2, 2);
