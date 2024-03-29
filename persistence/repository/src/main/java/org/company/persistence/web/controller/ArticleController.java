package org.company.persistence.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.persistence.dto.ArticleDTO;
import org.company.persistence.model.Article;
import org.company.persistence.repository.ArticleRepository;
import org.company.persistence.repository.ProductRepository;
import org.company.persistence.util.JpaSpecificationUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.company.persistence.util.ValidationUtil.*;

@RestController
@RequestMapping(value = ArticleController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class ArticleController {
    public static final String REST_URL = "/articles";

    private final ArticleRepository articleRepository;
    private final ProductRepository productRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Article> create(@Valid @RequestBody ArticleDTO articleDTO) {
        checkNew(articleDTO);
        log.info("Create new article - {}", articleDTO);
        Article created = save(articleFrom(articleDTO), articleDTO.getProductId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource)
                .body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody ArticleDTO articleDTO, @PathVariable int id) {
        assureIdConsistent(articleDTO, id);
        log.info("Update article - {}", articleDTO);
        checkNotFoundWithId(save(articleFrom(articleDTO), articleDTO.getProductId()), articleDTO.getId());
    }

    protected Article save(Article article, int productId) {
        if (!article.isNew() && get(article.getId()) == null) {
            return null;
        }
        article.setProduct(checkNotFoundWithId(productRepository.findById(productId), productId));
        return articleRepository.save(article);
    }

    @GetMapping("/{id}")
    public Article get(@PathVariable int id) {
        log.info("Get article with id={}", id);
        return checkNotFoundWithId(articleRepository.findById(id), id);
    }

    @GetMapping
    public List<Article> getAll(@RequestParam Optional<String> sortBy,
                                @RequestParam Optional<String> filterBy,
                                @RequestParam Optional<String> filter,
                                @RequestParam Optional<LocalDate> fromDate,
                                @RequestParam Optional<LocalDate> toDate)
    {
        log.info("Get all articles{}{}",
                sortBy.isPresent() ? ", sort by " + sortBy : "",
                filterBy.isPresent() ? ", filter by " + filterBy : ""
        );
        if (filterBy.isPresent()) {
            Specification<Article> spec = JpaSpecificationUtil.filterBy(filterBy.get(), filter, fromDate, toDate);
            return sortBy.map(col -> articleRepository.findAll(spec, Sort.by(Sort.Direction.DESC, col)))
                    .orElse(articleRepository.findAll(spec));
        } else
            return sortBy.map(s -> articleRepository.findAll(Sort.by(Sort.Direction.DESC, s)))
                    .orElseGet(articleRepository::findAll);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete article with id={}", id);
        checkNotFoundWithId(articleRepository.delete(id), id);
    }

    private Article articleFrom(ArticleDTO articleTo) {
        return new Article(articleTo.getId(), articleTo.getName(), null, articleTo.getContent(), LocalDate.now());
    }
}
