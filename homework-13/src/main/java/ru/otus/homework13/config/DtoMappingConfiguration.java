package ru.otus.homework13.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework13.domain.Role;

@Configuration
public class DtoMappingConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper mapper = new ModelMapper();
        mapper.typeMap(Role.class, String.class)
                .setConverter(a -> a.getSource().getAuthority());
        return mapper;
    }
}
