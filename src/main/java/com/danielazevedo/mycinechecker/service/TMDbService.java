package com.danielazevedo.mycinechecker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class TMDbService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;
    private final String language;

    public TMDbService(RestTemplate restTemplate,
                       @Value("${tmdb.api.url}") String baseUrl,
                       @Value("${tmdb.api.key}") String apiKey,
                       @Value("${tmdb.api.language}") String language) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.language = language;
    }

    public Map<String, Object> buscarFilmesPopulares() {
        String url = String.format("%s/movie/popular?api_key=%s&language=%s", baseUrl, apiKey, language);
        return restTemplate.getForObject(url, Map.class);
    }
}
