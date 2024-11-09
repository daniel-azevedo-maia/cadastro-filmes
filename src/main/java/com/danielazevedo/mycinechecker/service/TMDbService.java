package com.danielazevedo.mycinechecker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class TMDbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String baseUrl;

    @Value("${tmdb.api.language}")
    private String language;

    private final RestTemplate restTemplate;

    public TMDbService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> buscarFilmesPopulares() {
        String url = String.format("%s/movie/popular?api_key=%s&language=%s", baseUrl, apiKey, language);
        return restTemplate.getForObject(url, Map.class);
    }
}
