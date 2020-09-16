package ru.otus.homework13.mongock;

import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.SpringDataMongo3Driver;
import com.github.cloudyrock.spring.v5.MongockSpring5;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongockConfig {
    @Bean
    public MongockSpring5.MongockInitializingBeanRunner mongockApplicationRunner(
            ApplicationContext springContext,
            MongoTemplate mongoTemplate
    ) {
        return MongockSpring5.builder()
                .setDriver(SpringDataMongo3Driver.withDefaultLock(mongoTemplate))
                .addChangeLogsScanPackage("ru.otus.homework12.mongock.changelog")
                .setSpringContext(springContext)
                .buildInitializingBeanRunner();
    }
}
