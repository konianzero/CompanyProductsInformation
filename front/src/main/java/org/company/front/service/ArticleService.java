package org.company.front.service;

import lombok.RequiredArgsConstructor;
import org.company.persistence.model.Article;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticleService extends RestTemplateService {

    public static final String URI_ARTICLES = "/articles";
    private static final String URI_ARTICLES_ID = URI_ARTICLES + "/{id}";

    private final RestTemplate restTemplate;

    public Article create(String requestBody) {
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers());
        return restTemplate.postForObject(URI_ARTICLES, request, Article.class);
    }

    public Article get(int id) {
        return restTemplate.getForObject(URI_ARTICLES_ID, Article.class, uriVariable(id));
    }

    public List<Article> getAll(String requestQuery) {
        final String getAll = URI_ARTICLES + "?" + requestQuery;
        return restTemplate.exchange(getAll, HttpMethod.GET, null, new ParameterizedTypeReference<List<Article>>(){}).getBody();
    }

    public void update(String requestBody, Integer id) {
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers());
        restTemplate.put(URI_ARTICLES_ID, request, uriVariable(id));
    }

    public void delete(int id) {
        restTemplate.delete(URI_ARTICLES_ID, uriVariable(id));
    }
}
