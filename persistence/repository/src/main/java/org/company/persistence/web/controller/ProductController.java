package org.company.persistence.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.persistence.dto.ProductDTO;
import org.company.persistence.model.Product;
import org.company.persistence.repository.ProductRepository;
import org.company.persistence.util.JpaSpecificationUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.company.persistence.util.ValidationUtil.*;

@RestController
@RequestMapping(value = ProductController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    public static final String REST_URL = "/products";

    private final ProductRepository productRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> create(@Valid @RequestBody ProductDTO productDTO) {
        checkNew(productDTO);
        log.info("Create new product - {}", productDTO);
        Product created = save(productFrom(productDTO));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource)
                .body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody ProductDTO productDTO, @PathVariable int id) {
        assureIdConsistent(productDTO, id);
        log.info("Update product - {}", productDTO);
        checkNotFoundWithId(save(productFrom(productDTO)), productDTO.getId());
    }

    protected Product save(Product product) {
        return productRepository.save(product);
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable int id) {
        log.info("Get product with id={}", id);
        return checkNotFoundWithId(productRepository.findById(id), id);
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
        if (filterBy.isPresent()) {
            Specification<Product> spec = JpaSpecificationUtil.filterBy(filterBy.get(), filter, costFrom, costTo);
            return sortBy.map(col -> productRepository.findAll(spec, Sort.by(Sort.Direction.DESC, col)))
                    .orElse(productRepository.findAll(spec));
        } else
            return sortBy.map(s -> productRepository.findAll(Sort.by(Sort.Direction.DESC, s)))
                    .orElseGet(productRepository::findAll);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete product with id={}", id);
        checkNotFoundWithId(productRepository.delete(id), id);
    }

    private Product productFrom(ProductDTO productTo) {
        return new Product(productTo.getId(), productTo.getName(), productTo.getDescription(), productTo.getImplementationCost());
    }
}
