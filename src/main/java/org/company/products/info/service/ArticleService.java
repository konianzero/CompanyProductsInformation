package org.company.products.info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.company.products.info.model.Article;
import org.company.products.info.repository.ArticleRepository;
import org.company.products.info.util.Util;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article create(Article article) {
        return save(article);
    }

    @Transactional
    protected Article save(Article article) {
        return articleRepository.save(article);
    }

    public Article get(int id) {
        return articleRepository.findById(id).get();
    }

    public List<Article> getAll(Optional<String> sortColumn,
                                Optional<String> filterColumn,
                                Optional<String> filter,
                                Optional<LocalDate> fromDate,
                                Optional<LocalDate> toDate)
    {
        if (filterColumn.isPresent()) {
            Specification<Article> spec = Util.filterBy(filterColumn.get(), filter, fromDate, toDate);
            return sortColumn.map(col -> articleRepository.findAll(spec, Sort.by(Sort.Direction.DESC, col)))
                             .orElse(articleRepository.findAll(spec));
        } else
            return sortColumn.map(s -> articleRepository.findAll(Sort.by(Sort.Direction.DESC, s)))
                             .orElseGet(articleRepository::findAll);
    }

    public void update(Article article) {
        save(article);
    }

    @Transactional
    public void delete(int id) {
        articleRepository.deleteById(id);
    }
}
