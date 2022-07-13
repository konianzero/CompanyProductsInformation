package org.company.front.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.front.service.ProductService;
import org.company.persistence.model.Article;
import org.company.persistence.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.company.front.util.ValidationUtil.assureIdConsistent;
import static org.company.front.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = Products.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class Products {
    public static final String REST_URL = "/products";

    private final ProductService productService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> create(HttpServletRequest request) throws IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
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
    public Product get(@PathVariable int id) {
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
    public void update(HttpServletRequest request, @PathVariable int id) throws IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
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