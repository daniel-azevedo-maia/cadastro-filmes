package com.danielazevedo.mycinechecker.application.service;

import com.danielazevedo.mycinechecker.application.dto.RecaptchaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RecaptchaValidationService {

    @Value("${recaptcha.secret.key}")
    private String secretKey;

    @Value("${recaptcha.site.key}")
    private String siteKey;

    @Value("${recaptcha.api.url}")
    private String apiUrl;

    @Value("${recaptcha.project.id}")
    private String projectId;

    @Value("${recaptcha.api.key}")
    private String apiKey;

    public RecaptchaResponse validate(String token, String action) {
        RestTemplate restTemplate = new RestTemplate();

        // Construir URL com API_KEY
        String url = apiUrl + "/projects/" + projectId + "/assessments?key=" + apiKey;
        log("URL da Requisição", url);

        // Configurar cabeçalhos
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log("Cabeçalhos da Requisição", "Content-Type: " + headers.getContentType());

        // Criar corpo da requisição no formato esperado
        Map<String, Object> body = Map.of(
                "event", Map.of(
                        "token", token,
                        "expectedAction", action,
                        "siteKey", siteKey
                )
        );
        log("Corpo da Requisição", body.toString());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            log("Iniciando a Requisição", "Enviando POST para " + url);

            ResponseEntity<RecaptchaResponse> response = restTemplate.postForEntity(url, request, RecaptchaResponse.class);

            // Log da resposta
            log("Resposta da API", String.format("Status Code: %s, Body: %s",
                    response.getStatusCode(),
                    response.getBody() != null ? response.getBody().toString() : "null"));

            if (response.getBody() != null) {
                log("Resposta Detalhada",
                        "isValid: " + response.getBody().isValid() +
                                ", score: " + response.getBody().getScore() +
                                ", action: " + response.getBody().getAction());
            }

            // Validação dos campos retornados pela API
            if (response.getBody() == null || !response.getBody().isValid() || response.getBody().getScore() < 0.5) {
                throw new RuntimeException("Validação do reCAPTCHA falhou.");
            }

            if (!response.getBody().getAction().equals(action)) {
                throw new RuntimeException("Ação do reCAPTCHA não corresponde.");
            }

            log("Validação Bem-Sucedida", "Pontuação: " + response.getBody().getScore());
            return response.getBody();

        } catch (Exception e) {
            log("Erro na Validação", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao validar o reCAPTCHA: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para logs
    private void log(String title, String message) {
        System.out.println("=== " + title + " ===");
        System.out.println(message);
        System.out.println("==============================");
    }
}
