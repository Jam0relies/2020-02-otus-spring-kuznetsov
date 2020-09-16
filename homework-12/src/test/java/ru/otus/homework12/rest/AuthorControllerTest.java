package ru.otus.homework12.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework12.domain.Author;
import ru.otus.homework12.repository.AuthorRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
@Import({ModelMapper.class, SecurityConfig.class})
class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorRepository authorRepository;
    @Captor
    private ArgumentCaptor<Author> authorArgumentCaptor;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @WithMockUser(
            username = "admin"
    )
    @DisplayName("call service save method")
    @Test
    void addAuthor() throws Exception {
        Mockito.when(authorRepository.save(any())).thenReturn(new Author("newAuthor"));
        mvc.perform(post("/api/authors").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"newAuthor\"}")).andExpect(status().isOk());
        Mockito.verify(authorRepository).save(authorArgumentCaptor.capture());
        assertEquals("newAuthor", authorArgumentCaptor.getValue().getName());
    }

}