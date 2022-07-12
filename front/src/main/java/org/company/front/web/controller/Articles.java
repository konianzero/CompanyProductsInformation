package org.company.front.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.front.service.ArticleService;
import org.company.persistence.model.Article;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = Articles.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class Articles {
    public static final String REST_URL = "/articles";

    private final ArticleService articleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> create(HttpServletRequest request) throws IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        log.info("Create new article - {}", requestBody);
        Article created = articleService.create(requestBody);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource)
                .body(created);
    }

    @GetMapping("/{id}")
    public Article get(@PathVariable int id) {
        log.info("Get article with id={}", id);
        return articleService.get(id);
    }

    @GetMapping
    public List<Article> getAll(HttpServletRequest request) {
        String requestQuery = URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8);
        log.info("Get all articles with query - {}", requestQuery);
        return articleService.getAll(requestQuery);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(HttpServletRequest request, @PathVariable int id) throws IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        log.info("Update article - {}", requestBody);
        articleService.update(requestBody, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete article with id={}", id);
        articleService.delete(id);
    }
}
