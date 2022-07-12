package org.company.front.service;

import lombok.RequiredArgsConstructor;
import org.company.persistence.model.Article;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticleService {

    public static final String URI_ARTICLES = "http://localhost:8081/articles";
    private static final String URI_ARTICLES_ID = URI_ARTICLES + "/{id}";

    private final RestTemplate restTemplate;

    @Transactional
    public Article create(String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForObject(URI_ARTICLES, request, Article.class);
    }

    public Article get(int id) {
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        return restTemplate.getForObject(URI_ARTICLES_ID, Article.class, params);
    }

    // TODO - add ParameterizedTypeReference
    public List<Article> getAll(String requestQuery) {
        final String getAll = URI_ARTICLES + "?" + requestQuery;
        return Arrays.asList(
                Objects.requireNonNull(
                        restTemplate.getForObject(getAll, Article[].class)
                )
        );
    }

    @Transactional
    public void update(String requestBody, Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        restTemplate.put(URI_ARTICLES_ID, request, params);
    }

    @Transactional
    public void delete(int id) {
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        restTemplate.delete(URI_ARTICLES_ID, params);
    }
}
