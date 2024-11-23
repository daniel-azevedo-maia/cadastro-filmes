package com.danielazevedo.mycinechecker.application.security;

import com.danielazevedo.mycinechecker.domain.model.User;
import com.danielazevedo.mycinechecker.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Construtor para injeção do repositório de usuários.
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Carrega os detalhes do usuário com base no username.
     *
     * @param username o username do usuário.
     * @return os detalhes do usuário no formato que o Spring Security entende.
     * @throws UsernameNotFoundException se o usuário não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o nome: " + username));

        // Retorna os dados do usuário convertidos para o formato do Spring Security.
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername()) // Define o username.
                .password(user.getPassword()) // Define a senha criptografada.
                .authorities(user.getRole().name()) // Define as roles do usuário.
                .build();
    }
}
