package ru.otus.homework13.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework13.domain.Role;
import ru.otus.homework13.security.JwtAuthority;

@Configuration
public class DtoMappingConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper mapper = new ModelMapper();
        mapper.typeMap(Role.class, String.class)
                .setConverter(a -> a.getSource().getAuthority());
        mapper.typeMap(JwtAuthority.class, String.class)
                .setConverter(a -> a.getSource().getAuthority());
        return mapper;
    }
}
