package org.company.front.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.front.service.ProductService;
import org.company.front.web.view.ProductView;
import org.company.persistence.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping(value = Products.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class Products {
    public static final String REST_URL = "/products";

    private final ProductService productService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> create(@RequestBody String requestBody) {
        log.info("Create new article - {}", requestBody);
        Product created = productService.create(requestBody);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource)
                .body(created);
    }

    @GetMapping("/{id}")
    public ProductView get(@PathVariable int id) throws Exception {
        log.info("Get product with id={}", id);
        return productService.get(id);
    }

    @GetMapping
    public List<Product> getAll(HttpServletRequest request) {
        String requestQuery = URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8);
        log.info("Get all articles with query - {}", requestQuery);
        return productService.getAll(requestQuery);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody String requestBody, @PathVariable int id) {
        log.info("Update article - {}", requestBody);
        productService.update(requestBody, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete product with id={}", id);
        productService.delete(id);
    }
}