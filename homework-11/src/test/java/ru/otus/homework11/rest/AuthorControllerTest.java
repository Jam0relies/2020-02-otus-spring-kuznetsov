package ru.otus.homework11.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.otus.homework11.domain.Author;
import ru.otus.homework11.repository.AuthorRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private AuthorRepository authorRepository;
    @Captor
    private ArgumentCaptor<Author> authorArgumentCaptor;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("call service save method")
    @Test
    void addAuthor() {
        Mockito.when(authorRepository.save(any())).thenReturn(Mono.just(new Author("newAuthor")));
        webTestClient.post().uri("/api/authors").contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"name\":\"newAuthor\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class);
        Mockito.verify(authorRepository).save(authorArgumentCaptor.capture());
        assertEquals("newAuthor", authorArgumentCaptor.getValue().getName());
    }
}