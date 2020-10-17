package ru.otus.homework14.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Primary
@Configuration
@ConfigurationProperties(prefix = "datasource.batch")
public class BatchDatasourceConfig extends DataSourceProperties {
    @Primary
    @Bean(name = "batchDatasource")
    public DataSource batchDatasource() {
        return super.initializeDataSourceBuilder().build();
    }
}
