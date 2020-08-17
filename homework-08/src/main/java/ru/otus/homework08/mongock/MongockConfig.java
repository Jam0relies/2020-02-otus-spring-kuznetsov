package ru.otus.homework08.mongock;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.context.annotation.Configuration;

@EnableMongock
@Configuration
public class MongockConfig {
//    @Bean
//    public MongockSpring5.MongockInitializingBeanRunner mongockApplicationRunner(
//            ApplicationContext springContext,
//            MongoTemplate mongoTemplate){
//        return MongockSpring5.builder()
//                .setDriver(SpringDataMongo3Driver.withDefaultLock(mongoTemplate))
//                .addChangeLogsScanPackage("ru.otus.homework08.mongock.changelog")
//                .setSpringContext(springContext)
//                .buildInitializingBeanRunner();
//    }
}
