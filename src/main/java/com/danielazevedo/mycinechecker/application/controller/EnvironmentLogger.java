package com.danielazevedo.mycinechecker.application.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentLogger {

    // Variáveis de ambiente diretamente
    @Value("${recaptcha.site.key}")
    private String siteKey;

    @Value("${recaptcha.secret.key}")
    private String secretKey;

    @Value("${recaptcha.api.url}")
    private String apiUrl;

    @Value("${recaptcha.project.id}")
    private String projectId;

    @PostConstruct
    public void logEnvironmentAndConfig() {
        System.out.println("=== Variáveis de Ambiente ===");
        System.out.println("GOOGLE_APPLICATION_CREDENTIALS: " + System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
        System.out.println("DB_URL: " + System.getenv("DB_URL"));
        System.out.println("DB_USERNAME: " + System.getenv("DB_USERNAME"));
        System.out.println("DB_PASSWORD: " + System.getenv("DB_PASSWORD"));
        System.out.println("PROJECT_ID: " + System.getenv("PROJECT_ID"));
        System.out.println("SITE_KEY: " + System.getenv("SITE_KEY"));
        System.out.println("SECRET_KEY: " + System.getenv("SECRET_KEY"));
        System.out.println("RECAPTCHA_API_URL: " + System.getenv("RECAPTCHA_API_URL"));
        System.out.println("=============================");

        System.out.println("=== Valores do application.properties ===");
        System.out.println("recaptcha.site.key: " + siteKey);
        System.out.println("recaptcha.secret.key: " + secretKey);
        System.out.println("recaptcha.api.url: " + apiUrl);
        System.out.println("recaptcha.project.id: " + projectId);
        System.out.println("=========================================");
    }
}
