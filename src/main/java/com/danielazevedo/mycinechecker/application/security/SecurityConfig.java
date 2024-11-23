package com.danielazevedo.mycinechecker.application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Define o codificador de senhas da aplicação.
     * Utiliza BCrypt, que é um algoritmo seguro e amplamente usado para criptografar senhas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura a cadeia de filtros de segurança do Spring.
     * Inclui configurações de autenticação, autorização, login, logout e proteção CSRF.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita a proteção contra ataques CSRF (Cross-Site Request Forgery).
                // Isso é geralmente usado em APIs REST ou quando tokens são utilizados no frontend.
                .csrf(csrf -> csrf.disable())

                // Configura as permissões de acesso às URLs.
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso público aos endpoints de autenticação e aos arquivos estáticos (CSS/JS).
                        .requestMatchers("/auth/**", "/css/**", "/js/**").permitAll()

                        // Exige autenticação para todas as outras URLs.
                        .anyRequest().authenticated()
                )

                // Configura o comportamento da página de login.
                .formLogin(form -> form
                        // Define a página personalizada de login.
                        .loginPage("/auth/login")

                        // Redireciona o usuário autenticado para a página inicial após login bem-sucedido.
                        .defaultSuccessUrl("/home", true)

                        // Redireciona para a página de login com mensagem de erro em caso de falha.
                        .failureUrl("/auth/login?error=true")

                        // Permite acesso irrestrito à página de login.
                        .permitAll()
                )

                // Configura o comportamento do logout.
                .logout(logout -> logout
                        // Define a URL que realiza o logout.
                        .logoutUrl("/logout")

                        // Redireciona para a página de login após o logout ser concluído.
                        .logoutSuccessUrl("/auth/login")

                        // Permite que qualquer usuário acesse o endpoint de logout.
                        .permitAll()
                );

        // Retorna a configuração da cadeia de filtros do Spring Security.
        return http.build();
    }
}
