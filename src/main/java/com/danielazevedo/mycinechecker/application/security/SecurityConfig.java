package com.danielazevedo.mycinechecker.application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/css/**", "/js/**", "/public/**").permitAll()  // Permitir acesso sem autenticação
                        .anyRequest().authenticated() // Exigir autenticação para outras requisições
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")  // Página de login personalizada
                        .defaultSuccessUrl("/home", true)  // Redireciona para /home após login bem-sucedido
                        .failureUrl("/auth/login?error=true")  // Página de erro de login
                        .permitAll()  // Permite acesso à página de login sem autenticação
                )
                // Configuração do logout
                .logout(logout -> logout
                        .logoutSuccessUrl("/auth/login?logout=true")  // Redireciona para a página de login após o logout
                        .clearAuthentication(true)  // Limpa a autenticação
                        .invalidateHttpSession(true)  // Invalida a sessão do usuário
                        .permitAll()  // Permite acesso ao logout sem autenticação
                )
                // Habilitando CSRF
                .csrf(csrf -> csrf.ignoringRequestMatchers("/auth/**"));  // Ignorar CSRF apenas para login
        return http.build();
    }
}
