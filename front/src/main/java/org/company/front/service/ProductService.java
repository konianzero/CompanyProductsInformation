package org.company.front.service;

import lombok.RequiredArgsConstructor;
import org.company.front.service.jms.JmsClient;
import org.company.front.service.jms.to.ProductInfo;
import org.company.front.service.jms.to.ProductsInfoRequest;
import org.company.front.web.view.ProductView;
import org.company.persistence.model.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductService extends RestTemplateService {

    public static final String URI_PRODUCTS = "/products";
    private static final String URI_PRODUCTS_ID = URI_PRODUCTS + "/{id}";

    private final RestTemplate restTemplate;
    private final JmsClient jmsClient;

    public Product create(String requestBody) {
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers());
        return restTemplate.postForObject(URI_PRODUCTS, request, Product.class);
    }

    @Transactional
    public ProductView get(int id) {
        Product product = restTemplate.getForObject(URI_PRODUCTS_ID, Product.class, uriVariable(id));

        jmsClient.sendMessage(new ProductsInfoRequest(Collections.singletonList(id)));
        ProductInfo productInfo = jmsClient.getReceivedPayload().get(0);

        return new ProductView(product, productInfo);
    }

    @Transactional
    public List<ProductView> getAll(String requestQuery) {
        final String getAll = URI_PRODUCTS + "?" + requestQuery;
        List<Product> products = restTemplate.exchange(getAll, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>(){}).getBody();

        List<Integer> productsIds = products.stream().map(product -> product.getId()).toList();
        jmsClient.sendMessage(new ProductsInfoRequest(productsIds));
        List<ProductInfo> productsInfo = jmsClient.getReceivedPayload();

        return Stream.iterate(0, id -> id < productsIds.size(), id -> id + 1)
                .map(i -> new ProductView(products.get(i), productsInfo.get(i)))
                .toList();
    }

    public void update(String requestBody, Integer id) {
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers());
        restTemplate.put(URI_PRODUCTS_ID, request, uriVariable(id));
    }

    public void delete(int id) {
        restTemplate.delete(URI_PRODUCTS_ID, uriVariable(id));
    }
}
