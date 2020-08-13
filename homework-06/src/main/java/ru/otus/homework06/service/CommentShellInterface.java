package ru.otus.homework06.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.domain.Book;
import ru.otus.homework06.domain.Comment;
import ru.otus.homework06.repository.BookRepository;
import ru.otus.homework06.repository.CommentRepository;

import java.time.Instant;

@ShellComponent
@RequiredArgsConstructor
public class CommentShellInterface {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @ShellMethod(value = "Find comment by id (long id)", key = {"commentById"})
    public String getById(@ShellOption long id) {
        return commentRepository.findById(id).toString();
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Find comment by book id (long id)", key = {"commentsByBookId"})
    public String getByBookId(@ShellOption long id) {
        Book book = bookRepository.findById(id).get();
        return book.getComments().toString();
    }

    @ShellMethod(value = "Add comment by book id (long bookId, String text)", key = {"addComment"})
    public String addComment(@ShellOption long id, @ShellOption String text) {
        return commentRepository.save(new Comment(text, Instant.now(), new Book(id))).toString();
    }

    @ShellMethod(value = "Delete comment by id (long id)", key = {"deleteComment"})
    public void delete(@ShellOption long id) {
        commentRepository.delete(id);
    }
}
