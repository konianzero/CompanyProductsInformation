package org.company.products.info.service;

import org.company.products.info.model.Article;
import org.company.products.info.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    public void update(Article article) {
        save(article);
    }

    @Transactional
    public void delete(int id) {
        articleRepository.deleteById(id);
    }
}
