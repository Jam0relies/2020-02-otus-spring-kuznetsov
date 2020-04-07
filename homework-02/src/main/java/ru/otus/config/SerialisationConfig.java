package ru.otus.config;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SerialisationConfig {
    @Bean
    public CsvMapper csvMapper() {
        return new CsvMapper();
    }
}
