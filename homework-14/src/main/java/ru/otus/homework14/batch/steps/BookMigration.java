package ru.otus.homework14.batch.steps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
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
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework14.batch.MongoAggregationItemReader;
import ru.otus.homework14.domain.BookGenreProjection;
import ru.otus.homework14.domain.BookInfoProjection;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Slf4j
@Configuration
public class BookMigration {
    private static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;

    public BookMigration(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public ItemReader<BookInfoProjection> bookReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<BookInfoProjection>()
                .name("bookReader")
                .template(mongoTemplate)
                .query(new Query())
                .collection("books")
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .targetType(BookInfoProjection.class)
                .build();
    }

    @Bean
    public ItemWriter<BookInfoProjection> bookWriter(@Qualifier("targetDatasource") DataSource targetDatasource) {
        return new JdbcBatchItemWriterBuilder<BookInfoProjection>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into books (mongo_id, name) values (:id, :name)")
                .dataSource(targetDatasource)
                .build();
    }

    //    @Bean
//    public ItemWriter<Book> bookGenreWriter(@Qualifier("targetDatasource") DataSource targetDatasource) {
//        return new JdbcBatchItemWriterBuilder<Book>()
//                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql("insert into books (mongo_id, name) values (:id, :name)")
//                .dataSource(targetDatasource)
//                .build();
//    }
    @Bean(name = "bookMigrationStep")
    public Step bookMigrationStep(ItemReader<BookInfoProjection> bookInfoReader, ItemWriter<BookInfoProjection> bookInfoWriter) {
        return stepBuilderFactory.get("bookMigration")
                .<BookInfoProjection, BookInfoProjection>chunk(CHUNK_SIZE)
                .reader(bookInfoReader)
                .writer(bookInfoWriter)
                .build();
    }

    @Bean
    public ItemReader<BookGenreProjection> bookGenreReader(ReactiveMongoTemplate reactiveMongoTemplate) {
        List<AggregationOperation> pipeline = Arrays.asList(
                unwind("$genres", false),
                addFields().addField("fk").withValueOfExpression("{$objectToArray: '$genres'}").build(),
                lookup("genres", "fk.1.v", "_id", "genre"),
                unwind("$genre", false));
        Aggregation aggregation = Aggregation.newAggregation(pipeline);
        return new MongoAggregationItemReader<>(reactiveMongoTemplate, aggregation, "books", BookGenreProjection.class);
    }

    @Bean
    public ItemWriter<BookGenreProjection> bookGenreWriter(@Qualifier("targetDatasource") DataSource targetDatasource) {
        return new JdbcBatchItemWriterBuilder<BookGenreProjection>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into book_genre (book_id, genre_id) " +
                        "values ((select id from books where mongo_id = :id), (select id from genres where mongo_id = :genre.id))")
                .dataSource(targetDatasource)
                .build();
    }

    @Bean(name = "bookGenreMigrationStep")
    public Step bookGenreMigrationStep(ItemReader<BookGenreProjection> bookGenreReader, ItemWriter<BookGenreProjection> bookGenreWriter) {
        ItemProcessor<BookGenreProjection, BookGenreProjection> processor = new ItemProcessor<BookGenreProjection, BookGenreProjection>() {
            @Override
            public BookGenreProjection process(BookGenreProjection item) throws Exception {
                log.info("{}", item);
                return item;
            }
        };
        return stepBuilderFactory.get("bookGenreMigration")
                .<BookGenreProjection, BookGenreProjection>chunk(CHUNK_SIZE)
                .reader(bookGenreReader)
                .processor(processor)
                .writer(bookGenreWriter)
                .build();
    }
}
