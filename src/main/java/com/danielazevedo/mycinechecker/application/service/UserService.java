package com.danielazevedo.mycinechecker.application.service;

import com.danielazevedo.mycinechecker.application.dto.CadastroDTO;
import com.danielazevedo.mycinechecker.application.dto.LoginDTO;
import com.danielazevedo.mycinechecker.application.exception.UsuarioExistenteException;
import com.danielazevedo.mycinechecker.domain.model.User;
import com.danielazevedo.mycinechecker.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean validarLogin(LoginDTO loginDTO) {
        Optional<User> usuarioOpt = userRepository.findByUsername(loginDTO.getUsername());
        if (usuarioOpt.isPresent()) {
            User usuario = usuarioOpt.get();
            return passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword());
        }
        return false;
    }

    public void cadastrarNovoUsuario(CadastroDTO cadastroDTO) {
        if (userRepository.existsByUsername(cadastroDTO.getUsername())) {
            throw new UsuarioExistenteException("O nome de usuário já está em uso.");
        }
        if (userRepository.existsByEmail(cadastroDTO.getEmail())) {
            throw new UsuarioExistenteException("O e-mail já está em uso.");
        }

        User novoUsuario = new User();
        novoUsuario.setNome(cadastroDTO.getNome());
        novoUsuario.setUsername(cadastroDTO.getUsername());
        novoUsuario.setEmail(cadastroDTO.getEmail());
        novoUsuario.setPassword(passwordEncoder.encode(cadastroDTO.getPassword()));

        userRepository.save(novoUsuario);
    }
}
