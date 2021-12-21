package org.company.product.info.web.controller;

import org.company.product.info.AbstractControllerTest;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.company.product.info.service.ArticleService;
import org.company.product.info.TestUtil;
import org.company.product.info.model.Article;
import org.company.product.info.util.JsonUtil;
import org.company.product.info.util.exception.NotFoundException;

import static org.company.product.info.web.controller.ArticleTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticlesTest extends AbstractControllerTest {

    private static final String REST_URL = Articles.REST_URL + '/';

    @Autowired
    private ArticleService service;

    @Test
    void create() throws Exception {
        Article expected = ArticleTestData.getNew();
        ResultActions action = perform(
                MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(getToFrom(expected)))
        ).andExpect(status().isCreated());

        Article actual = TestUtil.readFromJson(action, Article.class);
        int newId = actual.getId();
        expected.setId(newId);

        ARTICLE_TEST_MATCHER.assertMatch(actual, expected);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ARTICLE_1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ARTICLE_TEST_MATCHER.contentJson(ARTICLE_1));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ARTICLE_TEST_MATCHER.contentJson(ALL_ARTICLES));
    }

    @Test
    void update() throws Exception {
        Article expected = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + ARTICLE_4_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getToFrom(expected))))
                .andExpect(status().isNoContent());

        ARTICLE_TEST_MATCHER.assertMatch(service.get(ARTICLE_4_ID), expected);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + ARTICLE_1_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(ARTICLE_1_ID));
    }
}