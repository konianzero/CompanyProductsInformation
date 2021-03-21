package org.company.products.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.company.products.info.model.Article;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Article a WHERE a.id = ?1")
    int delete(int id);
}
