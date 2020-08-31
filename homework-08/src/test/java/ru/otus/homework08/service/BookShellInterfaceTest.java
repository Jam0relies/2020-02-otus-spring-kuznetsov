package ru.otus.homework08.service;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.shell.Shell;
import ru.otus.homework08.domain.Author;
import ru.otus.homework08.domain.Book;
import ru.otus.homework08.domain.Genre;
import ru.otus.homework08.repository.AuthorRepository;
import ru.otus.homework08.repository.BookRepository;
import ru.otus.homework08.repository.GenreRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BookShellInterfaceTest {
    @Autowired
    private Shell shell;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    void addAuthor() {
        Book book = bookRepository.findByName("Hamlet").get(0);
        Author author = authorRepository.findByName("Harold Abelson").get(0);

        Object res = shell.evaluate(() -> "addBookAuthor" + " " + book.getId() + " " + author.getId());
        assertTrue(res instanceof String, res.toString());
        assertTrue(bookRepository.findByName("Hamlet").get(0).getAuthors().contains(author));
    }

    @Test
    void addGenre() {
        Book book = bookRepository.findByName("Hamlet").get(0);
        Genre genre = genreRepository.findByName("Religion").get(0);

        Object res = shell.evaluate(() -> "addBookGenre" + " " + book.getId() + " " + genre.getId());
        assertTrue(res instanceof String, res.toString());
        assertTrue(bookRepository.findByName("Hamlet").get(0).getGenres().contains(genre));
    }
}