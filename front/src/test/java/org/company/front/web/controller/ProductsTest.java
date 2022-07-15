package org.company.front.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.company.front.TestData;
import org.company.front.service.ProductService;
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

@RestClientTest(ProductService.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class ProductsTest {

    @Autowired
    private ProductService client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        // given
        String productOne = """
                {
                  "name":"Новый продукт",
                  "description":"Описание Нового продукта",
                  "implementationCost":300000
                }
                """;
        // when
        this.server.expect(requestTo(ProductService.URI_PRODUCTS))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(productOne, MediaType.APPLICATION_JSON));
        // then
        assertThat(this.client.create(productOne))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(TestData.PRODUCT_NEW);
    }

    @Test
    void get() throws Exception {
        // given
        String productOne = objectMapper.writeValueAsString(TestData.PRODUCT_1);
        // when
        this.server.expect(requestTo(ProductService.URI_PRODUCTS + "/100000"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(productOne, MediaType.APPLICATION_JSON));
        // then
        assertThat(this.client.get(100000))
                .usingRecursiveComparison()
                .isEqualTo(TestData.PRODUCT_1);
    }

    // TODO - Fix Test https://stackoverflow.com/questions/69459620/resttemplate-result-cannot-be-deserialized
//    @Test
//    void getAll() throws Exception {
//        // given
//        String requestQuery = "filterBy=id&filter=100001";
//        String productTwo = objectMapper.writeValueAsString(PRODUCT_2);
//        // when
//        this.server.expect(requestTo(ProductService.URI_PRODUCTS + "?" + requestQuery))
//                .andRespond(withSuccess(productTwo, MediaType.APPLICATION_JSON));
//        // then
//        assertThat(this.client.getAll(requestQuery))
//                .isNotEmpty()
//                .filteredOn(product ->
//                        product.getId().equals(PRODUCT_2.getId())
//                        && product.getName().equals(PRODUCT_2.getName())
//                        && product.getDescription().equals(PRODUCT_2.getDescription())
//                        && product.getImplementationCost() == PRODUCT_2.getImplementationCost()
//                ).hasSize(1);
//    }
}