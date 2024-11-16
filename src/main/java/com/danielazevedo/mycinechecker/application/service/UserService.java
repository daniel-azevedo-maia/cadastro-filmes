package com.danielazevedo.mycinechecker.application.service;

import com.danielazevedo.mycinechecker.application.dto.CadastroDTO;
import com.danielazevedo.mycinechecker.application.exception.UsuarioExistenteException;
import com.danielazevedo.mycinechecker.domain.model.Role;
import com.danielazevedo.mycinechecker.domain.model.User;
import com.danielazevedo.mycinechecker.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void cadastrarNovoUsuario(CadastroDTO cadastroDTO) {
        if (userRepository.existsByUsername(cadastroDTO.getUsername())) {
            throw new UsuarioExistenteException("O nome de usuário já está em uso.");
        }
        if (userRepository.existsByEmail(cadastroDTO.getEmail())) {
            throw new UsuarioExistenteException("O e-mail já está cadastrado.");
        }

        User user = new User();
        user.setUsername(cadastroDTO.getUsername());
        user.setEmail(cadastroDTO.getEmail());
        user.setPassword(passwordEncoder.encode(cadastroDTO.getPassword()));

        userRepository.save(user);
    }
}
