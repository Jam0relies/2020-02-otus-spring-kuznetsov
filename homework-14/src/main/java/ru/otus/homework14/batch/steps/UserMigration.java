package ru.otus.homework14.batch.steps;

import org.springframework.batch.core.Step;
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
import ru.otus.homework14.domain.User;

import javax.sql.DataSource;
import java.util.Collections;

@Configuration
public class UserMigration {
    private static final int CHUNK_SIZE = 5;
    private final StepBuilderFactory stepBuilderFactory;

    public UserMigration(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public ItemReader<User> userReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<User>()
                .name("userReader")
                .template(mongoTemplate)
                .query(new Query())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .targetType(User.class)
                .build();
    }

    @Bean
    public ItemWriter<User> userWriter(@Qualifier("targetDatasource") DataSource targetDatasource) {
        return new JdbcBatchItemWriterBuilder<User>()
                .beanMapped()
                .sql("insert into users (mongo_id, username, password, account_non_expired, account_non_locked, " +
                        "credentials_non_expired, enabled) values (:id, :username, :password, :accountNonExpired, " +
                        ":accountNonLocked, :credentialsNonExpired, :enabled)")
                .dataSource(targetDatasource)
                .build();
    }

    @Bean(name = "userMigrationStep")
    public Step userMigrationStep(ItemReader<User> userReader, ItemWriter<User> userWriter) {
        return stepBuilderFactory.get("userMigration")
                .<User, User>chunk(CHUNK_SIZE)
                .reader(userReader)
                .writer(userWriter)
                .build();
    }
}
