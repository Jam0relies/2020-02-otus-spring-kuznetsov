package ru.otus.homework07.repository;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework07.domain.Author;
import ru.otus.homework07.domain.Book;
import ru.otus.homework07.domain.Genre;

import javax.transaction.Transactional;
import java.util.Set;

public class BookRepositoryImpl implements BookRepositoryCustom {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Transactional
    @Override
    public Book addAuthorById(long bookId, long authorId) {
        Author author = authorRepository.getOne(authorId);
        Book book = bookRepository.getOne(bookId);
        Set<Author> authors = book.getAuthors();
        authors.add(author);
        book.setAuthors(authors);
        bookRepository.save(book);
        return book;
    }

    @Transactional
    @Override
    public Book addGenreById(long bookId, long genreId) {
        Genre genre = genreRepository.getOne(genreId);
        Book book = bookRepository.getOne(bookId);
        Set<Genre> genres = book.getGenres();
        genres.add(genre);
        book.setGenres(genres);
        bookRepository.save(book);
        return book;
    }
}
