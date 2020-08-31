package ru.otus.homework11.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework11.domain.Author;
import ru.otus.homework11.service.AuthorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthorController.class)
class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorService authorService;
    @Captor
    private ArgumentCaptor<String> nameCaptor;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("call service save method")
    @Test
    void addAuthor() throws Exception {
        Mockito.when(authorService.addAuthor(any())).thenReturn(new Author(1, "newAuthor"));
        mvc.perform(post("/api/authors").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"newAuthor\"}")).andExpect(status().isOk());
        Mockito.verify(authorService).addAuthor(nameCaptor.capture());
        assertEquals("newAuthor", nameCaptor.getValue());
    }

    @DisplayName("validate name field")
    @Test
    void addNamelessAuthor() throws Exception {
        Mockito.when(authorService.addAuthor(any())).thenReturn(new Author(1, "newAuthor"));
        mvc.perform(post("/api/authors").contentType(MediaType.APPLICATION_JSON)
                .content("")).andExpect(status().isBadRequest());
    }

    @TestConfiguration
    public static class Config {
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }
}