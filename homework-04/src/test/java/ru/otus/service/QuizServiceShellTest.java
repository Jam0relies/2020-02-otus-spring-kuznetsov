package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Shell command test")
@SpringBootTest
class QuizServiceShellTest {
    private static final String COMMAND_NAME = "name";
    private static final String COMMAND_START = "start";
    private static final String COMMAND_ANSWER = "answer";

    @Autowired
    private Shell shell;

    @DisplayName(" should require name before start")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldRequireNameBeforeStartingTest() {
        Object res = shell.evaluate(() -> COMMAND_START);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName(" should require start before answer")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldRequireStartBeforeAnswer() {
        Object res = shell.evaluate(() -> COMMAND_NAME);
        assertThat(res).isInstanceOf(String.class);
        res = shell.evaluate(() -> COMMAND_ANSWER);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName(" should be passable")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void testIsAnswerable() {
        Object res = shell.evaluate(() -> COMMAND_NAME);
        System.out.println(res);
        res = shell.evaluate(() -> COMMAND_START);
        System.out.println(res);
        for (int i = 0; i < 5; i++) {
            final String answer = "answer" + (i + 1);
            res = shell.evaluate(() -> COMMAND_ANSWER + " " + answer);
            System.out.println(res);
        }
        assertTrue(res instanceof String);
        assertTrue(((String) res).contains("5"));
    }

    @TestConfiguration
    static class Config {
        @Bean
        Locale locale() {
            return new Locale("en", "EN");
        }
    }
}