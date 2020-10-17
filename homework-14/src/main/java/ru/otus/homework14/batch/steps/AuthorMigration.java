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
import ru.otus.homework14.domain.Author;

import javax.sql.DataSource;
import java.util.Collections;

@Configuration
public class AuthorMigration {
    private static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;

    public AuthorMigration(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public ItemReader<Author> authorReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Author>()
                .name("authorReader")
                .template(mongoTemplate)
                .query(new Query())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .targetType(Author.class)
                .build();
    }

    @Bean
    public ItemWriter<Author> authorWriter(@Qualifier("targetDatasource") DataSource targetDatasource) {
        return new JdbcBatchItemWriterBuilder<Author>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into authors (mongo_id, name) values (:id, :name)")
                .dataSource(targetDatasource)
                .build();
    }

    @Bean(name = "authorMigrationStep")
    public Step authorMigrationStep(ItemReader<Author> authorReader, ItemWriter<Author> authorWriter) {
        return stepBuilderFactory.get("authorMigration")
                .<Author, Author>chunk(CHUNK_SIZE)
                .reader(authorReader)
                .writer(authorWriter)
                .build();
    }
}
