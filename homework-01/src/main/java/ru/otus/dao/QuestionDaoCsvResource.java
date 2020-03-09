package ru.otus.dao;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.core.io.ClassPathResource;
import ru.otus.domain.Question;
import ru.otus.domain.QuestionImpl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class QuestionDaoCsvResource implements QuestionDao {
    private String resourceName;
    private CsvMapper mapper;
    private CsvSchema schema;

    public QuestionDaoCsvResource(String resourceName, CsvMapper mapper) {
        this.resourceName = resourceName;
        this.mapper = mapper;
        this.schema = mapper.schema().withHeader();
    }

    @Override
    public List<Question> getAll() {
        try {
            File file = new ClassPathResource(resourceName).getFile();
            MappingIterator<Question> readValues =
                    mapper.readerFor(QuestionImpl.class).with(schema).readValues(file);
            return readValues.readAll();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
