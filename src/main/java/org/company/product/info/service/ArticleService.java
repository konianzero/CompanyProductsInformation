package org.company.product.info.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.company.product.info.model.Article;
import org.company.product.info.to.ArticleTo;
import org.company.product.info.repository.ProductRepository;
import org.company.product.info.repository.ArticleRepository;
import org.company.product.info.util.JpaSpecificationUtil;

import static org.company.product.info.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Article create(ArticleTo articleTo) {
        Article newArticle = new Article(articleTo);
        return save(newArticle, articleTo.getProductId());
    }

    protected Article save(Article article, int productId) {
        if (!article.isNew() && get(article.getId()) == null) {
            return null;
        }
        article.setProduct(checkNotFoundWithId(productRepository.findById(productId), productId));
        return articleRepository.save(article);
    }

    public Article get(int id) {
        return checkNotFoundWithId(articleRepository.findById(id), id);
    }

    public List<Article> getAll(Optional<String> sortColumn,
                                Optional<String> filterColumn,
                                Optional<String> filter,
                                Optional<LocalDate> fromDate,
                                Optional<LocalDate> toDate)
    {
        if (filterColumn.isPresent()) {
            Specification<Article> spec = JpaSpecificationUtil.filterBy(filterColumn.get(), filter, fromDate, toDate);
            return sortColumn.map(col -> articleRepository.findAll(spec, Sort.by(Sort.Direction.DESC, col)))
                             .orElse(articleRepository.findAll(spec));
        } else
            return sortColumn.map(s -> articleRepository.findAll(Sort.by(Sort.Direction.DESC, s)))
                             .orElseGet(articleRepository::findAll);
    }

    @Transactional
    public void update(ArticleTo articleTo) {
        Article updateArticle = new Article(articleTo);
        checkNotFoundWithId(save(updateArticle, articleTo.getProductId()), articleTo.getId());
    }

    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(articleRepository.delete(id), id);
    }
}
