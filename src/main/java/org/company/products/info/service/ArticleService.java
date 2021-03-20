package org.company.products.info.service;

import org.company.products.info.model.Article;
import org.company.products.info.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public List<Article> getAll(Optional<String> sortColumn) {
        return sortColumn.map(col -> articleRepository.findAll(Sort.by(Sort.Direction.DESC, col)))
                         .orElse(articleRepository.findAll());
    }

    public void update(Article article) {
        save(article);
    }

    @Transactional
    public void delete(int id) {
        articleRepository.deleteById(id);
    }
}
