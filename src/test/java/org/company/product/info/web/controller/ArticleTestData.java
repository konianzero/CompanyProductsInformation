package org.company.product.info.web.controller;

import org.company.product.info.TestMatcher;
import org.company.product.info.model.Article;
import org.company.product.info.to.ArticleTo;

import java.util.List;

import static java.time.LocalDate.now;
import static org.company.product.info.web.controller.ProductTestData.*;
import static org.company.product.info.model.BaseEntity.START_SEQ;

public class ArticleTestData {
    public static final TestMatcher<Article> ARTICLE_TEST_MATCHER = TestMatcher.usingEqualsComparator(Article.class);

    public static final int ARTICLE_1_ID = START_SEQ + 2;
    public static final int ARTICLE_2_ID = START_SEQ + 3;
    public static final int ARTICLE_3_ID = START_SEQ + 4;
    public static final int ARTICLE_4_ID = START_SEQ + 5;

    public static final Article ARTICLE_1 = new Article(ARTICLE_1_ID,
            "Статья-1 про первый продукт",
            PRODUCT_1,
            "Содержание статьи-1 о первом продукте",
            now().minusDays(1));
    public static final Article ARTICLE_2 = new Article(ARTICLE_2_ID,
            "Статья-1 про второй продукт",
            PRODUCT_2,
            "Содержание статьи-1 о втором продукте",
            now().minusDays(1));
    public static final Article ARTICLE_3 = new Article(ARTICLE_3_ID,
            "Статья-2 про первый продукт",
            PRODUCT_1,
            "Содержание статьи-2 о первом продукте",
            now());
    public static final Article ARTICLE_4 = new Article(ARTICLE_4_ID,
            "Статья-2 про второй продукт",
            PRODUCT_2,
            "Содержание статьи-2 о втором продукте",
            now());

    public static final List<Article> ALL_ARTICLES = List.of(ARTICLE_1, ARTICLE_2, ARTICLE_3, ARTICLE_4);

    public static Article getNew() {
        return new Article(null,
                "Статья про НОВЫЙ продукт",
                PRODUCT_2,
                "Содержание статьи о НОВОМ продукте",
                now());
    }

    public static Article getUpdated() {
        return new Article(ARTICLE_4_ID,
                "Обновленная статья про второй продукт",
                PRODUCT_2,
                "Обновленное содержание статьи-2 о втором продукте",
                now());
    }

    public static ArticleTo getToFrom(Article article) {
        return new ArticleTo(article.getId(),
                             article.getName(),
                             article.getProduct().getId(),
                             article.getContent()
        );
    }
}
