package ru.otus.homework07.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework07.domain.Book;
import ru.otus.homework07.domain.Comment;
import ru.otus.homework07.repository.CommentRepository;

import java.time.Instant;

@ShellComponent
@RequiredArgsConstructor
public class CommentShellInterface {
    private final CommentRepository repository;

    @ShellMethod(value = "Find comment by id (long id)", key = {"commentById"})
    public String getById(@ShellOption long id) {
        return repository.findById(id).toString();
    }

    @ShellMethod(value = "Find comment by book id (long id)", key = {"commentsByBookId"})
    public String getByBookId(@ShellOption long id) {
        return repository.findByBookId(id).toString();
    }

    @ShellMethod(value = "Add comment by book id (long bookId, String text)", key = {"addComment"})
    public String addComment(@ShellOption long id, @ShellOption String text) {
        return repository.save(new Comment(0, text, Instant.now(), new Book(id))).toString();
    }

    @ShellMethod(value = "Delete comment by id (long id)", key = {"deleteComment"})
    public String delete(@ShellOption long id) {
        return Boolean.toString(repository.delete(id));
    }
}
