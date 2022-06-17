package org.company.front.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.front.service.ProductService;
import org.company.persistence.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        checkNew(product);
        log.info("Create new product - {}", product);
        Product created = productService.create(product);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
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
    public List<Product> getAll(@RequestParam Optional<String> sortBy,
                                @RequestParam Optional<String> filterBy,
                                @RequestParam Optional<String> filter,
                                @RequestParam Optional<Integer> costFrom,
                                @RequestParam Optional<Integer> costTo)
    {
        log.info("Get all products{}{}",
                sortBy.isPresent() ? ", sort by " + sortBy : "",
                filterBy.isPresent() ? ", filter by " + filterBy : ""
        );
        return productService.getAll(sortBy, filterBy, filter, costFrom, costTo);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Product product, @PathVariable int id) {
        assureIdConsistent(product, id);
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