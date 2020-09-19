package ru.otus.homework13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class Homework13Application {

    public static void main(String[] args) {
        SpringApplication.run(Homework13Application.class, args);
    }

}
