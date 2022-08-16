package org.company.front;

import lombok.experimental.UtilityClass;
import org.company.front.service.jms.to.ProductInfo;
import org.company.front.web.view.ProductView;
import org.company.persistence.model.Article;
import org.company.persistence.model.Product;

import java.util.List;

import static java.time.LocalDate.now;

@UtilityClass
public class TestData {
    public static final Product PRODUCT_1 = new Product(
            100000,
            "Продукт номер один",
            "Описание первого продукта",
            200000);
    public static final Product PRODUCT_2 = new Product(
            100001,
            "Продукт номер два",
            "Описание второго продукта",
            250000);
    public static final Product PRODUCT_NEW = new Product(
            null,
            "Новый продукт",
            "Описание Нового продукта",
            300000);

    public static final Article ARTICLE_1 = new Article(
            100002,
        "Статья-1 про первый продукт",
            new Product(100000, "Продукт номер один", "Описание первого продукта", 200000),
        "Содержание статьи-1 о первом продукте",
        now());
    public static final Article ARTICLE_2 = new Article(
            100003,
            "Статья-1 про второй продукт",
            new Product(100001, "Продукт номер два", "Описание второго продукта", 250000),
            "Содержание статьи-1 о втором продукте",
            now());
    public static final Article ARTICLE_NEW = new Article(
            100004,
            "Статья про НОВЫЙ продукт",
            null,
            "Содержание статьи о НОВОМ продукте",
            null);
    public static final ProductView PRODUCT_1_VIEW = new ProductView(PRODUCT_1, new ProductInfo());
    public static final List<ProductView> PRODUCTS_VIEW = List.of(new ProductView(PRODUCT_2, new ProductInfo()));
}
