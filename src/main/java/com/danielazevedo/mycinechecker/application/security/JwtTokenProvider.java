package com.danielazevedo.mycinechecker.application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}") // Chave secreta para assinatura do JWT.
    private String jwtSecret;

    @Value("${app.jwt.expiration-milliseconds}") // Tempo de expiração do JWT (em milissegundos).
    private long jwtExpirationMilliseconds;

    private final UserDetailsService userDetailsService;

    /**
     * Gera um token JWT com base nos dados de autenticação.
     *
     * @param authentication informações do usuário autenticado.
     * @return o token JWT gerado.
     */
    public String generateToken(Authentication authentication) {
        String username = authentication.getName(); // Obtém o username do usuário autenticado.
        Date now = new Date(); // Data atual.
        Date expiryDate = new Date(now.getTime() + jwtExpirationMilliseconds); // Define a expiração.

        // Cria e retorna o token JWT assinado.
        return Jwts.builder()
                .setSubject(username) // Define o username como o subject do token.
                .setIssuedAt(now) // Define a data de emissão.
                .setExpiration(expiryDate) // Define a data de expiração.
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // Assina o token com a chave secreta.
                .compact();
    }

    /**
     * Valida o token JWT.
     *
     * @param token o token JWT a ser validado.
     * @return true se o token for válido, false caso contrário.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token); // Verifica a assinatura e validade.
            return true;
        } catch (Exception ex) {
            return false; // Retorna false se o token for inválido ou expirado.
        }
    }

    /**
     * Recupera as informações de autenticação a partir do token JWT.
     *
     * @param token o token JWT.
     * @return o objeto Authentication contendo os detalhes do usuário.
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret) // Usa a chave secreta para decodificar o token.
                .parseClaimsJws(token)
                .getBody(); // Extrai o corpo do token (claims).

        String username = claims.getSubject(); // Recupera o username do subject.
        UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Carrega os detalhes do usuário.

        // Cria e retorna um token de autenticação.
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
