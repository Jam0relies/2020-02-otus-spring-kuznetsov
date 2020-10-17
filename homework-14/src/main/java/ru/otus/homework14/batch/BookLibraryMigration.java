package ru.otus.homework14.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookLibraryMigration {
    private final JobBuilderFactory jobBuilderFactory;

    public BookLibraryMigration(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Job libraryMigrationJob(@Qualifier("roleMigrationStep") Step roleStep,
                                   @Qualifier("userMigrationStep") Step userStep,
                                   @Qualifier("authorMigrationStep") Step authorStep,
                                   @Qualifier("genreMigrationStep") Step genreStep,
                                   @Qualifier("bookMigrationStep") Step bookInfoStep,
                                   @Qualifier("bookGenreMigrationStep") Step bookGenreMigrationStep) {
        return jobBuilderFactory.get("libraryMigration")
                .incrementer(new RunIdIncrementer())
                .flow(roleStep)
                .next(userStep)
                .next(authorStep)
                .next(genreStep)
                .next(bookInfoStep)
                .next(bookGenreMigrationStep)
                .end().build();
    }
}
