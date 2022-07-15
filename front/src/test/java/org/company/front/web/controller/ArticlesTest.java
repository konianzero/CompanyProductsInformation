package org.company.front.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.company.front.TestData;
import org.company.front.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ArticleService.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class ArticlesTest {

    @Autowired
    private ArticleService client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        // given
        String articleOne = """
                {
                  "name":"Статья про НОВЫЙ продукт",
                  "productId":100000,
                  "content":"Содержание статьи о НОВОМ продукте"
                }
                """;
        // when
        this.server.expect(requestTo(ArticleService.URI_ARTICLES))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(articleOne, MediaType.APPLICATION_JSON));
        // then
        assertThat(this.client.create(articleOne))
                .usingRecursiveComparison()
                .ignoringFields("id", "product", "date")
                .isEqualTo(TestData.ARTICLE_NEW);
    }

    @Test
    void get() throws Exception {
        // given
        String articleOne = objectMapper.writeValueAsString(TestData.ARTICLE_1);
        // when
        this.server.expect(requestTo(ArticleService.URI_ARTICLES + "/100002"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(articleOne, MediaType.APPLICATION_JSON));
        // then
        assertThat(this.client.get(100002))
                .usingRecursiveComparison()
                .isEqualTo(TestData.ARTICLE_1);
    }

    // TODO - Fix Test https://stackoverflow.com/questions/69459620/resttemplate-result-cannot-be-deserialized
//    @Test
//    void getAll() throws Exception {
//        // given
//        String requestQuery = "filterBy=id&filter=100003";
//        String articleTwo = objectMapper.writeValueAsString(ARTICLE_2);
//        // when
//        this.server.expect(requestTo(ArticleService.URI_ARTICLES + "?" + requestQuery))
//                .andRespond(withSuccess(articleTwo, MediaType.APPLICATION_JSON));
//        // then
//        assertThat(this.client.getAll(requestQuery))
//                .isNotEmpty()
//                .filteredOn(article ->
//                        article.getId().equals(ARTICLE_2.getId())
//                        && article.getName().equals(ARTICLE_2.getName())
//                        && article.getContent().equals(ARTICLE_2.getContent())
//                ).hasSize(1);
//    }
}


























