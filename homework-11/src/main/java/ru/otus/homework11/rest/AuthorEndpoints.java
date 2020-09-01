package ru.otus.homework11.rest;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.homework11.domain.Author;
import ru.otus.homework11.repository.AuthorRepository;
import ru.otus.homework11.rest.dto.AddAuthorRequestDto;
import ru.otus.homework11.rest.dto.AuthorDto;

import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class AuthorEndpoints {
    private final ModelMapper modelMapper;

    public AuthorEndpoints(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Bean
    public RouterFunction<ServerResponse> authorRoutes(AuthorRepository authorRepository) {
        final Function<Author, AuthorDto> authorToDtoMapper = (Author author) -> modelMapper.map(author, AuthorDto.class);
        return route()
                .GET("/api/authors",
                        request -> ok().contentType(APPLICATION_JSON)
                                .body(authorRepository.findAll()
                                        .map(authorToDtoMapper), AuthorDto.class))
                .DELETE("/api/authors/{authorId}",
                        request -> authorRepository.deleteById(request.pathVariable("authorId"))
                                .flatMap(v -> ok().build()))
                .POST("/api/authors", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(AddAuthorRequestDto.class)
                                .map(dto -> {
                                    Author author = new Author(dto.getName());
                                    return authorRepository.save(author);
                                })
                                .flatMap(result -> ok().body(result, Author.class)))

//                .GET("/func/person", queryParam("name", StringUtils::isNotEmpty),
//                        request -> request.queryParam("name")
//                                .map(repository::findAllByLastName)
//                                .map(persons -> ok().body(persons, Person.class))
//                                .orElse(notFound().build())
//                )
//                .GET("/func/person", accept(APPLICATION_JSON),
//                        request -> ok().contentType(APPLICATION_JSON).body(repository.findAll(), Person.class))
//                .GET("/func/person/{id}", accept(APPLICATION_JSON),
//                        request -> repository.findById(request.pathVariable("id"))
//                                .flatMap(person -> ok().contentType(APPLICATION_JSON).body(fromObject(person)))
//                )
                .build();
    }

}
