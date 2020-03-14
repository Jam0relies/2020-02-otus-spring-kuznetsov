package ru.otus.config;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionDaoCsvResource;

@Configuration
public class I18nConfig {
    @Bean
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/application");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Bean
    QuestionDao localizedQuestionsDao(MessageSource messageSource, CsvMapper mapper) {
        String questionResource = messageSource.getMessage("questions.source.csv", new String[0], LocaleContextHolder.getLocale());
        return new QuestionDaoCsvResource(questionResource, mapper);
    }
}