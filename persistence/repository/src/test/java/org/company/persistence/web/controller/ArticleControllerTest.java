package org.company.persistence.web.controller;

import org.company.persistence.AbstractControllerTest;
import org.company.persistence.TestUtil;
import org.company.persistence.model.Article;
import org.company.persistence.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;

import static org.company.persistence.web.controller.ArticleTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ArticleController.REST_URL + '/';

    @Autowired
    private ArticleController articleController;

    @Test
    void create() throws Exception {
        Article expected = ArticleTestData.getNew();
        ResultActions action = perform(
                MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(getDtoFrom(expected)))
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
                .content(JsonUtil.writeValue(getDtoFrom(expected))))
                .andExpect(status().isNoContent());

        ARTICLE_TEST_MATCHER.assertMatch(articleController.get(ARTICLE_4_ID), expected);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + ARTICLE_1_ID))
                .andExpect(status().isNoContent());
        assertThrows(EntityNotFoundException.class, () -> articleController.get(ARTICLE_1_ID));
    }
}