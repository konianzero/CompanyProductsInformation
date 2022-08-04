package org.company.front.service;

import lombok.RequiredArgsConstructor;
import org.company.front.service.jms.JmsClient;
import org.company.front.service.jms.to.ProductInfo;
import org.company.front.service.jms.to.ProductInfoRequest;
import org.company.front.web.view.ProductView;
import org.company.persistence.model.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService extends RestTemplateService {

    public static final String URI_PRODUCTS = "/products";
    private static final String URI_PRODUCTS_ID = URI_PRODUCTS + "/{id}";

    private final RestTemplate restTemplate;
    private final JmsClient jmsClient;

    @Transactional
    public Product create(String requestBody) {
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers());
        return restTemplate.postForObject(URI_PRODUCTS, request, Product.class);
    }

    public ProductView get(int id) {
        Product product = restTemplate.getForObject(URI_PRODUCTS_ID, Product.class, uriVariable(id));

        jmsClient.sendMessage(new ProductInfoRequest(id));
        ProductInfo productInfo = jmsClient.getReceivedPayload();

        return new ProductView(product, productInfo);
    }

    public List<Product> getAll(String requestQuery) {
        final String getAll = URI_PRODUCTS + "?" + requestQuery;
        return restTemplate.exchange(getAll, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>(){}).getBody();
    }

    @Transactional
    public void update(String requestBody, Integer id) {
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers());
        restTemplate.put(URI_PRODUCTS_ID, request, uriVariable(id));
    }

    @Transactional
    public void delete(int id) {
        restTemplate.delete(URI_PRODUCTS_ID, uriVariable(id));
    }
}
