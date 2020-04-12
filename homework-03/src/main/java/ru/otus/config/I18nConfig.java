package ru.otus.config;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionDaoCsvResource;

import java.util.Locale;

@Configuration
public class I18nConfig {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/local");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Bean
    public Locale locale() {
        return LocaleContextHolder.getLocale();
    }

    @Bean
    public QuestionDao localizedQuestionsDao(MessageSource messageSource, CsvMapper mapper, Locale locale) {
        String questionResource = messageSource.getMessage("questions.source.csv", null, locale);
        return new QuestionDaoCsvResource(questionResource, mapper);
    }
}
