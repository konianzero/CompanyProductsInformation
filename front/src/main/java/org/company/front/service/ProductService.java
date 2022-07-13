package org.company.front.service;

import lombok.RequiredArgsConstructor;
import org.company.persistence.model.Article;
import org.company.persistence.model.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    public static final String URI_PRODUCTS = "http://localhost:8081/products";
    private static final String URI_PRODUCTS_ID = URI_PRODUCTS + "/{id}";

    private final RestTemplate restTemplate;

    @Transactional
    public Product create(String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForObject(URI_PRODUCTS, request, Product.class);
    }

    public Product get(int id) {
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        return restTemplate.getForObject(URI_PRODUCTS_ID, Product.class, params);
    }

    public List<Product> getAll(String requestQuery) {
        final String getAll = URI_PRODUCTS + "?" + requestQuery;
        return restTemplate.exchange(getAll, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>(){}).getBody();
    }

    @Transactional
    public void update(String requestBody, Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        restTemplate.put(URI_PRODUCTS_ID, request, params);
    }

    @Transactional
    public void delete(int id) {
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        restTemplate.delete(URI_PRODUCTS_ID, params);
    }
}
