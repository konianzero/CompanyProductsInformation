package org.company.persistence.web.controller;

import org.company.persistence.AbstractControllerTest;
import org.company.persistence.TestUtil;
import org.company.persistence.model.Product;
import org.company.persistence.repository.ProductRepository;
import org.company.persistence.util.JsonUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import static org.company.persistence.web.controller.ArticleTestData.*;
import static org.company.persistence.web.controller.ProductTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProductController.REST_URL + '/';

    @Autowired
    private ProductController productController;

    @BeforeAll
    public static void init() {
        PRODUCT_1.setArticles(List.of(ARTICLE_1, ARTICLE_3));
        PRODUCT_2.setArticles(List.of(ARTICLE_2, ARTICLE_4));
    }

    @Test
    void create() throws Exception {
        Product expected = ProductTestData.getNew();
        ResultActions action = perform(
                MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(getDtoFrom(expected)))
        ).andExpect(status().isCreated());

        Product actual = TestUtil.readFromJson(action, Product.class);
        int newId = actual.getId();
        expected.setId(newId);

        PRODUCT_TEST_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + PRODUCT_1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PRODUCT_TEST_MATCHER.contentJson(PRODUCT_1));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PRODUCT_TEST_MATCHER.contentJson(ALL_PRODUCTS));
    }

    @Test
    void update() throws Exception {
        Product expected = ProductTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + PRODUCT_2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isNoContent());

        PRODUCT_TEST_MATCHER.assertMatch(productController.get(PRODUCT_2_ID), expected);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + PRODUCT_1_ID))
                .andExpect(status().isNoContent());
        assertThrows(EntityNotFoundException.class, () -> productController.get(PRODUCT_1_ID));
    }
}