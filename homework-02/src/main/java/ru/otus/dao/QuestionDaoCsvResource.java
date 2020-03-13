package ru.otus.dao;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.core.io.ClassPathResource;
import ru.otus.domain.Question;
import ru.otus.domain.QuestionImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class QuestionDaoCsvResource implements QuestionDao {
    private final String resourceName;
    private final CsvMapper mapper;
    private final CsvSchema schema;

    public QuestionDaoCsvResource(String resourceName, CsvMapper mapper) {
        this.resourceName = resourceName;
        this.mapper = mapper;
        this.schema = mapper.schema().withHeader();
    }

    @Override
    public List<Question> getAll() {
        try {
            InputStream csvStream = new ClassPathResource(resourceName).getInputStream();
            MappingIterator<Question> readValues =
                    mapper.readerFor(QuestionImpl.class).with(schema).readValues(csvStream);
            return readValues.readAll();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
