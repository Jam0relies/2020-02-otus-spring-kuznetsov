package ru.otus.homework08.service;

import lombok.RequiredArgsConstructor;
import org.bson.internal.UuidHelper;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework08.domain.Book;
import ru.otus.homework08.domain.Comment;
import ru.otus.homework08.repository.BookRepository;


import java.time.Instant;
import java.util.UUID;

@ShellComponent
@RequiredArgsConstructor
public class CommentShellInterface {
    private final BookRepository bookRepository;

    @ShellMethod(value = "Find comment by book id (String bookId, String commentId)", key = {"commentsByBookId"})
    public String getByBookId(@ShellOption String bookId, @ShellOption UUID commentId) {
        return bookRepository.findById(bookId).get().getComments().get(commentId).toString();
    }

    @ShellMethod(value = "Add comment by book id (String bookId, String text)", key = {"addComment"})
    public String addComment(@ShellOption String bookId, @ShellOption String text) {
        Book book = bookRepository.findById(bookId).get();
        Comment comment = new Comment(text, Instant.now());
        UUID id = UUID.randomUUID();
        book.getComments().put(id, comment);
        bookRepository.save(book);
        return id + " " + comment.toString();
    }

    @ShellMethod(value = "Delete comment from book by id (String bookId, UUID commentId)", key = {"deleteComment"})
    public void delete(@ShellOption String bookId, @ShellOption UUID commentId) {
        Book book = bookRepository.findById(bookId).get();
        book.getComments().remove(commentId);
        bookRepository.save(book);
    }
}
