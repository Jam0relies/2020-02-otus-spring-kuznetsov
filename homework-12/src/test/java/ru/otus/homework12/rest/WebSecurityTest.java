package ru.otus.homework12.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework12.repository.AuthorRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
@Import({ModelMapper.class, SecurityConfig.class})
public class WebSecurityTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorRepository authorRepository;

    @DisplayName("author list require authentication")
    @Test
    void unauthenticatedAuthorsList() throws Exception {
        mvc.perform(get("/api/authors")).andExpect(status().isFound());
    }
}
