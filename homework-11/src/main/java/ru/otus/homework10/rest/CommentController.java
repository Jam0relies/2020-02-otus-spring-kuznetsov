package ru.otus.homework10.rest;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework10.rest.dto.CommentDto;
import ru.otus.homework10.rest.dto.PostCommentDto;
import ru.otus.homework10.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/books/{bookId}/comments")
    List<CommentDto> getComments(@PathVariable long bookId) {
        return commentService.getByBookId(bookId).stream()
                .map(c -> modelMapper.map(c, CommentDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/api/books/{bookId}/comments")
    @ResponseStatus(HttpStatus.OK)
    void postComment(@PathVariable long bookId, @RequestBody PostCommentDto commentDto) {
        commentService.addComment(bookId, commentDto.getText());
    }
}
