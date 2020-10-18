package ru.otus.homework14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class Homework14Application {

	public static void main(String[] args) {
		SpringApplication.run(Homework14Application.class, args);
	}

}
