package ru.otus.homework13.rest;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework13.domain.Comment;
import ru.otus.homework13.repository.BookRepository;
import ru.otus.homework13.rest.dto.CommentDto;
import ru.otus.homework13.rest.dto.PostCommentDto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public CommentController(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/books/{bookId}/comments")
    public List<CommentDto> getAllComments(@PathVariable String bookId) {
        bookRepository.getAllComments(bookId);
        return bookRepository.getAllComments(bookId)
                .stream().map(c -> modelMapper.map(c, CommentDto.class)).collect(Collectors.toList());
    }

    @PostMapping("/api/books/{bookId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public void posComment(@PathVariable String bookId, @RequestBody PostCommentDto dto) {
        Comment comment = new Comment(dto.getText(), Instant.now());
        bookRepository.addComment(bookId, comment);
    }

}
