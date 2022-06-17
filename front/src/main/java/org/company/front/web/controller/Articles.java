package org.company.front.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.front.service.ArticleService;
import org.company.front.to.ArticleTo;
import org.company.persistence.model.Article;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.company.front.util.ValidationUtil.assureIdConsistent;
import static org.company.front.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = Articles.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class Articles {
    public static final String REST_URL = "/articles";

    private final ArticleService articleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> create(@Valid @RequestBody ArticleTo articleTo) {
        checkNew(articleTo);
        log.info("Create new article - {}", articleTo);
        Article created = articleService.create(articleTo);
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
    public void update(@Valid @RequestBody ArticleTo articleTo, @PathVariable int id) {
        assureIdConsistent(articleTo, id);
        log.info("Update article - {}", articleTo);
        articleService.update(articleTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete article with id={}", id);
        articleService.delete(id);
    }
}
