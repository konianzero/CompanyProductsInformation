package org.company.front.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.company.cache.service.HazelcastCache;
import org.company.front.TestData;
import org.company.front.service.ProductService;
import org.company.cache.external.jms.to.ProductInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.company.front.TestData.PRODUCTS_VIEW;
import static org.company.front.TestData.PRODUCT_2;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ActiveProfiles("test")
@RestClientTest(ProductService.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class ProductsTest {

    @Autowired(required=true)
    private ProductService client;
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HazelcastCache hazelcastCache;

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
        String productOne = objectMapper.writeValueAsString(TestData.PRODUCT_1_VIEW);
        // when
        Mockito.when(hazelcastCache.getData(any())).thenReturn(List.of(new ProductInfo()));
        this.server.expect(requestTo(ProductService.URI_PRODUCTS + "/100000"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(productOne, MediaType.APPLICATION_JSON));
        // then
        assertThat(this.client.get(100000))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(TestData.PRODUCT_1_VIEW);
    }

    @Test
    void getAll() throws Exception {
        // given
        String requestQuery = "filterBy=id&filter=100001";
        String products = objectMapper.writeValueAsString(PRODUCTS_VIEW);
        // when
        List<ProductInfo> list = List.of(new ProductInfo());
        Mockito.when(hazelcastCache.getData(any())).thenReturn(list);
        this.server.expect(requestTo(ProductService.URI_PRODUCTS + "?" + requestQuery))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(products, MediaType.APPLICATION_JSON));
        // then
        assertThat(this.client.getAll(requestQuery))
                .isNotNull()
                .isNotEmpty()
                .filteredOn(productView ->
                        productView.getId().equals(PRODUCT_2.getId())
                        && productView.getName().equals(PRODUCT_2.getName())
                        && productView.getDescription().equals(PRODUCT_2.getDescription())
                        && productView.getImplementationCost() == PRODUCT_2.getImplementationCost()
                )
                .hasSize(1);
    }
}