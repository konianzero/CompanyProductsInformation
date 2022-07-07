package org.company.persistence.web.controller;


import org.company.persistence.TestMatcher;
import org.company.persistence.dto.ProductDTO;
import org.company.persistence.model.Product;

import java.util.List;

import static org.company.persistence.model.BaseEntity.START_SEQ;


public class ProductTestData {
    public static final TestMatcher<Product> PRODUCT_TEST_MATCHER = TestMatcher.usingEqualsComparator(Product.class);

    public static final int PRODUCT_1_ID = START_SEQ;
    public static final int PRODUCT_2_ID = START_SEQ + 1;

    public static final Product PRODUCT_1 = new Product(PRODUCT_1_ID, "Продукт номер один", "Описание первого продукта", 200000);
    public static final Product PRODUCT_2 = new Product(PRODUCT_2_ID, "Продукт номер два", "Описание второго продукта", 250000);

    public static final List<Product> ALL_PRODUCTS = List.of(PRODUCT_1, PRODUCT_2);

    public static Product getNew() {
        return new Product(null, "Новый продукт", "Описание Нового продукта", 300000);
    }

    public static Product getUpdated() {
        return new Product(PRODUCT_2_ID, "Обновленный продукт", "Описание обновленного продукта", 450000);
    }

    public static ProductDTO getDtoFrom(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getImplementationCost());
    }
}
