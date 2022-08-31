package org.company.front.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.cache.external.jms.to.*;
import org.company.cache.service.HazelcastCache;
import org.company.front.web.view.ProductView;
import org.company.persistence.model.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService extends RestTemplateService {

    public static final String URI_PRODUCTS = "/products";
    private static final String URI_PRODUCTS_ID = URI_PRODUCTS + "/{id}";

    private final RestTemplate restTemplate;
    private final HazelcastCache hazelcastCache;

    @PostConstruct
    public void init() {
        log.info("{} started", this.getClass().getSimpleName());
        log.info("Is HazelcastCache autowired: {}", Objects.nonNull(hazelcastCache));
    }

    public Product create(String requestBody) {
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers());
        return restTemplate.postForObject(URI_PRODUCTS, request, Product.class);
    }

    //@Transactional
    public ProductView get(int id) {
        Product product = restTemplate.getForObject(URI_PRODUCTS_ID, Product.class, uriVariable(id));
        List<ProductInfo> productsInfo = hazelcastCache.getData(new ProductsInfoRequest(Collections.singletonList(id)));
        return new ProductView(product, productsInfo.get(0));
    }

    //@Transactional
    public List<ProductView> getAll(String requestQuery) {
        final String getAll = URI_PRODUCTS + "?" + requestQuery;
        List<Product> products = restTemplate.exchange(getAll, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>(){}).getBody();

        List<Integer> productsIds = products.stream().map(product -> product.getId()).toList();
        List<ProductInfo> productsInfo = hazelcastCache.getData(new ProductsInfoRequest(productsIds));

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
