package com.danielazevedo.mycinechecker.application.config;

import com.danielazevedo.mycinechecker.application.dto.CadastroDTO;
import com.danielazevedo.mycinechecker.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(CadastroDTO.class, User.class)
                .addMappings(mapper -> {
                    mapper.map(CadastroDTO::getNome, User::setNome);
                    mapper.map(CadastroDTO::getUsername, User::setUsername);
                    mapper.map(CadastroDTO::getEmail, User::setEmail);
                    mapper.map(CadastroDTO::getPassword, User::setPassword);

                    mapper.skip(User::setRole); // O campo role será configurado no serviço
                });

        return modelMapper;
    }
}
