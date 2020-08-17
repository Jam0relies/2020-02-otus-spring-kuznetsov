package ru.otus.homework08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework08.domain.Book;
import ru.otus.homework08.domain.Comment;
import ru.otus.homework08.repository.BookRepository;
import ru.otus.homework08.repository.CommentRepository;

import java.time.Instant;

@ShellComponent
@RequiredArgsConstructor
public class CommentShellInterface {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @ShellMethod(value = "Find comment by id (String id)", key = {"commentById"})
    public String getById(@ShellOption String id) {
        return commentRepository.findById(id).toString();
    }

    @ShellMethod(value = "Find comment by book id (String id)", key = {"commentsByBookId"})
    public String getByBookId(@ShellOption String id) {
        return commentRepository.findByBookId(id).toString();
    }

    @Transactional
    @ShellMethod(value = "Add comment by book id (String bookId, String text)", key = {"addComment"})
    public String addComment(@ShellOption String bookId, @ShellOption String text) {
        Book book = bookRepository.findById(bookId).get();
        return commentRepository.save(new Comment(text, Instant.now(), book)).toString();
    }

    @ShellMethod(value = "Delete comment by id (String id)", key = {"deleteComment"})
    public void delete(@ShellOption String id) {
        commentRepository.deleteById(id);
    }
}
