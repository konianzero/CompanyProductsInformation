package org.company.products.info.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import org.company.products.info.model.Product;
import org.company.products.info.service.ProductService;

@RestController
@RequestMapping(value = Products.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class Products {
    public static final String REST_URL = "/products";
    private static final Logger log = LoggerFactory.getLogger(Products.class);

    private final ProductService productService;

    public Products(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createWithLocation(@RequestBody Product product) {
        log.info("Create new product - {}", product);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                          .path(REST_URL + "/{id}")
                                                          .buildAndExpand(product.getId())
                                                          .toUri();
        return ResponseEntity.created(uriOfNewResource)
                             .body(product);
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable int id) {
        log.info("Get product with id={}", id);
        return productService.get(id);
    }

    @GetMapping
    public List<Product> getAll() {
        log.info("Get all products");
        return productService.getAll();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Product product) {
        log.info("Update product - {}", product);
        productService.update(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete product with id={}", id);
        productService.delete(id);
    }
}
