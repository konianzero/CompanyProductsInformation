package org.company.products.info.web.controller;

import com.fasterxml.jackson.annotation.JsonView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.company.products.info.Views;
import org.company.products.info.model.Article;
import org.company.products.info.service.ArticleService;

import javax.validation.Valid;

import static org.company.products.info.util.ValidationUtil.assureIdConsistent;
import static org.company.products.info.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = Articles.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class Articles {
    public static final String REST_URL = "/articles";
    private static final Logger log = LoggerFactory.getLogger(Articles.class);

    private final ArticleService articleService;

    public Articles(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> create(@Valid @RequestBody Article article) {
        checkNew(article);
        log.info("Create new article - {}", article);
        Article created = articleService.create(article);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                          .path(REST_URL + "/{id}")
                                                          .buildAndExpand(created.getId())
                                                          .toUri();
        return ResponseEntity.created(uriOfNewResource)
                             .body(article);
    }

    @JsonView(Views.ArticleView.class)
    @GetMapping("/{id}")
    public Article get(@PathVariable int id) {
        log.info("Get article with id={}", id);
        return articleService.get(id);
    }

    @JsonView(Views.ArticleView.class)
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
        return articleService.getAll(sortBy, filterBy, filter, fromDate, toDate);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Article article, @PathVariable int id) {
        assureIdConsistent(article, id);
        log.info("Update article - {}", article);
        articleService.update(article);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete article with id={}", id);
        articleService.delete(id);
    }
}
