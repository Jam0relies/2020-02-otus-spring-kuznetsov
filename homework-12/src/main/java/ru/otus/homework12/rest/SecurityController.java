package ru.otus.homework12.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SecurityController {

    /**
     * For authentication check and future JWT token refreshing
     */
    @PostMapping("/api/auth/refresh")
    @ResponseStatus(HttpStatus.OK)
    void refresh() {

    }
}
