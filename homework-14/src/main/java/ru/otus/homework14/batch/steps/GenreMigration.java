package ru.otus.homework14.batch.steps;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework14.domain.Genre;

import javax.sql.DataSource;
import java.util.Collections;

@Configuration
public class GenreMigration {
    private static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;

    public GenreMigration(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public ItemReader<Genre> genreReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Genre>()
                .name("genreReader")
                .template(mongoTemplate)
                .query(new Query())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .targetType(Genre.class)
                .build();
    }

    @Bean
    public ItemWriter<Genre> genreWriter(@Qualifier("targetDatasource") DataSource targetDatasource) {
        return new JdbcBatchItemWriterBuilder<Genre>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into genres (mongo_id, name) values (:id, :name)")
                .dataSource(targetDatasource)
                .build();
    }

    @Bean(name = "genreMigrationStep")
    public Step genreMigrationStep(ItemReader<Genre> genreReader, ItemWriter<Genre> genreWriter) {
        return stepBuilderFactory.get("genreMigration")
                .<Genre, Genre>chunk(CHUNK_SIZE)
                .reader(genreReader)
                .writer(genreWriter)
                .build();
    }
}
