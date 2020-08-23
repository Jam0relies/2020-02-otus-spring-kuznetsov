package ru.otus.homework10.rest;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework10.rest.dto.AuthorDto;
import ru.otus.homework10.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private final AuthorService authorService;
    private final ModelMapper modelMapper;

    public AuthorController(AuthorService authorService, ModelMapper modelMapper) {
        this.authorService = authorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/authors")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAll().stream().map(a -> modelMapper.map(a, AuthorDto.class)).collect(Collectors.toList());
    }

    @DeleteMapping("/api/authors/{authorId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable long authorId) {
        authorService.delete(authorId);
    }
}
