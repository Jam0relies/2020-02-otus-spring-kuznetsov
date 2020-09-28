package ru.otus.homework14.batch;

//import org.h2.jdbcx.JdbcDataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework14.domain.Role;

import java.util.Collections;

@EnableBatchProcessing
@Configuration
public class BookLibraryMigration {
    @Bean
    public ItemReader<Role> roleReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Role>()
                .name("roleReader")
                .template(mongoTemplate)
                .query(new Query())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .targetType(Role.class)
                .build();
    }

//    @Bean
//    public ItemWriter<Role> roleWriter() {
//        return new JdbcBatchItemWriterBuilder<Role>()
//                .dataSource(new JdbcDataSource().)
//                .build();
//    }

}
