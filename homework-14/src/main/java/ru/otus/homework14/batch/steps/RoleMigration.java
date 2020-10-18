package ru.otus.homework14.batch.steps;

//import org.h2.jdbcx.JdbcDataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework14.domain.Role;

import javax.sql.DataSource;
import java.util.Collections;

@EnableBatchProcessing
@Configuration
public class RoleMigration {
    private static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;

    public RoleMigration(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

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

    @Bean
    public ItemWriter<Role> roleWriter(@Qualifier("targetDatasource") DataSource targetDatasource) {
        return new JdbcBatchItemWriterBuilder<Role>()
                .beanMapped()
                .sql("insert into roles (authority, mongo_id) values (:authority, :id)")
                .dataSource(targetDatasource)
                .build();
    }

    @Bean(name = "roleMigrationStep")
    public Step roleMigration(ItemReader<Role> roleReader, ItemWriter<Role> roleWriter) {
        return stepBuilderFactory.get("roleMigration")
                .<Role, Role>chunk(CHUNK_SIZE)
                .reader(roleReader)
                .writer(roleWriter)
                .build();
    }
}
