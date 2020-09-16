package ru.otus.homework13.rest;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework13.domain.Author;
import ru.otus.homework13.repository.AuthorRepository;
import ru.otus.homework13.rest.dto.AddAuthorRequestDto;
import ru.otus.homework13.rest.dto.AuthorDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorController(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/authors")
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream().map(a -> modelMapper.map(a, AuthorDto.class)).collect(Collectors.toList());
    }

    @DeleteMapping("/api/authors/{authorId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable String authorId) {
        authorRepository.deleteById(authorId);
    }

    @PostMapping("/api/authors")
    public AuthorDto addAuthor(@RequestBody @Valid AddAuthorRequestDto dto) {
        Author author = authorRepository.save(new Author(dto.getName()));
        return modelMapper.map(author, AuthorDto.class);
    }
}
