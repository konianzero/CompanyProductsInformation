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

import org.company.products.info.model.Article;
import org.company.products.info.service.ArticleService;

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
    public ResponseEntity<Article> createWithLocation(@RequestBody Article article) {
        log.info("Create new article - {}", article);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                          .path(REST_URL + "/{id}")
                                                          .buildAndExpand(article.getId())
                                                          .toUri();
        return ResponseEntity.created(uriOfNewResource)
                             .body(article);
    }

    @GetMapping("/{id}")
    public Article get(@PathVariable int id) {
        log.info("Get article with id={}", id);
        return articleService.get(id);
    }

    @GetMapping
    public List<Article> getAll() {
        log.info("Get all articles");
        return articleService.getAll();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Article article) {
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
