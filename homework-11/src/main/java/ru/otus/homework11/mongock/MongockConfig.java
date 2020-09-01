package ru.otus.homework11.mongock;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableMongock
public class MongockConfig {
//    @Bean
//    public MongockSpring5.MongockInitializingBeanRunner mongockApplicationRunner(
//            ApplicationContext springContext,
//            MongoTemplate mongoTemplate
//    ) {
//        return MongockSpring5.builder()
//                .setDriver(SpringDataMongo3Driver.withDefaultLock(mongoTemplate))
//                .addChangeLogsScanPackage("ru.otus.homework11.mongock.changelog")
//                .setSpringContext(springContext)
//                .buildInitializingBeanRunner();
//    }
}
