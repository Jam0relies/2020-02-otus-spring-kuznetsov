package ru.otus.homework14.batch.steps;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import ru.otus.homework14.batch.MongoAggregationItemReader;
import ru.otus.homework14.domain.BookCommentProjection;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

@Configuration
public class CommentMigration {
    private static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;

    public CommentMigration(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public ItemReader<BookCommentProjection> bookCommentReader(ReactiveMongoTemplate reactiveMongoTemplate) {
        List<AggregationOperation> pipeline = Arrays.asList(
                unwind("$comments", false));
        Aggregation aggregation = Aggregation.newAggregation(pipeline);
        return new MongoAggregationItemReader<>(reactiveMongoTemplate, aggregation, "books", BookCommentProjection.class);
    }

    @Bean
    public ItemWriter<BookCommentProjection> bookCommentWriter(@Qualifier("targetDatasource") DataSource targetDatasource) {
        return new JdbcBatchItemWriterBuilder<BookCommentProjection>()
                .beanMapped()
                .sql("insert into comments (text, timestamp, book_id) " +
                        "values (:comments.text, :comments.timestamp, (select id from books where mongo_id = :id))")
                .dataSource(targetDatasource)
                .build();
    }

    @Bean(name = "commentMigrationStep")
    public Step bookAuthorMigrationStep(ItemReader<BookCommentProjection> commentReader, ItemWriter<BookCommentProjection> commentWriter) {
        return stepBuilderFactory.get("bookCommentMigration")
                .<BookCommentProjection, BookCommentProjection>chunk(CHUNK_SIZE)
                .reader(commentReader)
                .writer(commentWriter)
                .build();
    }
}
