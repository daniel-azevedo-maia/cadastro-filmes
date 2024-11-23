package com.danielazevedo.mycinechecker.application.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Método principal que intercepta requisições HTTP.
     * Verifica se o token JWT está presente e, se válido, autentica o usuário no contexto do Spring Security.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extrai o token JWT do cabeçalho "Authorization".
        String token = getJwtFromRequest(request);

        // Verifica se o token é válido.
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // Recupera a autenticação com base no token.
            var authentication = jwtTokenProvider.getAuthentication(token);

            // Confirma se a autenticação é do tipo UsernamePasswordAuthenticationToken.
            if (authentication instanceof UsernamePasswordAuthenticationToken usernamePasswordAuth) {
                // Adiciona os detalhes da requisição à autenticação.
                usernamePasswordAuth.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Configura o contexto de segurança do Spring com a autenticação.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuth);
            }
        }

        // Continua a cadeia de filtros, passando para o próximo.
        filterChain.doFilter(request, response);
    }

    /**
     * Extrai o token JWT do cabeçalho "Authorization" da requisição.
     *
     * @param request a requisição HTTP.
     * @return o token JWT ou null se não estiver presente ou mal formatado.
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // Verifica se o token está presente e começa com "Bearer ".
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Retorna o token sem o prefixo "Bearer ".
        }
        return null;
    }
}
