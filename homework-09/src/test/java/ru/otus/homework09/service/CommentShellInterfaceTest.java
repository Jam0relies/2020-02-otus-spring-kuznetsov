package ru.otus.homework09.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CommentShellInterfaceTest {
    @Autowired
    private Shell shell;

    @DisplayName("Should work without transaction errors")
    @Test
    void getByBookId() {
        Object res = shell.evaluate(() -> "commentsByBookId" + " 1");
        assertTrue(res instanceof String);
    }
}