package ru.otus.homework09.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
class BookShellInterfaceTest {
    private static final long FIRST_BOOK_ID = 1;
    //    @Autowired
//    private Shell shell;
    @Autowired
    private EntityManager em;

//    @DisplayName("should add author to book")
//    @Test
//    void addAuthorById() {
//        shell.evaluate(() -> "addBookAuthor" + " 1" + " 2");
//        Book book = em.find(Book.class, FIRST_BOOK_ID);
//        Author author = book.getAuthors().stream().filter(a -> a.getId() == 2).findAny().get();
//        assertEquals("Harold Abelson", author.getName());
//    }
//
//    @DisplayName("should add author to book")
//    @Test
//    void addGenreById() {
//        shell.evaluate(() -> "addBookGenre" + " 1" + " 2");
//        Book book = em.find(Book.class, FIRST_BOOK_ID);
//        Genre genre = book.getGenres().stream().filter(g -> g.getId() == 2).findAny().get();
//        assertEquals("Religion", genre.getName());
//    }
}